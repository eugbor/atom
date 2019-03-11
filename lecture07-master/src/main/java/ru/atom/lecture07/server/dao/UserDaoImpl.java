package ru.atom.lecture07.server.dao;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture07.server.model.User;
import ru.atom.lecture07.server.service.ChatService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getByLogin(String login) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("login"), login));
        TypedQuery<User> typed = em.createQuery(criteria);
        User user;
        try {
            user = typed.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    @Override
    public void save(User user) {//
        log.info("*** 3 daoi mpl login: "+user.getLogin());
        em.persist(user);//lgn 3
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("Select t from " + User.class.getSimpleName() + " t").getResultList();
    }
}

























