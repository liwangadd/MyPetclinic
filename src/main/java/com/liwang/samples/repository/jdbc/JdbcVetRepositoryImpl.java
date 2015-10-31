package com.liwang.samples.repository.jdbc;

import com.liwang.samples.model.Specialty;
import com.liwang.samples.model.Vet;
import com.liwang.samples.repository.VetRepository;
import com.liwang.samples.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nikolas on 2015/10/25.
 */
@Repository
public class JdbcVetRepositoryImpl implements VetRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Collection<Vet> findAll() throws DataAccessException {
        List<Vet> vets = new ArrayList<Vet>();
        vets.addAll(jdbcTemplate.query("SELECT id, first_name, last_name FROM vets ORDER BY last_name,first_name",
                BeanPropertyRowMapper.newInstance(Vet.class)));
        final List<Specialty> specialties = jdbcTemplate.query("SELECT id, name FROM specialties",
                BeanPropertyRowMapper.newInstance(Specialty.class));
        for (Vet vet : vets) {
            final List<Integer> vetSpecialtiesIds = jdbcTemplate.query("SELECT specialty_id FROM vet_specialties WHERE vet_id=?",
                    new RowMapper<Integer>() {
                        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                            return Integer.valueOf(resultSet.getInt(1));
                        }
                    }, vet.getId().intValue());
            for (int specialtyId : vetSpecialtiesIds) {
                Specialty specialty = EntityUtil.getById(specialties, Specialty.class, specialtyId);
                vet.addSpecialty(specialty);
            }
        }
        return vets;
    }
}
