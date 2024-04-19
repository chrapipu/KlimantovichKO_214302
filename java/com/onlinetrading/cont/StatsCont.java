package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Category;
import com.onlinetrading.models.Incomes;
import com.onlinetrading.repo.CategoryRepo;
import com.onlinetrading.repo.IncomesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Main {
    @Autowired
    protected IncomesRepo incomesRepo;
    @Autowired
    protected CategoryRepo categoryRepo;

    @GetMapping
    public String stats(Model model) {
        addAttributes(model);
        List<Incomes> incomes = incomesRepo.findAll();
        model.addAttribute("incomes", incomes);
        incomes.sort(Comparator.comparing(Incomes::getCount));
        Collections.reverse(incomes);

        String[] stringIncomes = new String[5];
        int[] intIncomes = new int[5];

        int index = 0;

        for (Incomes i : incomes) {
            if (index == 5) break;
            stringIncomes[index] = i.getProduct().getName();
            intIncomes[index] = i.getCount();
            index++;
        }

        model.addAttribute("stringIncomes", stringIncomes);
        model.addAttribute("intIncomes", intIncomes);

        List<Category> categories = categoryRepo.findAll();

        String[] catString = new String[categories.size()];
        int[] catInt = new int[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            catString[i] = categories.get(i).getName();
            catInt[i] = categories.get(i).getIncome();
        }

        model.addAttribute("catString", catString);
        model.addAttribute("catInt", catInt);

        return "stats";
    }
}
