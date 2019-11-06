package chya.zhyy.user;

import chya.zhyy.entity.sys.SysUser;

public class SysUserModel extends SysUser {

	private static final long serialVersionUID = 1L;
	
	private String organiseName;
	private Integer orgId;
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrganiseName() {
		return organiseName;
	}

	public void setOrganiseName(String organiseName) {
		this.organiseName = organiseName;
	}
	
}
