package ru.atom.lecture07.server.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.lecture07.server.controller.ChatController;
import ru.atom.lecture07.server.dao.MessageDao;
import ru.atom.lecture07.server.dao.UserDao;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    static {
        log.info("****************************************** started");
    }

    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;

    @Nullable
    @Transactional
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByLogin(name);
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return new ArrayList<>(userDao.findAll());
    }

    @Transactional
    public void login(@NotNull String login) {
        User user = new User();
        user.setLogin(login);
        log.info("*** 2 service login: ");
        userDao.save(user);//.setLogin(login));//lgn 2
    }


    @Transactional
    public void say(String name, String message) {
        User _user = userDao.getByLogin(name);
        Message _mess = new Message();

        _mess.setUser(_user);
        _mess.setValue(message);
        _mess.setTime(new Date());

        messageDao.whatYouSaid(_mess);
        List <Message> _all_message=messageDao.getAllMessage();
    }

    @Transactional
    public List allUserMessage(Integer _id_user) {

        List <Message> allMess=messageDao.getAllMessageOneUser(_id_user);
        return allMess;


    }

    @Transactional
    public List getAllMessage() {
        List <Message> allMess=messageDao.getAllMessage();
        return allMess;
    }

    @Transactional
    public User getUser(String name) {
        return userDao.getByLogin(name);
    }


}
