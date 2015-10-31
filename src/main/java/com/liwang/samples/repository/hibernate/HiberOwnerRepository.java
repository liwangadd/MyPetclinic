package com.liwang.samples.repository.hibernate;

import com.liwang.samples.model.Owner;
import com.liwang.samples.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Nikolas on 2015/10/31.
 */
@Repository
public class HiberOwnerRepository implements OwnerRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Collection<Owner> findByLastName(String lastName) throws DataAccessException {
        List<Owner> owners = (List<Owner>) hibernateTemplate.find("from Owner o where o.last_name like ?", lastName + "%");
        System.out.println(owners);
        return owners;
    }

    public Owner findById(int id) throws DataAccessException {
        return hibernateTemplate.get(Owner.class, id);
    }

    public void save(Owner owner) throws DataAccessException {
        if (owner.isNew()) {
            hibernateTemplate.save(owner);
        } else {
            hibernateTemplate.update(owner);
        }
    }
}
