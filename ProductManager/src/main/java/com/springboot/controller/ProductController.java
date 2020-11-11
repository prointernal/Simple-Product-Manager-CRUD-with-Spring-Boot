package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.Product;
import com.springboot.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;

	@RequestMapping("/list_products")
	public String viewHomePage(Model model) {

	
		String keyword = null;
		return listByPage(model, 1,"name","asc",keyword);
	}
	
	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, 
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword) {

		Page<Product> page = productService.listAll(currentPage, sortField, sortDir,keyword);
		
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		List<Product> listProducts = page.getContent();
		
		model.addAttribute("currentPage" , currentPage);
		model.addAttribute("totalItems" , totalItems);
		model.addAttribute("totalPages" , totalPages);
		model.addAttribute("listProducts" , listProducts);
		model.addAttribute("sortField" , sortField);
		model.addAttribute("sortDir" , sortDir);
		model.addAttribute("keyword" , keyword);

		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir" , reverseSortDir);

		
		return "list_product";

	}

	@RequestMapping("/new")
	public String ShowNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);

		return "new_product";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		productService.save(product);
		return "redirect:/list_products";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView EditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product");

		Product product = productService.get(id);
		mav.addObject("product", product);
		return mav;
	}
  
	@RequestMapping("/delete/{id}")
	public String DeleteProduct(@PathVariable(name = "id") Long id) {
		productService.delete(id);
		return "redirect:/list_products";
	}

}
