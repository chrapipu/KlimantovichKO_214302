package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Main {
    @GetMapping("/index")
    public String Index1() {
        return "redirect:/profile";
    }

    @GetMapping("/")
    public String Index2() {
        return "redirect:/profile";
    }
}
