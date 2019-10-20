package chya.zhyy.user;

import chya.zhyy.entity.sys.SysUser;

public class SysUserModel extends SysUser {

	private static final long serialVersionUID = 1L;
	
	private String organiseName;

	public String getOrganiseName() {
		return organiseName;
	}

	public void setOrganiseName(String organiseName) {
		this.organiseName = organiseName;
	}
	
}
