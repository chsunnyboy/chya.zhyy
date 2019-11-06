package chya.zhyy.funcGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import chya.zhyy.BaseService;
import chya.zhyy.PageResult;

@Service(SysFuncGroupService.SERVICE_NAME)
@Transactional
public class SysFuncGroupServiceImpl extends BaseService<SysFuncGroupQuery> implements SysFuncGroupService {

	@Override
	public PageResult<SysFuncGroupModel> search(SysFuncGroupQuery query) throws Exception {
		PageResult<SysFuncGroupModel> result=new PageResult<SysFuncGroupModel>();
		StringBuffer sql=new StringBuffer();
		sql.append(getSelect(query)).append(getFrom(query)).append(getWhere(query)).append(query.addSortAndLimitSql());;
		List<SysFuncGroupModel> list = select.query(SysFuncGroupModel.class, sql.toString());
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
	public String getSelect(SysFuncGroupQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * ");
		return sql.toString();
	}

	@Override
	public String getFrom(SysFuncGroupQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" from sys_func_group ");
		return sql.toString();
	}

	@Override
	public String getWhere(SysFuncGroupQuery query) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("where 1=1");
		if(!StringUtils.isEmpty(query.getKeyword())) {
			sql.append(" and (group_code like '%"+query.getKeyword().trim()+"%'");
			sql.append(" or group_name like '%"+query.getKeyword().trim()+"%'");
			sql.append(")");
		}
		if(!StringUtils.isEmpty(query.getGroupCode())) {
			sql.append(" and group_code='"+query.getGroupCode().trim()+"'");
		}
		if(!StringUtils.isEmpty(query.getGroupName())) {
			sql.append(" and group_name='"+query.getGroupName().trim()+"'");
		}
		return sql.toString();
	}

	@Override
	public void save(SysFuncGroupModel data) throws Exception {
		Integer id=data.getId();
		if(id==null) {
			//新增数据
			Map<String,Object> arg=new HashMap<String,Object>();
			arg.put("group_name", data.getGroupName());
			arg.put("group_code", data.getGroupCode());
			arg.put("status", data.getStatus());
			arg.put("memo", data.getMemo());
			arg.put("order_no", getOrderNo());
			arg.put("icon_cls", data.getIconCls());
			insert.doInsert("sys_func_group", arg);
		}else {
			//保存数据
			Object[] arg=new Object[] {data.getGroupName(),data.getGroupCode(),data.getStatus(),data.getIconCls(),data.getMemo(),data.getId()};
			String updatesql="update sys_func_group set group_name=?,group_code=?,status=?,icon_cls=?,memo=? where id=?";
			update.doUpdate(updatesql, arg);
		}
		
	}
	private Integer getOrderNo() throws Exception{
		String sql="select coalesce(max(order_no),0)+1 as order_no from sys_func_group";
		Map<String, Object> map = select.doQueryOne(sql);
		Integer orderNo=(Integer)map.get("order_no");
		return orderNo;
	}

	@Override
	public SysFuncGroupModel findOneById(Integer id) throws Exception {
		String sql="select * from sys_func_group where id="+id;
		return select.queryForObject(SysFuncGroupModel.class,sql);
	}

	@Override
	public void remove(String ids) throws Exception {
		String sql="select * from sys_func where group_id in ("+ids+")";
		List<Map<String, Object>> doQuery = select.doQuery(sql);
		if(doQuery!=null&&doQuery.size()>0) {
			throw new RuntimeException("被删除的功能组下还有其他功能！");
		}
		delete.doDelete("delete from sys_func_group where id in ("+ids+")");
	}

}
