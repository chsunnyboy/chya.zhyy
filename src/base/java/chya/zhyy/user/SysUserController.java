package chya.zhyy.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(SysUserController.FUNC_PATH)
public class SysUserController {
	private final Log log = LogFactory.getLog(getClass());
	public static final String FUNC_PATH = "/SYS002";
	 
	@ModelAttribute("funcPath")
    public String funcPath() {
        return FUNC_PATH;
    }
	
}
