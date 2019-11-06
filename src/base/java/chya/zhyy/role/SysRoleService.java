package chya.zhyy.role;

import java.util.Map;

import chya.zhyy.PageResult;

public interface SysRoleService{
	public final static String SERVICE_NAME="sysRoleService";
	PageResult<SysRoleModel> search(SysRoleQuery query)throws Exception;
	void save(SysRoleModel data) throws Exception;
	SysRoleModel findOneById(Integer id) throws Exception;
	void remove(String ids) throws Exception;
	Map<String, Object> getAllRoleFuncs(String funcName,String funcCode,Integer roleId)throws Exception;
	void authRoleFuncs(Integer roleId,String funcIds)throws Exception;
    void removeRoleFuncs(Integer roleId,String funcIds)throws Exception;
}
