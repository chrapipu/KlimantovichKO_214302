package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Reviews;
import com.onlinetrading.repo.ProductsRepo;
import com.onlinetrading.repo.ReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/{id}/reviews")
public class ReviewsCont extends Main {

    @Autowired
    protected ReviewsRepo reviewsRepo;
    @Autowired
    protected ProductsRepo productsRepo;

    @GetMapping
    public String reviews(Model model, @PathVariable Long id) {
        addAttributes(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
        model.addAttribute("reviews", reviewsRepo.findAll());
        return "reviews";
    }

    @PostMapping("/add")
    public String reviewAdd(@PathVariable Long id, @RequestParam String review, @RequestParam int score) {
        reviewsRepo.save(new Reviews(review, getDateNowYearMonthDay(), score, productsRepo.getReferenceById(id), getUser()));
        return "redirect:/products/{id}/reviews";
    }
}
