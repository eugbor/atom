package ru.atom.lecture07.server.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;
import ru.atom.lecture07.server.service.ChatService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




@Transactional
@Repository
public class MessageDaoImpl implements MessageDao{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Message> findAll() {
        return em.createQuery("Select t from " + Message.class.getSimpleName() + " t").getResultList();
    }

    @Override
    public void whatYouSaid(Message mes) {
        em.persist(mes);
        log.info("***[" + mes.getValue() + "] новый пользователь save");
    }

    @Override
    public List getAllMessageOneUser(Integer _id_user) {
        User _usr=em.find(User.class, _id_user);
        return _usr.getMessageOneUser();
    }

    @Override
    public List<Message> getAllMessage() {
//        ArrayList<Message> _all_message= (ArrayList<Message>) em.createQuery("select_all_message", Message.class);
        List<Message> _all_message= em.createQuery("FROM Message").getResultList();


        log.info("***All message: "+_all_message);

        return _all_message;
    }
}
