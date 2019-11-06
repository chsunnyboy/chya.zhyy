package chya.zhyy.security;


import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RoleResourceVoter implements AccessDecisionVoter<FilterInvocation> {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@Autowired(required = false)
	SecuritySupportService securitySupportService;

	@Override
	public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
		try {
			
			Object principal = authentication.getPrincipal();
			if (null == principal) {
				return ACCESS_DENIED;
			}

			if (!(principal instanceof LoginUser)) {
				return ACCESS_DENIED;
			}

			LoginUser user = (LoginUser) principal;

			if (user.isAdmin()) {
				return AccessDecisionVoter.ACCESS_GRANTED;
			}
			
			String uri = object.getHttpRequest().getRequestURI();
			logger.info("uri==="+uri);
			
			String[] ss = uri.split("/");
			String functionCode = "";
			if(ss.length>0) {
				functionCode=ss[1];
			}
			
			if (StringUtils.isEmpty(functionCode)) {
				return ACCESS_DENIED;
			}
			//首页直接通过
			if("main".equals(functionCode)){
				return AccessDecisionVoter.ACCESS_GRANTED;
			}
			
			return securitySupportService.authorityGranted(user, functionCode) ? ACCESS_GRANTED : ACCESS_DENIED;
		} catch (Exception ex) {
			logger.error("error", ex);
			return ACCESS_DENIED;
		}
	}

}
