package com.liwang.samples.web;

import com.liwang.samples.model.PetType;
import com.liwang.samples.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by Nikolas on 2015/10/28.
 */
public class PetTypeFormatter implements Formatter<PetType> {

    @Autowired
    private ClinicService clinicService;

    public PetType parse(String s, Locale locale) throws ParseException {
        Collection<PetType> findPetTypes = clinicService.findPetTypes();
        for (PetType type : findPetTypes) {
            if (type.getName().equals(s)) {
                return type;
            }
        }
        throw new ParseException("type not found: " + s, 0);
    }

    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

}
