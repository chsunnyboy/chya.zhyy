package chya.zhyy.organise;

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
@RequestMapping(SysOrganiseController.FUNC_PATH)
public class SysOrganiseController extends BaseController{
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS001";
	 
	@Autowired SysOrganiseService service;
	
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
	@ModelAttribute("organiseType")
    public List<Map<String,Object>> getOrganiseType(Model model)throws Exception {
		List<Map<String, Object>> loadOption = optionService.loadOption("SYS_ORGANISE_TYPE");
		model.addAttribute("organiseTypeP", JSONObject.toJSONString(loadOption));
        return loadOption;
        
    }
	
	@RequestMapping("/")
	public String home() {
		return "chya/zhyy/organise/home";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	PageResult<SysOrganiseModel> search(SysOrganiseQuery query)throws Exception{
		PageResult<SysOrganiseModel> result;
		try {
			result = service.search(query);
		} catch (Exception e) {
			String errMsg=e.getMessage();
			log.info(errMsg);
			result=new PageResult<SysOrganiseModel> ();
			result.setSuccess(false);
			result.setError(errMsg);
		}
		return result;
	}
	
	@RequestMapping("/loadOrganise")
	@ResponseBody
	List<Map<String,Object>> loadOrganise() throws Exception{
		List<Map<String,Object>> result = service.loadOrganise();
		return result;
	}
	@RequestMapping("/save")
	@ResponseBody
	PageResult<String> save(@RequestBody SysOrganiseModel data) throws Exception{
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
	RequestResult<SysOrganiseModel> findOneById(Integer id)throws Exception{
		RequestResult<SysOrganiseModel> result=new RequestResult<SysOrganiseModel>();
		try {
			SysOrganiseModel findOneById = service.findOneById(id);
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
