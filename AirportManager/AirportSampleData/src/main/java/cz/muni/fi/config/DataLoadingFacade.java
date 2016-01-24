package cz.muni.fi.config;

import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airport.entity.User;
import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportservicelayer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Jakub Stromský
 * @author Sebastian Kupka
 */
@Transactional
@Component
public class DataLoadingFacade {

    @Autowired
    private StewardService stewardService;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private UserService userService;

    public void loadData() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 1, 2, 0);
        Date date1 = cal.getTime();
        cal.set(2015, 1, 1, 12, 30);
        Date date2 = cal.getTime();
        cal.set(2015, 1, 2, 0, 0);
        Date date3 = cal.getTime();

        Airplane a1 = new Airplane();
        a1.setCapacity(1);
        a1.setName("airA");
        a1.setType("typeA");

        airplaneService.create(a1);

        Airplane a2 = new Airplane();
        a2.setCapacity(2);
        a2.setName("airB");
        a2.setType("typeB");

        airplaneService.create(a2);

        Destination d1 = new Destination();
        d1.setLocation("Brno");

        destinationService.create(d1);

        Destination d2 = new Destination();
        d2.setLocation("Praha");

        destinationService.create(d2);

        Steward s1 = new Steward();
        s1.setFirstname("Hank");
        s1.setSurname("Struman");
        s1.setPersonalIdentificator("111-11111");
        s1.setDateOfBirth(formatter.parse("1988/02/02"));
        s1.setEmploymentDate(formatter.parse("2014/03/01"));
        s1.setUsername("hank");
        s1.setIsAdmin(true);
        s1.setGender(Gender.MALE);

        stewardService.createSteward(s1,"heslo");

        Steward s2 = new Steward();
        s2.setFirstname("Peter");
        s2.setSurname("File");
        s2.setPersonalIdentificator("111-11112");
        s2.setDateOfBirth(formatter.parse("1988/02/02"));
        s2.setEmploymentDate(formatter.parse("2014/03/01"));
        s2.setUsername("peter");
        s2.setIsAdmin(false);
        s2.setGender(Gender.MALE);

        stewardService.createSteward(s2, "heslo");

        Steward s3 = new Steward();
        s3.setFirstname("Joan");
        s3.setSurname("Solverson");
        s3.setPersonalIdentificator("111-11113");
        s3.setDateOfBirth(formatter.parse("1988/02/02"));
        s3.setEmploymentDate(formatter.parse("2014/03/01"));
        s3.setUsername("joan");
        s3.setIsAdmin(false);
        s3.setGender(Gender.FEMALE);

        stewardService.createSteward(s3, "heslo");

        Flight f1 = new Flight();
        f1.setAirplane(a1);
        f1.setArrival(date2);
        f1.setDeparture(date1);
        f1.setOrigin(d1);
        f1.setDestination(d2);
        f1.addSteward(s1);
        f1.addSteward(s2);

        flightService.create(f1);

        Flight f2 = new Flight();
        f2.setAirplane(a2);
        f2.setArrival(date3);
        f2.setDeparture(date2);
        f2.setOrigin(d2);
        f2.setDestination(d1);
        f2.addSteward(s3);

        flightService.create(f2);

        User user = new User();
        user.setAdmin(false);
        user.setUserName("user");
        userService.registerUser(user, "user");

        User admin = new User();
        admin.setAdmin(true);
        admin.setUserName("admin");
        userService.registerUser(admin, "admin");
    }
}
