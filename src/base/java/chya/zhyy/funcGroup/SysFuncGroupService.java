package chya.zhyy.funcGroup;

import chya.zhyy.PageResult;

public interface SysFuncGroupService{
	public final static String SERVICE_NAME="sysFuncGroupService";
	PageResult<SysFuncGroupModel> search(SysFuncGroupQuery query)throws Exception;
	void save(SysFuncGroupModel data) throws Exception;
	SysFuncGroupModel findOneById(Integer id) throws Exception;
	void remove(String ids) throws Exception;
}
