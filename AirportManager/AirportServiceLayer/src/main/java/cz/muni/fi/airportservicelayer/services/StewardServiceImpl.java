/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.dao.DestinationDao;
import cz.muni.fi.airport.dao.StewardDao;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airportservicelayer.exceptions.IllegalArgumentDataException;
import cz.muni.fi.airportservicelayer.exceptions.BasicDataAccessException;
import cz.muni.fi.airportservicelayer.exceptions.ValidationDataException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.validation.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastian Kupka
 */
@Service
public class StewardServiceImpl implements StewardService {

    @Inject
    private StewardDao stewardDao;

    @Override
    public Steward findById(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return stewardDao.findById(id);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public Steward findByPersonalIdentificator(String personalIdentificator) {
        if (personalIdentificator == null) {
            return null;
        }
        try {
            return stewardDao.findByIdentificator(personalIdentificator);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Steward> findAllStewards() {
        try {
            return stewardDao.findAll();
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Steward> findByName(String name, String surname) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        if (surname == null) {
            throw new IllegalArgumentException("surname");
        }
        try {
            return stewardDao.findByName(name, surname);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public Long createSteward(Steward steward, String password) {
        if (steward == null || password == null) {
            return null;
        }
        steward.setPwHash(createHash(password));
        try {
            stewardDao.create(steward);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
        return steward.getId();
    }

    @Override
    public void removeSteward(Long id) {
        if (id == null) {
            return;
        }
        try {
            stewardDao.remove(id);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public void updateSteward(Steward update, String password) {
        if (update == null || update.getId() == null) {
            return;
        }

        if (password != null) {
            update.setPwHash(createHash(password));
        }
        try {
            stewardDao.update(update);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Flight> findStewardFlights(Steward steward) {
        if (steward == null || steward.getId() == null) {
            return new ArrayList<Flight>();
        }
        try {
            return stewardDao.findLastStewardFlights(steward);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Steward> getRelevantStewards(String personalIdentificator, String name, String surname) {

        if (personalIdentificator == null) {
            personalIdentificator = new String();
        } else {
            personalIdentificator = personalIdentificator.toUpperCase();
        }
        try {
            List<Steward> all = stewardDao.findByName(name, surname);

            List<Steward> filtered = new ArrayList<Steward>();
            for (Steward steward : all) {
                if (steward.getPersonalIdentificator().contains(personalIdentificator)) {
                    filtered.add(steward);
                }
            }
            return filtered;
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Steward> findAvailableStewards(Date fromDate, Date toDate) {
        if (fromDate == null) {
            throw new IllegalArgumentException("fromDate");
        }
        if (toDate == null) {
            throw new IllegalArgumentException("toDate");
        }
        if (fromDate.compareTo(toDate) > 0) {
            throw new IllegalArgumentException("fromDate is later than toDate");
        }
        try {
            return stewardDao.findAvailableStewards(fromDate, toDate);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    //Advanced service
    @Override
    public List<Steward> findSpecificStewards(Date fromDate, Date toDate, Long locationId) {
        List<Steward> availableStewards = null;
        if (fromDate != null || toDate != null) {
            if (fromDate == null) {
                fromDate = toDate;
            }

            if (toDate == null) {
                toDate = fromDate;
            }
            availableStewards = this.findAvailableStewards(fromDate, toDate);
        } else {
            availableStewards = this.findAllStewards();
        }
        if (locationId != null) {
            return findSpecificStewards(availableStewards, locationId);
        } else {
            return availableStewards;
        }
    }

    @Override
    public List<Steward> findAvailableStewardsAtLocation(long locationId) {
        return this.findSpecificStewards(null, null, locationId);
    }

    private List<Steward> findSpecificStewards(List<Steward> availableStewards, long locationId) {
        List<Steward> specificAirplanes = new ArrayList<>();
        for (Steward steward : availableStewards) {
            Destination dest = this.findStewardLocation(steward);
            if (dest != null && new Long(locationId).equals(dest.getId())) {
                specificAirplanes.add(steward);
            }
        }
        return specificAirplanes;
    }

    @Override
    public Destination findStewardLocation(Steward steward) {
        List<Flight> flights = this.findStewardFlights(steward);
        if (flights == null || flights.isEmpty()) {
            return null;
        } else {
            return flights.get(0).getDestination();
        }
    }

    @Override
    public Steward findByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username is null");
        }
        try {
            return stewardDao.findByUsername(username);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public boolean authentication(Steward steward, String pw) {
        if (steward == null) {
            return false;
        }
        try {
            return validatePassword(pw, steward.getPwHash());
        } catch (Exception ex) {
            throw new ValidationDataException(ex);
        }
    }

    //see  https://crackstation.net/hashing-security.htm#javasourcecode
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if (password == null) {
            return false;
        }
        if (correctHash == null) {
            throw new IllegalArgumentException("password hash is null");
        }
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }
}
