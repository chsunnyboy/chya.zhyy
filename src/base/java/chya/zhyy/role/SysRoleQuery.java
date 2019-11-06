package chya.zhyy.role;

import chya.zhyy.BaseQuery;

public class SysRoleQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private String roleName;
	private String roleCode;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
			case "roleName" :  sortField="role_name"; break;
			case "roleCode" :  sortField="role_code"; break;
			case "orderNo" :  sortField="order_no"; break;
			default : sortField="order_no" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
