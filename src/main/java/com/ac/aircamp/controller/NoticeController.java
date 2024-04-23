package com.ac.aircamp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ac.aircamp.model.Notice;
import com.ac.aircamp.model.Search;
import com.ac.aircamp.service.NoticeService;
import com.ac.aircamp.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

	private final NoticeService noticeService;
	
	@GetMapping("/list")
	public String noticeList(Search search, Model model) {
		List<Notice> getNoticeList = new ArrayList<>();
		int limit = 10;
		search.setLimit(limit);
		int startRow = (search.getPage() - 1)  * limit; 
		
		search.setStartRow(startRow);
		getNoticeList = noticeService.getNoticeList(search);

		int count = noticeService.getNoticeCount(search);
		int maxPage = count / limit + ((count % 10 ==0)? 0:1);
		int startPage = ((search.getPage()-1) /10) * limit +1;
		int endPage = startPage + limit - 1;
		if(endPage > maxPage)
			endPage = maxPage;
		
		model.addAttribute("search", search);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("count", count);
		model.addAttribute("noticeList", getNoticeList);
		
		return "notice/list";
	}
	
	// 공지사항 작성폼으로 이동
	@GetMapping("/write")
	public String noticeWrite() {
		
		return "notice/writeform";
	}
	
	// 공지사항 작성후 db 저장
	@PostMapping("/write")
	public String noticeWrite(Notice notice) {
		noticeService.insertNotice(notice);
		return "redirect:/list";
	}
	
	// 공지사항 상세 정보 출력
	@GetMapping("/detail")
	public String noticeDetail(@RequestParam("no_no") int no_no, Search search, Model model) {
		
		noticeService.noticeReadcount(no_no);
		Notice noticeOne = noticeService.getNoticeOne(no_no);
		String no_content = noticeOne.getNo_content().replace("\n","<br>");
		noticeOne.setNo_content(no_content); 
		
		model.addAttribute("notice", noticeOne);
		model.addAttribute("search", search);
		
		return "notice/detail";
		
	}
   // 업데이트 폼으로 이동
	@GetMapping("/update")
   public String noticeUpdate(@ModelAttribute Notice notice, Model model, Search search) {
      
      notice = noticeService.getNoticeOne(notice.getNo_no());
      
      model.addAttribute("notice", notice);
      model.addAttribute("search", search);
      
      return "notice/updateForm";
   }
   
	// 업데이트된 정보 db에 저장
   @PostMapping("/update")
   public String noticeUpdate(@ModelAttribute Notice notice) {
      noticeService.updateNotice(notice);
      
      return "redirect:detail?no_no="+notice.getNo_no();
   }
	
	//공지사항 삭제
	@GetMapping("/delete")
	public String deleteNotice(@RequestParam("no_no") int no_no, Search search, Model model) {
		
		noticeService.deleteNotice(no_no);
		
		model.addAttribute("search", search);
		
		return "redirect:/notice/list?page="+search.getPage()+"&keyword="+search.getKeyword();
	}
	

}
