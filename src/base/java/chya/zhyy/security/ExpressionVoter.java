package chya.zhyy.security;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

public class ExpressionVoter extends WebExpressionVoter {

	public ExpressionVoter() {
		super();
	}

	@Override
	public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
		return super.vote(authentication, fi, attributes);
	}

	
}
