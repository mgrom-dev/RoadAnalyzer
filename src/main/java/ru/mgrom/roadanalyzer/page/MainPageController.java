package ru.mgrom.roadanalyzer.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.service.SessionUtils;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        User user = SessionUtils.getUser(request);
        model.addAttribute("isActiveUser", user.isActive());
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

    @GetMapping("/expenses")
    public String expenses() {
        return "expenses";
    }
}
