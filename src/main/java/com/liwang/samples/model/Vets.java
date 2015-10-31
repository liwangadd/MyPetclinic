package com.liwang.samples.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolas on 2015/10/24.
 */
@XmlRootElement
public class Vets {

    private List<Vet> vets;

    @XmlElement
    public List<Vet> getVetList() {
        if (vets == null) {
            vets=new ArrayList<Vet>();
        }
        return vets;
    }

    @Override
    public String toString() {
        return "Vets{" +
                "vets=" + vets +
                '}';
    }
}
