package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	//@Autowired
	//private final MemberDAO memberDao;
	//@Autowired
	//private final SqlSessionTemplate sqlSession;
	private final PasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	private final MemberMapper memberMapper;
	/*
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/

	@Override
	public MemberDTO login(MemberDTO member) {
		//log.info("핫하 나는 MemberServiceImpl이다");
		
		// 로그인이라는 요청을 처리해주어야하는데
		// 아이디가 10자가 넘으면 안되겠네?
		// 아이디/비밀번호 : 빈문자열 또는 null이면 안되겠네?
				
		// 1. Table에 아이디가 존재해야겠다.
		// 2. 비밀번호가 일치해야겠네
		// 3. 둘다 통과면 정상적으로 조회할 수 있도록 해주어야겠다.
		/*
		 * SqlSession sqlSession = getSqlSession();
		 * MemberDTO loginMember = new MemberDAO().login(sqlSession, member);
		 * sqlSession.close();
		 * return loginMember;
		 */
		// MemberDTO loginMember = memberMapper.login(member);
		// 아이디만 일치하더라도 행의 정보를 필드에 담아옴
		
		// 1. loginMember가 null값과 동일하다면 아이디가 존재하지 않는다.
		/*
		if(loginMember == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디입니다.");
		}
		*/
		// 2. 아이디만 가지고 조회를 하기 때문에
		// 비밀번호를 검증 후
		// 비밀번호가 유효하다면 회원의정보를 session에 담기
		// 비밀번호가 유효하지않다면 비밀번호 이상한데?? 
		
		
		MemberDTO loginMember = validator.validateMemberExists(member);
		if(passwordEncoder.matches(member.getMemberPw(), loginMember.getMemberPw())) {
			return loginMember;
		} else {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}
		
	}

	@Override
	public void signUp(MemberDTO member) {
		/*
		if(member == null || 
		   member.getMemberId() == null || 
		   member.getMemberId().trim().isEmpty() ||
		   member.getMemberPw() == null ||
		   member.getMemberPw().trim().isEmpty()) {
		}
		*/
		
		// validator 객체의 validatedLoginMember 메소드를 호출 member 객체를 메소드에게 전달
		//validator.validatedLoginMember(member); // 로그인하려는 회원의 정보가 유효한지 검증하는 작업
		
		/*
		int result = memberDao.checkId(sqlSession, member.getMemberId());
		
		if(result > 0) { return; }
		*/
		
		//memberMapper.login(member);
		
		//log.info("사용자가 입력한 비밀번호 평문 : {}", member.getMemberPw());
		// 암호화 하는법 == .encode()호출
		//log.info("평문을 암호문으로 바꾼것 : {}", passwordEncoder.encode(member.getMemberPw()));
		
		// INSERT 해야지~
		validator.validatedJoinMember(member);
		member.setMemberPw(passwordEncoder.encod(member.getMemberPw()));
		
		memberMapper.signUp(member);

		/*
		if(consequence > 0) {
			return;
		} else {
			return;
		}
		*/
	}
	
	@Override
	public void update(MemberDTO member, HttpSession session) { // void는 반환 값 없음 return 안씀
		MemberDTO sessionMember =((MemberDTO)session.getAttribute("loginMember")); // session member -> 로그인 했을 때 정보
		// 사용자 검증
		if(!member.getMemberId().equals(sessionMember.getMemberId())){
			throw new AuthenticationException("권한없는 접근입니다");
		}
		// 존재하는 아이디인지 검증 (update 전)
		validator.validateMemberExists(member);
		int result = memberMapper.update(member);  
		// SQL문 수행 결과 검증
		if(result != 1) {
			throw new AuthenticationException("문제가 일어남. 다시 시도하셈");
		}
		
		sessionMember.setMemberName(member.getMemberName());
		sessionMember.setEmail(member.getEmail());
			
	}

	@Override
	public int delete(MemberDTO member) {
		return 0;
	}
	
	@Override
	public String idCheck(String memberId) {
		//String result = 
		return memberMapper.idCheck(memberId) != null ? "NNNY" : "NNNN";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}