package chya.zhyy.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import chya.zhyy.user.SysUserModel;

@Service(UserDetailsServiceImpl.SERVICE_NAME)
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String SERVICE_NAME = "hpsDatabaseUserDetailsService";
    @Autowired
    private SecuritySupportService securitySupportService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	SysUserModel user = securitySupportService.findUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        // 获取密码
        String password = user.getWebPwd();

        // 这里可以获取权限的一些信息，这里具有全部权限， 后期可以加权限（参考IBS SecurityService）  TODO
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        List<UserRoles> userRoles = securitySupportService.findUserRoles(username);
        authorities.addAll(userRoles);

        Integer status = user.getStatus();
        Boolean enabled=(status==1?true:false);
        // 构建人员
        LoginUser loginUser = new LoginUser(username, password, enabled, true, true, true, authorities);
        loginUser.setId(user.getId());
        loginUser.setUserName(user.getUserName());
        loginUser.setUserCode(user.getUserCode());
        loginUser.setAdmin(user.getAdmin()==true?true:false);
        
        return loginUser;
    }
}
