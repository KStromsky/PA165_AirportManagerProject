/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.dao;

import cz.muni.fi.airport.entity.Steward;
import java.util.List;
import javax.persistence.EntityManager;
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
    public Steward findById(Long id) {
        return em.find(Steward.class, id);
    }

    @Override
    public Steward findIdentificator(String identificator) {
        return em.createQuery("SELECT s FROM Steward s WHERE s.personalIdentificator like :pi ",
				Steward.class).setParameter("pi", "%" + identificator + "%").getSingleResult();
    }

    @Override
    public List<Steward> findAll() {
        return em.createQuery("SELECT s FROM Steward s", Steward.class).getResultList();
    }
    
}
