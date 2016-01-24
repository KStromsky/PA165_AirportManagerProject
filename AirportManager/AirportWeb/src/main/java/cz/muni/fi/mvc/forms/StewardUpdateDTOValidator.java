package cz.muni.fi.mvc.forms;

import cz.muni.fi.airportapi.dto.StewardCreationalDTO;
import cz.muni.fi.airportapi.dto.UpdateStewardDTO;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates, if there is already a steward with given Personal Ident
 * @author Sebastian Kupka
 */
public class StewardUpdateDTOValidator implements Validator {
    
    final static Logger log = LoggerFactory.getLogger(StewardUpdateDTOValidator.class);

    private StewardFacade stewardFacade;

    public StewardUpdateDTOValidator(StewardFacade stewardFacade) {
        this.stewardFacade = stewardFacade;
    }
    
    

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateStewardDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (stewardFacade == null) {
            return;
        }
        try {
            UpdateStewardDTO updateStewardDTO = (UpdateStewardDTO) target;
            
            if (updateStewardDTO.getPassword() == null) {
                return;
            } else if (updateStewardDTO.getPassword().isEmpty()) {
                updateStewardDTO.setPassword(null);
            } else if (updateStewardDTO.getPassword() == null 
                    //|| stewardCreationalDTO.getPassword().length() < 5
                    || !updateStewardDTO.getPassword().matches("(?=.*[A-Z]).*\\d+.*")) {
                errors.rejectValue("password", "StewardCreationalDTOValidator.invalid.password");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
