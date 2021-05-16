package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService; //OwnerService is interface

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


    @GetMapping("index")
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());

        return "owners/index";
    }


    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        // find all by last name
        Collection<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        Collection<Owner> owners = ownerService.findByLastNameContainingIgnoreCase(owner.getLastName());

        if(results.isEmpty()) {
            // if nothing found, reject with error
            result.rejectValue("lastName", "not found", "not found");
            return "owners/findOwners";
        } else if(results.size() == 1) {
            // if the one is found, redirect to details
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // if empty string "" provided, find all there is and redirect to ownersList
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }


    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable int ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById((long) ownerId));

        return mav;
    }

}
