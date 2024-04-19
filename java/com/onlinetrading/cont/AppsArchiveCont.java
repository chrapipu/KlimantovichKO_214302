package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Apps;
import com.onlinetrading.models.enums.AppStatus;
import com.onlinetrading.repo.AppsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/apps/archive")
public class AppsArchiveCont extends Main {
    @Autowired
    protected AppsRepo appsRepo;

    @GetMapping
    public String appsArchive(Model model) {
        addAttributes(model);
        List<Apps> apps = appsRepo.findAllByStatus(AppStatus.CONFIRMED);
        apps.addAll(appsRepo.findAllByStatus(AppStatus.REJECT));
        model.addAttribute("apps", apps);
        return "appsArchive";
    }
}
