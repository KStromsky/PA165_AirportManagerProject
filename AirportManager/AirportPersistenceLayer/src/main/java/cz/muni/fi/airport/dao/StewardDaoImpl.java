/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.dao;

import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * Implementation of Data Access Object for entity Steward
 *
 * @author Sebastian Kupka
 */
@Transactional
@Repository
public class StewardDaoImpl implements StewardDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Steward steward) {
        em.persist(steward);
    }

    @Override
    public Steward update(Steward steward) {
        return em.merge(steward);
    }

    @Override
    public void remove(Steward steward) throws IllegalArgumentException {
        em.remove(findById(steward.getId()));
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {
        em.remove(findById(id));
    }

    @Override
    public Steward findById(Long id) {
        return em.find(Steward.class, id);
    }

    @Override
    public Steward findByIdentificator(String identificator) {
        try {
            return em.createQuery("SELECT s FROM Steward s WHERE s.personalIdentificator like :pi ",
                    Steward.class).setParameter("pi", "%" + identificator + "%").getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Steward> findAll() {
        return em.createQuery("SELECT s FROM Steward s", Steward.class).getResultList();
    }

    @Override
    public List<Steward> findByName(String name, String surname) {
        if (name == null) {
            name = new String();
        } else {
            name = name.toLowerCase();
        }
        if (surname == null) {
            surname = new String();
        } else {
            surname = surname.toLowerCase();
        }
        return em.createQuery("SELECT s FROM Steward s WHERE Lower(s.firstname) LIKE :name AND Lower(s.surname) LIKE :surname", Steward.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("surname", "%" + surname + "%")
                .getResultList();
    }

    @Override
    public List<Steward> findAvailableStewards(Date fromDate, Date toDate) {
        return em.createQuery("SELECT s FROM Steward s WHERE s NOT IN "
                + "(SELECT fs FROM Flight f JOIN f.stewards fs WHERE "
                + "(f.arrival BETWEEN :fromDate AND :toDate)"
                + " OR (f.departure BETWEEN :fromDate AND :toDate)"
                + " OR (f.departure <= :fromDate AND f.arrival >= :toDate))")
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }

    @Override
    public List<Flight> findLastStewardFlights(Steward steward) {
        List<Flight> flights = em.createQuery("SELECT f FROM Flight f JOIN f.stewards fs WHERE fs.id = :stewardId"
                + " ORDER BY f.arrival DESC", Flight.class).setParameter("stewardId", steward.getId())
                .getResultList();
        return flights;
    }

    @Override
    public Steward findByUsername(String username) {
        try {
            return em.createQuery("SELECT s FROM Steward s WHERE s.username = :username", Steward.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
