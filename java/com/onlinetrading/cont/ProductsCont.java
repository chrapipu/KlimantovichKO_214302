package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.*;
import com.onlinetrading.models.enums.ProductStatus;
import com.onlinetrading.repo.CartsRepo;
import com.onlinetrading.repo.CategoryRepo;
import com.onlinetrading.repo.CouponsRepo;
import com.onlinetrading.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductsCont extends Main {

    @Autowired
    protected CartsRepo cartsRepo;
    @Autowired
    protected ProductsRepo productsRepo;
    @Autowired
    protected CouponsRepo couponsRepo;
    @Autowired
    protected CategoryRepo categoryRepo;

    @GetMapping
    public String products(Model model) {
        addAttributes(model);
        model.addAttribute("products", productsRepo.findAllByStatus(ProductStatus.ACTIVE));
        model.addAttribute("coupons", getUser().getCoupons());
        model.addAttribute("settings", getSettings());
        model.addAttribute("cumulative", cumulative(getUser()));
        return "products";
    }

    @GetMapping("/search")
    public String productsSearch(Model model, @RequestParam String name, @RequestParam String sort) {
        addAttributes(model);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);

        List<Products> products = productsRepo.findAllByNameContainingAndStatus(name, ProductStatus.ACTIVE);

        products.sort(Comparator.comparing(Products::getPrice));

        if (sort.equals("expensive")) {
            Collections.reverse(products);
        }

        model.addAttribute("products", products);
        model.addAttribute("coupons", getUser().getCoupons());
        model.addAttribute("settings", getSettings());
        model.addAttribute("cumulative", cumulative(getUser()));

        return "products";
    }

    @GetMapping("/my")
    public String productsMy(Model model) {
        addAttributes(model);
        model.addAttribute("carts", getUser().getCarts());
        return "products_my";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {
        addAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
        return "product_add";
    }

    @GetMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable Long id) {
        addAttributes(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
        model.addAttribute("categories", categoryRepo.findAll());
        return "product_edit";
    }

    @PostMapping("/buy/{id}")
    public String productBuy(Model model, @PathVariable Long id, @RequestParam int count, @RequestParam String price) {
        Products product = productsRepo.getReferenceById(id);
        Settings settings = getSettings();

        String name = price.split(":")[0];

        if (name.equals("multi")) {
            if (count < settings.getMulti()) {
                model.addAttribute("message", "Не выбрано достаточное количество для скидки!");
                addAttributes(model);
                model.addAttribute("products", productsRepo.findAllByStatus(ProductStatus.ACTIVE));
                model.addAttribute("coupons", getUser().getCoupons());
                model.addAttribute("settings", getSettings());
                model.addAttribute("cumulative", cumulative(getUser()));
                return "products";
            }
        }

        for (Coupons c : getUser().getCoupons()) {
            if (c.getName().equals(name)) {
                c.setCount(c.getCount() - 1);
                couponsRepo.save(c);
                break;
            }
        }

        int p = Integer.parseInt(price.split(":")[1]);

        product.setCount(product.getCount() - count);
        productsRepo.save(product);

        Incomes income = product.getIncome();
        income.setCount(income.getCount() + count);
        income.setIncome(income.getIncome() + (p * count));

        cartsRepo.save(new Carts(count, (p * count), getUser(), product));

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable Long id) {
        productsRepo.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/archive/{id}")
    public String productArchive(@PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);
        product.setStatus(ProductStatus.ARCHIVE);
        productsRepo.save(product);
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String productAdd(Model model, @RequestParam String name, @RequestParam Long categoryId, @RequestParam int count, @RequestParam int price, @RequestParam int discount, @RequestParam MultipartFile file) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                addAttributes(model);
                model.addAttribute("categories", categoryRepo.findAll());
                return "product_add";
            }
        }

        Products product = new Products(name, count, price, discount, res, categoryRepo.getReferenceById(categoryId));
        productsRepo.save(product);

        return "redirect:/products/add";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @RequestParam String name, @RequestParam Long categoryId, @RequestParam int count, @RequestParam int price, @RequestParam int discount, @RequestParam MultipartFile file, @PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);
        product.setName(name);
        product.setCount(count);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setCategory(categoryRepo.getReferenceById(categoryId));

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                addAttributes(model);
                model.addAttribute("product", productsRepo.getReferenceById(id));
                model.addAttribute("categories", categoryRepo.findAll());
                return "product_edit";
            }
            product.setFile(res);
        }

        productsRepo.save(product);

        return "redirect:/products";
    }
}
