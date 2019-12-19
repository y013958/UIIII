package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CommentDto;

@Mapper	
public interface ChallengeDao {
	
	public void PublicChallenge(String name, String title, String type, String detail);
	public ArrayList<ChallengeDto> PublicList();
	public void PrivateChallenge(String title, String category, String type, String frequency, String start, String end,
			String fee, String time, String detail, String image, String host);
	public ArrayList<ChallengeDto> PrivateList(String id);
	public ArrayList<ChallengeDto> PrivateModify(String host);
	public void PrivateDelete(int challenge_num);
	public void CommentInsert(int challenge_num, String member_id, String content);
	public ArrayList<CommentDto> CommentSelect();
	public void CommentLikeInsert(int challenge_num, int comment_num, String member_id);
	public void CommentLikeDelete(String member_id, int challenge_num, int comment_num);
	public int CommentLikeExist(String member_id, int challenge_num, int comment_num);
	public int CommentLikeCount(int challenge_num, int comment_num);
	public void ChallengeLikeInsert(int challenge_num, String member_id);
	public void ChallengeLikeDelete(String member_id, int challenge_num);
	public int ChallengeLikeExist(String member_id, int challenge_num);
	public int ChallengeLikeCount(int challenge_num);
	public ArrayList<ChallengeDto> PrivateSearchCode(String code);
}
