package chya.zhyy.user;

import chya.zhyy.BaseQuery;

public class SysUserQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private String userCode;
	private String userName;
	private Integer orgId;
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
			case "userCode" :  sortField="users.user_code"; break;
			case "userName" :  sortField="users.user_code"; break;
			case "phoneNo" :  sortField="users.phone_no"; break;
			case "validDate" :  sortField="users.valid_date"; break;
			case "orderNo" :  sortField="users.order_no"; break;
			default : sortField="users.order_no" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
