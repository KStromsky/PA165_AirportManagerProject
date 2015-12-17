package cz.muni.fi.airport.dao;

import cz.muni.fi.airport.entity.User;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User u) {
        em.persist(u);
    }

    @Override
    public User findUserByUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null userName");
        }

        try {
            return em
                    .createQuery("select u from User u where u.userName=:userName",
                            User.class).setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u",
                User.class);
        return (List<User>) query.getResultList();
    }

}
