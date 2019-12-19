package com.study.springboot.uzinee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.LoginDao;

@Controller
@RequestMapping("/uzinee")
public class LoginController {

	@Autowired
	LoginDao dao;
	
	@RequestMapping("/join")
	public void join(HttpServletRequest request, @RequestParam("id") String id, @RequestParam("pw") String pw,
			@RequestParam("name") String name, @RequestParam("nickname") String nickname) {
		System.out.println("join");

		System.out.println(id);
		System.out.println(pw);
		System.out.println(name);
		System.out.println(nickname);

		dao.UserJoin(id, pw, name, nickname);
	}
}
	
