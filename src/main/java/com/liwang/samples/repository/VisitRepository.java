package com.liwang.samples.repository;

import com.liwang.samples.model.Visit;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by Nikolas on 2015/10/25.
 */
public interface VisitRepository {

    void save(Visit visit) throws DataAccessException;

    List<Visit> findByPetId(int id) throws DataAccessException;

}
