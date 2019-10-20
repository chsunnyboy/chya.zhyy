package chya.zhyy.option;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import chya.zhyy.BaseService;
import chya.zhyy.PageResult;
import chya.zhyy.RequestResult;

@Service(SysOptionService.SERVICE_NAME)
@Transactional
public class SysOptionServiceImpl extends BaseService<SysOptionQuery> implements SysOptionService {

	@Override
	public PageResult<SysOptionModel> search(SysOptionQuery query) throws Exception {
		PageResult<SysOptionModel> result=new PageResult<SysOptionModel>();
		StringBuffer sql=new StringBuffer();
		sql.append(getSelect(query)).append(getFrom(query)).append(getWhere(query));
		List<SysOptionModel> list = select.query(SysOptionModel.class, sql.toString());
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
	public String getSelect(SysOptionQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * ");
		return sql.toString();
	}

	@Override
	public String getFrom(SysOptionQuery t) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" from sys_option ");
		return sql.toString();
	}

	@Override
	public String getWhere(SysOptionQuery query) throws Exception {
		
		StringBuffer sql=new StringBuffer("where 1=1");
		if(!StringUtils.isEmpty(query.getKeyword())) {
			sql.append(" and (option_code like '%"+query.getKeyword().trim()+"%'");
			sql.append(" or option_name like '%"+query.getKeyword().trim()+"%'");
			sql.append(")");
		}
		if(!StringUtils.isEmpty(query.getOptionCode())) {
			sql.append(" and option_code='"+query.getOptionCode().trim()+"'");
		}
		if(!StringUtils.isEmpty(query.getOptionName())) {
			sql.append(" and option_name='"+query.getOptionName().trim()+"'");
		}
		
		return sql.toString();
	}

	@Override
	public PageResult<SysOptionItemModel> searchOptionItems(Integer optionId) throws Exception {
		String sql="select * from sys_option_item where option_id="+optionId;
		List<SysOptionItemModel> items = select.query(SysOptionItemModel.class, sql);
		PageResult<SysOptionItemModel> result=new PageResult<SysOptionItemModel>();
		result.setData(items);
		return result;
	}

	@Override
	public void saveDoc(SysOptionModel data) throws Exception {
		Integer docId=data.getId();
		if(docId==null) {
			//新只能数据
			Map<String,Object> insertdata=new HashMap<>();
			insertdata.put("option_code", data.getOptionCode());
			insertdata.put("option_name", data.getOptionName());
			insertdata.put("user_flag", data.getUserFlag());
			insertdata.put("memo", data.getMemo());
			insert.doInsert("sys_option", insertdata);
		}else {
			String sql="update sys_option set option_code=?,option_name=?,user_flag=?,memo=? where id=?";
			Object[] arg=new Object[] {data.getOptionCode(),data.getOptionName(),data.getUserFlag(),
					data.getMemo(),docId};
			update.doUpdate(sql, arg);
		}
	}

	@Override
	public void saveDtl(SysOptionItemModel data) throws Exception {
		Integer dtlId=data.getId();
		if(dtlId==null) {
			Map<String,Object> insertData=new HashMap<String,Object>();
			insertData.put("item_name",data.getItemName());
			insertData.put("item_id", data.getItemId());
			insertData.put("memo", data.getMemo());
			insertData.put("option_id", data.getOptionId());
			insert.doInsert("sys_option_item", insertData);
		}else {
			String sql="update sys_option_item set item_name=?,item_id=?,memo=? where id=?";
			Object[] arg=new Object[] {data.getItemName(),data.getItemId(),data.getMemo(),dtlId};
			update.doUpdate(sql, arg);
		}
	}

	@Override
	public RequestResult<SysOptionModel> findDocById(Integer id) throws Exception {
		String sql="select * from sys_option where id="+id;
		SysOptionModel queryForObject = select.queryForObject(SysOptionModel.class, sql);
		RequestResult<SysOptionModel> result=new RequestResult<SysOptionModel>();
		result.setData(queryForObject);
		return result;
	}

	@Override
	public RequestResult<SysOptionItemModel> findDtlById(Integer id) throws Exception {
		String sql="select * from sys_option_item where id="+id;
		SysOptionItemModel queryForObject = select.queryForObject(SysOptionItemModel.class, sql);
		RequestResult<SysOptionItemModel> result=new RequestResult<SysOptionItemModel>();
		result.setData(queryForObject);
		return result;
	}

	@Override
	public void removeDoc(String ids) throws Exception {
		String sql="delete from sys_option_item where option_id in ("+ids+")";
		delete.doDelete(sql);
		sql="delete from sys_option where id in ("+ids+")";
		delete.doDelete(sql);
	}

	@Override
	public void removeDtl(String ids) throws Exception {
		String sql="delete from sys_option_item where id in ("+ids+")";
		delete.doDelete(sql);
	}

	@Override
	public void initOptions() throws Exception {
		InputStream resource=this.getClass().getClassLoader().getResourceAsStream("resources/chya/zhyy/option.default.txt");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		buffer.readLine();//去掉标题行
		String line = null;
		String sql="select option.id as option_id,option.option_code,item.item_id from sys_option option left join sys_option_item item on option.id=item.option_id";
        List<Map<String, Object>> options = select.doQuery(sql);
        List<Map<String, Object>> txtOptions=new ArrayList<Map<String, Object>>();
		while ((line = buffer.readLine()) != null) {
			
			if(StringUtils.isEmpty(line))continue;
            String ss[] = line.split(",");

            // 一行五个长度
            if (ss.length < 5) {
                continue;
            }

            //获取字典optionCode、optionName
            String optionCode = ss[0] ;
            String optionName = ss[1] ;
            Boolean userFlag=Boolean.valueOf(ss[2]);
            String itemId=ss[3];
            String itemName=ss[4];
            Map<String, Object> txtOption=new HashMap<String, Object>();
            txtOption.put("option_code", optionCode);
            txtOption.put("item_id", itemId);
            txtOptions.add(txtOption);
            if (StringUtils.isEmpty(optionCode) || StringUtils.isEmpty(optionName)) {
                continue;
            }
            List<Map<String, Object>> collect = options.stream().filter(v->{
            	return optionCode.equals(v.get("option_code"));
            }).collect(java.util.stream.Collectors.toList());
            if(collect==null||collect.size()==0) {
            	Map<String,Object> optionarg=new HashMap<String,Object>();
            	optionarg.put("option_code", optionCode);
            	optionarg.put("option_name", optionName);
            	optionarg.put("user_flag", userFlag);
            	Integer optionId=insert.doInsert("sys_option", optionarg);
            	
            	Map<String,Object> itemarg=new HashMap<String,Object>();
            	itemarg.put("option_id", optionId);
            	itemarg.put("item_name", itemName);
            	itemarg.put("item_id", itemId);
            	insert.doInsert("sys_option_item",itemarg);
            	Map<String,Object> temp =new HashMap<String,Object>();
            	temp.put("option_id", optionId);
            	temp.put("option_code", optionCode);
            	temp.put("item_id", itemId);
            	options.add(temp);
            }else {
            	Integer optionId = (Integer) collect.get(0).get("option_id");
            	List<Map<String, Object>> collect2=collect.stream().filter(v->{
            		return itemId.equals(v.get("item_id"));
            	}).collect(java.util.stream.Collectors.toList());
            	if(collect2!=null&&collect2.size()>0) {
            		continue;
            	}else {
            		Map<String,Object> itemarg=new HashMap<String,Object>();
                	itemarg.put("option_id", optionId);
                	itemarg.put("item_name", itemName);
                	itemarg.put("item_id", itemId);
                	insert.doInsert("sys_option_item",itemarg);
                	Map<String,Object> temp =new HashMap<String,Object>();
                	temp.put("option_id", optionId);
                	temp.put("option_code", optionCode);
                	temp.put("item_id", itemId);
                	options.add(temp);
            	}
            }
        }
		List<Object[]> optionIds=new ArrayList<Object[]>();
		List<Object[]> optionItemIds=new ArrayList<Object[]>();
		
		options.stream().forEach(v->{
			String optionCode=(String) v.get("option_code");
			String itemId=(String) v.get("item_id");
			Integer optionId=(Integer) v.get("option_id");
			
			List<Map<String,Object>> list1 = txtOptions.stream().filter(o->{
				return optionCode.equals((String)o.get("option_code"));
			}).collect(java.util.stream.Collectors.toList());
			if(list1==null||list1.size()==0) {
				Object[] arg=new Object[] {optionId};
				optionIds.add(arg);
			}else {
				List<Map<String,Object>> list2=list1.stream().filter(o1->{
					return itemId.equals((String)o1.get("item_id"));
				}).collect(java.util.stream.Collectors.toList());
				if(list2==null||list2.size()==0) {
					Object[] arg=new Object[] {optionId,itemId};
					optionItemIds.add(arg);
				}
			}
		});
		if(optionIds.size()>0) {
			String sql1="delete from sys_option_item where option_id=?";
			delete.doDelete(sql1, optionIds);
			String sql2="delete from sys_option where id=?";
			delete.doDelete(sql2, optionIds);
		}
		if(optionItemIds.size()>0) {
			String sql1="delete from sys_option_item where option_id=? and item_id=?";
			delete.doDelete(sql1,optionItemIds);
		}
	}

	@Override
	public List<Map<String, Object>> loadOption(String optionName) throws Exception {
		String sql=" select item.item_name as value,item.item_id as key from sys_option option"
				+ " left join sys_option_item item on option.id=item.option_id"
				+ " where option.option_code=?";
		List<Map<String, Object>> result = select.doQuery(sql, new Object[] {optionName});
		return result;
	}
}
