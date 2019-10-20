package chya.zhyy.sqlhelper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UpdateApi {
	private final Log log = LogFactory.getLog(getClass());

	public UpdateApi() {
		super();
	}

	@Autowired
	private JdbcTemplate jdbc;

    /**
     * 最后更新语句
     */
    private final String last_modify_repalc = ",last_modify_time = CURRENT_TIMESTAMP where ";

	public void doUpdate(String sql) throws Exception {
		doUpdate(sql, true);
		return;
	}

	public void doUpdate(String sql, Object[] args) throws Exception {
		doUpdate(sql, args, true);
		return;
	}

	public void doUpdate(String sql, List<Object[]> args) throws Exception {
		doUpdate(sql, args, true);
		return;
	}

	public void doUpdate(String sql, boolean lastModifyFlag) throws Exception {
		if (lastModifyFlag) {
		    sql = sql.replace(" where ", last_modify_repalc);
		}
		log.info("UpdateApi执行sql：" + sql);
		jdbc.update(sql);
		return;
	}

	public void doUpdate(String sql, Object[] args, boolean lastModifyFlag) throws Exception {
		if (lastModifyFlag) {
		    sql = sql.replace(" where ", last_modify_repalc);
		}
		log.info("UpdateApi执行sql：" + sql);
		jdbc.update(sql, args);
		return;
	}

	public void doUpdate(String sql, List<Object[]> args, boolean lastModifyFlag) throws Exception {
		if (lastModifyFlag) {
            sql = sql.replace(" where ", last_modify_repalc);
		}
		log.info("UpdateApi执行sql：" + sql);
		jdbc.batchUpdate(sql, args);
		return;
	}

}
