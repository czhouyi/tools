package com.chinadaas.common.tools.bean;

import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import junit.framework.TestCase;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月11日 上午11:31:21<br>
 * @author 开发者真实姓名[Andy]
 */
public class BeanFactoryTest extends TestCase {

	public void testGetJdbcTemplate() {
		JdbcTemplate jdbcTemplate = BeanFactory.getJdbcTemplate();
		assertNotNull(jdbcTemplate);
		
		try {
			Map rs = jdbcTemplate.queryForMap("select count(1) from t_user");
			
			for (Iterator iterator = rs.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				int cnt = Integer.valueOf(entry.getValue().toString());
				assertTrue(cnt >= 0);
			}
		} catch (DataAccessException e) {
			fail(e.getMessage());
		}
	}

}

