package com.liwang.samples.web;

import com.liwang.samples.model.Pet;
import com.liwang.samples.model.Visit;
import com.liwang.samples.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Nikolas on 2015/10/28.
 */
@Controller
public class VisitController {

    @Autowired
    private ClinicService clinicService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") int petId) {
        Pet pet = clinicService.findPetById(petId);
        Visit visit = new Visit();
        pet.addVisit(visit);
        return visit;
    }

    @RequestMapping(value = "/owners/*/pets/{petId}/visits/new", method = RequestMethod.GET)
    public String initNewVisitForm(@PathVariable int petId, Model model) {
        return "pets/createOrUpdateVisitForm";
    }

    @RequestMapping(value = "/owners/*/pets/{petId}/visits/new", method = RequestMethod.POST)
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            clinicService.saveVisit(visit);
            return "redirect:/owners/{ownerId}";
        }
    }

    @RequestMapping(value = "/owners/*/pets/{petId}/visits", method = RequestMethod.GET)
    public String showVisits(@PathVariable int petId, Model model) {
        model.addAttribute("visits", clinicService.findPetById(petId).getVisits());
        return "visitList";
    }

}
