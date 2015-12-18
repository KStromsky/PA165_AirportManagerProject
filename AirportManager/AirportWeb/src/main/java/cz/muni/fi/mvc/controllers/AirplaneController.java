package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.dto.AirplaneDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.StewardCreationalDTO;
import cz.muni.fi.airportapi.dto.StewardDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import static cz.muni.fi.mvc.controllers.StewardController.log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Jakub Stromský
 */

@Import({FacadeTestConfiguration.class})
@RequestMapping("/airplane")
@Controller
public class AirplaneController {

    @Autowired
    private AirplaneFacade airplaneFacade;
    
    @Autowired
    private DestinationFacade destinationFacade;    
    
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
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        AirplaneController.CustomDateFormater dateFormat = new AirplaneController.CustomDateFormater("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Creates form for Airplane creation
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newAirplane(Model model) {
        AirplaneCreationalDTO airplaneCreate = new AirplaneCreationalDTO();
        model.addAttribute("airplaneCreate", airplaneCreate);
        return "airplane/new";
    }
    
    /**
     * Creates a new Airplane
     *
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@Valid @ModelAttribute("airplaneCreate") AirplaneCreationalDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            return "airplane/new";
        }
        try {
            if (airplaneFacade.getAirplaneWithName(formBean.getName()) != null) {
                bindingResult.addError(new FieldError("airplaneCreate", "airplaneName", "Airplane with given name already exists"));
                model.addAttribute("airplaneName_error", true);
                return "airplane/new";
            }
            
            Long id = airplaneFacade.createAirplane(formBean);
            redirectAttributes.addFlashAttribute("alert_info", "Airplane with id: " + id + " was created");
        } catch (Exception ex) {
            model.addAttribute("alert_danger", "Airplane was not created because of some unexpected error");
            redirectAttributes.addFlashAttribute("alert_danger", "Airplane was not created because of some unexpected error");
        }
        return "redirect:" + uriBuilder.path("/airplane").toUriString();
    }
    
    /**
     * Deletes Airplane
     *
     * @param id Airplane identificator
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {

        AirplaneDTO airplaneDTO;
        try {
            airplaneDTO = airplaneFacade.getAirplaneWithId(id);
            if (airplaneDTO == null) {
                redirectAttributes.addFlashAttribute("alert_danger", "Airplane with id: " + id + " does not exist.");
                return "redirect:" + uriBuilder.path("/airplane").toUriString();
            }
            airplaneFacade.removeAirplane(id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Airplane with id: " + id + " cannot be deleted.");
            return "redirect:" + uriBuilder.path("/airplane").toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "Airplane with id: " + airplaneDTO.getId() + " was deleted.");
        return "redirect:" + uriBuilder.path("/airplane").toUriString();
    }

    /**
     * Shows Airplane
     *
     * @param id airplane identificator
     * @param model display data
     * @return jsp page
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("airplane", airplaneFacade.getAirplaneWithId(id));
            model.addAttribute("flights", airplaneFacade.getAirplaneFlights(id));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Airplane with id: " + id + " was not found.");
            return "redirect:" + uriBuilder.path("/airplane").toUriString();
        }
        return "airplane/detail";
    }
    
     /**
     * Shows a list of airplanes which are available at given location at given
     * time.
     *
     * @param locationId identificator of lacation, for which we want to find airplane(s)
     * @param dateFromStr available from date
     * @param dateToStr available to date
     * @param model display data
     * @return jsp page
     */ 
    @RequestMapping()
    public String list(@RequestParam(value = "destination", required = false) String location,
            @RequestParam(value = "dateFromStr", required = false) String dateFromStr,
            @RequestParam(value = "dateToStr", required = false) String dateToStr,
            @RequestParam(value = "capacity", required = false) Integer capacity,
            @RequestParam(value = "invalid", required = false, defaultValue = "false") boolean invalid,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {

        Date dateTo = null;
        Date dateFrom = null;

        StringBuilder sb = new StringBuilder("redirect:" + uriBuilder.path("/airplane").toUriString());
        sb.append("?");
        if (location != null) {
            sb.append("destination=" + location);
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
        if (capacity != null) {
            sb.append("capacity=" + capacity);
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
                if (location != null && destinationFacade.getDestinationWithLocation(location).get(0) == null) {
                    location = null;
                }
                List<AirplaneDTO> airplanes = airplaneFacade.getSpecificAirplanes(dateFrom, dateTo, capacity, location); 
                model.addAttribute("airplanes", airplanes);
                Map<Long, List<FlightDTO>> airplanesFlights = new HashMap<>();
                for (AirplaneDTO airplane : airplanes) {
                    airplanesFlights.put(airplane.getId(), airplaneFacade.getAirplaneFlights(airplane.getId()));
                }
                model.addAttribute("airplaneFlights", airplanesFlights);
            } catch (Exception e) {
                log.debug("Service error - ignoring To Date", e);
                redirectAttributes.addFlashAttribute("alert_danger", "Error while processing request");
                return returnURI;
            }
        }
        try {
            model.addAttribute("destinations", destinationFacade.getAllDestinations());
            return "airplane/list";
        } catch (Exception e) {
            log.debug("Service error - ignoring To Date", e);
            redirectAttributes.addFlashAttribute("alert_danger", "Available To is not a valid Date!");
            return "redirect:" + uriBuilder.toUriString();
        }
    }
    
}
