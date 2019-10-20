package chya.zhyy;

import org.springframework.beans.factory.annotation.Autowired;

import chya.zhyy.option.SysOptionService;

public class BaseController {
	@Autowired protected SysOptionService optionService;
}
