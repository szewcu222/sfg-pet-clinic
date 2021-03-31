package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService; //OwnerService is interface

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/", "index"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());

        return "owners/index";
    }

    @GetMapping(value = "/find", produces = MediaType.TEXT_PLAIN_VALUE)
    // @ResponseBody
    public String findOwners() {
        return "notimplemented";
    }
}
