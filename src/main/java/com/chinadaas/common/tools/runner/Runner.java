package com.chinadaas.common.tools.runner;

import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:41:34<br>
 * @author 开发者真实姓名[Andy]
 */
public abstract class Runner {

	protected String[] params;

	public void setParams(String[] params) {
		this.params = params;
	}

	public abstract Object run() throws RunnerException, ParamException;
	
	public abstract String help() ;

}

