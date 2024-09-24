package ru.mgrom.roadanalyzer.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String home() {
        return "index"; // return template index.html
    }

    @GetMapping("/overview")
    public String overview() {
        return "overview"; // return template overview.html
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "notifications";
    }

    @GetMapping("/actions")
    public String actions() {
        return "actions";
    }
}
