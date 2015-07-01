package com.chinadaas.common.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.runner.Runner;
import com.chinadaas.common.tools.util.CommonUtil;

public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class);

	public static final String APP_CLASSPATH = "com.chinadaas.common.tools.runner";
	static Map<String, String> APPS;
	
	static {
		if (APPS == null) {
			APPS = CommonUtil.getApps();
		}
	}
	
	public static String tip0() {
		StringBuffer tip0 = new StringBuffer();
		tip0.append("Usage : java -jar chinadaas-tools.jar COMMAND\n");
		tip0.append("\twhere COMMAND is one of:\n");
		for (Iterator iterator = APPS.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			tip0.append("\40\40").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
		}
		return tip0.toString();
	}
	
	public static void main(String[] args) {
		if(args.length <= 0) {
			System.out.println(tip0());
			System.exit(0);
		}
		
		Set<String> applist = APPS.keySet();
		
		if(!applist.contains(args[0])) {
			System.out.println(tip0());
			System.exit(0);
		}
		
		String appName = CommonUtil.capticalFirst(args[0]);
		
		try {
			Runner app = (Runner) Class.forName(APP_CLASSPATH + "." + appName).newInstance();
			app.setParams(args);
			app.run();
		} catch (ParamException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.printf("Some errors occur when running %s. Details are following:\n", appName);
			System.out.println(e.getMessage());
		}
	}

}
