package chya.zhyy.security;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public LoginSuccessHandler() {
		super();
		setDefaultTargetUrl("/main");
		setAlwaysUseDefaultTargetUrl(true);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		//可添加特定逻辑代码
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
