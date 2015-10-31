package com.liwang.samples.repository.jdbc;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nikolas on 2015/10/25.
 */
class JdbcPetRowMapper extends BeanPropertyRowMapper<JdbcPet> {

    @Override
    public JdbcPet mapRow(ResultSet rs, int rownum) throws SQLException {
        JdbcPet pet = new JdbcPet();
        pet.setId(rs.getInt("id"));
        pet.setName(rs.getString("name"));
        Date birthDate = rs.getDate("birth_date");
        pet.setBirthDate(new DateTime(birthDate));
        pet.setTypeId(rs.getInt("type_id"));
        pet.setOwnerId(rs.getInt("owner_id"));
        return pet;
    }
}
