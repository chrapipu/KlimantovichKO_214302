package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Cumulatives;
import com.onlinetrading.models.Settings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingsCont extends Main {
    @GetMapping
    public String settings(Model model) {
        addAttributes(model);
        model.addAttribute("settings", getSettings());
        model.addAttribute("cumulatives", cumulativesRepo.findAllByOrderByMin());
        return "settings";
    }

    @GetMapping("/fiz/personal")
    public String legalPersonal() {
        Settings settings = getSettings();
        settings.setFiz_personal(!settings.isFiz_personal());
        settingsRepo.save(settings);
        return "redirect:/settings";
    }

    @GetMapping("/fiz/cumulative")
    public String legalCumulative() {
        Settings settings = getSettings();
        settings.setFiz_cumulative(!settings.isFiz_cumulative());
        settingsRepo.save(settings);
        return "redirect:/settings";
    }

    @GetMapping("/fiz/coupon")
    public String legalCoupon() {
        Settings settings = getSettings();
        settings.setFiz_coupon(!settings.isFiz_coupon());
        settingsRepo.save(settings);
        return "redirect:/settings";
    }

    @PostMapping("/edit/firstDis")
    public String settingFirstDis(@RequestParam int firstDis) {
        Settings setting = getSettings();
        setting.setFirstDis(firstDis);
        settingsRepo.save(setting);
        return "redirect:/settings";
    }

    @PostMapping("/edit/pension")
    public String settingFirstDis(@RequestParam int pensionAge, @RequestParam int pensionDis) {
        Settings setting = getSettings();
        setting.setPensionAge(pensionAge);
        setting.setPensionDis(pensionDis);
        settingsRepo.save(setting);
        return "redirect:/settings";
    }

    @PostMapping("/edit/birthday")
    public String settingBirthday(@RequestParam int birthday, @RequestParam int birthdayDis) {
        Settings setting = getSettings();
        setting.setBirthday(birthday);
        setting.setBirthdayDis(birthdayDis);
        settingsRepo.save(setting);
        return "redirect:/settings";
    }

    @PostMapping("/edit/multi")
    public String settingMulti(@RequestParam int multi, @RequestParam int multiDis) {
        Settings setting = getSettings();
        setting.setMulti(multi);
        setting.setMultiDis(multiDis);
        settingsRepo.save(setting);
        return "redirect:/settings";
    }

    @PostMapping("/cumulatives/add")
    public String cumulativeAdd(Model model, @RequestParam int min, @RequestParam int max, @RequestParam int discount) {
        List<Cumulatives> cumulatives = cumulativesRepo.findAllByOrderByMin();

        if (max != 0) {
            if (max < min) {
                model.addAttribute("message", "Минимальное значение превышает максимальное!");
                addAttributes(model);
                model.addAttribute("settings", getSettings());
                model.addAttribute("cumulatives", cumulativesRepo.findAllByOrderByMin());
                return "settings";
            }
        }

        if (cumulatives.isEmpty()) {
            cumulativesRepo.save(new Cumulatives(min, max, discount));
        } else {
            if (cumulatives.get(0).getMin() == max && cumulatives.get(0).getDiscount() > discount) {
                cumulativesRepo.save(new Cumulatives(min, max, discount));
            } else if (cumulatives.get(cumulatives.size() - 1).getMax() == 0) {
                model.addAttribute("message", "Некорректные данные!");
                addAttributes(model);
                model.addAttribute("settings", getSettings());
                model.addAttribute("cumulatives", cumulativesRepo.findAllByOrderByMin());
                return "settings";
            } else if (cumulatives.get(cumulatives.size() - 1).getMax() == min && cumulatives.get(cumulatives.size() - 1).getDiscount() < discount) {
                cumulativesRepo.save(new Cumulatives(min, max, discount));
            } else {
                model.addAttribute("message", "Некорректные данные!");
                addAttributes(model);
                model.addAttribute("settings", getSettings());
                model.addAttribute("cumulatives", cumulativesRepo.findAllByOrderByMin());
                return "settings";
            }
        }

        return "redirect:/settings";
    }

    @GetMapping("/cumulatives/delete/{id}")
    public String cumulativeDelete(@PathVariable Long id) {
        cumulativesRepo.deleteById(id);
        return "redirect:/settings";
    }
}
