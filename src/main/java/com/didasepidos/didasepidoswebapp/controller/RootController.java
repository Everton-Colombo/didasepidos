package com.didasepidos.didasepidoswebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.didasepidos.didasepidoswebapp.service.MainWebAppService;
import com.didasepidos.didasepidoswebapp.type.InstitutionRatingSummary;


@Controller
public class RootController {
	
	@Autowired
	MainWebAppService mainWebAppService;
	
	@GetMapping("/")
	public String showHome(Model model) {
		long totalInstCount = mainWebAppService.getTotalInstitutionCount();
		model.addAttribute("institutionCount", (totalInstCount - (totalInstCount % 10)));
		
		long totalReviewCount = mainWebAppService.getTotalReviewCount();
		model.addAttribute("reviewCount", (totalReviewCount - (totalReviewCount % 10)));
		
		return "home";
	}
	
	@GetMapping("/app")
	public String showAppHome(Model model) {
		List<InstitutionRatingSummary> summaries = this.mainWebAppService.getTopXRatingSummary(10);
		System.out.println(summaries);
		model.addAttribute("topSummary", summaries);
		
		return "app/home";
	}
	
	@GetMapping("/about")
	public String showAbout() {
		return("about");
	}
	
}
