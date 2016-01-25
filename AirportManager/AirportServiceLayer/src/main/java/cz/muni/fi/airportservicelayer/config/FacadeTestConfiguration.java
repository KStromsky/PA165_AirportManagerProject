/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.config;

import cz.muni.fi.airport.JpaTestContext;
import cz.muni.fi.airportservicelayer.facade.*;
import cz.muni.fi.airportservicelayer.services.*;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaTestContext.class)
@ComponentScan(basePackageClasses={StewardServiceImpl.class, StewardFacadeImpl.class,
                                   DestinationServiceImpl.class, DestinationFacadeImpl.class,
                                   AirplaneServiceImpl.class, AirplaneFacadeImpl.class,
                                   FlightServiceImpl.class, FlightFacadeImpl.class})
public class FacadeTestConfiguration {
	

	@Bean
	public Mapper dozer(){
		DozerBeanMapper dozer = new DozerBeanMapper();		
		//dozer.addMapping(new DozerCustomConfig());
		return dozer;
	}
		
}
