package chya.zhyy.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import chya.zhyy.RequestResult;
import chya.zhyy.func.SysFuncService;
import chya.zhyy.security.LoginUser;
import chya.zhyy.user.SysUserService;
@Controller
public class MainController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping("/noAcess")
    public String doError() {
        return "chya/zhyy/main/noAcess";
    }
	
	@Autowired 
	SysUserService userService;
	
	@Autowired
	SysFuncService funcService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model, HttpServletRequest req, HttpServletResponse rep){
		Object exobj = null;
		String failure = req.getParameter("failure");
		if(null != failure){
			HttpSession session = req.getSession(false);
			if (null != session) {
				exobj = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			}
			if (null != exobj) {
				if(exobj instanceof SessionAuthenticationException){
					model.addAttribute("message", "此用户已登录，请使用其他用户进行登录.");
				}else if(exobj instanceof BadCredentialsException){
					model.addAttribute("message", "密码错误");
				}else if(exobj instanceof UsernameNotFoundException){
					model.addAttribute("message", "用户不存在");
				}else if(exobj instanceof DisabledException){
					model.addAttribute("message", "用户已停用");
				}else {
					model.addAttribute("message", "登陆失败，请重试");
				}
			}
		}
		return "chya/zhyy/main/login";
	}
	/**
     * 主页面
     * @return
     */
    @RequestMapping("/main")
    public String home(Model model) {
    	LoginUser user=LoginUser.getLoginUser();
    	model.addAttribute("userName", user.getUserName());
    	model.addAttribute("userRoles",user.getAuthorities());
    	return "chya/zhyy/main/home2";
    }
    
    @RequestMapping("/toUpdatePwd")
	public String setPws(Model model){
		return "chya/zhyy/main/resetPwd";
	}
	
	@RequestMapping("/updatePwd")
	@ResponseBody
	public RequestResult<String> updatePwd(String data){
		RequestResult<String> result = new RequestResult<String>();
		try {
			userService.updatePwd(data);
		} catch (Exception e) {
			String errorMsg = e.getMessage();
            log.error("新增客户信息 error：" + errorMsg, e);
            result.setError(errorMsg);
		}
		return result;
	}
	
	@RequestMapping("/getMainMemu")
	@ResponseBody
	public RequestResult<String> getMainMemu()throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			List<Map<String,Object>> list=funcService.getMainMemu();
			result.setData(JSONObject.toJSONString(list));
		} catch (Exception e) {
			log.info(e.toString());
			result.setSuccess(false);
			result.setError(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
