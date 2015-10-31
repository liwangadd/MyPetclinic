package com.liwang.samples.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Nikolas on 2015/10/24.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
