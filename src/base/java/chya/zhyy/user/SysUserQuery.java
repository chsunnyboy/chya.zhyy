package chya.zhyy.user;

import chya.zhyy.BaseQuery;

public class SysUserQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String addSortSql() {
		String sortOrder=this.getSortOrder();
		String sortField = this.getSortField();
		if (!"desc".equals(sortOrder)) {
			sortOrder = "asc";
		}
		switch(sortField) {
//			case "goodsName" :  sortField="cusGoods.goods_name"; break;
//			case "goodsCode" :  sortField="cusGoods.goods_code"; break;
//			case "customName" :  sortField="custom.custom_name"; break;
//			case "barCode" :  sortField="cusGoods.bar_code"; break;
//			case "goodsSpec" :  sortField="cusGoods.goods_spec"; break;
//			case "doSale" :  sortField="cusGoods.do_sale"; break;
//			default : sortField="cusGoods.goods_code" ; break;
		}
		return " order by " + sortField + " " + sortOrder;
	}
}
