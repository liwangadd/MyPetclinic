package com.liwang.samples.web;

import com.liwang.samples.model.Vets;
import com.liwang.samples.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Nikolas on 2015/10/26.
 */
@Controller
public class VetController {

    @Autowired
    private ClinicService clinicService;

    @RequestMapping(value = {"vets.html"})
    public String showVetList(Map<String,Object> model){
        Vets vets=new Vets();
        vets.getVetList().addAll(clinicService.findVets());
        model.put("vets", vets);
        return "vets/vetList";
    }

    @RequestMapping(value = "/vets", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Vets showXmlVetList(){
        Vets vets=new Vets();
        vets.getVetList().addAll(clinicService.findVets());
        return vets;
    }

    @RequestMapping(value = "vets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Vets showResourceVetList(){
        Vets vets=new Vets();
        vets.getVetList().addAll(clinicService.findVets());
        return vets;
    }
}
