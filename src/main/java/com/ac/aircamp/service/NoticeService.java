package com.ac.aircamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.NoticeDao;
import com.ac.aircamp.model.Notice;
import com.ac.aircamp.model.Search;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeDao noticeDao; 
	
	public List<Notice> getNoticeList(Search search) {
		return noticeDao.getNoticeList(search);
	}

	public int insertNotice(Notice notice) {
		return noticeDao.insertNotice(notice);
	}

	public int getNoticeCount(Search search) {
		return noticeDao.getNoticeCount(search);
	}

	public Notice getNoticeOne(int no_no) {
		return noticeDao.getNoticeOne(no_no);
	}
	
	public void noticeReadcount(int no_no) {
		noticeDao.noticeReadcount(no_no);
	}

	public List<Notice> getMainNoticeList() {
		return noticeDao.getMainNoticeList();
	}

   public int updateNotice(Notice notice) {
	      return noticeDao.updateNotice(notice);
	   }
	
	public void deleteNotice(int no_no) {
		noticeDao.deleteNotice(no_no);
	}
	
}
