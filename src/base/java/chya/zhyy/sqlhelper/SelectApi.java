package chya.zhyy.sqlhelper;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SelectApi {
	private final Log log = LogFactory.getLog(getClass());
	
	public SelectApi(){
		super();
	}
	
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * 查询sql
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> doQuery(String sql) throws Exception{
		log.info("SelectApi.sql:"+sql);
		List<Map<String, Object>> records = jdbc.queryForList(sql);
		return records;
	}
	
	/**
	 * 查询sql
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doQueryOne(String sql) throws Exception{
		log.info("SelectApi.sql:"+sql);
		List<Map<String, Object>> records = jdbc.queryForList(sql);
		if(records!=null&&records.size()>0){
			return records.get(0);
		}
		return null;
	}
	
	/**
	 * 查询sql
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> doQuery(String sql,Object[] args) throws Exception{
		log.info("SelectApi.sql:"+sql);
		List<Map<String, Object>> records = jdbc.queryForList(sql, args);
		return records;
	}
	
	/**
	 * 查询sql
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doQueryOne(String sql,Object[] args) throws Exception{
		log.info("SelectApi.sql:"+sql);
		List<Map<String, Object>> records = jdbc.queryForList(sql, args);
		if(records!=null&&records.size()>0){
			return records.get(0);
		}
		return null;
	}

	public <T> List<T> query(Class<T> t, String sql)throws Exception{
		log.info("SelectApi.query:" + sql);
		return jdbc.query(sql, new BeanPropertyRowMapper<>(t));
	}

	public <T> T queryForObject(Class<T> t, String sql)throws Exception{
		log.info("SelectApi.queryForObject:" + sql);
		List<T> listData = this.query(t, sql);
		if (listData == null || listData.size() <= 0) {
			return null;
		}
		return listData.get(0);
	}
}
