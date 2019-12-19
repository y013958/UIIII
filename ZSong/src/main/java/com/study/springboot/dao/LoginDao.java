package com.study.springboot.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {
	public String UserJoin(String id, String pw, String name, String nicname);
}
