package ru.atom.lecture07.server.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.lecture07.server.model.ChatMessageAllUsers;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;
import ru.atom.lecture07.server.service.ChatService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatController.class);

    private Map<String, Long> usersOnline = new ConcurrentHashMap<>();

    @Autowired
    private ChatService chatService;

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(path = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {

        if (name.length() < 1) {
            return ResponseEntity.badRequest()
                    .body("Too short name");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("Too long name");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn != null) {
            log.info("*** existing user: " + alreadyLoggedIn.toString());
            return ResponseEntity.badRequest()
                    .body("Already logged in");
        }

        log.info("*** 1 controller login: " + name);
        chatService.login(name);//lgn 1
        chatService.say(name, "Новый пользователь: " + name);

        usersOnline.put(name, new Date().getTime());

        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {

        List<Message> _ms = chatService.getAllMessage();
        String _response = "";
        for (Message _m : _ms) {
            _response = _response + _m.getTime() + " " + _m.getUser().getLogin() + " " + _m.getValue() + "\n";
        }
        return new ResponseEntity<>(_response, HttpStatus.OK);
//        return ResponseEntity.badRequest().build();
    }


    @RequestMapping(path = "ping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity ping(@RequestParam("name") String name) {
        System.out.println("ping " + name);
        if (!usersOnline.containsKey(name)){
            if (chatService.getUser(name)!= null) usersOnline.put(name, new Date().getTime());
        }

        for (Map.Entry<String, Long> entry : usersOnline.entrySet()) {
            if (entry.getKey().equals(name)) usersOnline.put(name, new Date().getTime());
            System.out.println("Map: " + entry.getKey() + " " + entry.getValue());

            if ((new Date().getTime() - entry.getValue()) > 10000) usersOnline.remove(entry.getKey());
        }

        return ResponseEntity.ok().build();
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//TODO
    }


    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(path = "users", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> users() {
//        String _s = usersOnline.entrySet().stream().map(e->e.getKey()+"="+e.getValue()).collect(Collectors.joining("\n"));
        String _s = usersOnline.entrySet().stream().map(e -> e.getKey()).collect(Collectors.joining("\n"));
        return new ResponseEntity<>(_s, HttpStatus.OK);
    }







    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(path = "logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        return ResponseEntity.badRequest().build();
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(path = "online", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {

        List<User> online = chatService.getOnlineUsers();
        String responseBody = online.stream()
                .map(User::getLogin)
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok().body(responseBody);
    }

    @RequestMapping(
            path = "getAllSay",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity getallsay() {
        List<ChatMessageAllUsers> _messages_chat = new ArrayList<ChatMessageAllUsers>();
        ChatMessageAllUsers _m4 = new ChatMessageAllUsers();

        List<User> online = chatService.getOnlineUsers();
        log.info("*** all users " + online.size());

        for (User _usr : online) {
            List<Message> _mess = chatService.allUserMessage(_usr.getId());
            for (Message _mes : _mess) {
                _m4 = new ChatMessageAllUsers();
                _m4.setNameUser(_usr.getLogin());
                _m4.setTime(_mes.getTime());
                _m4.setValue(_mes.getValue());
                _messages_chat.add(_m4);
//                log.info("*** message: "+_m4.getNameUser()+" "+
//                        _m4.getValue());
            }
        }
//        for (ChatMessageAllUsers _one :_messages_chat   ) {
//            log.info("*** message: "+_one.getNameUser()+" "+ _one.getValue());
//        }

        String responseBody = _messages_chat.stream()
                .map(ChatMessageAllUsers::toString)
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok().body(responseBody);
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        log.info("*** say name " + name + " " + " msg " + msg);

        if (name.length() < 1) {
            return ResponseEntity.badRequest()
                    .body("***Too short name");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("***Too long name");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(name);
//        log.info("*** say verifi logined user "+alreadyLoggedIn.getLogin());
        if (alreadyLoggedIn == null) {
            log.info("*** say user not logined ");
            return ResponseEntity.badRequest()
                    .body("***user not logined");
        }

        log.info("*** say valid. write to chat ");
        chatService.say(name, msg);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(
            path = "getAllUserSay",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sayUser(@RequestParam("name") String name) {
        log.info("*** say name " + name);

        if (name.length() < 1) {
            return ResponseEntity.badRequest()
                    .body("***Too short name");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("***Too long name");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn == null) {
            log.info("*** say user not logined ");
            return ResponseEntity.badRequest()
                    .body("***user not logined");
        }

        User _user = chatService.getLoggedIn(name);

        List<Message> mess = chatService.allUserMessage(_user.getId());
        log.info("*** send all message " + mess.size());


        String responseBody = mess.stream().map(Message::getValue).collect(Collectors.joining("\n"));

        return ResponseEntity.ok().body(responseBody);
    }


}
