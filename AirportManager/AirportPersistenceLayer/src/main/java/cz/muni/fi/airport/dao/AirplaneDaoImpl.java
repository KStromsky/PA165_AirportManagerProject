package cz.muni.fi.airport.dao;

import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Flight;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jakub Stromský
 */
@Repository
public class AirplaneDaoImpl implements AirplaneDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Airplane a) {
        em.persist(a);
    }

    @Override
    public void delete(Airplane a) {
        em.remove(a);
    }
    
     @Override
    public void update(Airplane a) {
        em.merge(a);
    }

    @Override
    public List<Airplane> findAllAirplanes() {
        return em.createQuery("SELECT a FROM Airplane a", Airplane.class).getResultList();
    }

    @Override
    public Airplane findById(Long id) {
        return em.find(Airplane.class, id);
    }

    @Override
    public List<Airplane> findByName(String name) {
        if (name == null) {
            name = new String();
        } else {
            name = name.toLowerCase();
        }
        return em.createQuery("SELECT a FROM Airplane a WHERE Lower(a.name) like :name ",
                Airplane.class).setParameter("name", "%" + name + "%").getResultList();
    }
        
    @Override
    public List<Airplane> findAvailableAirplanes(Date fromDate, Date toDate) {
        if(fromDate.after(toDate)) {
            throw new IllegalArgumentException("From date must be before to date");
        }
        return em.createQuery("SELECT a FROM Airplane a WHERE a NOT IN "
                + "(SELECT f.airplane FROM Flight f WHERE "
                + "(f.arrival BETWEEN :fromDate AND :toDate)"
                + " OR (f.departure BETWEEN :fromDate AND :toDate)"
                + " OR (f.departure <= :fromDate AND f.arrival >= :toDate))")
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }

    @Override
    public List<Flight> findAirplaneFlights(Airplane a) {
        List<Flight> flights = em.createQuery("SELECT f FROM Flight f WHERE f.airplane = :airplane"
                + " ORDER BY f.arrival DESC", Flight.class).setParameter("airplane", a)
                .getResultList();
        return flights;
    }  
}
