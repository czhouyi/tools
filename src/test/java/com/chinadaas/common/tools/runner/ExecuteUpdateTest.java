package com.chinadaas.common.tools.runner;

import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;

import junit.framework.TestCase;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月11日 上午11:39:01<br>
 * @author 开发者真实姓名[Andy]
 */
public class ExecuteUpdateTest extends TestCase {

	public void testRun() {
		Runner app = new ExecuteUpdate();
		app.setParams(new String[]{"executeUpdate", "update t_user set state = 1 where id = 1"});
		try {
			app.run();
		} catch (RunnerException e) {
			fail(e.getMessage());
		} catch (ParamException e) {
			fail(e.getMessage());
		}
	}

}

