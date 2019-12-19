package com.study.springboot.uzinee;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.SerachDao;
import com.study.springboot.dto.Serach_ChallengeDto;
import com.study.springboot.dto.filterDto;

@Controller
@RequestMapping("/uzinee")
public class Serach_mainController {

	@Autowired
	SerachDao dao;


	ArrayList<Serach_ChallengeDto> serach_public_list;
	ArrayList<filterDto>filter_list;

	// 공개 챌린지 리스트
	@RequestMapping("/serach_public_list")
	
	public String serach_public_list(Model model, @RequestParam("member_id") String member_id) {
		
		System.out.println("serach_public_list");
		
		serach_public_list = dao.serach_list();
		
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		
		model.addAttribute("search_list", serach_public_list);
		
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
		return "/search_list";
	}

	
	@RequestMapping("/bookmake")
	public String bookmake(Model model, 
		    @RequestParam("challenge_num") int challenge_num,
		    @RequestParam("member_id") String member_id){
		
		// 여기서 체크하는 메소드 만들어서 있으면 delete 없으면 insert
		if (dao.bookmakerExist(challenge_num, member_id) == 0) {
			   dao.bookmake(challenge_num,member_id);
			System.out.println("bookmarkerInsert");

		} else {
			dao.bookmakeDelete(challenge_num,member_id);
			System.out.println("bookmarkerDelete");
		}
		System.out.println("bookmake");
			
		model.addAttribute("search_list", serach_public_list);
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
		return "/search_list";
		
	}
	
	@RequestMapping("/public_bookmark")
	public String public_bookmark(Model model, 
		    @RequestParam("member_id") String member_id){
		
		System.out.println("public_bookmark");
		
		serach_public_list = dao.public_bookmark(member_id);

		model.addAttribute("search_list", serach_public_list);
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
		return "/search_list";
		
	}
	
	@RequestMapping("/serach_filer_main")
	public String serach_filer_main(Model model, 
		    @RequestParam("member_id") String member_id,
		    @RequestParam("filer")String filer){
		
		System.out.println("serach_filer_main");
		serach_public_list = dao.serach_filer_main(filer);

		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		
		model.addAttribute("search_list", serach_public_list);
		
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
		return "/search_list";
	}
	
	
	//검색어 삽입후 
	@RequestMapping("/insert_filter")
	public void insert_filter(Model model, @RequestParam("filter") String filter){
		
		System.out.println("insert_key");
		dao.insert_filter(filter);
	}
	
	
	//인기 검색어 뽑아내기
	@RequestMapping("/filter_result")
	public String filter_result(Model model){
		System.out.println("filter_key");
		filter_list = dao.filter_key();

		for(int i=0;i<filter_list.size();i++)	
		{
			System.out.println("filter"+filter_list.get(i));
		}
		
		model.addAttribute("filter_list", filter_list);

		return "/filter_list";
	}


	@RequestMapping("/category_hobby")
	
	public String category_hobby(Model model, @RequestParam("member_id") String member_id) {
		
		System.out.println("category_hobby");
		serach_public_list = dao.category_hobby();
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		
		model.addAttribute("search_list", serach_public_list);
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
	
		return "/search_list";
	}
	
	@RequestMapping("/category_life")
	
	public String category_life(Model model, @RequestParam("member_id") String member_id) {
		
		System.out.println("category_life");
		serach_public_list = dao.category_life();
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		
		model.addAttribute("search_list", serach_public_list);
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
	
		return "/search_list";
	}
	
	
	@RequestMapping("/category_capability")
	public String category_capability(Model model, @RequestParam("member_id") String member_id) {
		System.out.println("category_capability");
		serach_public_list = dao.category_capability();
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		model.addAttribute("search_list", serach_public_list);
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
	
		return "/search_list";
	}
	
	
	@RequestMapping("/category_health")
	public String category_health(Model model, @RequestParam("member_id") String member_id) {
		System.out.println("category_health");
		serach_public_list = dao.category_health();
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		model.addAttribute("search_list", serach_public_list);
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
	
		return "/search_list";
	}
	
	@RequestMapping("/category_relationship")
	public String category_relationship(Model model, @RequestParam("member_id") String member_id) {
		System.out.println("category_relationship");
		serach_public_list = dao.category_relationship();
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		model.addAttribute("search_list", serach_public_list);
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
	
		return "/search_list";
	}
	
	@RequestMapping("/category_asset")
	public String category_asset(Model model, @RequestParam("member_id") String member_id) {
		System.out.println("category_asset");
		serach_public_list = dao.category_asset();
		for(int i=0;i<serach_public_list.size();i++)	
		{
			System.out.println(serach_public_list.get(i));
		}
		model.addAttribute("search_list", serach_public_list);
		//북마크
		int[] bookmarkerExist= new int[serach_public_list.size()];
		for (int i = 0; i < serach_public_list.size(); i++) {
		
			bookmarkerExist[i] = dao.bookmakerExist(serach_public_list.get(i).getChallenge_num(),member_id);
			System.out.println(serach_public_list.get(i).getChallenge_num() + " : " + bookmarkerExist[i]);

			System.out.println("전체 " +serach_public_list.get(i));
		}
		
		model.addAttribute("bookmakerlist", bookmarkerExist);
	
		return "/search_list";
	}
	

	 
}

	
