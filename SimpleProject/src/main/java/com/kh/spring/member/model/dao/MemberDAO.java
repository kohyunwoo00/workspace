package com.kh.spring.member.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.dto.MemberDTO;

@Repository
public class MemberDAO {
	public MemberDTO login(SqlSession sqlSession, MemberDTO member) {
		return sqlSession.selectOne("memberMapper.login",member);
	}
	
	public int checkId(SqlSessionTemplate sqlSession, String memberId) {
		return sqlSession.selectOne("memberMapper.checkId",memberId);
	}
	public int signUp(SqlSessionTemplate sqlSession, MemberDTO member) {
		return sqlSession.insert("memberMapper.signUp", member);
	}
}
