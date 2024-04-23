package com.ac.aircamp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ac.aircamp.model.Camp;
import com.ac.aircamp.model.Search;
import com.ac.aircamp.service.CampService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/camp")
public class CampController {

	private final CampService campService;
	
	//CampList
		@GetMapping("/list")
		public String campList(Search search, Model model) {

			List<Camp> searchCampList = new ArrayList<>();
			
			int limit = 12;
			search.setLimit(limit);
			
			int startRow = (search.getPage() - 1)  * limit; 
			
			int endRow = startRow + 10 -1;
			search.setStartRow(startRow);
			searchCampList = campService.searchCampList(search);
			
			int count = campService.searchCampCount(search);
			
			int maxPage = count / limit + ((count % 10 ==0)? 0:1);
			int startPage = ((search.getPage()-1) /10) * 10 +1;
			int endPage = startPage + 10 - 1;
			if(endPage > maxPage)
				endPage = maxPage;
			
			model.addAttribute("search", search);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("campCount", count);
			model.addAttribute("campList", searchCampList);
			
			return "camp/list";
			}
		
		
		@GetMapping("/detail")
		public String campDetail(@RequestParam("camp_no") int camp_no, Model model) {
			
			Camp campDetail = campService.getCampOne(camp_no);
			
			model.addAttribute("camp", campDetail);
			
			return "camp/detail";
		}
}
