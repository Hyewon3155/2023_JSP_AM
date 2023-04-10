package com.koreaIT.java.am;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home/main")
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int loginedMemberId = -1;
		
		if(session.getAttribute("loginedMemberId") != null) {
			// null인 경우, 값이 안 들어있음, 로그인을 하지 않음 
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			//session의 타입은 Object, 즉, 형 변환을 해주어야 함 
		}
		request.setAttribute("loginedMemberId", loginedMemberId);
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	}
}
