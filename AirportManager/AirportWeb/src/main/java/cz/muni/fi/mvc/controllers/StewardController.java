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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    }

    @ModelAttribute("genders")
    public Gender[] colors() {
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
        try {
//            if (stewardFacade.getStewardWithPersonalIdentificator(formBean.getPersonalIdentificator()) != null) {
//                bindingResult.addError(new FieldError("stewardCreate", "personalIdentificator",
//                        formBean.getPersonalIdentificator(), false, 
//                        new String[]{"StewardCreationalDTOValidator.invalid.personalIdetificator"}, 
//                        null, "Personal identificator already exists."));
//                model.addAttribute("personalIdentificator_error", true);
//                return "steward/new";
//            }

            Long id = stewardFacade.createSteward(formBean);
            redirectAttributes.addFlashAttribute("alert_info", "Steward with id: " + id + " was created");
        } catch (Exception ex) {
            model.addAttribute("alert_danger", "Steward was not created because of some unexpected error");
            redirectAttributes.addFlashAttribute("alert_danger", "Steward was not created because of some unexpected error");
        }
        return "redirect:" + uriBuilder.path("/steward").toUriString();
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

        redirectAttributes.addFlashAttribute("alert_success", "Steward with id: " + steward.getId() + " was deleted.");
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
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
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
     * @param locationId id of lacation, for which we want to find stewards
     * @param dateFromStr available from date
     * @param dateToStr available to date
     * @param model display data
     * @return jsp page
     */
    @RequestMapping()
    public String list(@RequestParam(value = "destination", required = false) Long locationId,
            @RequestParam(value = "dateFromStr", required = false) String dateFromStr,
            @RequestParam(value = "dateToStr", required = false) String dateToStr,
            @RequestParam(value = "invalid", required = false, defaultValue = "false") boolean invalid,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws IllegalAccessException {

        Date dateTo = null;
        Date dateFrom = null;
        

        StringBuilder sb = new StringBuilder("redirect:" + uriBuilder.path("/steward").toUriString());
        sb.append("?");
        if (locationId != null) {
            sb.append("destination=" + locationId);
            sb.append("&");
        }
        if (dateFromStr != null && !dateFromStr.isEmpty()) {
            sb.append("dateFromStr=" + dateFromStr);
            sb.append("&");
        }
        if (dateToStr != null && !dateToStr.isEmpty()) {
            sb.append("dateToStr=" + dateToStr);
            sb.append("&");
        }
        sb.append("invalid=true");

        String returnURI = sb.toString();

        if (!invalid) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            if (dateFromStr != null && !dateFromStr.isEmpty()) {
                try {
                    dateFrom = formatter.parse(dateFromStr);
                } catch (ParseException ex) {
                    log.debug("Parsing error - ignoring From Date", ex);
                    redirectAttributes.addFlashAttribute("alert_danger", "Available From is not a valid Date!");
                    return returnURI;
                }
            }
            if (dateToStr != null && !dateToStr.isEmpty()) {
                try {
                    dateTo = formatter.parse(dateToStr);
                } catch (ParseException ex) {
                    log.debug("Parsing error - ignoring To Date", ex);
                    redirectAttributes.addFlashAttribute("alert_danger", "Available To is not a valid Date!");
                    return returnURI;
                }
            }

            if (dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0) {
                redirectAttributes.addFlashAttribute("alert_danger", "Available From is later than Available To");
                return returnURI;
            }

            try {
                if (locationId != null && destinationFacade.getDestinationWithId(locationId) == null) {
                    locationId = null;
                }

                List<StewardDTO> stewards = stewardFacade.findSpecificStewards(dateFrom, dateTo, locationId);
                model.addAttribute("stewards", stewards);
                Map<Long, List<FlightDTO>> stewardsFlights = new HashMap<>();
                for (StewardDTO steward : stewards) {
                    stewardsFlights.put(steward.getId(), stewardFacade.getStewardFlights(steward.getId()));
                }
                model.addAttribute("stewardsFlights", stewardsFlights);
            } catch (Exception e) {
                log.debug("Service error - ignoring To Date", e);
                redirectAttributes.addFlashAttribute("alert_danger", "Error while processing request");
                return returnURI;
            }
        }
        try {
            model.addAttribute("destinations", destinationFacade.getAllDestinations());
            return "steward/list";
        } catch (Exception e) {
            log.debug("Service error - ignoring To Date", e);
            redirectAttributes.addFlashAttribute("alert_danger", "Available To is not a valid Date!");
            return "redirect:" + uriBuilder.toUriString();
        }
    }
}
