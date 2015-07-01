package com.chinadaas.common.tools.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.chinadaas.common.tools.bean.BeanFactory;
import com.chinadaas.common.tools.exception.RunnerException;
import com.chinadaas.common.tools.util.CommonUtil;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午4:43:33<br>
 * @author 开发者真实姓名[Andy]
 */
public class BaseDao {
	
	private static Logger logger = Logger.getLogger(BaseDao.class);
	
	/**
	 * desc: 执行数据库更新语句<br>
	 * date: 2014年10月30日 上午10:08:47<br>
	 * @author 开发者真实姓名[Andy]
	 * @param sql
	 * @return
	 * @throws RunnerException
	 */
	public int update(String sql) throws RunnerException {
		JdbcTemplate jdbcTemplate = BeanFactory.getJdbcTemplate();
		try {
			return jdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	public List<Map<String, Object>> query(String sql, Object... params) throws RunnerException {
		JdbcTemplate jdbcTemplate = BeanFactory.getJdbcTemplate();
		try {
			return jdbcTemplate.queryForList(sql, params);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	/**
	 * desc: 获取工商码表名称<br>
	 * date: 2014年10月30日 上午10:09:14<br>
	 * @author 开发者真实姓名[Andy]
	 * @param codetype
	 * @param codevalue
	 * @return
	 * @throws RunnerException 
	 */
	public String getCodeName(String codetype, String codevalue) throws RunnerException {
		String sql = " select codename from t_dex_app_codelist where code_type_value=? and codevalue=? ";
		List<Map<String, Object>> result = query(sql, codetype, codevalue);
		if(result.size() <= 0) {
			return null;
		} else {
			String rs = CommonUtil.getString(result.get(0).get("CODENAME"));
			return CommonUtil.removeBlank(rs);
		}
	}
	
}

