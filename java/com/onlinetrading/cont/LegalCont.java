package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legal")
public class LegalCont extends Main {
    @GetMapping
    public String legal(Model model) {
        addAttributes(model);
        model.addAttribute("users", usersRepo.findAllByRole(Role.LEGAL));
        return "legal";
    }

    @GetMapping("/{id}/personal")
    public String legalPersonal(@PathVariable Long id) {
        Users user = usersRepo.getReferenceById(id);
        user.setLegal_personal(!user.isLegal_personal());
        usersRepo.save(user);
        return "redirect:/legal";
    }

    @GetMapping("/{id}/cumulative")
    public String legalCumulative(@PathVariable Long id) {
        Users user = usersRepo.getReferenceById(id);
        user.setLegal_cumulative(!user.isLegal_cumulative());
        usersRepo.save(user);
        return "redirect:/legal";
    }

    @GetMapping("/{id}/coupon")
    public String legalCoupon(@PathVariable Long id) {
        Users user = usersRepo.getReferenceById(id);
        user.setLegal_coupon(!user.isLegal_coupon());
        usersRepo.save(user);
        return "redirect:/legal";
    }
}
