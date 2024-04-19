package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Apps;
import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.AppStatus;
import com.onlinetrading.models.enums.Role;
import com.onlinetrading.repo.AppsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apps")
public class AppsCont extends Main {
    @Autowired
    protected AppsRepo appsRepo;

    @GetMapping
    public String apps(Model model) {
        addAttributes(model);
        if (getUser().getRole() == Role.MANAGER) {
            model.addAttribute("apps", appsRepo.findAllByStatus(AppStatus.WAITING));
        } else {
            model.addAttribute("apps", getUser().getApps());
        }
        return "apps";
    }

    @PostMapping("/add")
    public String appAdd(@RequestParam String text, @RequestParam int discount) {
        appsRepo.save(new Apps(text, discount, getUser()));
        return "redirect:/apps";
    }

    @GetMapping("/confirm/{id}")
    public String appConfirm(@PathVariable Long id) {
        Apps app = appsRepo.getReferenceById(id);
        app.setStatus(AppStatus.CONFIRMED);
        appsRepo.save(app);

        Users user = app.getOwner();
        user.setDiscount(app.getDiscount());
        usersRepo.save(user);

        return "redirect:/apps";
    }

    @PostMapping("/reject/{id}")
    public String appReject(@PathVariable Long id, @RequestParam String reason) {
        Apps app = appsRepo.getReferenceById(id);
        app.setStatus(AppStatus.REJECT);
        app.setReason(reason);
        appsRepo.save(app);
        return "redirect:/apps";
    }

}
