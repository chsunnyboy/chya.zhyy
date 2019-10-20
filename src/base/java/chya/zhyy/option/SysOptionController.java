package chya.zhyy.option;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chya.zhyy.PageResult;
import chya.zhyy.RequestResult;

@Controller
@RequestMapping(SysOptionController.FUNC_PATH)
public class SysOptionController {
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS003";
	 
	@Autowired SysOptionService service;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
	@RequestMapping("/")
	public String home() {
		return "chya/zhyy/option/home";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	PageResult<SysOptionModel> search(SysOptionQuery query)throws Exception{
		PageResult<SysOptionModel> result=service.search(query);
		return result;
	}
	
	@RequestMapping("/searchOptionItems")
	@ResponseBody
	PageResult<SysOptionItemModel> searchOptionItems(Integer optionId)throws Exception{
		PageResult<SysOptionItemModel> result=service.searchOptionItems(optionId);
		return result;
	}
	
	@RequestMapping("/saveDoc")
	@ResponseBody
	RequestResult<String> saveDoc(@RequestBody SysOptionModel data)throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			service.saveDoc(data);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/saveDtl")
	@ResponseBody
	RequestResult<String> saveDtl(@RequestBody SysOptionItemModel data)throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			service.saveDtl(data);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	@RequestMapping("/findDocById")
	@ResponseBody
	RequestResult<SysOptionModel> findDocById(Integer id)throws Exception{
		RequestResult<SysOptionModel> result=new RequestResult<SysOptionModel>();
		try {
			result=service.findDocById(id);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	@RequestMapping("/findDtlById")
	@ResponseBody
	RequestResult<SysOptionItemModel> findDtlById(Integer id)throws Exception{
		RequestResult<SysOptionItemModel> result=new RequestResult<SysOptionItemModel>();
		try {
			result=service.findDtlById(id);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	@RequestMapping("/removeDoc")
	@ResponseBody
	RequestResult<String> removeDoc(String ids)throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			service.removeDoc(ids);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/removeDtl")
	@ResponseBody
	RequestResult<String> removeDtl(String ids)throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			service.removeDtl(ids);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/initOptions")
	@ResponseBody
	RequestResult<String> initOptions()throws Exception{
		RequestResult<String> result=new RequestResult<String>();
		try {
			service.initOptions();
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
}
