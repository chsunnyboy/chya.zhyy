package chya.zhyy.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chya.zhyy.BaseController;

@Controller
@RequestMapping(CommonController.FUNC_PATH)
public class CommonController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/common";
}
