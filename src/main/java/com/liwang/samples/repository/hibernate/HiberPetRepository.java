package com.liwang.samples.repository.hibernate;

import com.liwang.samples.model.Pet;
import com.liwang.samples.model.PetType;
import com.liwang.samples.repository.PetRepository;
import org.hibernate.validator.xml.GetterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nikolas on 2015/10/31.
 */
@Repository
public class HiberPetRepository implements PetRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public List<PetType> findPetTypes() throws DataAccessException {
        List<PetType> petTypes = hibernateTemplate.loadAll(PetType.class);
        return petTypes;
    }

    public Pet findById(int id) throws DataAccessException {
        Pet pet = hibernateTemplate.get(Pet.class, id);
        return pet;
    }

    public void save(Pet pet) throws DataAccessException {
        if(pet.isNew()) {
            hibernateTemplate.persist(pet);
        }else{
            hibernateTemplate.update(pet);
        }
    }
}
