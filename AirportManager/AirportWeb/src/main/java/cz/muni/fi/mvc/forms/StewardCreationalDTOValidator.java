package cz.muni.fi.mvc.forms;

import cz.muni.fi.airportapi.dto.StewardCreationalDTO;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The place for validation checks. Useful for checks involving multiple
 * properties at once.
 */
public class StewardCreationalDTOValidator implements Validator {
    
    final static Logger log = LoggerFactory.getLogger(StewardCreationalDTOValidator.class);

    private StewardFacade stewardFacade;

    public StewardCreationalDTOValidator(StewardFacade stewardFacade) {
        this.stewardFacade = stewardFacade;
    }
    
    

    @Override
    public boolean supports(Class<?> clazz) {
        return StewardCreationalDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (stewardFacade == null) {
            return;
        }
        try {
            StewardCreationalDTO productCreateDTO = (StewardCreationalDTO) target;
            if (productCreateDTO.getPersonalIdentificator().matches("[0-9,A-Z]{3}-\\d{5}") &&
                    stewardFacade.getStewardWithPersonalIdentificator(productCreateDTO.getPersonalIdentificator()) != null) {
                errors.rejectValue("personalIdentificator", "StewardCreationalDTOValidator.invalid.personalIdetificator");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
