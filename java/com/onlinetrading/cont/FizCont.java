package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fiz")
public class FizCont extends Main {
    @GetMapping
    public String fiz(Model model) {
        addAttributes(model);
        model.addAttribute("users", usersRepo.findAllByRole(Role.FIZ));
        return "fiz";
    }
}
