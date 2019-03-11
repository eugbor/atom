package ru.atom.lecture07.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("date")
public class Date_example {

//    @Autowired
//    private Date _my_time;

    @RequestMapping(path = "date", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String date() {


        System.out.println("____get time____");
        return new Date().toString();
//        return _my_time.toString();
    }


}
