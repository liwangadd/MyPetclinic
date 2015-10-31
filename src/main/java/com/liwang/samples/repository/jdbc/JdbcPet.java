package com.liwang.samples.repository.jdbc;

import com.liwang.samples.model.Pet;

/**
 * Created by Nikolas on 2015/10/25.
 */
public class JdbcPet extends Pet {

    private int typeId;

    private int ownerId;


    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

}
