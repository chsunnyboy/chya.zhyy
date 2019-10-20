package chya.zhyy;

import org.springframework.beans.factory.annotation.Autowired;

import chya.zhyy.option.SysOptionService;
import chya.zhyy.sqlhelper.DeleteApi;
import chya.zhyy.sqlhelper.InsertApi;
import chya.zhyy.sqlhelper.SelectApi;
import chya.zhyy.sqlhelper.UpdateApi;

public class BaseService<T> {
	
	@Autowired protected SelectApi select;
	@Autowired protected InsertApi insert;
	@Autowired protected UpdateApi update;
	@Autowired protected DeleteApi delete;
	
	public String getSelect(T query)throws Exception{
		return null;
	}
	public String getFrom(T query)throws Exception{
		return null;
	}
	public String getWhere(T query) throws Exception{
		return null;
	}
}
