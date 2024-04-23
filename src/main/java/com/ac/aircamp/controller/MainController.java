package com.ac.aircamp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ac.aircamp.model.Camp;
import com.ac.aircamp.model.Notice;
import com.ac.aircamp.model.Search;
import com.ac.aircamp.model.Tour;
import com.ac.aircamp.service.MainService;
import com.ac.aircamp.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;
	private final NoticeService noticeService;

	@GetMapping("/main")
	public String main(Model model) {

		// 추천 지역 선별
		String loc = mainService.getRecommendLoc();
		// 캠핑장 추천리스트
		List<Camp> campList = new ArrayList<>();
		campList = mainService.getCampList(loc);

		// 관광지 추천리스트
		List<Tour> tourList = new ArrayList<>();
		tourList = mainService.getTourList(loc);

		List<Notice> noticeList = new ArrayList<>();
		Search search = new Search();

		noticeList = noticeService.getMainNoticeList();

		model.addAttribute("tourList", tourList);
		model.addAttribute("campList", campList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("search", search);

		return "main";
	}
	
	// 이용약관
	@GetMapping("/terms")
	public String terms() {
		return "terms";
	}
	// 개인정보처리방침
	@GetMapping("/policy")
	public String policy() {
		return "policy";
	}

}
