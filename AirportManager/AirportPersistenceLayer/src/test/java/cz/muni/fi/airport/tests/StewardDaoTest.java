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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
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
 * @author Gabriela Podolnikova
 */
@ContextConfiguration(classes = {JpaTestContext.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class StewardDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private AirplaneDao airplaneDao;
    
    @Autowired
    private FlightDao flightDao;
    
    @Autowired
    public DestinationDao destinationDao;
        
    @Autowired
    public StewardDao stewardDao;
    
    Steward s1;
    Steward s2;
    SimpleDateFormat formatter;
    

    @BeforeMethod
    public void setUp() {
        s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        s2 = new Steward();
        Date birth2 = null;
        Date employment2 = null;
        try {
            birth2 = formatter.parse("1985/02/08");
            employment2 = formatter.parse("2013/04/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s2.setDateOfBirth(birth2);
        s2.setEmploymentDate(employment2);
        s2.setFirstname("Peter");
        s2.setSurname("Malick");
        s2.setGender(Gender.MALE);
        s2.setUsername("bbb");
        s2.setPersonalIdentificator("123-23456");
        
    }

    @Test
    public void testCreate() {    
        Assert.isNull(s1.getId());
        stewardDao.create(s1);
        Assert.notNull(stewardDao.findById(s1.getId()));     
    }
    
    @Test
    public void testUpdate() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        stewardDao.create(s1);
        Assert.notNull(stewardDao.findById(s1.getId()));
        s1.setSurname("Johnson");
        assert s1.getSurname().equals("Johnson");
        stewardDao.update(s1);
        assert stewardDao.findById(s1.getId()).getSurname().equals("Johnson");
    }
    
    @Test
    public void testRemove() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        stewardDao.create(s1);
        Assert.notNull(stewardDao.findById(s1.getId()));
        stewardDao.remove(s1);
        Assert.isNull(stewardDao.findById(s1.getId()));
    }
    
    @Test
    public void testFindByIdentificator() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        stewardDao.create(s1);
        assert stewardDao.findByIdentificator("123-12345").equals(s1);
    }
    
    @Test
    public void testFindByName() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("aaa");
        s1.setPersonalIdentificator("123-12345");
        
                
        Steward s2 = new Steward();
        Date birth2 = null;
        Date employment2 = null;
        try {
            birth2 = formatter.parse("1985/02/08");
            employment2 = formatter.parse("2013/04/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s2.setDateOfBirth(birth2);
        s2.setEmploymentDate(employment2);
        s2.setFirstname("Emma");
        s2.setSurname("Stevenson");
        s2.setGender(Gender.MALE);
        s2.setUsername("bbb");
        s2.setPersonalIdentificator("123-23456");
        
        Steward s3 = new Steward();
        Date birth3 = null;
        Date employment3 = null;
        try {
            birth3 = formatter.parse("1984/02/08");
            employment3 = formatter.parse("2014/04/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s3.setDateOfBirth(birth2);
        s3.setEmploymentDate(employment2);
        s3.setFirstname("Stevenson");
        s3.setSurname("Stevenson");
        s3.setGender(Gender.MALE);
        s3.setUsername("ccc");
        s3.setPersonalIdentificator("123-23457");
        
        Steward s4 = new Steward();
        Date birth4 = null;
        Date employment4 = null;
        try {
            birth4 = formatter.parse("1984/02/08");
            employment4 = formatter.parse("2010/04/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s4.setDateOfBirth(birth2);
        s4.setEmploymentDate(employment2);
        s4.setFirstname("Emma");
        s4.setSurname("Emma");
        s4.setGender(Gender.FEMALE);
        s4.setUsername("ddd");
        s4.setPersonalIdentificator("123-23458");
        
        stewardDao.create(s1);
        stewardDao.create(s2);
        stewardDao.create(s3);
        stewardDao.create(s4);
        
        List<Steward> stewards = stewardDao.findByName("Emma", "Stevenson");
        
        assert stewards.size() == 2;
        assert stewards.contains(s1);
        assert stewards.contains(s2);
    }
    
    @Test
    public void testFindAll() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("aaa");
        s1.setPersonalIdentificator("123-12345");
        
                
        Steward s2 = new Steward();
        Date birth2 = null;
        Date employment2 = null;
        try {
            birth2 = formatter.parse("1985/02/04");
            employment2 = formatter.parse("2013/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s2.setDateOfBirth(birth2);
        s2.setEmploymentDate(employment2);
        s2.setFirstname("Joey");
        s2.setSurname("Johnson");
        s2.setGender(Gender.MALE);
        s2.setUsername("bbb");
        s2.setPersonalIdentificator("123-23456");
        
        stewardDao.create(s1);
        stewardDao.create(s2);
        
        List<Steward> stewards = stewardDao.findAll();
        
        assert stewards.size() == 2;
        assert stewards.contains(s1);
        assert stewards.contains(s2);
        
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledPersonalIdentificator() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setUsername("bbb");
        s1.setGender(Gender.FEMALE);
        stewardDao.create(s1);

    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testIncorrectlyFilledPersonalIdentificator() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("Invalid");
        
        stewardDao.create(s1);

    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledFirstname() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        stewardDao.create(s1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledSurname() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        stewardDao.create(s1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledGender() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        stewardDao.create(s1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledDateOfBirt() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        stewardDao.create(s1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNotFilledEmploymentDate() {
        Steward s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setUsername("bbb");
        s1.setPersonalIdentificator("123-12345");
        
        stewardDao.create(s1);
    }
    
    @Test(expectedExceptions = PersistenceException.class)
    public void testPersonalIdentificatorUniquness() {
        s2.setPersonalIdentificator("123-12345");
        stewardDao.create(s1);
        stewardDao.create(s2);
    }
    
    @Test
    public void testFindAvailableStewards() throws ParseException {
        Airplane airA = new Airplane();
        airA.setCapacity(10);
        airA.setName("airA");
        airA.setType("typeA");
        
        airplaneDao.create(airA);
        
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
        s2.setUsername("ccc");
        s2.setGender(Gender.MALE);

        Steward awaibleS1 = new Steward();
        awaibleS1.setFirstname("fn2");
        awaibleS1.setSurname("sn3");
        awaibleS1.setPersonalIdentificator("111-11113");
        awaibleS1.setDateOfBirth(formatter.parse("1988/02/02"));
        awaibleS1.setEmploymentDate(formatter.parse("2014/03/01"));
        awaibleS1.setUsername("aaa");
        awaibleS1.setGender(Gender.FEMALE);

        Steward awaibleS2 = new Steward();
        awaibleS2.setFirstname("fn2");
        awaibleS2.setSurname("sn3");
        awaibleS2.setPersonalIdentificator("111-11114");
        awaibleS2.setDateOfBirth(formatter.parse("1988/02/02"));
        awaibleS2.setEmploymentDate(formatter.parse("2014/03/01"));
        awaibleS2.setUsername("asa");
        awaibleS2.setGender(Gender.FEMALE);

        stewardDao.create(s1);
        stewardDao.create(s2);
        stewardDao.create(awaibleS1);
        stewardDao.create(awaibleS2);
        
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
        f1.addSteward(s2);

        Flight f2 = new Flight();
        f2.setAirplane(airA);
        f2.setArrival(date4);
        f2.setDeparture(date3);
        f2.setOrigin(d1);
        f2.setDestination(d2);
        f2.addSteward(s2);
        
        flightDao.create(f1);
        flightDao.create(f2);

        List<Steward> availableStewards = stewardDao.findAvailableStewards(from, to);
        assert availableStewards.size() == 2;
    }
    
    @Test
    public void testfindLastStewardFlights() throws ParseException {
        Airplane airA = new Airplane();
        airA.setCapacity(10);
        airA.setName("airA");
        airA.setType("typeA");
        
        airplaneDao.create(airA);
        
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

        Steward awaibleS1 = new Steward();
        awaibleS1.setFirstname("fn2");
        awaibleS1.setSurname("sn3");
        awaibleS1.setPersonalIdentificator("111-11113");
        awaibleS1.setDateOfBirth(formatter.parse("1988/02/02"));
        awaibleS1.setEmploymentDate(formatter.parse("2014/03/01"));
        awaibleS1.setUsername("asda");
        awaibleS1.setGender(Gender.FEMALE);

        Steward awaibleS2 = new Steward();
        awaibleS2.setFirstname("fn2");
        awaibleS2.setSurname("sn3");
        awaibleS2.setPersonalIdentificator("111-11114");
        awaibleS2.setDateOfBirth(formatter.parse("1988/02/02"));
        awaibleS2.setEmploymentDate(formatter.parse("2014/03/01"));
        awaibleS2.setUsername("asdasaasa");
        awaibleS2.setGender(Gender.FEMALE);

        stewardDao.create(s1);
        stewardDao.create(s2);
        stewardDao.create(awaibleS1);
        stewardDao.create(awaibleS2);
        
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
        f1.addSteward(s2);

        Flight f2 = new Flight();
        f2.setAirplane(airA);
        f2.setArrival(date4);
        f2.setDeparture(date3);
        f2.setOrigin(d1);
        f2.setDestination(d2);
        f2.addSteward(s2);
        
        flightDao.create(f1);
        flightDao.create(f2);

        List<Flight> flights = stewardDao.findLastStewardFlights(s2);
        assert flights.size() == 2;
        
        flights = stewardDao.findLastStewardFlights(s1);
        assert flights.size() == 1;
    }
}
