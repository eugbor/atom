package ru.atom.lecture07.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "chat")
public class User {

    public User (){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Message> messageOneUser;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public List<Message> getMessageOneUser() {
        return messageOneUser;
    }

    public void setMessageOneUser(List<Message> messageOneUser) {
        this.messageOneUser = messageOneUser;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
