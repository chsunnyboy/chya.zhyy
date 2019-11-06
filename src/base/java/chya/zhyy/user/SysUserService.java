package chya.zhyy.user;

import java.util.Map;

import chya.zhyy.PageResult;
import chya.zhyy.organise.SysOrganiseModel;

public interface SysUserService {
	public final static String SERVICE_NAME="sysUserService";
	PageResult<SysUserModel> search(SysUserQuery query)throws Exception;
	void save(SysUserModel datamodel) throws Exception;
	SysUserModel findOneById(Integer id) throws Exception;
	void remove(String ids) throws Exception;
	Map<String, Object> getAllUserRoles(String roleName,String roleCode,Integer userId)throws Exception;
    void authUserRoles(Integer userId,String roleIds)throws Exception;
    void removeUserRoles(Integer userId,String roleIds)throws Exception;
    void resPwd(Integer id) throws Exception;
    void updatePwd(String data)throws Exception;
}
