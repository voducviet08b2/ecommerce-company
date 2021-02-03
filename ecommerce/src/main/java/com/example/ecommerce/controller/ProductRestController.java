package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.dto.ProductDTONew;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductDTO;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/productApi")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "admin")
    public List<ProductDTONew> showPageAdmin() {
        List<Tuple> tuples = productRepository.getAllProductTuple();
        List<ProductDTONew> productList = tuples
                .stream()
                .map(user -> new ProductDTONew((Integer) user.get("id"), String.valueOf(user.get("productName")), (Double) user.get("price"), String.valueOf(user.get("nameCategory"))))
                .collect(Collectors.toList());
        return productList;
    }

    @PostMapping("product")
    public int addProduct(@ModelAttribute ProductDTO productDTO) {
        Product product = new Product();
        if(productDTO.getId() != 0){
            product.setId(productDTO.getId());
        }
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryService.findById(productDTO.getCategory()));
        productService.saveProduct(product);
        return 1;
    }

    @RequestMapping("deleteProduct/{id}")
    public int deleteProduct(@PathVariable int id) {
    productRepository.deleteById(id);
    return 1;
    }

    @RequestMapping(value = "category")
    public List<CategoryDTO> getCategory(){
        List<Tuple> tuples = categoryRepository.getAllCategoryTuple();
        List<CategoryDTO> categoryList = tuples
                .stream()
                .map(tuple -> new CategoryDTO((Integer) tuple.get("id"), String.valueOf(tuple.get("categoryName"))))
                .collect(Collectors.toList());
        return categoryList;
    }
}
