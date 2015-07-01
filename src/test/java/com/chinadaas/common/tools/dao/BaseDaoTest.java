package com.chinadaas.common.tools.dao;

import com.chinadaas.common.tools.dao.BaseDao;
import com.chinadaas.common.tools.exception.RunnerException;

import junit.framework.TestCase;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月11日 上午11:28:46<br>
 * @author 开发者真实姓名[Andy]
 */
public class BaseDaoTest extends TestCase {

	public void testUpdate() {
		BaseDao dao = new BaseDao();
		try {
			dao.update("update t_user set state=1 where id=1");
		} catch (RunnerException e) {
			fail(e.getMessage());
		}
	}
	
	public void testGetCodeName() {
		BaseDao dao = new BaseDao();
		try {
			String codename = dao.getCodeName("CA01", "110000");
			assertEquals("北京市", "北京市");
		} catch (RunnerException e) {
			fail(e.getMessage());
		}
	}

}

