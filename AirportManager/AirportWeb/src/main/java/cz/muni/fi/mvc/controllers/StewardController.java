/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportapi.dto.*;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import cz.muni.fi.airportservicelayer.config.ServiceTestConfiguration;
import cz.muni.fi.mvc.forms.StewardCreationalDTOValidator;
import cz.muni.fi.mvc.forms.StewardUpdateDTOValidator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
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
 *
 * @author Sebastian Kupka
 */
@Import({FacadeTestConfiguration.class})
@RequestMapping("/steward")
@Controller
public class StewardController {

    private class CustomDateFormater extends SimpleDateFormat {

        private String pattern;

        @Override
        public Date parse(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }

            try {
                return super.parse(source);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Date format is incorrect, correct format is '" + this.pattern + "'", ex);
            }
        }

        public CustomDateFormater(String pattern) {
            super(pattern);
            this.pattern = pattern;
        }
    }

    final static Logger log = LoggerFactory.getLogger(StewardController.class);

    @Autowired
    private StewardFacade stewardFacade;

    @Autowired
    private DestinationFacade destinationFacade;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        CustomDateFormater dateFormat = new CustomDateFormater("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        if (binder.getTarget() instanceof StewardCreationalDTO) {
            binder.addValidators(new StewardCreationalDTOValidator(stewardFacade));
        }
        
        if (binder.getTarget() instanceof UpdateStewardDTO) {
            binder.addValidators(new StewardUpdateDTOValidator(stewardFacade));
        }
    }

    @ModelAttribute("genders")
    public Gender[] genders() {
        log.debug("genders()");
        return Gender.values();
    }
    
    @ModelAttribute("dateNow")
    public String dateNow() {
        log.debug("colors");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    /**
     * Creates form for Steward creation
     *
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newSteward(Model model) {
        StewardCreationalDTO stewardCreate = new StewardCreationalDTO();
        model.addAttribute("stewardCreate", stewardCreate);
        return "steward/new";
    }

    /**
     * Creates a new Steward
     *
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@Valid @ModelAttribute("stewardCreate") StewardCreationalDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
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
        Long id = 0L;
        try {
//            if (stewardFacade.getRelevantStewards(formBean.getPersonalIdentificator()) != null) {
//                bindingResult.addError(new FieldError("stewardCreate", "personalIdentificator",
//                        formBean.getPersonalIdentificator(), false, 
//                        new String[]{"StewardCreationalDTOValidator.invalid.personalIdetificator"}, 
//                        null, "Personal identificator already exists."));
//                model.addAttribute("personalIdentificator_error", true);
//                return "steward/new";
//            }
            id = stewardFacade.createSteward(formBean);
            redirectAttributes.addFlashAttribute("alert_info", "Steward with id: " + id + " was created");
        } catch (Exception ex) {
            model.addAttribute("alert_danger", "Steward was not created because of some unexpected error");
            redirectAttributes.addFlashAttribute("alert_danger", "Steward was not created because of some unexpected error");
        }
        request.getSession().setAttribute("authenticated", stewardFacade.getStewardWithPersonalIdentificator(formBean.getPersonalIdentificator()));
        return "redirect:" + uriBuilder.path("/steward").toUriString();
    }
    
    /**
     * Prepares edit form.
     *
     * @param id, model
     * @return JSP page name
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editSteward(@PathVariable("id") long id, 
            RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Model model) 
    {    
        UpdateStewardDTO steward = stewardFacade.getUpdateStewardWithId(id);
        
        if (steward == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Steward " + id + " cannot be updated");
            return "redirect:" + uriBuilder.path("/steward/list").toUriString();
        }
        
        model.addAttribute("steward", steward);
        return "steward/edit";
    }
    
    /**
     * Updates steward
     *
     * @param id, modelAttribute, bindingResult, model, redirectAttributes, uriBuilder
     * @return JSP page
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateSteward(@PathVariable("id") long id, @Valid @ModelAttribute("steward") UpdateStewardDTO updatedSteward, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder){
        
        try {
            StewardDTO steward = stewardFacade.getStewardWithId(id);
            stewardFacade.updateStewardName(updatedSteward);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Steward " + id + " wasn't updated because of some unknow error");
            return "redirect:" + uriBuilder.path("/steward/edit/{id}").buildAndExpand(id).encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_info", "Steward " + id + " was updated");
        return "redirect:" + uriBuilder.path("/steward/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

    /**
     * Deletes a Steward
     *
     * @param id Steward's id
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {

        StewardDTO steward;
        try {
            steward = stewardFacade.getStewardWithId(id);
            if (steward == null) {
                redirectAttributes.addFlashAttribute("alert_danger", "Steward with id: " + id + " does not exist.");
                return "redirect:" + uriBuilder.path("/steward").toUriString();
            }
            stewardFacade.removeSteward(id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Steward with id: " + id + " cannot be deleted.");
            return "redirect:" + uriBuilder.path("/steward").toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_info", "Steward with id: " + steward.getId() + " was deleted.");
        return "redirect:" + uriBuilder.path("/steward").toUriString();
    }

    /**
     * Shows a Steward
     *
     * @param id identificator of te steward
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        try {
            model.addAttribute("steward", stewardFacade.getStewardWithId(id));
            model.addAttribute("flights", stewardFacade.getStewardFlights(id));
        } catch (Exception e) {
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
//    @RequestMapping()
//    public String list(Model model) {
//        List<StewardDTO> stewards = stewardFacade.getAllStewards();
//        model.addAttribute("stewards", stewards);
//        Map<Long, List<FlightDTO>> stewardsFlights = new HashMap<>();
//        for(StewardDTO steward : stewards) {
//            stewardsFlights.put(steward.getId(),stewardFacade.getStewardFlights(steward.getId()));
//        }
//        model.addAttribute("stewardsFlights", stewardsFlights);
//        model.addAttribute("destinations", destinationFacade.getAllDestinations());
//        return "steward/list";
//    }
    /**
     * Shows a list of stewards which are available at given location at given
     * time.
     *
     * @param ident id of lacation, for which we want to find stewards
     * @param name available from date
     * @param surname available to date
     * @param model display data
     * @return jsp page
     */
    @RequestMapping()
    public String list(@RequestParam(value = "ident", required = false) String ident,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "invalid", required = false, defaultValue = "false") boolean invalid,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws IllegalAccessException {

        StringBuilder sb = new StringBuilder("redirect:" + uriBuilder.path("/steward").toUriString());
        sb.append("?");
        if (ident != null) {
            sb.append("ident=" + ident);
            sb.append("&");
        }
        if (name != null && !name.isEmpty()) {
            sb.append("name=" + name);
            sb.append("&");
        }
        if (surname != null && !surname.isEmpty()) {
            sb.append("surname=" + surname);
            sb.append("&");
        }
        sb.append("invalid=true");

        String returnURI = sb.toString();

        if (!invalid) {
            try {
                List<StewardDTO> stewards = stewardFacade.getRelevantStewards(ident, name, surname);
                model.addAttribute("stewards", stewards);
                Map<Long, List<FlightDTO>> stewardsFlights = new HashMap<>();
                for (StewardDTO steward : stewards) {
                    stewardsFlights.put(steward.getId(), stewardFacade.getStewardFlights(steward.getId()));
                }
                model.addAttribute("stewardsFlights", stewardsFlights);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("alert_danger", "Error while processing request");
                return returnURI;
            }
        }
        try {
            model.addAttribute("destinations", destinationFacade.getAllDestinations());
            return "steward/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Destinations unloadable");
            return returnURI;
        }
    }
}
