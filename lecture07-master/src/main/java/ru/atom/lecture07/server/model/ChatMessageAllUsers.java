package ru.atom.lecture07.server.model;

import java.util.Date;

public class ChatMessageAllUsers {
private String NameUser;
private Date time;
private String Value;

    public ChatMessageAllUsers() {
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return NameUser+"   "+time+"   "+ Value;
    }
}
