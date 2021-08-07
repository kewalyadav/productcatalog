package com.product.catalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

	@GetMapping({ "/","", "/swagger", "/swagger-ui.html", "/docs" })
	public String redirectSwager() {
		return "redirect:/swagger-ui/index.html";
	}
}
