package com.liwang.samples.repository;

import com.liwang.samples.model.Owner;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * Created by Nikolas on 2015/10/25.
 */
public interface OwnerRepository {

    Collection<Owner> findByLastName(String lastName) throws DataAccessException;

    Owner findById(int id) throws DataAccessException;

    void save(Owner owner) throws DataAccessException;

}
