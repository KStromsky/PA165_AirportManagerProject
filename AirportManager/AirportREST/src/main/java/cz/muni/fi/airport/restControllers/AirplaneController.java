package cz.muni.fi.airport.restControllers;

import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.dto.AirplaneDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Rest Controller for Airplane
 * @author Jakub Stromský
 */

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    @Inject
    private AirplaneFacade airplaneFacade;

    /**
     * Get all airplanes
     * @return list of AirplaneDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AirplaneDTO> getAirplanes() {
        return airplaneFacade.getAllAirplanes();
    }

    /**
     * Handles Exception throw during processing REST actions
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Airplane not found")
    @ExceptionHandler(Exception.class)
    public void notFound() {
    }
    
    /**
     * Get airplane with given id
     * @param id of airplane
     * @return AirplaneDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AirplaneDTO getAirplaneWithId(@PathVariable("id") long id) throws Exception {
        return airplaneFacade.getAirplaneWithId(id);
    }

    /**
     * Create a new airplane
     * @param airplaneCreationalDTO airplane to create
     * @return BookDTO of book which was added
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AirplaneDTO addAirplane(@RequestBody AirplaneCreationalDTO airplaneCreationalDTO) throws Exception {
        Long id = airplaneFacade.createAirplane(airplaneCreationalDTO);
        return airplaneFacade.getAirplaneWithId(id);
    }

    /**
     * Get airplane with name airplane
     * @param name name of airplane which we want to get
     * @return list of airplanes with given name
     * @throws java.lang.Exception
     */ 
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AirplaneDTO> getAirplaneWithName(@PathVariable("name") String name) throws Exception {
        return airplaneFacade.getAirplaneWithName(name);
    }
    
}