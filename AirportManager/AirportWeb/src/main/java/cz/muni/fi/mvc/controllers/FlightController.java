package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airportapi.dto.FlightCreationalDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportapi.facade.FlightFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SpringMVC Controller for Flight management
 * @author Gabi
 */
@Import({FacadeTestConfiguration.class})
@RequestMapping("/flight")
@Controller
public class FlightController {
    
    final static Logger log = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightFacade flightFacade;
    
    @Autowired
    private StewardFacade stewardFacade;
    
    @Autowired
    private DestinationFacade destinationFacade;
    
    @Autowired
    private AirplaneFacade airplaneFacade;
    
    
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        
    }
    
     /**
     * Creates form for Flight creation
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newFlight(Model model) {
        FlightCreationalDTO flightCreate = new FlightCreationalDTO();
        model.addAttribute("flightCreate", flightCreate);
        model.addAttribute("destinations", destinationFacade.getAllDestinations());
        model.addAttribute("stewards", stewardFacade.getAllStewards());
        model.addAttribute("airplanes", airplaneFacade.getAllAirplanes());
        return "flight/new";
    }
    
     /**
     * Creates a new Flight
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@Valid @ModelAttribute("flightCreate") FlightCreationalDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            return "flight/new";
        }
        Long id = flightFacade.createFlight(formBean);
        redirectAttributes.addFlashAttribute("alert_info", "Flight with id: " + id + " was created");
        return "redirect:" + uriBuilder.path("/flight").toUriString();
    }
    
    /**
     * Deletes a Flight
     * @param id flight's id
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        FlightDTO flight = flightFacade.getFlightWithId(id);
        flightFacade.removeFlight(id);
        redirectAttributes.addFlashAttribute("alert_success", "Flight with id: " + flight.getId() + " was deleted.");
        return "redirect:" + uriBuilder.path("/flight").toUriString();
    }
    
    /**
     * Shows a Flight
     * @param id identificator of flight
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("flight", flightFacade.getFlightWithId(id));
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Flight with id: " + id + " was not found.");
            return "redirect:" + uriBuilder.path("/flight").toUriString();
        }
        return "flight/detail";
    }
     
    /**
     * Shows a list of Flights with the ability to add, delete or edit.
     *
     * @param model display data
     * @return jsp page
     */
    @RequestMapping()
    public String list(Model model) {
        model.addAttribute("flights", flightFacade.getAllFlights());
        return "flight/list";
    }
}
