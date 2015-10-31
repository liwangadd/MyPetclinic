package com.liwang.samples.repository.jdbc;

import com.liwang.samples.model.Owner;
import com.liwang.samples.model.Pet;
import com.liwang.samples.model.PetType;
import com.liwang.samples.model.Visit;
import com.liwang.samples.repository.OwnerRepository;
import com.liwang.samples.repository.PetRepository;
import com.liwang.samples.repository.VisitRepository;
import com.liwang.samples.util.EntityUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolas on 2015/10/25.
 */
@Repository
public class JdbcPetRepositoryImpl implements PetRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private OwnerRepository ownerRepository;

    private VisitRepository visitRepository;

    @Autowired
    public JdbcPetRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                 OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("pets")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<PetType> findPetTypes() throws DataAccessException {
        List<PetType> petTypes = namedParameterJdbcTemplate.query("SELECT id, name FROM types ORDER BY name",
                new HashMap<String, Object>(), BeanPropertyRowMapper.newInstance(PetType.class));
        return petTypes;
    }

    public Pet findById(int id) throws DataAccessException {
        JdbcPet pet;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        pet = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE id=:id",
                params,
                new JdbcPetRowMapper());
        Owner owner = this.ownerRepository.findById(pet.getOwnerId());
        owner.addPet(pet);
        pet.setType(EntityUtil.getById(findPetTypes(), PetType.class, pet.getTypeId()));

        List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
        for (Visit visit : visits) {
            pet.addVisit(visit);
        }
        return pet;
    }

    public void save(Pet pet) throws DataAccessException {
        if (pet.isNew()) {
            Number number = simpleJdbcInsert.executeAndReturnKey(createPetParameterSource(pet));
            pet.setId(number.intValue());
        } else {
            this.namedParameterJdbcTemplate.update("UPDATE pets SET name=:name, birth_date=:birth_date, type_id=:type_id, " +
                    "owner_id=:owner_id WHERE id=:id", createPetParameterSource(pet));
        }
    }

    private MapSqlParameterSource createPetParameterSource(Pet pet) {
        return new MapSqlParameterSource()
                .addValue("id", pet.getId())
                .addValue("name", pet.getName())
                .addValue("birth_date", pet.getBirthDate().toLocalDate())
                .addValue("type_id", pet.getType().getId())
                .addValue("owner_id", pet.getOwner().getId());
    }
}
