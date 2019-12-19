package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.CashListDto;


@Mapper
public interface KakaopayDao {

	public String UserJoin(String id, String pw, String name, String nicname);

	public int cash(String id, int cash_deposit); // 입금
	public int cash_result(String id); // 총액
	public void cal_cash(String id, int cash_deposit); // 계산
	public int cash_deposit(String id, int cash_deposit); // 입금 내역
	public void withdrawal(String id, int cash_deposit); // 출금
	public void cash_withdrawal(String id, int cash_deposit); // 촐금내역
	public ArrayList<CashListDto> cashList(String id);
	public void reward(String id, int reward_deposit); // 상금 삽입
	public void cal_reward(String id, int reward_deposit);// 상금 업데이트
	public int reward_result(String id);
	public void change_reward(String id, int reward_deposit); // 상금을 마일리지로
	public void list_reward(String id, int reward_deposit); // 변환 결과
	public void total_money(String id, int reward_deposit); // 진짜 마일리지 다 계산

	public void participation(String id, int fee);//챌린지 참여시 마일리지 빼가기

	public void fee_delete(String host, int fee); //챌린지 취소시 마일리지 돌려주기 

}
