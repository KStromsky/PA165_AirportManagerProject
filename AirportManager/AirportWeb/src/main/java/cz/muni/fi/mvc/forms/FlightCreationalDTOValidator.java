package cz.muni.fi.mvc.forms;

import cz.muni.fi.airportapi.dto.FlightCreationalDTO;
import cz.muni.fi.airportapi.facade.FlightFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates if Flight has valid input data.
 * @author Gabriela Podolnikova
 */
public class FlightCreationalDTOValidator implements Validator {
    
    final static Logger log = LoggerFactory.getLogger(FlightCreationalDTOValidator.class);

    private FlightFacade flightFacade;
    
    public FlightCreationalDTOValidator(FlightFacade flightFacade) {
        this.flightFacade = flightFacade;
    }
    
    @Override
    public boolean supports(Class<?> type) {
        return FlightCreationalDTO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (flightFacade == null) {
            return;
        }
        try {
            FlightCreationalDTO flightCreationalDTO = (FlightCreationalDTO) target;
            
            if (flightCreationalDTO.getDeparture().compareTo(flightCreationalDTO.getArrival()) > 0) {
                 errors.rejectValue("arrival", "FlightCreationalDTOValidator.invalid.arrival");
            }
            if (flightCreationalDTO.getOriginId().equals(flightCreationalDTO.getDestinationId())) {
                 errors.rejectValue("destinationId", "FlightCreationalDTOValidator.invalid.destination");
                 errors.rejectValue("originId", "FlightCreationalDTOValidator.invalid.destination");
            }
            if (flightCreationalDTO.getStewardsIds().isEmpty()) {
                 errors.rejectValue("stewardsIds", "FlightCreationalDTOValidator.invalid.stewards");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
    
}
