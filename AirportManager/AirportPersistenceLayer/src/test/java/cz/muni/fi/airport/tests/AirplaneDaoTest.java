/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.tests;

import cz.muni.fi.airport.JpaTestContext;
import cz.muni.fi.airport.dao.AirplaneDao;
import cz.muni.fi.airport.dao.DestinationDao;
import cz.muni.fi.airport.dao.FlightDao;
import cz.muni.fi.airport.dao.StewardDao;
import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airport.enums.Gender;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Michal Zbranek
 */
@ContextConfiguration(classes = {JpaTestContext.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AirplaneDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private AirplaneDao airplaneDao;
    
    @Autowired
    private FlightDao flightDao;
    
    @Autowired
    public DestinationDao destinationDao;
        
    @Autowired
    public StewardDao stewardDao;
    
    @Test
    public void testCreate() {         
        Airplane airA = new Airplane();
        airA.setCapacity(1);
        airA.setName("airA");
        airA.setType("typeA");
        
        Assert.isNull(airA.getId());
        airplaneDao.create(airA);
        Assert.notNull(airplaneDao.findById(airA.getId()));
    }
    
    @Test
    public void testUpdate() { 
       Airplane airA = new Airplane();
       airA.setCapacity(1);
       airA.setName("airA");
       airA.setType("typeA");
        
       airplaneDao.create(airA);
       Assert.notNull(airplaneDao.findById(airA.getId()));
       airA.setCapacity(2);
       airA.setName("airB");
       airA.setType("typeB");
       assert airA.getCapacity() == 2;
       assert airA.getName().equals("airB");
       assert airA.getType().equals("typeB");
       airplaneDao.update(airA);
       assert airplaneDao.findById(airA.getId()).getCapacity() == 2;
       assert airplaneDao.findById(airA.getId()).getName().equals("airB");
       assert airplaneDao.findById(airA.getId()).getType().equals("typeB");
    }
    
    @Test
    public void testRemove() {
        Airplane airA = new Airplane();
        airA.setCapacity(1);
        airA.setName("airA");
        airA.setType("typeA");
        
        airplaneDao.create(airA);
        Assert.notNull(airplaneDao.findById(airA.getId()));
        airplaneDao.delete(airA);
        Assert.isNull(airplaneDao.findById(airA.getId()));
    }
    
    @Test
    public void testNotFilledCapacity() {
        Airplane airA = new Airplane();
        airA.setName("airA");
        airA.setType("typeA");
        airplaneDao.create(airA);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledName() {
        Airplane airA = new Airplane();
        airA.setType("typeA");
        airA.setCapacity(1);
        airplaneDao.create(airA);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledType() {
        Airplane airA = new Airplane();
        airA.setCapacity(1);
        airA.setName("airA");
        airplaneDao.create(airA);
    }
    
    @Test(expectedExceptions = PersistenceException.class)
    public void testNameUniquness() {
        Airplane airA = new Airplane();
        Airplane airB = new Airplane();
        airA.setName("airA");
        airB.setName("airA");
        airA.setCapacity(1);
        airB.setCapacity(2);
        airA.setType("typeA");
        airB.setType("typeB");
        
        airplaneDao.create(airA);
        airplaneDao.create(airB);
    }
    
    @Test
    public void testFindAll() {
        Airplane airA = new Airplane();
        airA.setCapacity(1);
        airA.setName("airA");
        airA.setType("typeA");
        Airplane airB = new Airplane();
        airB.setCapacity(2);
        airB.setName("airB");
        airB.setType("typeB");
        Airplane airC = new Airplane();
        airC.setCapacity(3);
        airC.setName("airC");
        airC.setType("typeC");
        
        airplaneDao.create(airA);
        airplaneDao.create(airB);
        airplaneDao.create(airC);
        
        List<Airplane> airplanes = airplaneDao.findAllAirplanes();
        
        assert airplanes.size() == 3;
        assert airplanes.contains(airA);
        assert airplanes.contains(airB);
        assert airplanes.contains(airC);
    }
    
    @Test
    public void testFindByName() {
        Airplane airA = new Airplane();
        airA.setCapacity(1);
        airA.setName("airA");
        airA.setType("typeA");
        
        airplaneDao.create(airA);
        
        List<Airplane> airplanes = airplaneDao.findByName("airA");
        assert airplanes.size() > 0;
        assert airA.getName().equals(airplanes.get(0).getName());
    }
    
    @Test
    public void testFindAvailableAirplanes() throws ParseException {
        Airplane airA = new Airplane();
        airA.setCapacity(10);
        airA.setName("airA");
        airA.setType("typeA");
        
        Airplane airB = new Airplane();
        airB.setCapacity(10);
        airB.setName("airB");
        airB.setType("typeB");
        
        Airplane airC = new Airplane();
        airC.setCapacity(10);
        airC.setName("airC");
        airC.setType("typeC");
        
        Airplane airD = new Airplane();
        airD.setCapacity(10);
        airD.setName("airD");
        airD.setType("typeD");
        
        Airplane availableAirplane = new Airplane();
        availableAirplane.setCapacity(10);
        availableAirplane.setName("avaiA");
        availableAirplane.setType("typeE");
        
        airplaneDao.create(airA);
        airplaneDao.create(airB);
        airplaneDao.create(airC);
        airplaneDao.create(airD);
        airplaneDao.create(availableAirplane);
        
        Destination d1 = new Destination();
        d1.setLocation("Brno");

        Destination d2 = new Destination();
        d2.setLocation("Praha");

        destinationDao.create(d1);
        destinationDao.create(d2);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        
        Steward s1 = new Steward();
        s1.setFirstname("fn1");
        s1.setSurname("sn1");
        s1.setPersonalIdentificator("111-11111");
        s1.setDateOfBirth(formatter.parse("1988/02/02"));
        s1.setEmploymentDate(formatter.parse("2014/03/01"));
        s1.setUsername("bbb");
        s1.setGender(Gender.MALE);

        Steward s2 = new Steward();
        s2.setFirstname("fn2");
        s2.setSurname("sn1");
        s2.setPersonalIdentificator("111-11112");
        s2.setDateOfBirth(formatter.parse("1988/02/02"));
        s2.setEmploymentDate(formatter.parse("2014/03/01"));
        s2.setUsername("aaa");
        s2.setGender(Gender.MALE);

        Steward s3 = new Steward();
        s3.setFirstname("fn2");
        s3.setSurname("sn3");
        s3.setPersonalIdentificator("111-11113");
        s3.setDateOfBirth(formatter.parse("1988/02/02"));
        s3.setEmploymentDate(formatter.parse("2014/03/01"));
        s3.setUsername("ccc");
        s3.setGender(Gender.FEMALE);

        Steward s4 = new Steward();
        s4.setFirstname("fn2");
        s4.setSurname("sn3");
        s4.setPersonalIdentificator("111-11114");
        s4.setDateOfBirth(formatter.parse("1988/02/02"));
        s4.setEmploymentDate(formatter.parse("2014/03/01"));
        s4.setUsername("ddd");
        s4.setGender(Gender.FEMALE);

        stewardDao.create(s1);
        stewardDao.create(s2);
        stewardDao.create(s3);
        stewardDao.create(s4);
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 6, 2, 0); //YY MM DD 0 january
        Date date1 = cal.getTime();
        cal.set(2015, 1, 18, 2, 0);
        Date date2 = cal.getTime();
        
        cal.set(2015, 1, 5, 0, 0);
        Date date3 = cal.getTime();
        cal.set(2015, 1, 10, 0, 0);
        Date date4 = cal.getTime();
        
        cal.set(2015, 1, 11, 0, 0);
        Date date5 = cal.getTime();
        cal.set(2015, 1, 13, 0, 0);
        Date date6 = cal.getTime();
        
        cal.set(2015, 1, 14, 0, 0);
        Date date7 = cal.getTime();
        cal.set(2015, 1, 17, 0, 0);
        Date date8 = cal.getTime();
        
        cal.set(2015, 1, 8, 0, 0);
        Date from = cal.getTime();
        cal.set(2015, 1, 15, 0, 0);
        Date to = cal.getTime();
        

        Flight f1 = new Flight();
        f1.setAirplane(airA);
        f1.setArrival(date2);
        f1.setDeparture(date1);
        f1.setOrigin(d1);
        f1.setDestination(d2);
        f1.addSteward(s1);

        Flight f2 = new Flight();
        f2.setAirplane(airB);
        f2.setArrival(date4);
        f2.setDeparture(date3);
        f2.setOrigin(d1);
        f2.setDestination(d2);
        f2.addSteward(s2);

        Flight f3 = new Flight();
        f3.setAirplane(airC);
        f3.setArrival(date6);
        f3.setDeparture(date5);
        f3.setOrigin(d1);
        f3.setDestination(d2);
        f3.addSteward(s3);

        Flight f4 = new Flight();
        f4.setAirplane(airD);
        f4.setArrival(date8);
        f4.setDeparture(date7);
        f4.setOrigin(d1);
        f4.setDestination(d2);
        f4.addSteward(s4);
        
        flightDao.create(f1);
        flightDao.create(f2);
        flightDao.create(f3);
        flightDao.create(f4);

        List<Airplane> availableAirplanes = airplaneDao.findAvailableAirplanes(from, to);
        assert availableAirplanes.size() == 1;
        assert availableAirplanes.get(0).equals(availableAirplane);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindAvailableAirplanesIllegalArguments() {
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date from = cal.getTime();
        cal.set(2015, 1, 15, 0, 0);
        Date to = cal.getTime();
        airplaneDao.findAvailableAirplanes(to, from);
    }
    
    @Test
    public void testGetLastAirplaneFlights() throws ParseException {
        
        Airplane airplane = new Airplane();
        airplane.setCapacity(10);
        airplane.setName("avaiA");
        airplane.setType("typeE");

        airplaneDao.create(airplane);
        
        Destination d1 = new Destination();
        d1.setLocation("Brno");

        Destination d2 = new Destination();
        d2.setLocation("Praha");

        destinationDao.create(d1);
        destinationDao.create(d2);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        
        Steward s1 = new Steward();
        s1.setFirstname("fn1");
        s1.setSurname("sn1");
        s1.setPersonalIdentificator("111-11111");
        s1.setDateOfBirth(formatter.parse("1988/02/02"));
        s1.setEmploymentDate(formatter.parse("2014/03/01"));
        s1.setUsername("bbb");
        s1.setGender(Gender.MALE);

        stewardDao.create(s1);
        
        Calendar cal = Calendar.getInstance();
        
        cal.set(2015, 1, 5, 0, 0); //YY MM DD 0 january
        Date date3 = cal.getTime();
        cal.set(2015, 1, 10, 0, 0);
        Date date4 = cal.getTime();
        
        cal.set(2015, 1, 11, 0, 0);
        Date date5 = cal.getTime();
        cal.set(2015, 1, 13, 0, 0);
        Date date6 = cal.getTime();
        
        cal.set(2015, 1, 14, 0, 0);
        Date date7 = cal.getTime();
        cal.set(2015, 1, 17, 0, 0);
        Date date8 = cal.getTime();
        

        Flight f1 = new Flight();
        f1.setAirplane(airplane);
        f1.setArrival(date4);
        f1.setDeparture(date3);
        f1.setOrigin(d1);
        f1.setDestination(d2);
        f1.addSteward(s1);

        Flight f2 = new Flight();
        f2.setAirplane(airplane);
        f2.setArrival(date6);
        f2.setDeparture(date5);
        f2.setOrigin(d1);
        f2.setDestination(d2);
        f2.addSteward(s1);

        Flight f3 = new Flight();
        f3.setAirplane(airplane);
        f3.setArrival(date8);
        f3.setDeparture(date7);
        f3.setOrigin(d1);
        f3.setDestination(d2);
        f3.addSteward(s1);
        
        flightDao.create(f1);
        flightDao.create(f2);
        flightDao.create(f3);
        
        List<Flight> lastFlights = airplaneDao.findAirplaneFlights(airplane);
        Assert.notNull(lastFlights);
        assert lastFlights.size() == 3;
    }
}
