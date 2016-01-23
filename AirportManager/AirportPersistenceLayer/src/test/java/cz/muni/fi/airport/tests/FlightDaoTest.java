/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.tests;

import cz.muni.fi.airport.JpaTestContext;
import cz.muni.fi.airport.dao.*;
import cz.muni.fi.airport.entity.*;
import cz.muni.fi.airport.enums.Gender;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sebastian Kupka
 */
@ContextConfiguration(classes = {JpaTestContext.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class FlightDaoTest extends AbstractTestNGSpringContextTests {
    
        @Autowired
	public FlightDao flightDao;

	@Autowired
	public StewardDao stewardDao;

	@Autowired
	public DestinationDao destinationDao;
        
        @Autowired
	public AirplaneDao airplaneDao;

	private Steward s1;
	private Steward s2;
        private Steward s3;
	private Destination d1;
	private Destination d2;
	private Airplane a1;
	private Airplane a2;
	private Flight f1;
        
        private Date date1;
	private Date date2;
	private Date date3;
        
        @BeforeMethod
        public void before() throws ParseException {
            
            Calendar cal = Calendar.getInstance();
            cal.set(2015, 1, 1, 2, 0);
            date1 = cal.getTime();
            cal.set(2015, 1, 1, 12, 30);
            date2 = cal.getTime();
            cal.set(2015, 1, 2, 0, 0);
            date3 = cal.getTime();
            
            a1 = new Airplane();
            a1.setCapacity(1);
            a1.setName("airA");
            a1.setType("typeA");
            
            a2 = new Airplane();
            a2.setCapacity(2);
            a2.setName("airB");
            a2.setType("typeB");
            
            airplaneDao.create(a1);
            airplaneDao.create(a2);
            
            d1 = new Destination();
            d1.setLocation("Brno");
            
            d2 = new Destination();
            d2.setLocation("Praha"); 

            destinationDao.create(d1);
            destinationDao.create(d2);
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            
            s1 = new Steward();
            s1.setFirstname("fn1");
            s1.setSurname("sn1");
            s1.setPersonalIdentificator("111-11111");
            s1.setDateOfBirth(formatter.parse("1988/02/02"));
            s1.setEmploymentDate(formatter.parse("2014/03/01"));
            s1.setUsername("bbb");
            s1.setGender(Gender.MALE);
            
            s2 = new Steward();
            s2.setFirstname("fn2");
            s2.setSurname("sn1");
            s2.setPersonalIdentificator("111-11112");
            s2.setDateOfBirth(formatter.parse("1988/02/02"));
            s2.setEmploymentDate(formatter.parse("2014/03/01"));
            s2.setUsername("ccc");
            s2.setGender(Gender.MALE);
            
            s3 = new Steward();
            s3.setFirstname("fn2");
            s3.setSurname("sn3");
            s3.setPersonalIdentificator("111-11113");
            s3.setDateOfBirth(formatter.parse("1988/02/02"));
            s3.setEmploymentDate(formatter.parse("2014/03/01"));
            s3.setUsername("ddd");
            s3.setGender(Gender.FEMALE);
            
            stewardDao.create(s1);
            stewardDao.create(s2);
            stewardDao.create(s3);
            
            f1 = new Flight();
            f1.setAirplane(a1);
            f1.setArrival(date2);
            f1.setDeparture(date1);
            f1.setOrigin(d1);
            f1.setDestination(d2);
            f1.addSteward(s1);
            f1.addSteward(s2);    
        }
        
        @Test
        public void testCreate() {         
            Assert.isNull(f1.getId());
            flightDao.create(f1);
            Assert.notNull(flightDao.findById(f1.getId()));
        }
        
        @Test
        public void testFind() {    
            
            Flight f1 = new Flight();
            f1.setAirplane(a1);
            f1.setArrival(date2);
            f1.setDeparture(date1);
            f1.setOrigin(d1);
            f1.setDestination(d2);
            f1.addSteward(s1);
            f1.addSteward(s2);  
            
            Assert.isNull(f1.getId());
            flightDao.create(f1);
            f1 = flightDao.findById(f1.getId());
            Assert.notNull(f1);
            assert f1.getAirplane().equals(a1);
            assert f1.getArrival().equals(date2);
            assert f1.getDeparture().equals(date1);
            assert f1.getOrigin().equals(d1);
            assert f1.getDestination().equals(d2);
            assert f1.getStewards().size() == 2;
            
        }
        
        @Test
        public void testUpdate() { 
           flightDao.create(f1);
           Assert.notNull(flightDao.findById(f1.getId()));
           f1.setAirplane(a2);
           f1.setArrival(date3);
           f1.setDeparture(date2);
           f1.setOrigin(d2);
           f1.setDestination(d1);
           f1.addSteward(s3);
           flightDao.update(f1);
           
           f1 = flightDao.findById(f1.getId());
           
           assert flightDao.findById(f1.getId()).getAirplane().equals(airplaneDao.findById(a2.getId()));
           assert flightDao.findById(f1.getId()).getArrival().equals(date3);
           assert flightDao.findById(f1.getId()).getDeparture().equals(date2);
           assert flightDao.findById(f1.getId()).getOrigin().equals(destinationDao.findById(d2.getId()));
           assert flightDao.findById(f1.getId()).getDestination().equals(destinationDao.findById(d1.getId()));
           assert flightDao.findById(f1.getId()).getStewards().size() == 3;
        }
    
    @Test
    public void testRemove() {
        flightDao.create(f1);
        Assert.notNull(flightDao.findById(f1.getId()));
        flightDao.delete(f1);
        Assert.isNull(flightDao.findById(f1.getId()));
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledAirplane() {
        f1.setAirplane(null);
        flightDao.create(f1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledDestination() {
        f1.setDestination(null);
        flightDao.create(f1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledOrigin() {
        f1.setOrigin(null);
        flightDao.create(f1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledArrival() {
        f1.setArrival(null);
        flightDao.create(f1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledDeparture() {
        f1.setDeparture(null);
        flightDao.create(f1);
    }
    
    @Test
    public void testSorted() {
        Flight f3 = new Flight();
        f3.setAirplane(a1);
        f3.setArrival(date2);
        f3.setDeparture(date3);
        f3.setOrigin(d1);
        f3.setDestination(d2);
        f3.addSteward(s2); 
        
        Flight f2 = new Flight();
        f2.setAirplane(a2);
        f2.setArrival(date3);
        f2.setDeparture(date2);
        f2.setOrigin(d2);
        f2.setDestination(d1);
        f2.addSteward(s1); 
        
        Flight f1 = new Flight();
        f1.setAirplane(a2);
        f1.setArrival(date1);
        f1.setDeparture(date1);
        f1.setOrigin(d1);
        f1.setDestination(d2);
        f1.addSteward(s3); 
        
        flightDao.create(f3);
        flightDao.create(f2);
        flightDao.create(f1);
        
        Assert.notNull(flightDao.findById(f1.getId()));
        Assert.notNull(flightDao.findById(f2.getId()));
        Assert.notNull(flightDao.findById(f3.getId()));
        
        List<Flight> flights = flightDao.listByDate(true);
        
        assert f1.equals(flights.get(0));
        assert f3.equals(flights.get(1));
        assert f2.equals(flights.get(2));
        
        
        flights = flightDao.listByDate(false);
        
        assert f1.equals(flights.get(0));
        assert f2.equals(flights.get(1));
        assert f3.equals(flights.get(2));
    }
    
    @Test
    public void testFindAll() {
        Flight f3 = new Flight();
        f3.setAirplane(a1);
        f3.setArrival(date2);
        f3.setDeparture(date1);
        f3.setOrigin(d1);
        f3.setDestination(d2);
        f3.addSteward(s2); 
        
        Flight f2 = new Flight();
        f2.setAirplane(a1);
        f2.setArrival(date3);
        f2.setDeparture(date2);
        f2.setOrigin(d2);
        f2.setDestination(d1);
        f2.addSteward(s1); 
        
        Flight f1 = new Flight();
        f1.setAirplane(a2);
        f1.setArrival(date3);
        f1.setDeparture(date1);
        f1.setOrigin(d1);
        f1.setDestination(d2);
        f1.addSteward(s3); 
        
        flightDao.create(f1);
        flightDao.create(f2);
        flightDao.create(f3);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(f1);
        flights.add(f2);
        flights.add(f3);
        
        for (Flight f : flightDao.findAll()) {
            assert testFlightPresent(f, flights);
        }
    }
    
    private boolean testFlightPresent(Flight flight, List<Flight> flights) {
        boolean match = false;
        for (Flight f : flights) {
            if (f.equals(flight)) {
                match = true;
                break;
            }
        }
        return match;
    }
}
