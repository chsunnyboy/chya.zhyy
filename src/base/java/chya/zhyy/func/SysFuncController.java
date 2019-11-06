package chya.zhyy.func;

import java.util.List;
import java.util.Map;

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
@RequestMapping(SysFuncController.FUNC_PATH)
public class SysFuncController extends BaseController{
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS005";
	 
	@Autowired SysFuncService service;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
	@ModelAttribute("status")
    public List<Map<String,Object>> getOrganiseStatus(Model model)throws Exception {
		List<Map<String, Object>> loadOption = optionService.loadOption("SYS_USE_STATUS");
		model.addAttribute("statusP", JSONObject.toJSONString(loadOption));
        return loadOption;
    }
	
	@RequestMapping("/")
	public String home() {
		return "chya/zhyy/func/home";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	PageResult<SysFuncModel> search(SysFuncQuery query)throws Exception{
		PageResult<SysFuncModel> result;
		try {
			result = service.search(query);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result=new PageResult<SysFuncModel> ();
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/loadFuncGroup")
	@ResponseBody
	List<Map<String,Object>> loadFuncGroup() throws Exception{
		List<Map<String,Object>> result = service.loadFuncGroup();
		return result;
	}
	@RequestMapping("/save")
	@ResponseBody
	PageResult<String> save(@RequestBody SysFuncModel data) throws Exception{
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
	RequestResult<SysFuncModel> findOneById(Integer id)throws Exception{
		RequestResult<SysFuncModel> result=new RequestResult<SysFuncModel>();
		try {
			SysFuncModel findOneById = service.findOneById(id);
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
}
