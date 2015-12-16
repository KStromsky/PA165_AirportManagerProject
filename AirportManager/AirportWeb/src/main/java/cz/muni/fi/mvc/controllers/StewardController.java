/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportapi.dto.*;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import cz.muni.fi.airportservicelayer.config.ServiceTestConfiguration;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * SpringMVC Controller for Steward managment
 * @author Sebastian Kupka
 */
@Import({FacadeTestConfiguration.class})
@RequestMapping("/steward")
@Controller
public class StewardController {
    
    final static Logger log = LoggerFactory.getLogger(StewardController.class);

    @Autowired
    private StewardFacade stewardFacade;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    @ModelAttribute("genders")
    public Gender[] colors() {
        log.debug("genders()");
        return Gender.values();
    }

    /**
     * Creates form for Steward creation
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newSteward(Model model) {
        StewardCreationalDTO stewardCreate = new StewardCreationalDTO();
        stewardCreate.setEmploymentDate(new Date());
        model.addAttribute("stewardCreate", stewardCreate);
        return "steward/new";
    }

    /**
     * Creates a new Steward
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@Valid @ModelAttribute("stewardCreate") StewardCreationalDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            return "steward/new";
        }
        Long id = stewardFacade.createSteward(formBean);
        redirectAttributes.addFlashAttribute("alert_info", "Steward with id: " + id + " was created");
        return "redirect:" + uriBuilder.path("/steward").toUriString();
    }
    
    /**
     * Deletes a Steward
     * @param id Steward's id
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        StewardDTO steward = stewardFacade.getStewardWithId(id);
        stewardFacade.removeSteward(id);
        redirectAttributes.addFlashAttribute("alert_success", "Steward with id: " + steward.getId() + " was deleted.");
        return "redirect:" + uriBuilder.path("/steward").toUriString();
    }

    /**
     * Shows a Steward
     * @param id identificator of te steward
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("steward", stewardFacade.getStewardWithId(id));
            model.addAttribute("flights", stewardFacade.getStewardFlights(id));
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Steward with id: " + id + " was not found.");
            return "redirect:" + uriBuilder.path("/steward").toUriString();
        }
        return "steward/detail";
    }
    
    /**
     * Shows a list of Stewards with the ability to add, delete or edit.
     *
     * @param model display data
     * @return jsp page
     */
    @RequestMapping()
    public String list(Model model) {
        model.addAttribute("stewards", stewardFacade.getAllStewards());
        return "steward/list";
    }

//    /**
//     * Shows a list of stewards which are available at given location at given time.
//     * @param locationId id of lacation, for which we want to find stewards
//     * @param dateFromStr available from date
//     * @param dateToStr available to date
//     * @param model display data
//     * @return jsp page
//     */
//    @RequestMapping()
//    public String list(@RequestParam(value="locationId", required = false) Long locationId,
//            @RequestParam(value="dateFromStr", required = false) String dateFromStr,
//            @RequestParam(value="dateToStr", required = false) String dateToStr,          
//            Model model) {
//        
//        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Date dateFrom = null;
//        try {
//            dateFrom = formatter.parse(dateFromStr);
//        } catch (ParseException ex) {
//            log.debug("Parsing error - ignoring From Date", ex);
//        }
//        Date dateTo = null;
//        try {
//            dateTo = formatter.parse(dateToStr);
//        } catch (ParseException ex) {
//            log.debug("Parsing error - ignoring To Date", ex);
//        }
//        model.addAttribute("stewards", stewardFacade.findSpecificStewards(dateFrom, dateTo, locationId));
//        return "steward/list";
//    }
}
