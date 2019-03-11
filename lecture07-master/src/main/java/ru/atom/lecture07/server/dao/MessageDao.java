package ru.atom.lecture07.server.dao;

import ru.atom.lecture07.server.model.Message;

import java.util.List;


public interface MessageDao {

    List<Message> findAll();

    List <Message> getAllMessageOneUser(Integer _id);

    List <Message> getAllMessage();

    void whatYouSaid (Message mes);

}
