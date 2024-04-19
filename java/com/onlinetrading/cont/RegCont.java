package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class RegCont extends Main {
    @GetMapping("/reg")
    public String reg(Model model) {
        return "reg";
    }

    @PostMapping("/reg")
    public String newUser(Model model, @RequestParam String username, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String password, @RequestParam String passwordRepeat, @RequestParam Role role) {
        if (usersRepo.findAll().isEmpty()) {
            usersRepo.save(new Users(username, firstname, lastname, password, Role.ADMIN, defAvatar));
            return "redirect:/login";
        }

        if (!Objects.equals(password, passwordRepeat)) {
            model.addAttribute("message", "Пароли не совпадают!");
            return "reg";
        }

        if (usersRepo.findByUsername(username) != null) {
            model.addAttribute("message", "Пользователь c таким адресом электронной почты уже существует!");
            return "reg";
        }

        usersRepo.save(new Users(username, firstname, lastname, password, role, defAvatar));

        return "redirect:/login";
    }
}
