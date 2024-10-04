package ru.mgrom.roadanalyzer.page;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.service.SpendingService;

@Controller
public class SpendingPageController {

    // private final SpendingService spendingService;

    // public SpendingPageController(SpendingService spendingService) {
    //     this.spendingService = spendingService;
    // }

    // @GetMapping("/spendings")
    // public String getAllSpendings(Model model, @RequestParam(required = false) LocalDate createdAtBefore,
    //         @RequestParam(required = false) LocalDate createdAtAfter) {

    //     List<Spending> spendings = spendingService.getAll(createdAtBefore, createdAtAfter);
    //     model.addAttribute("spendings", spendings);
    //     return "spendings";
    // }
}