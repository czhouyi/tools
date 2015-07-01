package com.chinadaas.common.tools.runner;

import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;
import com.chinadaas.common.tools.util.CommonUtil;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:42:43<br>
 * @author 开发者真实姓名[Andy]
 */
public class Sample extends Runner {

	public Object run() throws RunnerException, ParamException {
		if(params.length < 4) {
			throw new ParamException(help());
		}
		
		double ratio = 0.5;
		String srcfile = params[1];
		String destfile = params[2];
		String algorithm = params[3];
		if (params.length > 4) {
			ratio = Double.valueOf(params[4]);
		}
		
		int cnt = CommonUtil.sampleFromFile(srcfile, destfile, algorithm, ratio);
		System.out.printf("Sample %.0f%% percentage records from file %s...\n", ratio*100, srcfile);
		System.out.printf("Sample success. %d record(s) to file %s.\n", cnt, destfile);
		return cnt;
	}

	@Override
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("Usage : java -jar chinadaas-tools.jar sample <srcfile> <destfile> <algorithm> [<ratio>]\n");
		buf.append("\talgorithm can be specified with random or sequence\n");
		buf.append("\tratio not specified with default value 0.5\n");
		buf.append("Such as: \n");
		buf.append("\40\40java -jar chinadaas-tools.jar sample source.txt dest.txt random 0.2 \n");
		buf.append("\40\40java -jar chinadaas-tools.jar sample /tmp/source.txt /tmp/dest.txt sequence \n");
		return buf.toString();
	}

}

