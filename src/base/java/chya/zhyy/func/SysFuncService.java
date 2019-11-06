package chya.zhyy.func;

import java.util.List;
import java.util.Map;

import chya.zhyy.PageResult;

public interface SysFuncService{
	public final static String SERVICE_NAME="sysFuncService";
	PageResult<SysFuncModel> search(SysFuncQuery query)throws Exception;
	List<Map<String,Object>> loadFuncGroup() throws Exception;
	void save(SysFuncModel data) throws Exception;
	SysFuncModel findOneById(Integer id) throws Exception;
	void remove(String ids) throws Exception;
	List<Map<String,Object>> getMainMemu()throws Exception;
}
