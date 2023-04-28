package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutCon {

    @RequestMapping(value = {"/logout"})
    public String logout() {
        return "login.html";
    }
}
