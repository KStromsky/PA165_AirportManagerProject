/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airportapi.dto.DestinationCreationalDTO;
import cz.muni.fi.airportapi.dto.DestinationDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SpringMVC Controller for Destination management
 * @author Michal Zbranek
 */
@Import({FacadeTestConfiguration.class})
@RequestMapping("destination")
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
        Long id = destinationFacade.createDestination(formBean);
        redirectAttributes.addFlashAttribute("alert_info", "Destination with id: " + id + " was created");
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
        DestinationDTO destination = destinationFacade.getDestinationWithId(id);
        destinationFacade.removeDestination(id);
        redirectAttributes.addFlashAttribute("alert_success", "Destination with id: " + destination.getId() + " was deleted.");
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