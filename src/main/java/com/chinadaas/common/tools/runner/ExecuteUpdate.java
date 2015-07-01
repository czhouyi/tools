package com.chinadaas.common.tools.runner;

import com.chinadaas.common.tools.dao.BaseDao;
import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:42:43<br>
 * @author 开发者真实姓名[Andy]
 */
public class ExecuteUpdate extends Runner {

	public Object run() throws RunnerException, ParamException {
		if(params.length < 2) {
			throw new ParamException(help());
		}
		BaseDao dao = new BaseDao();
		int cnt = dao.update(params[1]);
		System.out.printf("SQL Update: %s\n", params[1]);
		System.out.printf("Update success. %d record(s) effected.\n", cnt);
		return cnt;
	}

	@Override
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("Usage: java -jar chinadaas-tools.jar executeUpdate <sql>\n");
		buf.append("Such as: \n");
		buf.append("\40\40java -jar chinadaas-tools.jar executeUpdate \"update table set field1='***' where field2='***'\" \n");
		buf.append("\40\40java -jar chinadaas-tools.jar executeUpdate \"insert into table values ('**', '**')\" \n");
		return buf.toString();
	}

}

