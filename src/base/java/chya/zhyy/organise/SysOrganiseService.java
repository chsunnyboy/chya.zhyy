package chya.zhyy.organise;

import java.util.List;
import java.util.Map;

import chya.zhyy.PageResult;

public interface SysOrganiseService{
	public final static String SERVICE_NAME="sysOrganiseService";
	PageResult<SysOrganiseModel> search(SysOrganiseQuery query)throws Exception;
	List<Map<String,Object>> loadOrganise() throws Exception;
	void save(SysOrganiseModel data) throws Exception;
	SysOrganiseModel findOneById(Integer id) throws Exception;
	void remove(String ids) throws Exception;
}
