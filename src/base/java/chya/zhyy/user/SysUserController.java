package chya.zhyy.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import chya.zhyy.BaseController;
import chya.zhyy.PageResult;
import chya.zhyy.RequestResult;
import chya.zhyy.organise.SysOrganiseModel;
import chya.zhyy.organise.SysOrganiseService;

@Controller
@RequestMapping(SysUserController.FUNC_PATH)
public class SysUserController extends BaseController{
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS002";
	 
	@Autowired 
	SysUserService service;
	
	@Autowired 
	SysOrganiseService organiseService;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
	@ModelAttribute("userStatus")
	public List<Map<String, Object>> getUserStatus(Model model) throws Exception {
         List<Map<String, Object>> loadOption = optionService.loadOption("SYS_USE_STATUS");
         model.addAttribute("userStatusP", JSONObject.toJSONString(loadOption));
         return loadOption;
    }
	
	@RequestMapping("/")
	public String home(Model modle) {
		return "chya/zhyy/user/home";
	}
	
	@RequestMapping("/loadOrganise")
	@ResponseBody
	List<Map<String,Object>> loadOrganise() throws Exception{
		List<Map<String,Object>> result = organiseService.loadOrganise();
		return result;
	}
	
	@RequestMapping("/search")
	@ResponseBody
	PageResult<SysUserModel> search(SysUserQuery query) throws Exception{
		return service.search(query);
	}
	@RequestMapping("/save")
	@ResponseBody
	RequestResult<String> save(@RequestBody SysUserModel datamodel)throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			service.save(datamodel);
		} catch (Exception e) {
			log.info(e);
			result.setSuccess(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/findOneById")
	@ResponseBody
	RequestResult<SysUserModel> findOneById(Integer id)throws Exception{
		RequestResult<SysUserModel> result=new RequestResult<SysUserModel>();
		try {
			SysUserModel findOneById = service.findOneById(id);
			result.setData(findOneById);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	PageResult<String> remove(String ids) throws Exception{
		PageResult<String> result=new PageResult<String>();
		try {
			service.remove(ids);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	/** 用户角色维护 **/
	@RequestMapping("/setUserRoles")
	public String setUserRole(Model model, HttpSession session) throws Exception {
		return "chya/zhyy/user/userRole";
	}
	
    @RequestMapping(value = "/getAllUserRoles")
    @ResponseBody
    public Map<String, Object> getAllUserRoles(String roleName,String roleCode,Integer userId)throws Exception{
    	Map<String, Object> result = service.getAllUserRoles(roleName,roleCode,userId);
    	return result;
    }
    
    @RequestMapping(value = "/authUserRoles")
    @ResponseBody
    public RequestResult<String> authUserRoles(Integer userId,String roleIds) throws Exception {
        RequestResult<String> result = new RequestResult<String>();
        try {
        	service.authUserRoles(userId,roleIds);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.error("用户授权 error：" + errorMsg, e);
            result.setError(errorMsg);
        }
        return result;
    }
    
    @RequestMapping(value = "/removeUserRoles")
    @ResponseBody
    public RequestResult<String> removeUserRoles(Integer userId,String roleIds) throws Exception {
        RequestResult<String> result = new RequestResult<String>();
        try {
        	service.removeUserRoles(userId,roleIds);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.error("解除用户授权 error：" + errorMsg, e);
            result.setError(errorMsg);
        }
        return result;
    }
    /** 用户角色维护 **/
    
    @RequestMapping("/resPwd")
    @ResponseBody
    RequestResult<String> resPwd(Integer id) throws Exception{
    	RequestResult<String> result = new RequestResult<String>();
    	try {
			service.resPwd(id);
		} catch (Exception e) {
			String errorMsg = e.getMessage();
            log.error("密码重置error：" + errorMsg, e);
			result.setError(errorMsg);
		}
    	return result;
    }
}
