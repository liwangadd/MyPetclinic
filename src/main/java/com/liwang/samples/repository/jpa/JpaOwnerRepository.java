package com.liwang.samples.repository.jpa;

import com.liwang.samples.model.Owner;
import com.liwang.samples.repository.OwnerRepository;
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
public class JpaOwnerRepository implements OwnerRepository {

    @PersistenceContext
    private EntityManager em;

    public Collection<Owner> findByLastName(String lastName) throws DataAccessException {
        Query query = this.em.createQuery("FROM Owner owner WHERE owner.last_name LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    public Owner findById(int id) throws DataAccessException {
        Query query = this.em.createQuery("FROM Owner owner WHERE owner.id =:id");
        query.setParameter("id", id);
        return (Owner) query.getSingleResult();
    }

    public void save(Owner owner) throws DataAccessException {
        if (owner.isNew()) {
            em.persist(owner);
        } else {
            em.merge(owner);
        }
    }
}
