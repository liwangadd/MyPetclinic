package com.liwang.samples.repository.jpa;

import com.liwang.samples.model.Pet;
import com.liwang.samples.model.PetType;
import com.liwang.samples.repository.PetRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by liwang on 15-10-31.
 */
@Repository
public class JpaPetRepository implements PetRepository {

    @PersistenceContext
    private EntityManager em;

    public List<PetType> findPetTypes() throws DataAccessException {
        Query query = em.createQuery("FROM PetTypes");
        return query.getResultList();
    }

    public Pet findById(int id) throws DataAccessException {
        return em.find(Pet.class, id);
    }

    public void save(Pet pet) throws DataAccessException {
        if(pet.isNew()){
            em.persist(pet);
        }else{
            em.merge(pet);
        }
    }
}
