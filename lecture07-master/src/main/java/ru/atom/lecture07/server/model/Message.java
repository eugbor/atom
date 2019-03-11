package ru.atom.lecture07.server.model;

import javax.persistence.*;
import java.util.Date;

//@org.hibernate.annotations.NamedQueries({
//        @org.hibernate.annotations.NamedQuery(name="select_all_message",query = "from chat.message")
//})

@Entity
@Table(name = "message", schema = "chat")
public class Message {

    public Message (){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "time", nullable = false)
    private Date time = new Date();

    @Column(name = "value", unique = false, nullable = false, length = 1000)
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id")
    private User user;


    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Message setTime(Date timestamp) {
        this.time = timestamp;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Message setValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user=" + user +
                ", timestamp=" + time +
                ", value='" + value + '\'' +
                '}';
    }
}
