/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airportapi.dto.DestinationCreationalDTO;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.UpdateDestinationLocationDTO;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SpringMVC Controller for Destination management
 * @author Michal Zbranek
 */
@Import({FacadeTestConfiguration.class})
@RequestMapping("/destination")
@Controller
public class DestinationController {
    
    final static Logger log = LoggerFactory.getLogger(DestinationController.class);

    @Autowired
    private DestinationFacade destinationFacade;

    /**
     * Creates form for Destination creation
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newDestination(Model model) {
        DestinationCreationalDTO destinationCreate = new DestinationCreationalDTO();
        model.addAttribute("destinationCreate", destinationCreate);
        return "destination/new";
    }

    /**
     * Creates a new Destination
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@Valid @ModelAttribute("destinationCreate") DestinationCreationalDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            return "destination/new";
        }
        try {
            if (destinationFacade.getDestinationWithLocation(formBean.getLocation()) == null) {
                bindingResult.addError(new FieldError("destinationCreate", "location", "Destination was not created because it already exists."));
                model.addAttribute("location_error", true);
                return "destination/new";
            }

            Long id = destinationFacade.createDestination(formBean);
            redirectAttributes.addFlashAttribute("alert_info", "Destination with id: " + id + " was created");
        } catch (Exception ex) {
            model.addAttribute("alert_danger", "Destination was not created because of some unexpected error");
            redirectAttributes.addFlashAttribute("alert_danger", "Destination was not created because it already exists.");
        }
        return "redirect:" + uriBuilder.path("/destination").toUriString();
    }
    
    /**
     * Prepares edit form.
     *
     * @param id, model
     * @return JSP page name
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editDestination(@PathVariable("id") long id, Model model) {
        model.addAttribute("destination", destinationFacade.getDestinationWithId(id));
        return "destination/edit";
    }
    
    /**
     * Updates destination
     *
     * @param id, modelAttribute, bindingResult, model, redirectAttributes, uriBuilder
     * @return JSP page
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateDestination(@PathVariable("id") long id, @Valid @ModelAttribute("destination") DestinationDTO updatedDestination, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder){
         
        if ((updatedDestination.getLocation()).equals("")){
            redirectAttributes.addFlashAttribute("alert_info", "Location of destination is empty");
            return "redirect:" + uriBuilder.path("/destination/edit/{id}").buildAndExpand(id).encode().toUriString();
        }
        
        if (destinationFacade.getAllDestinations().contains(updatedDestination)){
            redirectAttributes.addFlashAttribute("alert_info", "Location of destination already exists");
            return "redirect:" + uriBuilder.path("/destination/edit/{id}").buildAndExpand(id).encode().toUriString();
        }
        
        DestinationDTO destination = destinationFacade.getDestinationWithId(id);
        UpdateDestinationLocationDTO destination2 = new UpdateDestinationLocationDTO();
        destination2.setId(destination.getId());
        destination2.setLocation(updatedDestination.getLocation());
        destinationFacade.updateDestinationLocation(destination2);

        redirectAttributes.addFlashAttribute("alert_success", "Destination " + id + " was updated");
        return "redirect:" + uriBuilder.path("/destination").toUriString();
    }
    
    /**
     * Deletes a Destination
     * @param id Destination's id
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        DestinationDTO destination;
        try {
            destination = destinationFacade.getDestinationWithId(id);
            if (destination == null) {
                redirectAttributes.addFlashAttribute("alert_danger", "Destination with id: " + id + " does not exist.");
                return "redirect:" + uriBuilder.path("/destination").toUriString();
            }
            destinationFacade.removeDestination(id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Destination with id: " + id + " cannot be deleted.");
            return "redirect:" + uriBuilder.path("/destination").toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_info", "Destination with id: " + destination.getId() + " was deleted.");
        return "redirect:" + uriBuilder.path("/destination").toUriString();
    }

    /**
     * Shows a Destination
     * @param id identificator of the destination
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("destination", destinationFacade.getDestinationWithId(id));
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Destination with id: " + id + " was not found.");
            return "redirect:" + uriBuilder.path("/destination").toUriString();
        }
        return "destination/detail";
    }
    
    /**
     * Shows a list of Destinations with the ability to add, delete or edit.
     *
     * @param model display data
     * @return jsp page
     */
    @RequestMapping()
    public String list(Model model) {
        model.addAttribute("destinations", destinationFacade.getAllDestinations());
        return "destination/list";
    }
}