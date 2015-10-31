package com.liwang.samples.repository.jdbc;

import com.liwang.samples.model.Owner;
import com.liwang.samples.model.PetType;
import com.liwang.samples.model.Visit;
import com.liwang.samples.repository.OwnerRepository;
import com.liwang.samples.repository.VisitRepository;
import com.liwang.samples.util.EntityUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolas on 2015/10/25.
 */
@Repository
public class JdbcOwnerRepositoryImpl implements OwnerRepository {

    private VisitRepository visitRepository;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcOwnerRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   VisitRepository visitRepository) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("owners")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.visitRepository = visitRepository;
    }

    public Collection<Owner> findByLastName(String lastName) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lastName", lastName + "%");
        List<Owner> owners = this.namedParameterJdbcTemplate.query(
                "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE last_name like :lastName",
                params,
                new BeanPropertyRowMapper<Owner>(Owner.class)
        );
        loadOwnersPetsAndVisits(owners);
        return owners;
    }

    public Owner findById(int id) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        System.out.println(id);
        Owner owner = namedParameterJdbcTemplate.queryForObject("SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE id= :id",
                params, BeanPropertyRowMapper.newInstance(Owner.class));
        loadPetsAndVisits(owner);
        return owner;
    }

    public void loadPetsAndVisits(final Owner owner) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", owner.getId().intValue());
        final List<JdbcPet> pets = this.namedParameterJdbcTemplate.query(
                "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE owner_id=:id",
                params,
                new JdbcPetRowMapper()
        );
        for (JdbcPet pet : pets) {
            owner.addPet(pet);
            // Pet types have not been loaded at this stage. They are loaded separately
            pet.setType(EntityUtil.getById(getPetTypes(), PetType.class, pet.getTypeId()));
            List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
            for (Visit visit : visits) {
                pet.addVisit(visit);
            }
        }
    }

    public void save(Owner owner) throws DataAccessException {
        System.out.println(owner);
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(owner);
        if (owner.isNew()) {
            Number number = simpleJdbcInsert.executeAndReturnKey(source);
            owner.setId(number.intValue());
        } else {
            namedParameterJdbcTemplate.update("UPDATE owners SET first_name=:first_name, last_name=:last_name, address=:address, city=:city, telephone=:telephone WHERE id=:id",
                    source);
        }
    }

    public Collection<PetType> getPetTypes() throws DataAccessException {
        return this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM types ORDER BY name", new HashMap<String, Object>(),
                BeanPropertyRowMapper.newInstance(PetType.class));
    }

    private void loadOwnersPetsAndVisits(List<Owner> owners) {
        for (Owner owner : owners) {
            loadPetsAndVisits(owner);
        }
    }
}
