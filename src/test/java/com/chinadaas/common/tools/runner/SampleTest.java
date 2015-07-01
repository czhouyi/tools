package com.chinadaas.common.tools.runner;

import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;

import junit.framework.TestCase;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月11日 上午11:36:31<br>
 * @author 开发者真实姓名[Andy]
 */
public class SampleTest extends TestCase {

	public void testRun() {
		Runner app = new Sample();
		app.setParams(new String[]{"sample", "source.txt", "target.txt", "random", "0.8"});
		try {
			app.run();
		} catch (RunnerException e) {
			fail(e.getMessage());
		} catch (ParamException e) {
			fail(e.getMessage());
		}
	}

}

