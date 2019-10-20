package chya.zhyy.option;

import chya.zhyy.BaseQuery;

public class SysOptionQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private String optionName;
	private String optionCode;
	
	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
			case "optionName" :  sortField="option_Name"; break;
			case "optionCode" :  sortField="option_code"; break;
			default : sortField="option_Name" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
