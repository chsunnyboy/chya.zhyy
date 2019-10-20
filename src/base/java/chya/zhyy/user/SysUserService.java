package chya.zhyy.user;

import java.util.Map;

import chya.zhyy.BaseService;

public interface SysUserService {
	public final static String SERVICE_NAME="sysUserService";
	Map<String,Object> search()throws Exception;
}
