package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airport.enums.Location;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.FlightCreationalDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightArrivalDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDepartureDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDestinationDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightOriginDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightStewardsDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightsAirplaneDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportapi.facade.FlightFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import cz.muni.fi.mvc.forms.FlightCreationalDTOValidator;
import cz.muni.fi.mvc.forms.FlightUpdateDTOValidator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SpringMVC Controller for Flight management
 * @author Gabriela Podolnikova
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
        /*if (binder.getTarget() instanceof FlightCreationalDTO) {
            binder.addValidators(new FlightCreationalDTOValidator(flightFacade));
        }*/
        
        if (binder.getTarget() instanceof UpdateFlightDTO) {
            binder.addValidators(new FlightUpdateDTOValidator(flightFacade));
        }
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
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public String delete(@PathVariable long id, Model model, HttpServletRequest  request,
            UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        FlightDTO flight = flightFacade.getFlightWithId(id);
        flightFacade.removeFlight(id);
        redirectAttributes.addFlashAttribute("alert_info", "Flight with id: " + flight.getId() + " was deleted.");
        UriComponentsBuilder build =  uriBuilder.path("/flight");
        for ( Entry<String,String[]> pair : request.getParameterMap().entrySet()) {
            build.queryParam(pair.getKey(), pair.getValue());
        }     
        return "redirect:" + build.toUriString();
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
     * Prepares edit form.
     *
     * @param id, model 
     * @return JSP page to edit flight
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editFlight(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        UpdateFlightDTO flight = flightFacade.getUpdateFlightWithId(id);
        if (flight == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Flight " + id + " cannot be updated");
            return "redirect:" + uriBuilder.path("/flight/list").toUriString();
        }
        model.addAttribute("flight", flight);
        model.addAttribute("destinations", destinationFacade.getAllDestinations());
        model.addAttribute("stewards", stewardFacade.getAllStewards());
        model.addAttribute("airplanes", airplaneFacade.getAllAirplanes());
        return "flight/edit";
    }
    
     /**
     * Updates flight
     *
     * @param id, modelAttribute, bindingResult, model, redirectAttributes, uriBuilder
     * @return JSP page
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateFlight(@PathVariable("id") long id, @Valid @ModelAttribute("flight") UpdateFlightDTO updatedFlight, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder){

        try {
            FlightDTO flight = flightFacade.getFlightWithId(id);
            flightFacade.updateFlightArrival(updatedFlight);
            flightFacade.updateFlightDeparture(updatedFlight);
            flightFacade.updateFlightOrigin(updatedFlight);
            flightFacade.updateFlightDestination(updatedFlight);
            flightFacade.updateFlightAirplane(updatedFlight);
            flightFacade.updateFlightStewards(updatedFlight);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Flight " + id + " wasn't updated because of some unknow error");
            return "redirect:" + uriBuilder.path("/flight/edit/{id}").buildAndExpand(id).encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success", "Flight " + id + " was updated");
        return "redirect:" + uriBuilder.path("/flight").toUriString();
    }
    
    @ModelAttribute("locations")
    public Location[] locations() {
        log.debug("locations()");
        return Location.values();
    }
    
    /**
     * Shows a list of flights which are available at given location 
     *
     * @param locationType specifies, if it is for Origin, or Destination
     * @param locationId id of location, for which we want to find flights
     * @param model display data
     * @return jsp page
     */
    @RequestMapping()
    public String list(
            @RequestParam(value = "locationType", required = false, defaultValue = "NONE") Location locationType,
            @RequestParam(value = "location", required = false) Long locationId,
            @RequestParam(value = "invalid", required = false, defaultValue = "false") boolean invalid,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws IllegalAccessException {

        StringBuilder sb = new StringBuilder("redirect:" + uriBuilder.path("/flight").toUriString());
        sb.append("?");
        if (locationId != null) {
            sb.append("locationType=").append(locationType);
            sb.append("&");
        }
        if (locationId != null) {
            sb.append("location=").append(locationId);
            sb.append("&");
        }
        sb.append("invalid=true");

        String returnURI = sb.toString();

        if (!invalid) {
            try {
                
                DestinationDTO destination = null;
                
                if (locationId != null) {
                    destination = destinationFacade.getDestinationWithId(locationId);
                }

                List<FlightDTO> flights = new ArrayList<>();
                
                
                if (destination == null || locationType == Location.NONE) {
                    flights = flightFacade.getAllFlights();
                } 
                else if (locationType == Location.DESTINATION) {
                    flights = flightFacade.getFlightsByDestination(destination);
                }
                else if (locationType == Location.ORIGIN) {
                    flights = flightFacade.getFlightsByOrigin(destination);
                }
                model.addAttribute("flights", flights);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("alert_danger", "Error while processing request");
                return returnURI;
            }
        }
        try {
            model.addAttribute("destinations", destinationFacade.getAllDestinations());
            return "flight/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Destinations unloadable");
            return returnURI;
        }
    }
}
