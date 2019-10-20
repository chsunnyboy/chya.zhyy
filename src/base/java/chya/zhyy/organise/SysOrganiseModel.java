package chya.zhyy.organise;

import chya.zhyy.entity.sys.SysOrganise;

public class SysOrganiseModel extends SysOrganise {

	private static final long serialVersionUID = 1L;
	private Integer parentOrgId;
	public Integer getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
}
