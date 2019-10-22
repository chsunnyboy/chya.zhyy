package chya.zhyy.user;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chya.zhyy.BaseController;
import chya.zhyy.organise.SysOrganiseService;

@Controller
@RequestMapping(SysUserController.FUNC_PATH)
public class SysUserController extends BaseController{
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS002";
	 
	@Autowired 
	SysOrganiseService service;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	@RequestMapping("/")
	public String home(Model modle) {
		return "chya/zhyy/user/home";
	}
	
	@RequestMapping("/loadOrganise")
	@ResponseBody
	List<Map<String,Object>> loadOrganise() throws Exception{
		List<Map<String,Object>> result = service.loadOrganise();
		return result;
	}
}
