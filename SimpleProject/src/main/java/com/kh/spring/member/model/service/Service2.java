package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dto.MemberDTO;

public class Service2 implements MemberService{
	
	public MemberDTO login(MemberDTO member) {
		System.out.println("핫하 나는 서비스");
		return null;
	}
	 
	public MemberDTO join(MemberDTO member) {
		return null;
	}

	@Override
	public MemberDTO signUp(MemberDTO member) {

		return null;
	}

	@Override
	public MemberDTO update(MemberDTO member) {

		return null;
	}

	@Override
	public int delete(MemberDTO member) {

		return 0;
	}

}