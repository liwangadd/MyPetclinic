package com.liwang.samples.repository.jpa;

import com.liwang.samples.model.Visit;
import com.liwang.samples.repository.VisitRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by liwang on 15-10-31.
 */
@Repository
public class JpaVisitRepository implements VisitRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Visit visit) throws DataAccessException {
        if (visit.isNew()) {
            em.persist(visit);
        } else {
            em.merge(visit);
        }
    }

    public List<Visit> findByPetId(int id) throws DataAccessException {
        Query query = em.createQuery("FROM Visit v WHERE v.pet_id = ?");
        query.setParameter(0, id);
        return query.getResultList();
    }
}
