package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutCont extends Main {
    @GetMapping("/about")
    public String about(Model model) {
        addAttributes(model);
        return "about";
    }
}
