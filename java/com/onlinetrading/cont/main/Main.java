package com.onlinetrading.cont.main;

import com.onlinetrading.models.Cumulatives;
import com.onlinetrading.models.Settings;
import com.onlinetrading.models.Users;
import com.onlinetrading.repo.CumulativesRepo;
import com.onlinetrading.repo.SettingsRepo;
import com.onlinetrading.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    @Autowired
    protected UsersRepo usersRepo;
    @Autowired
    protected SettingsRepo settingsRepo;
    @Autowired
    protected CumulativesRepo cumulativesRepo;

    @Value("${upload.img}")
    protected String uploadImg;

    protected String defAvatar = "default/avatar.png";

    protected Settings getSettings() {
        return settingsRepo.findAll().get(0);
    }

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return usersRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users user = getUser();
        if (user != null) return String.valueOf(user.getRole());
        return "NOT";
    }

    protected String getAvatar() {
        Users user = getUser();
        if (user != null) return user.getAvatar();
        return defAvatar;
    }

    protected String getFirstnameAndLastname() {
        Users user = getUser();
        if (user != null) return user.getFirstname() + " " + user.getLastname();
        return "Добро пожаловать";
    }

    protected String getDateNowYearMonthDay() {
        return LocalDateTime.now().toString().substring(0, 10);
    }

    protected void addAttributes(Model model) {
        model.addAttribute("avatar", getAvatar());
        model.addAttribute("firstnameLastname", getFirstnameAndLastname());
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected int cumulative(Users user) {

        List<Cumulatives> cumulatives = cumulativesRepo.findAllByOrderByMin();

        if (cumulatives.isEmpty()) return 0;

        if (user.getTotalBuyPrice() < cumulatives.get(0).getMin()) return 0;

        if (user.getTotalBuyPrice() > cumulatives.get(cumulatives.size() - 1).getMin())
            return cumulatives.get(cumulatives.size() - 1).getDiscount();

        for (Cumulatives cumulative : cumulatives) {
            if (cumulative.getMin() <= user.getTotalBuyPrice() && user.getTotalBuyPrice() < cumulative.getMax()) {
                return cumulative.getDiscount();
            }
        }

        return 0;
    }

}
