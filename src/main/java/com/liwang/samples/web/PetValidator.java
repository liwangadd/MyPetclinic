package com.liwang.samples.web;

import com.liwang.samples.model.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by Nikolas on 2015/10/27.
 */
public class PetValidator {

    public void validate(Pet pet, Errors errors) {
        String name = pet.getName();
        if (!StringUtils.hasLength(name)) {
            errors.rejectValue("name", "required", "required");
        } else if (pet.isNew() && pet.getOwner().getPet(name, true) != null) {
            errors.rejectValue("name", "duplicate", "already exists");
        }
        if (pet.isNew() && pet.getType() == null) {
            errors.rejectValue("type", "required", "required");
        }
        if (pet.getBirthDate() == null) {
            errors.rejectValue("birthDate", "required", "required");
        }
    }

}
