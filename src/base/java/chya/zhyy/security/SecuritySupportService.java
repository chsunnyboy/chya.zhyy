package chya.zhyy.security;

import java.util.List;

import chya.zhyy.user.SysUserModel;

public interface SecuritySupportService {
	SysUserModel findUser(String username);
		
	List<UserRoles> findUserRoles(String username);
	
	boolean authorityGranted(LoginUser user, String funccode);
    	
}
