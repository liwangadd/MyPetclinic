package com.liwang.samples.repository.jpa;

import com.liwang.samples.model.Vet;
import com.liwang.samples.repository.VetRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Created by liwang on 15-10-31.
 */
@Repository
public class JpaVetRepository implements VetRepository {

    @PersistenceContext
    private EntityManager em;

    public Collection<Vet> findAll() throws DataAccessException {
        Query query = em.createQuery("FROM vet");
        return query.getResultList();
    }
}
