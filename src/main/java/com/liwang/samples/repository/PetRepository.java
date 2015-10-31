package com.liwang.samples.repository;

import com.liwang.samples.model.Pet;
import com.liwang.samples.model.PetType;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by Nikolas on 2015/10/25.
 */
public interface PetRepository {

    List<PetType> findPetTypes() throws DataAccessException;

    Pet findById(int id) throws DataAccessException;

    void save(Pet pet) throws DataAccessException;

}
