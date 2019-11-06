package chya.zhyy.security;


import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class UserRoles implements GrantedAuthority,Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String roleid;
	String roleCode;

	public UserRoles() {
		super();
	}
	public UserRoles(String roleid) {
		super();
		this.roleid = roleid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Override
	public String getAuthority() {
		return this.roleid;
	}

	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
