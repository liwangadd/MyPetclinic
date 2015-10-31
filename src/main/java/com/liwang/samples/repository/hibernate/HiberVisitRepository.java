package com.liwang.samples.repository.hibernate;

import com.liwang.samples.model.Visit;
import com.liwang.samples.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nikolas on 2015/10/31.
 */
@Repository
public class HiberVisitRepository implements VisitRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public void save(Visit visit) throws DataAccessException {
        if (visit.isNew()) {
            hibernateTemplate.persist(visit);
        } else {
            hibernateTemplate.update(visit);
        }
    }

    public List<Visit> findByPetId(int id) throws DataAccessException {
        List<Visit> visits = (List<Visit>) hibernateTemplate.find("from Visit v where v.pet_id = ?", id);
        return visits;
    }
}
