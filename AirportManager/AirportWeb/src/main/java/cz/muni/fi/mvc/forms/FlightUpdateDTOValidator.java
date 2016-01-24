package cz.muni.fi.mvc.forms;

import cz.muni.fi.airportapi.dto.UpdateFlightDTO;
import cz.muni.fi.airportapi.facade.FlightFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Gabriela Podolnikova
 */
public class FlightUpdateDTOValidator implements Validator {

    final static Logger log = LoggerFactory.getLogger(FlightUpdateDTOValidator.class);

    private FlightFacade flightFacade;

    public FlightUpdateDTOValidator(FlightFacade flightFacade) {
        this.flightFacade = flightFacade;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateFlightDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (flightFacade == null) {
            return;
        }
        try {
            UpdateFlightDTO updatedFlight = (UpdateFlightDTO) target;
            if (updatedFlight.getArrival() == null) {
                 errors.rejectValue("alert_info", "Arrival of flight is empty");
            }
            if (updatedFlight.getDeparture() == null) {
                 errors.rejectValue("alert_info", "Departure of flight is empty");
            }
            if (updatedFlight.getDeparture().compareTo(updatedFlight.getArrival()) > 0) {
                 errors.rejectValue("alert_info", "Departure of flight cannot be after the arrival");
            }

            if (updatedFlight.getOriginId().equals(updatedFlight.getDestinationId())) {
                 errors.rejectValue("alert_info", "Origin and destination cannot be the same");
            }
            if (updatedFlight.getAirplaneId() == null) {
                errors.rejectValue("alert_info", "Airplane is empty");
            }
            if (updatedFlight.getStewardsIds().isEmpty()) {
                 errors.rejectValue("alert_info", "Stewards cannot be empty");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
