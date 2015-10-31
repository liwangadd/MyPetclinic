package com.liwang.samples.repository;

import com.liwang.samples.model.Vet;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * Created by Nikolas on 2015/10/25.
 */
public interface VetRepository {

    Collection<Vet> findAll() throws DataAccessException;

}
