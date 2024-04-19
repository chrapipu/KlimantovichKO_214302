package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Category;
import com.onlinetrading.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryCont extends Main {
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping
    public String category(Model model) {
        addAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
        return "category";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name) {
        categoryRepo.save(new Category(name));
        return "redirect:/category";
    }

    @PostMapping("/{id}/edit")
    public String edit(@RequestParam String name, @PathVariable Long id) {
        Category category = categoryRepo.getReferenceById(id);
        category.setName(name);
        categoryRepo.save(category);
        return "redirect:/category";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/category";
    }
}
