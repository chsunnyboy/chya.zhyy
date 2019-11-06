package chya.zhyy.func;

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
import chya.zhyy.security.LoginUser;

@Service(SysFuncService.SERVICE_NAME)
@Transactional
public class SysFuncServiceImpl extends BaseService<SysFuncQuery> implements SysFuncService {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public PageResult<SysFuncModel> search(SysFuncQuery query) throws Exception {
		PageResult<SysFuncModel> result=new PageResult<SysFuncModel>();
		StringBuffer sql=new StringBuffer();
		sql.append(getSelect(query)).append(getFrom(query)).append(getWhere(query)).append(query.addSortAndLimitSql());;
		List<SysFuncModel> list = select.query(SysFuncModel.class, sql.toString());
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
	public String getSelect(SysFuncQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * ");
		return sql.toString();
	}

	@Override
	public String getFrom(SysFuncQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" from sys_func ");
		return sql.toString();
	}

	@Override
	public String getWhere(SysFuncQuery query) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("where 1=1");
		if(!StringUtils.isEmpty(query.getKeyword())) {
			sql.append(" and (func_code like '%"+query.getKeyword().trim()+"%'");
			sql.append(" or func_name like '%"+query.getKeyword().trim()+"%'");
			sql.append(")");
		}
		if(!StringUtils.isEmpty(query.getFuncCode())) {
			sql.append(" and func_code='"+query.getFuncCode().trim()+"'");
		}
		if(!StringUtils.isEmpty(query.getFuncName())) {
			sql.append(" and func_name='"+query.getFuncName().trim()+"'");
		}
		if(query.getGroupId()!=null) {
			sql.append(" and group_id="+query.getGroupId());
		}
		return sql.toString();
	}

	@Override
	public List<Map<String,Object>> loadFuncGroup() throws Exception {
		String sql=" SELECT id,group_code, group_name from sys_func_group";
		List<Map<String,Object>> organise = select.doQuery(sql);
		return organise;
	}

	@Override
	public void save(SysFuncModel data) throws Exception {
		Integer id=data.getId();
		if(id==null) {
			//新增数据
			Map<String,Object> arg=new HashMap<String,Object>();
			arg.put("func_name", data.getFuncName());
			arg.put("func_code", data.getFuncCode());
			arg.put("status", data.getStatus());
			arg.put("group_id", data.getGroupId());
			arg.put("order_no", getOrderNo(data.getGroupId()));
			arg.put("icon_cls", data.getIconCls());
			arg.put("memo", data.getMemo());
			insert.doInsert("sys_func", arg);
		}else {
			//保存数据
			Object[] arg=new Object[] {data.getFuncName(),data.getFuncCode(),data.getStatus(),data.getIconCls(),data.getMemo(),data.getId()};
			String updatesql="update sys_func set func_name=?,func_code=?,status=?,icon_cls=?,memo=? where id=?";
			update.doUpdate(updatesql, arg);
		}
		
	}
	private Integer getOrderNo(Integer groupId) throws Exception{
		String sql="select coalesce(max(order_no),0)+1 as order_no from sys_func where group_id="+groupId;
		Map<String, Object> map = select.doQueryOne(sql);
		Integer orderNo=(Integer)map.get("order_no");
		return orderNo;
	}

	@Override
	public SysFuncModel findOneById(Integer id) throws Exception {
		String sql="select * from sys_func where id="+id;
		return select.queryForObject(SysFuncModel.class,sql);
	}

	@Override
	public void remove(String ids) throws Exception {
		delete.doDelete("delete from sys_func where id in ("+ids+")");
	}

	@Override
	public List<Map<String, Object>> getMainMemu() throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select funcGroup.id groupId,funcGroup.group_name,funcGroup.group_code,funcGroup.icon_cls as groupIconCls,");
		sql.append(" func.id funcId,func.func_name,func.func_code,func.icon_cls as funcIconCls,func.group_id");
		sql.append(" from sys_func func");
		sql.append(" left join sys_func_group funcGroup on func.group_id=funcGroup.id");
		sql.append(" left join sys_role_func roleFunc on func.id=roleFunc.func_id");
		sql.append(" left join sys_role role on roleFunc.role_id=role.id");
		sql.append(" left join sys_user_role userRole on userRole.role_id=role.id");
		sql.append(" left join sys_user users on userRole.user_id=users.id");
		sql.append(" where func.status=1");
		sql.append(" and funcGroup.status=1");
		sql.append(" and role.status=1");
		sql.append(" and users.status=1");
		LoginUser loginuser=LoginUser.getLoginUser();
		if(loginuser!=null && !loginuser.isAdmin()) {
			sql.append(" and user.id="+loginuser.getId());
		}
		List<Map<String, Object>> groupFuncs = select.doQuery(sql.toString());
		if(groupFuncs==null||groupFuncs.size()==0) {
			log.info("该用户还没有系统权限，请联系管理员!");
			throw new RuntimeException("该用户还没有系统权限，请联系管理员!");
		}
		List<Map<String,Object>> groups=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> groupFunc:groupFuncs) {
			Object groupId=groupFunc.get("groupId");
			Object groupName=groupFunc.get("group_name");
			Object iconCls=groupFunc.get("groupIconCls");
			List<Map<String, Object>> collect = groups.stream().filter(v->{
				Object id=v.get("id");
				return id.equals(groupId);
			}).collect(java.util.stream.Collectors.toList());
			if(collect!=null&&collect.size()>0) continue;
			Map<String,Object> group=new HashMap<String,Object>();
			group.put("id", groupId);
			group.put("text", groupName);
			group.put("iconCls", iconCls);
			groups.add(group);
		}
		if(groups==null||groups.size()==0) {
			log.info("该用户还没有系统权限，请联系管理员!");
			throw new RuntimeException("该用户还没有系统权限，请联系管理员!");
		}
		for(Map<String,Object> group:groups) {
			Object groupId = group.get("id");
			List<Map<String, Object>> funcs = groupFuncs.stream().filter(v->{
				Object id = v.get("group_id");
				return id.equals(groupId);
			}).collect(java.util.stream.Collectors.toList());
			List<Map<String, Object>> temps=new ArrayList<Map<String, Object>>();
			for(Map<String, Object> func:funcs) {
				Map<String, Object> temp=new HashMap<String, Object>();
				temp.put("id", groupId+"_"+func.get("funcId"));
				temp.put("iconCls", func.get("funcIconCls"));
				temp.put("text", func.get("func_name"));
				temp.put("url", "/"+func.get("func_code")+"/");
				temps.add(temp);
			}
			group.put("children", temps);
		}
		return groups;
	}

}
