package model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCookie {
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	public static void storeCookie(HttpServletResponse response, String userName) {
		System.out.println("Store user cookie");
		Cookie cookie = new Cookie(ATT_NAME_USER_NAME, userName);
//		1 phút
		cookie.setMaxAge(1);
		response.addCookie(cookie);
	}

	public static String getUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// Xóa Cookie của người dùng
	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		// 0 giây. (Cookie này sẽ hết hiệu lực ngay lập tức)
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}
}
