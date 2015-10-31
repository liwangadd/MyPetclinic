package com.liwang.samples.util;

import com.liwang.samples.model.BaseEntity;
import com.liwang.samples.model.NamedEntity;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.util.Collection;

/**
 * Created by Nikolas on 2015/10/24.
 */
public class EntityUtil {

    public static <T extends NamedEntity> T getById(Collection<T> entities, Class<T> entityClass, int entityId)
            throws ObjectRetrievalFailureException {
        for (T entity : entities) {
            if (entity.getId().intValue() == entityId && entityClass.isInstance(entity)) {
                return entity;
            }
        }
        throw new ObjectRetrievalFailureException(entityClass, entityId);
    }

}
