package chya.zhyy.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class MainController {
	
	@RequestMapping("/noAcess")
    public String doError() {
        return "hps/sys/main/noAcess";
    }
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model, HttpServletRequest req, HttpServletResponse rep){
//		Object exobj = null;
//		String failure = req.getParameter("failure");
//		if(null != failure){
//			HttpSession session = req.getSession(false);
//			if (null != session) {
//				exobj = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//			}
//			if (null != exobj) {
//				if(exobj instanceof SessionAuthenticationException){
//					model.addAttribute("message", "此用户已登录，请使用其他用户进行登录.");
//				}else if(exobj instanceof BadCredentialsException){
//					model.addAttribute("message", "密码错误");
//				}else if(exobj instanceof UsernameNotFoundException){
//					model.addAttribute("message", "用户不存在");
//				}else if(exobj instanceof DisabledException){
//					model.addAttribute("message", "用户已停用");
//				}else {
//					model.addAttribute("message", "登陆失败，请重试");
//				}
//			}
//		}
		return "hps/sys/proxyOperation/login/login";
	}
	/**
     * 主页面
     * @return
     */
    @RequestMapping("/")
    public String home(Model model) {
//    	LoginUser user=LoginUser.getLoginUser();
//    	model.addAttribute("userName", user.getUserName());
//    	model.addAttribute("userRoles",user.getAuthorities());
    	return "chya/zhyy/main/home2";
    }

}
