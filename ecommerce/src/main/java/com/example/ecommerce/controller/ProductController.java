package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDTONew;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ImageService;
import com.example.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ImageService imageService;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String showHome() {
        return "home";
    }


//    @RequestMapping(value= "admin")
//    public String showPageAdmin(Model model) {
//
//      ModelMapper modelMapper = new ModelMapper();
//      List<Tuple> tuples = productRepository.getAllProductTuple();
//      List<ProductDTONew> test = productService.getAllDao();
//        System.out.println(test);
//      List<ProductDTONew> productList = tuples
//              .stream()
//              .map(user -> new ProductDTONew((Integer) user.get("id"),String.valueOf(user.get("productName")), (Double) user.get("price"),String.valueOf(user.get("nameCategory"))))
//              .collect(Collectors.toList());
//        Product product = new Product();
//        model.addAttribute("product", product);
//
//        if (productList.isEmpty()) {
//            model.addAttribute("message", "Not Found List Product!!!");
//        } else {
//            model.addAttribute("productList", productList);
//        }
//        return "adminPage";
//    }

    @RequestMapping(value = "admin")
    public String showPageAdmin(Model model) {

        List<ProductDTONew> productList = productService.getAllDao();
        Product product = new Product();
        model.addAttribute("product", product);
        if (productList.isEmpty()) {
            model.addAttribute("message", "Not Found List Product!!!");
        } else {
            model.addAttribute("productList", productList);
        }
        return "adminPage";
    }

    @RequestMapping("addCategory")
    public String showPageAddCategory() {
        return "addCategory";
    }

//  @PostMapping("saveProduct")
//  public String saveProduct(@ModelAttribute Product product) {
//    productService.saveProduct(product);
//    return "redirect:/admin";
//  }

    @PostMapping("saveProduct")
    public String saveProductMaterial(@ModelAttribute Product product) {
        productService.saveProduct(product);

        return "adminPage";
    }

    @GetMapping("editProduct/{id}")
    public String showFormEdit(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategory();
        List<Image> images = imageService.getAllImage();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("images", images);
        return "formEditProduct";
    }

    @GetMapping("deleteProduct/{id}")
    public String deleteProductById(@PathVariable("id") int id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("message", "The product deleted!!!");
        return "redirect:/admin";
    }

    @PostMapping("/saveProductAjax")
    public String saveProductAjax(@ModelAttribute ProductDTO productDTO, Model model) {
        Product product = new Product();

        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryService.findById(productDTO.getCategory()));
        productService.saveProduct(product);
        List<ProductDTOTest> productList = productService.getAllProducts(1);
        model.addAttribute("productList", productList);
        return "include/table";
    }


}
