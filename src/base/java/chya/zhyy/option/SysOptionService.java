package chya.zhyy.option;

import java.util.List;
import java.util.Map;

import chya.zhyy.PageResult;
import chya.zhyy.RequestResult;

public interface SysOptionService{
	public final static String SERVICE_NAME="sysOptionService";
	PageResult<SysOptionModel> search(SysOptionQuery query)throws Exception;
	PageResult<SysOptionItemModel> searchOptionItems(Integer optionId)throws Exception;
	void saveDoc(SysOptionModel data)throws Exception;
	void saveDtl(SysOptionItemModel data)throws Exception;
	RequestResult<SysOptionModel> findDocById(Integer id)throws Exception;
	RequestResult<SysOptionItemModel> findDtlById(Integer id)throws Exception;
	void removeDoc(String ids)throws Exception;
	void removeDtl(String ids)throws Exception;
	void initOptions()throws Exception;
	List<Map<String,Object>> loadOption(String optionName)throws Exception;
}
