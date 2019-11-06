package chya.zhyy;

import java.io.Serializable;

public class BaseQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String showPager; 

	private int pageIndex;
	
	private int pageSize;
	
	private String sortField;
	
	private String sortOrder;
	
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getShowPager() {
		return showPager;
	}

	public void setShowPager(String showPager) {
		this.showPager = showPager;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String addSortAndLimitSql(){
		String orderAndLimit="";
		
		orderAndLimit = addSortSql();
		
		int size = Integer.valueOf(this.getPageSize());
		int start = Integer.valueOf(this.getPageIndex());
		if(size > 0){
			orderAndLimit+= " offset "+start*size+" limit "+ size;
		}
		return orderAndLimit;
	}
	/**
	 * 子类需要继承该方法
	 * @return
	 */
	public String addSortSql(){
		String sortOrder=this.sortOrder;
		String sortField = this.sortField;
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
