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
                 errors.rejectValue("arrival", "FlightUpdateDTOValidator.invalid.arrival");
            }
            if (updatedFlight.getDeparture() == null) {
                 errors.rejectValue("departure", "FlightUpdateDTOValidator.invalid.departure");
            }
            if (updatedFlight.getDeparture().compareTo(updatedFlight.getArrival()) > 0) {
                 errors.rejectValue("departure", "FlightCreationalDTOValidator.invalid.departure");
            }

            if (updatedFlight.getOriginId().equals(updatedFlight.getDestinationId())) {
                 errors.rejectValue("destinationId", "FlightCreationalDTOValidator.invalid.destination");
                 errors.rejectValue("originId", "FlightCreationalDTOValidator.invalid.origin");
            }
            if (updatedFlight.getAirplaneId() == null) {
                errors.rejectValue("airplaineId", "FlightUpdateDTOValidator.invalid.airplane");
            }
            if (updatedFlight.getStewardsIds() == null) {
                errors.rejectValue("stewardsIds", "FlightCreationalDTOValidator.invalid.stewards");
            }
            if (updatedFlight.getStewardsIds().isEmpty()) {
                 errors.rejectValue("stewardsIds", "FlightCreationalDTOValidator.invalid.stewards");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
