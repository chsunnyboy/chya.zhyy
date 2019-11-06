package chya.zhyy.funcGroup;

import chya.zhyy.BaseQuery;

public class SysFuncGroupQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private String groupName;
	private String groupCode;
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
			case "groupName" :  sortField="group_name"; break;
			case "groupCode" :  sortField="group_code"; break;
			case "orderNo" :  sortField="order_no"; break;
			default : sortField="order_no" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
