/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mvc.forms;

import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates, if there is already a steward with given Name
 * @author Sebastian Kupka
 */
public class AirplaneCreationalDTOValidator implements Validator {
    
    final static Logger log = LoggerFactory.getLogger(AirplaneCreationalDTOValidator.class);

    private AirplaneFacade airplaneFacade;

    public AirplaneCreationalDTOValidator(AirplaneFacade airplaneFacade) {
        this.airplaneFacade = airplaneFacade;
    }
    
    

    @Override
    public boolean supports(Class<?> clazz) {
        return AirplaneCreationalDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (airplaneFacade == null) {
            return;
        }
        try {
            AirplaneCreationalDTO airplaneCreateDTO = (AirplaneCreationalDTO) target;
            if (!airplaneFacade.getAirplaneWithName(airplaneCreateDTO.getName()).isEmpty()) {
                errors.rejectValue("name", "AirplaneCreationalDTOValidator.invalid.name");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
