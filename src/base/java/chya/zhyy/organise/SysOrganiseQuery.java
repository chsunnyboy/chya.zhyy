package chya.zhyy.organise;

import chya.zhyy.BaseQuery;

public class SysOrganiseQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private String organiseName;
	private String organiseCode;
	private Integer parentOrgId;
	
	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getOrganiseName() {
		return organiseName;
	}

	public void setOrganiseName(String organiseName) {
		this.organiseName = organiseName;
	}

	public String getOrganiseCode() {
		return organiseCode;
	}

	public void setOrganiseCode(String organiseCode) {
		this.organiseCode = organiseCode;
	}

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
			case "organiseName" :  sortField="organise_name"; break;
			case "organiseCode" :  sortField="organise_code"; break;
			case "organiseType" :  sortField="organise_type"; break;
			default : sortField="organise_name" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
