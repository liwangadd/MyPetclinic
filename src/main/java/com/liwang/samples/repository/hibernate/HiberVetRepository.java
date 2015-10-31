package com.liwang.samples.repository.hibernate;

import com.liwang.samples.model.Vet;
import com.liwang.samples.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Nikolas on 2015/10/31.
 */
@Repository
public class HiberVetRepository implements VetRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Collection<Vet> findAll() throws DataAccessException {
        return hibernateTemplate.loadAll(Vet.class);
    }

}
