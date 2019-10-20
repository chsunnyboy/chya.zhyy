package chya.zhyy.sqlhelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InsertApi {
	private final Log log = LogFactory.getLog(getClass());

	public InsertApi() {
		super();
	}

	@Autowired
	private JdbcTemplate jdbc;

	public List<Integer> doInsert(String tableName, String seqName, List<Map<String, Object>> args, Boolean flag) throws Exception {
		log.info("InsertHelper.tableName:" + tableName);

		// uuiID
		List<Integer> idList = new ArrayList<>();

		// insert语句
		String insertSql = null;
		List<Object[]> insertArgs = new ArrayList<>();
		// insert字段
		List<String> insertColumnList = new ArrayList<>();
		// 产品/平台字段对应值
		HashMap<String, Object> otherValueMap = new HashMap<>();
		for (Map<String, Object> map : args) {

			// 构造insert语句
			if (insertSql == null) {
				StringBuffer columnBuffer = new StringBuffer();
				StringBuffer valueBuffer = new StringBuffer();
				List<String> columnList = new ArrayList<>();

				// 普通字段
				for (String column : map.keySet()) {
					columnBuffer.append(column);
					columnBuffer.append(",");
					valueBuffer.append("?,");
					columnList.add(column);
					insertColumnList.add(column);
				}

				// 基础字段
				if (flag) {
					Map<String, Object> cpColumnMap = new HashMap<>();
					cpColumnMap.put("last_modify_time", new Date());
					cpColumnMap.put("create_time", new Date());
					cpColumnMap.put("version", 1);

					for (String column : cpColumnMap.keySet()) {
						if (!columnList.contains(column)) {
							columnBuffer.append(column);
							columnBuffer.append(",");
							valueBuffer.append("?,");
							insertColumnList.add(column);
							otherValueMap.put(column, cpColumnMap.get(column));
						}
					}
				}

				// uuid
				if (!insertColumnList.contains("id")) {
					columnBuffer.append("id,");
					valueBuffer.append("?,");
				}

				String column = columnBuffer.substring(0, columnBuffer.length() - 1);
				String value = valueBuffer.substring(0, valueBuffer.length() - 1);
				StringBuffer buffer = new StringBuffer();
				buffer.append("insert into ");
				buffer.append(tableName);
				buffer.append("(");
				buffer.append(column);
				buffer.append(") values (");
				buffer.append(value);
				buffer.append(")");

				insertSql = buffer.toString();
				log.info("InsertHelper.sql:" + insertSql);
			}

			// 绑定参数
			// 绑定参数
			Object[] columnArgs = null;
			if (insertColumnList.contains("id")) {
				columnArgs = new Object[insertColumnList.size()];
			} else {
				columnArgs = new Object[insertColumnList.size() + 1];
				// id
				Integer id= getSequenceId(seqName);
				idList.add(id);
				columnArgs[insertColumnList.size()] = id;
			}

			for (int i = 0; i < insertColumnList.size(); i++) {
				String column = insertColumnList.get(i);
				Object value = null;
				if (map.containsKey(column)) {
					value = map.get(column);
				} else {
					value = otherValueMap.get(column);
				}
				columnArgs[i] = value;
			}
			insertArgs.add(columnArgs);
		}

		if (insertArgs.size() > 0) {
			log.info("InsertHelper.doInsert");
			jdbc.batchUpdate(insertSql, insertArgs);
		}
		return idList;
	}

	/**
	 * insert
	 *
	 * @param tableName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public List<Integer> doInsert(String tableName, List<Map<String, Object>> args, Boolean flag) throws Exception {
		log.info("InsertHelper.tableName:" + tableName);
		// 取ID
		String seqName = tableName + "_id_seq";
		return doInsert(tableName, seqName, args, flag);
	}

	/**
	 * insert
	 *
	 * @param tableName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public List<Integer> doInsert(String tableName, List<Map<String, Object>> args) throws Exception {
		return doInsert(tableName, args, true);
	}

	/**
	 * insert
	 *
	 * @param tableName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Integer doInsert(String tableName, Map<String, Object> args, Boolean flag) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(args);
		List<Integer> idList = doInsert(tableName, list, flag);
		Integer id = null;
		if (idList != null && idList.size() > 0) {
			id = idList.get(0);
		}
		return id;
	}

	/**
	 * insert
	 *
	 * @param tableName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Integer doInsert(String tableName, Map<String, Object> args) throws Exception {
		return doInsert(tableName, args, true);
	}

	/**
	 * insert
	 *
	 * @param tableName
	 * @param seqName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Integer doInsert(String tableName, String seqName, Map<String, Object> args, Boolean flag) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(args);
		List<Integer> idList = doInsert(tableName, seqName, list, flag);
		Integer id = null;
		if (idList != null && idList.size() > 0) {
			id = idList.get(0);
		}
		return id;
	}

	/**
	 * insert
	 *
	 * @param tableName
	 * @param seqName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Integer doInsert(String tableName, String seqName, Map<String, Object> args) throws Exception {
		return doInsert(tableName, seqName, args, true);
	}

	/**
	 * 执行批量新增--使用insert完整语句
	 *
	 * @param sql
	 * @throws Exception
	 */
	public void doInsert(String sql) throws Exception {
		jdbc.batchUpdate(sql);
	}

	/**
	 * 执行保存，返回保存数据的ID
	 * @param tableName 表名
	 * @param args 参数
	 * @param flag 是否更新基础字段
	 * @return
	 * @throws Exception
	 */
	public List<Integer> insert(String tableName, List<Map<String, Object>> args, Boolean flag) throws Exception{
		List<Integer> list = this.doInsert(tableName, args, flag);
		// 根据uuid查询数据，返回id
		String sql = "select distinct id from " + tableName;
		List<Map<String, Object>> idMapList = jdbc.queryForList(sql);

		List<Integer> idList = new ArrayList<>();
		idMapList.forEach(t -> {
			Integer id = Integer.valueOf(t.get("id") + "");
			idList.add(id);
		});

		// 返回结果
		return idList;
	}

	/**
	 * 执行插入，返回插入数据ID
	 * @param tableName 表名
	 * @param args 参数
	 * @param flag 是否更新基础字段
	 * @return
	 * @throws Exception
	 */
	public Integer insert(String tableName, Map<String, Object> args, Boolean flag) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(args);

		List<Integer> idList = insert(tableName, list, flag);
		Integer id = null;
		if (idList != null && idList.size() > 0) {
			id = idList.get(0);
		}
		return id;
	}

	/**
	 * 执行插入，返回插入数据ID
	 * @param tableName 表名
	 * @param args 参数
	 * @return
	 * @throws Exception
	 */
	public Integer insert(String tableName, Map<String, Object> args) throws Exception {
		return insert(tableName, args, true);
	}

	/**
	 * 取UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 取序列号
	 * @param seqName
	 * @return
	 * @throws Exception
	 */
	public Integer getSequenceId(String seqName) throws Exception{
		String seqsql = "select nextval('" + seqName + "')";
		Integer id = jdbc.queryForObject(seqsql, Integer.class);
		return id;
	}
}
