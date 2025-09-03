package com.example.azure_webapp.controller;

import com.example.azure_webapp.model.Product;
import com.example.azure_webapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // <-- Thay đổi import
import org.springframework.ui.Model; // <-- Thêm import này
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody; // <-- Thêm import này

import java.util.List;

@Controller // <-- Thay đổi từ @RestController thành @Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home() {
        // Trả về tên của file index.html (sẽ tạo ở bước sau)
        return "index";
    }

    // Endpoint này vẫn trả về dữ liệu JSON như cũ
    @GetMapping("/products")
    @ResponseBody // <-- Thêm @ResponseBody để đảm bảo trả về JSON
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // --- PHƯƠNG THỨC MỚI ĐỂ HIỂN THỊ TRANG HTML ---
    @GetMapping("/products-list")
    public String showProductsPage(Model model) {
        // 1. Lấy danh sách sản phẩm từ repository
        List<Product> products = productRepository.findAll();

        // 2. Đưa danh sách này vào model để truyền sang file HTML
        //    "productList" là tên biến mà chúng ta sẽ dùng trong HTML
        model.addAttribute("productList", products);

        // 3. Trả về tên của file HTML (không có đuôi .html)
        //    Spring Boot sẽ tự tìm file 'products.html' trong thư mục templates
        return "products";
    }
}