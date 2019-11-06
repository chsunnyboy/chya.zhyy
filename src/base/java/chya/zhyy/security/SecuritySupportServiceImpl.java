package chya.zhyy.security;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import chya.zhyy.entity.sys.SysFunc;
import chya.zhyy.sqlhelper.SelectApi;
import chya.zhyy.user.SysUserModel;

@Service
public class SecuritySupportServiceImpl implements SecuritySupportService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	SelectApi select;
	
	@Override
	public boolean authorityGranted(LoginUser user, String funccode) {
		try {
			//看当前用户是否有funccode权限;
			if(StringUtils.isEmpty(user)){
				log.info("登录用户为空！");
				return false;
			}
			Integer userId = user.getId();
			if(userId==null){
				log.info("用户uuid为空！");
				return false;
			}
			if(StringUtils.isEmpty(funccode)){
				log.info("funccode为空");
				return false;
			}
			
			Collection<GrantedAuthority> authorities = user.getAuthorities();
			
			if(authorities==null) {
				return false;
			}
			//根据角色ID获取权限
			Boolean hasRole = false;
			for(GrantedAuthority authority:authorities) {
				String roleId = authority.getAuthority();
				StringBuffer sql=new StringBuffer();
				sql.append(" select func.* from sys_role_func roleFunc");
				sql.append(" left join sys_func func on roleFunc.func_id=func.id");
				sql.append(" where roleFunc.role_id="+roleId);
				sql.append(" and func.func_code='"+funccode+"'");
				
				List<SysFunc> funcs = select.query(SysFunc.class, sql.toString());
				if(funcs!=null&&funcs.size()>0) {
					hasRole=true;
					break;
				}
			}
			
			return hasRole;
		} catch (Exception e) {
			log.info("获取用户权限失败"+e.getMessage());
			return false;
		}
	}

	@Override
	public SysUserModel findUser(String usercode) {
		String sql="select * from sys_user where user_code='"+usercode+"'";
		List<SysUserModel> user=null;
		try {
			user = select.query(SysUserModel.class,sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user!=null&&user.size()>0) {
			return user.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public List<UserRoles> findUserRoles(String username) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select userRole.role_id roleid,role.role_code from sys_user_role userRole");
		sql.append(" left join sys_user users on userRole.user_id=users.id");
		sql.append(" left join sys_role role on userRole.role_id=role.id");
		sql.append(" where users.user_code='"+username+"'");
		List<UserRoles> result=null;
		try {
			result = select.query(UserRoles.class, sql.toString());
		} catch (Exception e) {
			log.info("查询用户角色失败"+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
