package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.UserAdd;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileCont extends Main {


    @GetMapping
    public String profile(Model model) {
        addAttributes(model);
        model.addAttribute("userAdds", UserAdd.values());
        model.addAttribute("settings", getSettings());
        model.addAttribute("cumulative", cumulative(getUser()));
        return "profile";
    }

    @PostMapping("/add")
    String profileAdd(@RequestParam UserAdd userAdd) {
        Users user = getUser();
        user.setUserAdd(userAdd);
        usersRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit")
    String ProfileEdit(@RequestParam String firstname, @RequestParam String lastname) {
        Users user = getUser();

        user.setFirstname(firstname);
        user.setLastname(lastname);

        usersRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit/date")
    String ProfileEditDate(@RequestParam String date) {
        Users user = getUser();
        user.setDate(date);
        usersRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit/name")
    String ProfileEditName(@RequestParam String name) {
        Users user = getUser();
        user.setName(name);
        usersRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit/password")
    String ProfileEditPassword(Model model, @RequestParam String passwordOld, @RequestParam String passwordNew) {
        Users user = getUser();

        if (!passwordOld.equals(user.getPassword())) {
            model.addAttribute("message", "Некорректный ввод текущего пароля");
            addAttributes(model);
            model.addAttribute("userAdds", UserAdd.values());
            model.addAttribute("settings", getSettings());
            model.addAttribute("cumulative", cumulative(getUser()));
            return "profile";
        }

        user.setPassword(passwordNew);

        usersRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/changeAvatar")
    String ChangeAvatar(Model model, @RequestParam MultipartFile avatar) {
        if (avatar != null && !Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "avatars/" + uuidFile + "_" + avatar.getOriginalFilename();
                    avatar.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось изменить аватарку");
                addAttributes(model);
                model.addAttribute("userAdds", UserAdd.values());
                model.addAttribute("settings", getSettings());
                model.addAttribute("cumulative", cumulative(getUser()));
                return "profile";
            }
            Users user = getUser();

            if (!user.getAvatar().equals(defAvatar)) {
                try {
                    Files.delete(Paths.get(uploadImg + "/" + user.getAvatar()));
                } catch (IOException e) {
                    model.addAttribute("message", "Не удалось изменить аватарку");
                    addAttributes(model);
                    model.addAttribute("userAdds", UserAdd.values());
                    model.addAttribute("settings", getSettings());
                    model.addAttribute("cumulative", cumulative(getUser()));
                    return "profile";
                }
            }
            user.setAvatar(res);
            usersRepo.save(user);
        }

        return "redirect:/profile";
    }
}
