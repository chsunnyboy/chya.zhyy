package chya.zhyy.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import chya.zhyy.BaseService;
import chya.zhyy.PageResult;

@Service(SysRoleService.SERVICE_NAME)
@Transactional
public class SysRoleServiceImpl extends BaseService<SysRoleQuery> implements SysRoleService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public PageResult<SysRoleModel> search(SysRoleQuery query) throws Exception {
		PageResult<SysRoleModel> result=new PageResult<SysRoleModel>();
		StringBuffer sql=new StringBuffer();
		sql.append(getSelect(query)).append(getFrom(query)).append(getWhere(query)).append(query.addSortAndLimitSql());;
		List<SysRoleModel> list = select.query(SysRoleModel.class, sql.toString());
		result.setData(list);
		StringBuffer countsql=new StringBuffer();
		countsql.append("select count(*) total ").append(getFrom(query)).append(getWhere(query));
		Map<String, Object> doQueryOne = select.doQueryOne(countsql.toString());
		if(doQueryOne!=null&&doQueryOne.size()>0) {
			Long total=(Long)doQueryOne.get("total");
			result.setTotal(total);
		}
		return result;
	}

	@Override
	public String getSelect(SysRoleQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * ");
		return sql.toString();
	}

	@Override
	public String getFrom(SysRoleQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" from sys_role ");
		return sql.toString();
	}

	@Override
	public String getWhere(SysRoleQuery query) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("where 1=1");
		if(!StringUtils.isEmpty(query.getKeyword())) {
			sql.append(" and (role_code like '%"+query.getKeyword().trim()+"%'");
			sql.append(" or role_name like '%"+query.getKeyword().trim()+"%'");
			sql.append(")");
		}
		if(!StringUtils.isEmpty(query.getRoleCode())) {
			sql.append(" and role_code='"+query.getRoleCode().trim()+"'");
		}
		if(!StringUtils.isEmpty(query.getRoleName())) {
			sql.append(" and role_name='"+query.getRoleName().trim()+"'");
		}
		return sql.toString();
	}

	@Override
	public void save(SysRoleModel data) throws Exception {
		Integer id=data.getId();
		if(id==null) {
			//新增数据
			Map<String,Object> arg=new HashMap<String,Object>();
			arg.put("role_name", data.getRoleName());
			arg.put("role_code", data.getRoleCode());
			arg.put("status", data.getStatus());
			arg.put("memo", data.getMemo());
			arg.put("order_no", getOrderNo());
			insert.doInsert("sys_role", arg);
		}else {
			//保存数据
			Object[] arg=new Object[] {data.getRoleName(),data.getRoleCode(),data.getStatus(),data.getMemo(),data.getId()};
			String updatesql="update sys_role set role_name=?,role_code=?,status=?,memo=? where id=?";
			update.doUpdate(updatesql, arg);
		}
		
	}
	private Integer getOrderNo() throws Exception{
		String sql="select coalesce(max(order_no),0)+1 as order_no from sys_role";
		Map<String, Object> map = select.doQueryOne(sql);
		Integer orderNo=(Integer)map.get("order_no");
		return orderNo;
	}

	@Override
	public SysRoleModel findOneById(Integer id) throws Exception {
		String sql="select * from sys_role where id="+id;
		return select.queryForObject(SysRoleModel.class,sql);
	}

	@Override
	public void remove(String ids) throws Exception {
		delete.doDelete("delete from sys_role where id in ("+ids+")");
	}

	@Override
	public Map<String, Object> getAllRoleFuncs(String funcName, String funcCode, Integer roleId) throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		if(roleId==null) throw new RuntimeException("角色id不能为空");
		StringBuffer where=new StringBuffer();
		if(!StringUtils.isEmpty(funcName)) {
			where.append(" and func.func_name='"+funcName.trim()+"'");
		}
		if(!StringUtils.isEmpty(funcCode)) {
			where.append(" and func.func_code='"+funcCode.trim()+"'");
		}
		//已授权角色
		StringBuffer authsql=new StringBuffer();
		authsql.append(" select func.id,func.func_name,func.func_code from sys_func func");
		authsql.append(" where exists (select * from sys_role_func roleFunc where roleFunc.role_id="+roleId+" and func.id=roleFunc.func_id)");
		authsql.append(where);
		authsql.append(" order by func.order_no ");
		List<Map<String, Object>> authData = select.doQuery(authsql.toString());
		result.put("authData", authData);
		
		//未授权角色
		authsql=new StringBuffer();
		authsql.append(" select func.id,func.func_name,func.func_code from sys_func func");
		authsql.append(" where not exists (select * from sys_role_func roleFunc where roleFunc.role_id="+roleId+" and func.id=roleFunc.func_id)");
		authsql.append(" and func.status=1");
		authsql.append(where);
		authsql.append(" order by func.order_no ");
		List<Map<String, Object>> noAuthData = select.doQuery(authsql.toString());
		result.put("noAuthData", noAuthData);
		return result;
	}

	@Override
	public void authRoleFuncs(Integer roleId, String funcIds) throws Exception {
		log.info("角色授权功能");
		if(funcIds==null||roleId==null) {
			throw new RuntimeException("角色或者功能信息不能为空！");
		}
		String[] ids = funcIds.split(",");
		List<Map<String,Object>> args=new ArrayList<Map<String,Object>>();
		for(String funcid:ids) {
			Map<String,Object> arg=new HashMap();
			arg.put("role_id",roleId);
			arg.put("func_id", Integer.valueOf(funcid));
			args.add(arg);
		}
		insert.doInsert("sys_role_func", args);
		log.info("角色授权功能完成");
		
	}

	@Override
	public void removeRoleFuncs(Integer roleId, String funcIds) throws Exception {
		log.info("解除角色授权功能");
		if(funcIds==null||roleId==null) {
			throw new RuntimeException("用户或者角色信息不能为空！");
		}
		String[] ids = funcIds.split(",");
		List<Object[]> args=new ArrayList<Object[]>();
		for(String funcid:ids) {
			Object[] arg=new Object[] {roleId,Integer.valueOf(funcid)};
			args.add(arg);
		}
		String sql="delete from sys_role_func where role_id=? and func_id=?";
		delete.doDelete(sql, args);
		log.info("解除角色授权功能完成");
		
	}
	
}
