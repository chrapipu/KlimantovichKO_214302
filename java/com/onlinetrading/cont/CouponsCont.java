package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Coupons;
import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.Role;
import com.onlinetrading.repo.CouponsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coupons")
public class CouponsCont extends Main {
    @Autowired
    protected CouponsRepo couponsRepo;

    @GetMapping
    public String coupons(Model model) {
        addAttributes(model);
        if (getRole().equals(Role.MANAGER.name())) {
            model.addAttribute("coupons", couponsRepo.findAll());
            List<Users> owners = usersRepo.findAllByRole(Role.FIZ);
            owners.addAll(usersRepo.findAllByRole(Role.LEGAL));
            model.addAttribute("owners", owners);
        } else {
            model.addAttribute("coupons", getUser().getCoupons());
        }
        return "coupons";
    }

    @PostMapping("/add")
    public String couponAdd(@RequestParam String name, @RequestParam Long ownerId, @RequestParam int discount, @RequestParam int count) {
        couponsRepo.save(new Coupons(name, discount, count, usersRepo.getReferenceById(ownerId)));
        return "redirect:/coupons";
    }

    @GetMapping("/delete/{id}")
    public String couponDelete(@PathVariable Long id) {
        couponsRepo.deleteById(id);
        return "redirect:/coupons";
    }

}
