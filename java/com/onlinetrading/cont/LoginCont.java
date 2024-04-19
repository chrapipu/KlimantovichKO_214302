package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Settings;
import com.onlinetrading.repo.SettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCont extends Main {

    @Autowired
    private SettingsRepo settingsRepo;

    @GetMapping("/login")
    public String login() {
        if (settingsRepo.findAll().isEmpty()) {
            settingsRepo.save(new Settings());
        }
        return "login";
    }
}
