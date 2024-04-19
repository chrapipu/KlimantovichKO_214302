package com.onlinetrading.cont;

import com.onlinetrading.cont.main.Main;
import com.onlinetrading.models.Products;
import com.onlinetrading.models.enums.ProductStatus;
import com.onlinetrading.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/archive")
public class ProductsArchiveCont extends Main {

    @Autowired
    protected ProductsRepo productsRepo;

    @GetMapping
    public String archive(Model model) {
        addAttributes(model);
        model.addAttribute("products", productsRepo.findAllByStatus(ProductStatus.ARCHIVE));
        return "products_archive";
    }

    @GetMapping("/active/{id}")
    public String archiveActive(@PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);
        product.setStatus(ProductStatus.ACTIVE);
        productsRepo.save(product);
        return "redirect:/products/archive";
    }
}
