package chya.zhyy.func;

import chya.zhyy.BaseQuery;

public class SysFuncQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private String funcName;
	private String funcCode;
	private Integer groupId;
	
	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
			case "funcName" :  sortField="func_name"; break;
			case "funcCode" :  sortField="func_code"; break;
			default : sortField="order_no" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
