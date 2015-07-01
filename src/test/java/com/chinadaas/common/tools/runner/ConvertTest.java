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
public class ConvertTest extends TestCase {
	
	public void testRun() {
		Runner app = new Convert();
		app.setParams(new String[]{"convert", "gh20140519.txt", "gh20140519_1.txt", "1:CA01,2:CA02"});
		try {
			app.run();
		} catch (RunnerException e) {
			fail(e.getMessage());
		} catch (ParamException e) {
			fail(e.getMessage());
		}
	}

}

