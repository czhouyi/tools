package com.chinadaas.common.tools.runner;

import junit.framework.TestCase;

import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2014年10月30日 上午10:58:13<br>
 * @author 开发者真实姓名[Andy]
 */
public class ExportTest extends TestCase {
	
	public void testRun() {
		Runner app = new Export();
		app.setParams(new String[]{"export", "export.csv", "csv", "select * from t_column_group"});
		try {
			app.run();
		} catch (RunnerException e) {
			fail(e.getMessage());
		} catch (ParamException e) {
			fail(e.getMessage());
		}
	}

}

