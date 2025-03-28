package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 의존성주입 생성자를 생서해주는 에노테이션
public class MemberController {
	
    //@Autowired  1번
	private final MemberService memberService; // final 달면 불변성이 됨
	
	/*
	@Autowired  2번
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	/*
	@Autowired  // 3번
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	/*
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
		System.out.println("나는 로그인 요청오면 출동함");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println("id : " + id + ", pw : " + pw);
		System.out.printf("id : %s, pw : %s", id, pw);
		log.info("id : {}, pw : {}", id, pw);
		return "main_page";
	}
	*/
	/*
	@RequestMapping("login")   // 이게 권장하는 방법
	public String login(@RequestParam(value="id", defaultValue="abcde") String id,@RequestParam(value="pw") String pw) {
		log.info("이렇게도 넘어옴? id : {}, pw : {}", id, pw);
		return "main_page";
	}
	*/
	
	/*
	@PostMapping("login")  // 원래 가정을 아래처럼 줄일 수 있음
	public String login(String id, String pw) {
		
		log.info("이렇게도 넘어옴? id : {} / pw : {}", id, pw);
		
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		return "main_page";
	}
	*/
	
	/*
	 * 커멘드 객체 방식
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키값과 객체의 필드명이 동의할 것
	 * 3. setter메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter메서드를 찾아서 요청 시 전달 값을 해당 필드에 대입
	 * (Setter Injection)
	
	 */
	
	/*
	@PostMapping("login") 
	public String login(MemberDTO member, HttpSession session, Model model) {
		
		//log.info("이런건 안됨 {}", member);
		
		/* 데이터가공 => 패스
		 * 요청처리 => 
		 * 응답화면지정
		 *
		log.info("{}", member);
		MemberDTO loginMember = memberService.login(member);
		/*
		if(loginMember !=null) {
			log.info("로그인 성공");
		} else {
			log.info("로그인 실패");
		}
		*
		if(loginMember != null) { // 성공
			// sessionScope에 로그인정보를 담아줌
			session.setAttribute("loginMember", loginMember);
			// 그다음에 main_page로 보내기
			// /WEB-INF/views/
			// .jsp
			// => 포워딩
			// sendRedirect
			// localhost/spring /
			return "redirect:/";
		} else { // 실패
			// error_page
			// requestScope에 에러문구를 담아서 포워딩
			// spring에서는 Model객체를 이용해서 RequestScope에 값을 담음
			model.addAttribute("message", "로그인 실패");
			
			// forwarding
			// /WEB-INF/views/
			// include/error_page
			// .jsp
			return "include/error_page";
		}
		//return "main_page";
	}
	*/
	
	// 두 번째 방법반환타입 ModelAndView로 돌아가기
	@PostMapping("login")
	public ModelAndView login(MemberDTO member, HttpSession session, ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("message","로그인 실패").setViewName("include/error_page");
		}
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mv) {
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("signup-form")
	public String singupForm() {
		// /WEB-INF/views/member/signup-form    .jsp
		return "member/signup-form";
	}
	/*
	 * @param member id
	 * @return 성공시 main~ 실패하면 error_page로
	 */
	@PostMapping("signup")
	public String join(MemberDTO member, HttpServletRequest request) {
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		//log.info("멤버 필드 찍어보기 : {}", member);
		memberService.signUp(member);
		return "main_page";
	}
	
	@GetMapping("my-page")
	public String mypage() {
		return "member/my_page";
	}
	
	
	@PostMapping("update-member") // update-member는 my_page에서 확인
	public String update(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 에노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		/*
		 * 1-1) 404오류 발생 : mapping값 잘못 적음
		 * org.springframework.web.servlet.PageNotFound
		 * - No mapping for POST / spring/update-member
		 * 
		 * 1-2) 405오류 발생 : 앞단에서 POST / GET으로 요청을 보내놓고 메소드와 맞지않은 에노테이션을 사용했을 때
		 * 		             Request method 'POST' not supported
		 * 
		 * 1-3) 필드에 값이 들어오지 않는 경우 : 사실상 오타
		 */
		log.info("사용자가 입력한 값 : {}", member);
		
		// 2. 이번에 실행할 SQL문을 생각(코드 입력 전 SQL문을 먼저 작성)
		// UPDATE문 => KH_MEMBER(MEMBER_ID)
		// ID, PW, NAME, EMAIL, DATE
		// 2-1) 매개변수 MemberDTO타입의 member 필드값
		// 2-2) SessionScope에 loginMember 키값에 memberId필드값
		// 넘겨주어야겠구나 +
		
		// 값들이 유효한 값인지 체크하기
		// MemberID가 존재하는 아이디인지 체크하기
		
		// UPDATE KH_MEMBER SET MEMBER_NAME = 사용자가 입력한 이름, 
		//						EMAIL = 사용자가 입력한 이메일
		//				  WHERE MEMBER_ID = 사용자가 입력한 아이디
		// UPDATE 수행의 결과 => PK를 조건으로 수행함 => 0 / 1
		// 수행 성공했을 때 => my_page.jsp로 이동 + 갱신된 회원의 정보 출력
		
		// Id를 가지고 다시 조회 => login에서도 재활용
		
		// 수행 실패했을 때 => message를 담아서 error_page로 포워딩
		// 예이 발생 =>  예외처리기도 위임
		
		 memberService.update(member, session);
		 
		 return "redirect:my-page";
	}
	
	// 탈퇴구현 숙제로하기
	// 비밀번호 입력받음
	// 비밀번호가 맞는지 검증 => 예외 발생시키기 
	@PostMapping("delete-member")
	public String delete(MemberDTO member, HttpSession session) {
		//memberService.delete(member, session);
		return "redirect:my-page";
	}
	
	@ResponseBody
	@GetMapping("id-check")
	public String idCheck(@RequestParam(name="memberId") String memberId) {
		// 응답을 어떻게 돌려줄것인지
		// 조회 겨로가가 있다 / 없다
		//     NNNY/ NNNN
		
		memberService.idCheck(memberId);
		// SELECT ??? FROM KH_MEMBER WHERE MEMBER_ID =사용자가 입력한 아이디
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
