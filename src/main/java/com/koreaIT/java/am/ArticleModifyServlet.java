package com.koreaIT.java.am;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.koreaIT.java.am.config.Config;
import com.koreaIT.java.am.util.DBUtil;
import com.koreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/modify")
public class ArticleModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;

		try {
			Class.forName(Config.getDBDriverName());

			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassWd());
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = SecSql.from("SELECT * FROM article");
			sql.append("WHERE id = ?", id);
			
			Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
			
			request.setAttribute("articleRow", articleRow);
			
			HttpSession session = request.getSession();
			
			int loginedMemberId = -1;
			
			if (session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
			}
			// 현재 로그인을 한 상태이면 loginedMemberId 변수에 로그인한 아이디를 가져옴(세션으로부터)
			if(loginedMemberId == -1) {
				response.setContentType("text/html; charset=UTF-8;");
				response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요'); location.replace('../member/login');</script>"));
				return;
			}
			// 현재 로그인을 한 상태가 아니면(값이 변수에 들어오지 못해, 초기값인 -1로 설정되어 있음) 
			// 로그인 후 이용해 달라는 문구 
			if ((int) articleRow.get("memberId") != loginedMemberId) {
				// URL로 검색해서 들어왔을 때, 파라미터로 직접 memberId를 입력한 경우
				response.setContentType("text/html; charset=UTF-8;");
				response.getWriter().append(String.format("<script>alert('해당 게시물에 대한 권한이 없습니다'); location.replace('detail?id=%d');</script>", id));
				return;
			}
			// 현재 로그인한 아이디와 작성한 사람의 Id가 동일하지 않으면, (게시물의 작성자와 로그인한 사람의 아이디를 비교)
			// 해당 게시물에 대한 권한이 없다고 출력
			
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
