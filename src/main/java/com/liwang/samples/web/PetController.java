package com.liwang.samples.web;

import com.liwang.samples.model.Owner;
import com.liwang.samples.model.Pet;
import com.liwang.samples.model.PetType;
import com.liwang.samples.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Collection;
import java.util.List;

/**
 * Created by Nikolas on 2015/10/27.
 */
@Controller
@SessionAttributes("pet")
public class PetController {

    @Autowired
    private ClinicService clinicService;

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return clinicService.findPetTypes();
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/owners/{ownerId}/pets/new", method = RequestMethod.GET)
    public String initCreateForm(@PathVariable int ownerId, Model model) {
        Owner owner = clinicService.findOwnerById(ownerId);
        Pet pet = new Pet();
        owner.addPet(pet);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdatePetForm";
    }

    @RequestMapping(value = "/owners/{ownerId}/pets/new", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("pet") Pet pet, BindingResult result, SessionStatus status) {
        new PetValidator().validate(pet, result);
        if (result.hasErrors()) {
            return "pets/createOrUpdatePetForm";
        } else {
            clinicService.savePet(pet);
            status.setComplete();
            return "redirect:/owners/{ownerId}";
        }
    }

    @RequestMapping(value = "/owners/*/pets/{petId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("petId")int petId, Model model){
        Pet pet=clinicService.findPetById(petId);
        model.addAttribute("pet",pet);
        return "pets/createOrUpdatePetForm";
    }

    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute("pet") Pet pet, BindingResult result, SessionStatus status){
        new PetValidator().validate(pet, result);
        if(result.hasErrors()){
            return "pets/createOrUpdatePetForm";
        }else{
            clinicService.savePet(pet);
            status.setComplete();
            return "redirect:/owners/{ownerId}";
        }
    }

}
