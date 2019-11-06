package chya.zhyy.role;

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

@Controller
@RequestMapping(SysRoleController.FUNC_PATH)
public class SysRoleController extends BaseController{
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS006";
	 
	@Autowired SysRoleService service;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
	@ModelAttribute("status")
    public List<Map<String,Object>> getRoleStatus(Model model)throws Exception {
		List<Map<String, Object>> loadOption = optionService.loadOption("SYS_USE_STATUS");
		model.addAttribute("statusP", JSONObject.toJSONString(loadOption));
        return loadOption;
        
    }
	
	@RequestMapping("/")
	public String home() {
		return "chya/zhyy/role/home";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	PageResult<SysRoleModel> search(SysRoleQuery query)throws Exception{
		PageResult<SysRoleModel> result;
		try {
			result = service.search(query);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result=new PageResult<SysRoleModel> ();
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	PageResult<String> save(@RequestBody SysRoleModel data) throws Exception{
		PageResult<String> result=new PageResult<String>();
		try {
			service.save(data);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/findOneById")
	@ResponseBody
	RequestResult<SysRoleModel> findOneById(Integer id)throws Exception{
		RequestResult<SysRoleModel> result=new RequestResult<SysRoleModel>();
		try {
			SysRoleModel findOneById = service.findOneById(id);
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
	
	@RequestMapping("/setRoleFuncs")
	public String setRoleFunc(Model model, HttpSession session) throws Exception {
		return "chya/zhyy/role/roleFunc";
	}
	
	/** 角色功能维护 **/
    @RequestMapping(value = "/getAllRoleFuncs")
    @ResponseBody
    public Map<String, Object> getAllRoleFuncs(String funcName,String funcCode,Integer roleId)throws Exception{
    	Map<String, Object> result = service.getAllRoleFuncs(funcName,funcCode,roleId);
    	return result;
    }
    @RequestMapping(value = "/authRoleFuncs")
    @ResponseBody
    public RequestResult<String> authRoleFuncs(Integer roleId,String funcIds) throws Exception {
        RequestResult<String> result = new RequestResult<String>();
        try {
        	service.authRoleFuncs(roleId,funcIds);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.error("角色功能授权 error：" + errorMsg, e);
            result.setError(errorMsg);
        }
        return result;
    }
    
    @RequestMapping(value = "/removeRoleFuncs")
    @ResponseBody
    public RequestResult<String> removeRoleFuncs(Integer roleId,String funcIds) throws Exception {
        RequestResult<String> result = new RequestResult<String>();
        try {
        	service.removeRoleFuncs(roleId,funcIds);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.error("解除角色功能授权 error：" + errorMsg, e);
            result.setError(errorMsg);
        }
        return result;
    }
    /** 角色功能维护 **/
}
