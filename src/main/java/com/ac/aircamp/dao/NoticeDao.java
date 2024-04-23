package com.ac.aircamp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ac.aircamp.model.Notice;
import com.ac.aircamp.model.Search;

@Mapper
public interface NoticeDao {

	List<Notice> getNoticeList(Search search);

	int insertNotice(Notice notice);

	int getNoticeCount(Search search);

	Notice getNoticeOne(int no_no);

	void noticeReadcount(int no_no);

	List<Notice> getMainNoticeList();

	void deleteNotice(int no_no);
	
	int updateNotice(Notice notice);

}
