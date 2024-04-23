package com.ac.aircamp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ac.aircamp.model.Search;
import com.ac.aircamp.model.Tour;
import com.ac.aircamp.service.TourService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourController {

	private final TourService tourService ;
	// TourList
		@GetMapping("/list")
		public String tourList(Search search, Model model) {
			List<Tour> searchTourList = new ArrayList<>();

			int limit = 12;
			
			search.setLimit(limit);
			
			int startRow = (search.getPage() - 1) * limit;
			int endRow = startRow + 10 -1; search.setStartRow(startRow);
			
			searchTourList = tourService.searchTourList(search);

			int count = tourService.searchTourCount(search);
			int maxPage = count / limit + ((count % 10 ==0)? 0:1);
			int startPage = ((search.getPage()-1) /10) * 10 +1;
			int endPage = startPage + 10 - 1;
			if(endPage > maxPage) endPage = maxPage;
			
			model.addAttribute("search", search);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("tourCount", count);
			model.addAttribute("tourList", searchTourList);
			
			return "tour/list";
			
		}
		
		@GetMapping("/detail")
		public String tourDetail(@RequestParam("tour_id") int tour_id, Model model) {
			
			Tour tourDetail = tourService.getTourOne(tour_id);
			
			//이미지 배열 자르기
			String tourimg = tourDetail.getTour_images();
			String[] imgarray = tourimg.split(",");

			model.addAttribute("tour", tourDetail);
			model.addAttribute("imgarray", imgarray);
			
			return "tour/detail";
		}
}
