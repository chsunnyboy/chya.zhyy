package chya.zhyy.sqlhelper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeleteApi {

	private final Log log = LogFactory.getLog(getClass());

	
	public DeleteApi(){
		super();
	}
	
	@Autowired
	private JdbcTemplate jdbc;
	
	public void doDelete(String sql) throws Exception{
		log.info(sql);
		jdbc.execute(sql);
	}
	
	public void doDelete(String sql,List<Object[]> args) throws Exception{
		log.info(sql+" Param:"+getArgsLog(args));
		jdbc.batchUpdate(sql,args);
	}
	
	public void doDelete(String sql,Object[] args) throws Exception{
		log.info(sql+" Param:"+getArgsLog(args));
		jdbc.update(sql, args);
	}
	
	
	private String getArgsLog(List<Object[]> args) throws Exception{
		StringBuffer buffer = new StringBuffer();
		for(Object[] ss:args){
			buffer.append("{");
			for(Object obj:ss){
				if(obj==null){
					obj = "null";
				}
				buffer.append(obj.toString());
				buffer.append(",");
			}
			buffer.append("}");
		}
		return buffer.toString();
	}
	
	private String getArgsLog(Object[] args) throws Exception{
		StringBuffer buffer = new StringBuffer();
			for(Object obj:args){
				if(obj==null){
					obj = "null";
				}
				buffer.append(obj.toString());
				buffer.append(",");
			}
		return buffer.toString();
	}
	
	
}
