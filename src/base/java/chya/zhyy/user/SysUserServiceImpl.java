package chya.zhyy.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import chya.zhyy.BaseService;
import chya.zhyy.PageResult;
import chya.zhyy.security.LoginUser;

@Service(SysUserService.SERVICE_NAME)
@Transactional
public class SysUserServiceImpl extends BaseService<SysUserQuery> implements SysUserService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public PageResult<SysUserModel> search(SysUserQuery query) throws Exception {
		PageResult<SysUserModel> result=new PageResult<SysUserModel>();
		StringBuffer sql=new StringBuffer();
		sql.append(getSelect(query)).append(getFrom(query)).append(getWhere(query)).append(query.addSortAndLimitSql());
		List<SysUserModel> list = select.query(SysUserModel.class,sql.toString());
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
	public String getSelect(SysUserQuery t) throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(" select users.* ");
		return sb.toString();
	}

	@Override
	public String getFrom(SysUserQuery t) throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(" from sys_user users");
		return sb.toString();
	}

	@Override
	public String getWhere(SysUserQuery t) throws Exception {
		StringBuffer sb=new StringBuffer(" where 1=1");
		if(!StringUtils.isEmpty(t.getKeyword())) {
			sb.append(" and (users.user_code like '%"+t.getKeyword()+"%'");
			sb.append(" or users.user_name like '%"+t.getKeyword()+"%'");
			sb.append(" or users.user_opcode like '%"+t.getKeyword()+"%')");
		}
		if(!StringUtils.isEmpty(t.getUserCode())) {
			sb.append(" and users.user_code='"+t.getUserCode()+"'");
		}
		if(!StringUtils.isEmpty(t.getUserName())) {
			sb.append(" and users.user_name='"+t.getUserName()+"'");
		}
		
		if(t.getOrgId()!=null) {
			if(t.getOrgId()==0) {
				sb.append(" and users.org_id is null");
			}else {
				sb.append(" and users.org_id="+t.getOrgId());
			}
		}
		return sb.toString();
	}

	@Override
	public void save(SysUserModel data) throws Exception {
		Integer id=data.getId();
		if(id==null) {
			//新增数据
			Map<String,Object> arg=new HashMap<String,Object>();
			arg.put("user_name", data.getUserName());
			arg.put("user_code", data.getUserCode());
			arg.put("phone_no", data.getPhoneNo());
			arg.put("admin", false);
			arg.put("status", data.getStatus());
			arg.put("valid_date", data.getValidDate());
			arg.put("memo", data.getMemo());
			if(data.getOrgId()==0) {
				arg.put("org_id", null);
			}else {
				arg.put("org_id", data.getOrgId());
			}
			arg.put("order_no", getOrderNo(data.getOrgId()));
			PasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();
			arg.put("web_pwd", userPasswordEncoder.encode("888888"));
			insert.doInsert("sys_user", arg);
		}else {
			//保存数据
			Object[] arg=new Object[] {data.getUserName(),data.getUserCode(),data.getPhoneNo(),
				data.getStatus(),data.getValidDate(),data.getMemo(),data.getId()};
			String updatesql="update sys_user set user_name=?,user_code=?,phone_no=?,"
					+ "status=?,valid_date=?,memo=? where id=?";
			update.doUpdate(updatesql, arg);
		}
	}
	
	private Integer getOrderNo(Integer orgId) throws Exception{
		String sql="";
		if(orgId==0) {
			sql="select coalesce(max(order_no),0)+1 as order_no from sys_user where org_id is null";
		}else {
			sql="select coalesce(max(order_no),0)+1 as order_no from sys_user where org_id="+orgId;
		}
		Map<String, Object> map = select.doQueryOne(sql);
		Integer orderNo=(Integer)map.get("order_no");
		return orderNo;
	}

	@Override
	public void remove(String ids) throws Exception {
		delete.doDelete("delete from sys_user where id in ("+ids+")");
	}

	@Override
	public SysUserModel findOneById(Integer id) throws Exception {
		String sql="select * from sys_user where id="+id;
		return select.queryForObject(SysUserModel.class,sql);
	}
	
	@Override
	public Map<String, Object> getAllUserRoles(String roleName, String roleCode, Integer userId) throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		if(userId==null) throw new RuntimeException("用户id不能为空");
		StringBuffer where=new StringBuffer();
		if(!StringUtils.isEmpty(roleName)) {
			where.append(" and role.role_name='"+roleName.trim()+"'");
		}
		if(!StringUtils.isEmpty(roleCode)) {
			where.append(" and role.role_code='"+roleCode.trim()+"'");
		}
		//已授权角色
		StringBuffer authsql=new StringBuffer();
		authsql.append(" select role.id,role.role_name,role.role_code from sys_role role");
		authsql.append(" where exists (select * from sys_user_role userRole where userRole.user_id="+userId+" and role.id=userRole.role_id)");
		authsql.append(where);
		authsql.append(" order by role.order_no ");
		List<Map<String, Object>> authData = select.doQuery(authsql.toString());
		result.put("authData", authData);
		
		//未授权角色
		authsql=new StringBuffer();
		authsql.append(" select role.id,role.role_name,role.role_code from sys_role role");
		authsql.append(" where not exists (select * from sys_user_role userRole where userRole.user_id="+userId+" and role.id=userRole.role_id)");
		authsql.append(" and role.status=1");
		authsql.append(where);
		authsql.append(" order by role.order_no ");
		List<Map<String, Object>> noAuthData = select.doQuery(authsql.toString());
		result.put("noAuthData", noAuthData);
		return result;
	}

	@Override
	public void authUserRoles(Integer userId, String roleIds) throws Exception {
		log.info("用户授权角色");
		if(roleIds==null||userId==null) {
			throw new RuntimeException("用户或者角色信息不能为空！");
		}
		String[] ids = roleIds.split(",");
		List<Map<String,Object>> args=new ArrayList<Map<String,Object>>();
		for(String roleid:ids) {
			Map<String,Object> arg=new HashMap();
			arg.put("user_id",userId);
			arg.put("role_id", Integer.valueOf(roleid));
			args.add(arg);
		}
		insert.doInsert("sys_user_role", args);
		log.info("用户授权角色完成");
	}

	@Override
	public void removeUserRoles(Integer userId, String roleIds) throws Exception {
		log.info("解除用户授权角色");
		if(roleIds==null||userId==null) {
			throw new RuntimeException("用户或者角色信息不能为空！");
		}
		String[] ids = roleIds.split(",");
		List<Object[]> args=new ArrayList<Object[]>();
		for(String roleid:ids) {
			Object[] arg=new Object[] {userId,Integer.valueOf(roleid)};
			args.add(arg);
		}
		String sql="delete from sys_user_role where user_id=? and role_id=?";
		delete.doDelete(sql, args);
		log.info("解除用户授权角色完成");
	}
	@Override
	public void resPwd(Integer id) throws Exception {
		log.info("重置用户密码");
		String sql="update sys_user set web_pwd=? where id=?";
		PasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();
		update.doUpdate(sql,new Object[] {userPasswordEncoder.encode("888888"),id});
		log.info("重置用户密码完成");
	}
	
	@Override
	public void updatePwd(String data) throws Exception {
		log.info("修改密码开始！！！");
		LoginUser loginUser=LoginUser.getLoginUser();
		Integer userId=loginUser.getId();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = objectMapper.readValue(data, Map.class);
		if(map!=null&&map.size()>0){
			String querySql="select * from sys_user where id="+userId;
			Map<String, Object> one=select.doQueryOne(querySql);
			String pwd=(String)one.get("web_pwd");//数据库查出来的密码
			String oldpwd=(String)map.get("oldpwd");//页面传过来的原始密码
			PasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();
			if(userPasswordEncoder.matches(oldpwd, pwd)){//判断原始密码是否正确
				String newpwd=(String)map.get("newpwd");//新密码
				String surepwd=(String)map.get("surepwd");//确认密码
				if(!newpwd.equals(surepwd)) throw new RuntimeException("确认密码不正确");
				String pwd1=userPasswordEncoder.encode(newpwd);
				String sql="update sys_user set web_pwd='"+pwd1+"' where id="+userId;
				update.doUpdate(sql);
			}else{
				throw new RuntimeException("原始密码不正确");
			}
		}
		
	}
}
