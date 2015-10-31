package com.liwang.samples.repository.jdbc;

import com.liwang.samples.model.Visit;
import com.liwang.samples.repository.VisitRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikolas on 2015/10/25.
 */
@Repository
public class JdbcVisitRepositoryImpl implements VisitRepository {

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcVisitRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("visits")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Visit visit) throws DataAccessException {
        if (visit.isNew()) {
            Number number = this.simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(visit));
            visit.setId(number.intValue());
        }
    }

    public void deletePet(int id) throws DataAccessException {
        this.jdbcTemplate.update("DELETE FROM pets WHERE id=?", id);
    }

    //@Override
    public List<Visit> findByPetId(int petId) {
        final List<Visit> visits = this.jdbcTemplate.query(
                "SELECT id, visit_date, description FROM visits WHERE pet_id=?",
                new BeanPropertyRowMapper<Visit>() {
                    @Override
                    public Visit mapRow(ResultSet rs, int row) throws SQLException {
                        Visit visit = new Visit();
                        visit.setId(rs.getInt("id"));
                        Date visitDate = rs.getDate("visit_date");
                        visit.setDate(new DateTime(visitDate));
                        visit.setDescription(rs.getString("description"));
                        return visit;
                    }
                },
                petId);
        return visits;
    }

    private MapSqlParameterSource createVisitParameterSource(Visit visit) {
        return new MapSqlParameterSource()
                .addValue("id", visit.getId())
                .addValue("visit_date", visit.getDate().toDate())
                .addValue("description", visit.getDescription())
                .addValue("pet_id", visit.getPet().getId());
    }
}
