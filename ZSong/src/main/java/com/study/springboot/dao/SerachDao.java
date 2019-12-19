package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.Serach_ChallengeDto;
import com.study.springboot.dto.filterDto;


@Mapper
public interface SerachDao {
	public ArrayList<Serach_ChallengeDto> serach_list();

	public ArrayList<Serach_ChallengeDto> category_hobby();

	public ArrayList<Serach_ChallengeDto> category_life();

	public ArrayList<Serach_ChallengeDto> category_capability();

	public ArrayList<Serach_ChallengeDto> category_health();

	public ArrayList<Serach_ChallengeDto> category_relationship();

	public ArrayList<Serach_ChallengeDto> category_asset();

	public void bookmake(int challenge_num, String member_id); //북마크 추가

	public int bookmakerExist(int challenge_num, String member_id);

	public void bookmakeDelete(int challenge_num, String member_id);

//	public ArrayList<Serach_ChallengeDto> public_bookmark();

	public ArrayList<Serach_ChallengeDto> public_bookmark(String member_id);

	public ArrayList<Serach_ChallengeDto> serach_filer_main(String filer); //검색 필터

	public ArrayList<filterDto> filter_key();

	public void insert_filter(String filter);  //검색어 입력




}
