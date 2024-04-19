package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/profiles")
public class ProfilesCont extends Main {
    @GetMapping
    public String profiles(Model model) {
        addAttributes(model);
        model.addAttribute("usersList", usersRepo.findAllByOrderByRole());
        model.addAttribute("roles", Arrays.asList(Role.values()));
        return "profiles";
    }

    @PostMapping("/{id}/edit")
    public String profileEditRole(@PathVariable Long id, @RequestParam Role role) {
        Users user = usersRepo.getReferenceById(id);
        user.setRole(role);
        usersRepo.save(user);
        return "redirect:/profiles";
    }

    @GetMapping("/{id}/delete")
    public String profileDelete(@PathVariable Long id) {
        usersRepo.deleteById(id);
        return "redirect:/profiles";
    }
}
