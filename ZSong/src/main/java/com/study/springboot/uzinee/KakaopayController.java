package com.study.springboot.uzinee;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.KakaopayDao;
import com.study.springboot.dto.CashListDto;

@Controller
@RequestMapping("/uzinee")
public class KakaopayController {

	@Autowired
	KakaopayDao dao;
	
	ArrayList<CashListDto> cashList;
	
	@RequestMapping("/cash_list")
	public String cash_list(Model model,@RequestParam("id") String id ) {
		
		System.out.println("cash_list");
		
		cashList = dao.cashList(id);
		for(int i=0;i<cashList.size();i++)	
			System.out.println(cashList.get(i));
		
		model.addAttribute("cashlist", cashList);
		
		
		return "/cash_list";
	}	
	
	@RequestMapping("cash") //돈 삽입
	   public void cash(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int cash_deposit) {
	      System.out.println("cash");
	      dao.cash(id,cash_deposit);
	   }
	   
	   @RequestMapping("withdrawal") //돈 출금
	   public void withdrawal(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int cash_deposit) {
	       System.out.println("cash");
	      dao.withdrawal(id,cash_deposit);
	   }
	   
	   @RequestMapping("cash_deposit") //입금 내역
	   public void cash_deposit(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int cash_deposit) {
	      System.out.println("cash_deposit");
	      dao.cash_deposit(id,cash_deposit);
	   }
	   
	   @RequestMapping("cash_withdrawal") //출금 내역
	   public void cash_withdrawal(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int cash_deposit) {
	      System.out.println("cash_deposit");
	      dao.cash_withdrawal(id,cash_deposit);
	   }
	   
	   
	   @RequestMapping("cal_cash") //돈 update
	   public void cal_cash(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int cash_deposit) {
	      System.out.println("cash_update");
	      dao.cal_cash(id,cash_deposit);
	   }
	   
	   @RequestMapping("cash_result") //돈 계산
	   public String cash_result(HttpServletRequest request, Model model,
	         @RequestParam("id") String id) {
	      
	      int total = dao.cash_result(id);
	      System.out.println(total);
	      model.addAttribute("total", total);
	      return "/cash_result";
	   }
	   
	   @RequestMapping("reward") //상금 삽입
	   public void reward(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int reward_deposit) {
	      System.out.println("cash");
	      dao.reward(id,reward_deposit);
	   }
	   
	   @RequestMapping("change_reward") //상금 마일리지로 변환
	   public void change_reward(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int reward_deposit) {
	      System.out.println("change_reward");
	      dao.change_reward(id,reward_deposit);
	   }
	   
	   @RequestMapping("cal_reward") //상금 업데이트
	   public void cal_reward(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("reward") int reward_deposit) {
	      System.out.println("cal_cash");
	      dao.cal_reward(id,reward_deposit);
	   }
	   
	   @RequestMapping("list_reward") //상금변환 업데이트(-)
	   public void list_reward(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int reward_deposit) {
	      System.out.println("list_reward");
	      
	      dao.list_reward(id,reward_deposit);
	      dao.total_money(id, reward_deposit);

	   }
	   
	   @RequestMapping("total_money") //상금변환 업데이트(-)
	   public void total_money(HttpServletRequest request, 
	         @RequestParam("id") String id, 
	         @RequestParam("cash_deposit") int reward_deposit) {
	      System.out.println("list_reward");
	      dao.total_money(id, reward_deposit);
	   }
	   
	   
	   @RequestMapping("total_reward") //돈 update
	   public String total_reward(HttpServletRequest request, Model model,
	         @RequestParam("id") String id){
		   int total = dao.reward_result(id);
		      System.out.println(total);
		      model.addAttribute("total", total);
		      return "/reward_result";
	   }
	   
   @RequestMapping("participation") //비공개 챌린지 참여시 마일리지 빼가기
	   public void participation(HttpServletRequest request, 
	         @RequestParam("id") String id, @RequestParam("fee") int fee) {
	      System.out.println("participation");
	      dao.participation(id,fee);
	      dao.cash_withdrawal(id, fee);
	   }
	   
   @RequestMapping("fee_delete") //비공개 챌린지 취소시 마일리지 돌려주기
   public void fee_delete(HttpServletRequest request, 
         @RequestParam("host") String host, @RequestParam("fee") int fee) {
      System.out.println("fee_delete");
      dao.fee_delete(host,fee);
      dao.cal_cash(host,fee);
      
     
   } 
}
