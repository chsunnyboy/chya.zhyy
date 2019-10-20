package chya.zhyy.organise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import chya.zhyy.BaseService;
import chya.zhyy.PageResult;

@Service(SysOrganiseService.SERVICE_NAME)
@Transactional
public class SysOrganiseServiceImpl extends BaseService<SysOrganiseQuery> implements SysOrganiseService {

	@Override
	public PageResult<SysOrganiseModel> search(SysOrganiseQuery query) throws Exception {
		PageResult<SysOrganiseModel> result=new PageResult<SysOrganiseModel>();
		StringBuffer sql=new StringBuffer();
		sql.append(getSelect(query)).append(getFrom(query)).append(getWhere(query));
		List<SysOrganiseModel> list = select.query(SysOrganiseModel.class, sql.toString());
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
	public String getSelect(SysOrganiseQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * ");
		return sql.toString();
	}

	@Override
	public String getFrom(SysOrganiseQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" from sys_organise ");
		return sql.toString();
	}

	@Override
	public String getWhere(SysOrganiseQuery query) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("where 1=1");
		if(!StringUtils.isEmpty(query.getKeyword())) {
			sql.append(" and (organise_code like '%"+query.getKeyword().trim()+"%'");
			sql.append(" or organise_name like '%"+query.getKeyword().trim()+"%'");
			sql.append(")");
		}
		if(!StringUtils.isEmpty(query.getOrganiseCode())) {
			sql.append(" and organise_code='"+query.getOrganiseCode().trim()+"'");
		}
		if(!StringUtils.isEmpty(query.getOrganiseName())) {
			sql.append(" and organise_name='"+query.getOrganiseName().trim()+"'");
		}
		if(query.getParentOrgId()!=null) {
			if(query.getParentOrgId()==0) {
				sql.append(" and parent_org_id is null");
			}else {
				sql.append(" and parent_org_id="+query.getParentOrgId());
			}
		}
		return sql.toString();
	}

	@Override
	public List<Map<String,Object>> loadOrganise() throws Exception {
		String sql=" SELECT id,organise_code, organise_name, organise_type,coalesce(parent_org_id,0) parent_org_id "
				+ " FROM public.sys_organise";
		List<Map<String,Object>> organise = select.doQuery(sql);
		if(organise==null)organise=new ArrayList<Map<String,Object>>();
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("organise_name", "组织机构");
		root.put("organise_code", "ROOT");
		root.put("id", 0);
		organise.add(root);
		return organise;
	}

	@Override
	public void save(SysOrganiseModel data) throws Exception {
		Integer id=data.getId();
		if(id==null) {
			//新增数据
			Map<String,Object> arg=new HashMap<String,Object>();
			arg.put("organise_name", data.getOrganiseName());
			arg.put("organise_code", data.getOrganiseCode());
			arg.put("organise_type", data.getOrganiseType());
			if(data.getParentOrgId()!=0) {
				arg.put("parent_org_id", data.getParentOrgId());
			}else {
				arg.put("parent_org_id", null);
			}
			arg.put("order_no", getOrderNo(data.getParentOrgId()));
			insert.doInsert("sys_organise", arg);
		}else {
			//保存数据
			Object[] arg=new Object[] {data.getOrganiseName(),data.getOrganiseCode(),data.getMemo(),data.getId()};
			String updatesql="update sys_organise set organise_name=?,organise_code=?,memo=? where id=?";
			update.doUpdate(updatesql, arg);
		}
		
	}
	private Integer getOrderNo(Integer parentOrgId) throws Exception{
		String sql="";
		if(parentOrgId==0) {
			sql="select coalesce(max(order_no),0)+1 as order_no from sys_organise where parent_org_id=null";
		}else {
			sql="select coalesce(max(order_no),0)+1 as order_no from sys_organise where parent_org_id="+parentOrgId;
		}
		Map<String, Object> map = select.doQueryOne(sql);
		Integer orderNo=(Integer)map.get("order_no");
		return orderNo;
	}

	@Override
	public SysOrganiseModel findOneById(Integer id) throws Exception {
		String sql="select * from sys_organise where id="+id;
		return select.queryForObject(SysOrganiseModel.class,sql);
	}

	@Override
	public void remove(String ids) throws Exception {
		String sql="select * from sys_organise where parent_org_id in ("+ids+")";
		List<Map<String, Object>> doQuery = select.doQuery(sql);
		if(doQuery!=null&&doQuery.size()>0) {
			throw new RuntimeException("被删除的组织机构下还有其他组织机构！");
		}
		delete.doDelete("delete from sys_organise where id in ("+ids+")");
	}

}
