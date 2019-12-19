package com.study.springboot.yejin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.study.springboot.dao.ChallengeDao;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CommentDto;

@Controller
@RequestMapping("/yejin")
public class ChallengeController2 {

	@Autowired
	ChallengeDao dao;

	ArrayList<ChallengeDto> challengelist;
	ArrayList<CommentDto> commentlist;

	@RequestMapping("/public_create")
	public void public_create(HttpServletRequest request, @RequestParam("title") String title,
			@RequestParam("category") String category, @RequestParam("type") String type,
			@RequestParam("detail") String detail) {

		System.out.println("create");
		dao.PublicChallenge(title, category, type, detail);
	}

	// 공개 챌린지 리스트
	@RequestMapping("/public_list")
	public String Public_Challenge_List(Model model, @RequestParam("member_id") String member_id) {

		System.out.println("Public_Challenge_List");

		challengelist = dao.PublicList();

		model.addAttribute("list", challengelist);

		int[] likeCount = new int[challengelist.size()];
		int[] likeExist = new int[challengelist.size()];

		for (int i = 0; i < challengelist.size(); i++) {
			likeCount[i] = dao.ChallengeLikeCount(challengelist.get(i).getChallenge_num());
			System.out.println(challengelist.get(i).getChallenge_num() + " : " + likeCount[i]);
			// 여기서 한번
			likeExist[i] = dao.ChallengeLikeExist(member_id, challengelist.get(i).getChallenge_num());
			System.out.println(challengelist.get(i).getChallenge_num() + " : " + likeExist[i]);
			System.out.println(challengelist.get(i));

		}

		model.addAttribute("countlist", likeCount);
		model.addAttribute("existlist", likeExist);

		return "/Andchallenge/public_list";
	}

	// 투표
	@RequestMapping("/public_challenge_like")
	public String Public_Challenge_like(Model model, @RequestParam("challenge_num") int challenge_num,
			@RequestParam("member_id") String member_id) {

		// 여기서 체크하는 메소드 만들어서 있으면 delete 없으면 insert
		if (dao.ChallengeLikeExist(member_id, challenge_num) == 0) {
			dao.ChallengeLikeInsert(challenge_num, member_id);
			System.out.println("ChallengeLikeInsert");
		} else {
			dao.ChallengeLikeDelete(member_id, challenge_num);
			System.out.println("ChallengeLikeDelete");
		
		}

		System.out.println("Public_Challenge_like");

		challengelist = dao.PublicList();

		model.addAttribute("list", challengelist);

		// 댓글 좋아요 출력
		int[] likeCount = new int[challengelist.size()];
		int[] likeExist = new int[challengelist.size()];

		for (int i = 0; i < challengelist.size(); i++) {
			likeCount[i] = dao.ChallengeLikeCount(challengelist.get(i).getChallenge_num());
			System.out.println(challengelist.get(i).getChallenge_num() + " : " + likeCount[i]);

			// 여기서 한번
			likeExist[i] = dao.ChallengeLikeExist(member_id, challengelist.get(i).getChallenge_num());
			System.out.println(challengelist.get(i).getChallenge_num() + " : " + likeExist[i]);

			System.out.println("전체 " + challengelist.get(i));
		}

		model.addAttribute("countlist", likeCount);
		model.addAttribute("existlist", likeExist);

		return "/Andchallenge/public_list";
	}

	// 댓글 추가
	@RequestMapping("/comment_insert")
	public String comment_insert(HttpServletRequest request, Model model,
			@RequestParam("challenge_num") int challenge_num, @RequestParam("member_id") String member_id,
			@RequestParam("content") String content) {

		System.out.println("comment_insert");

		dao.CommentInsert(challenge_num, member_id, content);

		commentlist = dao.CommentSelect();
		model.addAttribute("list", commentlist);

		int[] array = new int[commentlist.size()];
		int[] likeExist = new int[commentlist.size()];

		for (int i = 0; i < commentlist.size(); i++) {
			array[i] = dao.CommentLikeCount(commentlist.get(i).getChallenge_num(), commentlist.get(i).getComment_num());
			System.out.println(commentlist.get(i).getChallenge_num());

			likeExist[i] = dao.CommentLikeExist(commentlist.get(i).getMember_id(),
					commentlist.get(i).getChallenge_num(), commentlist.get(i).getComment_num());
			System.out.println(commentlist.get(i).getChallenge_num() + " : " + likeExist[i]);
		}

		model.addAttribute("countlist", array);
		model.addAttribute("existlist", likeExist);

		return "/Andchallenge/comment_list";
	}

	// 댓글 출력
	@RequestMapping("/comment_list")
	public String Public_Comment_list(Model model) {

		System.out.println("Public_Comment_list");

		commentlist = dao.CommentSelect();

		model.addAttribute("list", commentlist);

		// 댓글 좋아요 숫자 출력
		int[] likeCount = new int[commentlist.size()];
		int[] likeExist = new int[commentlist.size()];

		for (int i = 0; i < commentlist.size(); i++) {
			likeCount[i] = dao.CommentLikeCount(commentlist.get(i).getChallenge_num(),
					commentlist.get(i).getComment_num());
			System.out.println(commentlist.get(i).getChallenge_num() + " : " + likeCount[i]);

			// 여기서 한번
			likeExist[i] = dao.CommentLikeExist(commentlist.get(i).getMember_id(),
					commentlist.get(i).getChallenge_num(), commentlist.get(i).getComment_num());
			System.out.println(commentlist.get(i).getChallenge_num() + " : " + likeExist[i]);

		}

		model.addAttribute("countlist", likeCount);
		model.addAttribute("existlist", likeExist);

		return "/Andchallenge/comment_list";
	}

	// 댓글 좋아요
	@RequestMapping("/public_comment_like")
	public String Public_Comment_like(Model model, @RequestParam("challenge_num") int challenge_num,
			@RequestParam("member_id") String member_id, @RequestParam("comment_num") int comment_num) {

		// 여기서 체크하는 메소드 만들어서 있으면 delete 없으면 insert
		if (dao.CommentLikeExist(member_id, challenge_num, comment_num) == 0) {
			dao.CommentLikeInsert(challenge_num, comment_num, member_id);
			System.out.println("CommentLikeInsert");

		} else {
			dao.CommentLikeDelete(member_id, challenge_num, comment_num);
			System.out.println("CommentLikeDelete");
		}

		System.out.println("Public_Comment_like");

		commentlist = dao.CommentSelect();

		model.addAttribute("list", commentlist);

		// 댓글 좋아요 출력
		int[] likeCount = new int[commentlist.size()];
		int[] likeExist = new int[commentlist.size()];

		for (int i = 0; i < commentlist.size(); i++) {
			likeCount[i] = dao.CommentLikeCount(commentlist.get(i).getChallenge_num(),
					commentlist.get(i).getComment_num());
			System.out.println(commentlist.get(i).getChallenge_num() + " : " + likeCount[i]);

			// 여기서 한번
			likeExist[i] = dao.CommentLikeExist(commentlist.get(i).getMember_id(),
					commentlist.get(i).getChallenge_num(), commentlist.get(i).getComment_num());
			System.out.println(commentlist.get(i).getChallenge_num() + " : " + likeExist[i]);
		}

		model.addAttribute("countlist", likeCount);
		model.addAttribute("existlist", likeExist);

		return "/Andchallenge/comment_list";
	}

	// =====================================================
	// 비공개 챌린지

	@RequestMapping("/private_create")
	public void private_create(HttpServletRequest request, @RequestParam("title") String title,
			@RequestParam("category") String category, @RequestParam("type") String type,
			@RequestParam("frequency") String frequency, @RequestParam("start") String start,
			@RequestParam("end") String end, @RequestParam("fee") String fee, @RequestParam("time") String time,
			@RequestParam("first_image") String first_image, @RequestParam("detail") String detail,
			@RequestParam("host") String host) {

		System.out.println("create");
		fee = String.valueOf(Integer.parseInt(fee)*10000);
		dao.PrivateChallenge(title, category, type, frequency, start, end, fee, time, detail, first_image, host);
	}

	@RequestMapping("/private_list")
	public String Private_Challenge_List(Model model,
			@RequestParam("id") String id) {

		System.out.println("Private_Challenge_List");

		challengelist = dao.PrivateList(id);

		model.addAttribute("list", challengelist);
		return "/Andchallenge/private_list";
	}

	@RequestMapping("/private_modify")
	public String Private_Challenge_Modify(Model model, @RequestParam("challenge_num") String host) {

		System.out.println("Private_Challenge_Modify");

		challengelist = dao.PrivateModify(host);

		model.addAttribute("list", challengelist);
		return "/Andchallenge/private_list";
	}

	@RequestMapping("/private_delete")
	public String Private_Challenge_Delete(Model model, @RequestParam("challenge_num") int num, 
			@RequestParam("id") String id) {

		System.out.println("Private_Challenge_Delete");

		dao.PrivateDelete(num);

		// 여기 수정하기 (Update 안됨)

		challengelist = dao.PrivateList(id);

		model.addAttribute("list", challengelist);
		return "/Andchallenge/private_list";
	}
	
	@RequestMapping("/private_searchcode")
	public String Private_Challenge_SearchCode(Model model,  
			@RequestParam("code") String code) {
		
		System.out.println(code);
		
		challengelist = dao.PrivateSearchCode(code);
		model.addAttribute("list", challengelist);
		
		return "/Andchallenge/private_list";
	}	
}
