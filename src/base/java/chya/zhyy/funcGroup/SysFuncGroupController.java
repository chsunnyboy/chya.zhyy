package chya.zhyy.funcGroup;

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
@RequestMapping(SysFuncGroupController.FUNC_PATH)
public class SysFuncGroupController extends BaseController{
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS004";
	 
	@Autowired SysFuncGroupService service;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
	@ModelAttribute("funcGroupStatus")
    public List<Map<String,Object>> getGroupStatus(Model model)throws Exception {
		List<Map<String, Object>> loadOption = optionService.loadOption("SYS_USE_STATUS");
		model.addAttribute("funcGroupStatusP", JSONObject.toJSONString(loadOption));
        return loadOption;
        
    }
	
	@RequestMapping("/")
	public String home() {
		return "chya/zhyy/funcGroup/home";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	PageResult<SysFuncGroupModel> search(SysFuncGroupQuery query)throws Exception{
		PageResult<SysFuncGroupModel> result;
		try {
			result = service.search(query);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result=new PageResult<SysFuncGroupModel> ();
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	PageResult<String> save(@RequestBody SysFuncGroupModel data) throws Exception{
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
	RequestResult<SysFuncGroupModel> findOneById(Integer id)throws Exception{
		RequestResult<SysFuncGroupModel> result=new RequestResult<SysFuncGroupModel>();
		try {
			SysFuncGroupModel findOneById = service.findOneById(id);
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
