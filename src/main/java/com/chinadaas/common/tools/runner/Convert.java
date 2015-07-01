package com.chinadaas.common.tools.runner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.chinadaas.common.tools.dao.BaseDao;
import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;
import com.chinadaas.common.tools.file.LineRecordFileConvert;
import com.chinadaas.common.tools.util.CommonUtil;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:42:43<br>
 * @author 开发者真实姓名[Andy]
 */
public class Convert extends Runner {

	public Object run() throws RunnerException, ParamException {
		if(params.length < 4) {
			throw new ParamException(help());
		}
		
		String srcfile = params[1];
		String destfile = params[2];
		String rule = params[3];
		
		// 解析rule
		// 1:CA01,2:CA06,3:CA05
		final Map<Integer, String> ruleMap = new HashMap<Integer, String>();
		final BaseDao dao = new BaseDao();
		for(String item : rule.split(",")) {
			String[] i = item.split(":");
			ruleMap.put(Integer.valueOf(i[0]), i[1]);
		}
		
		// 文件按码表转换
		new LineRecordFileConvert(srcfile, destfile) {
			
			private final String DELIMETER = "\u0001";
			private final String NEWLINE = System.getProperty("line.separator");
			
			@Override
			public String process(String line) throws RunnerException {
				String[] record = line.replace(NEWLINE, "").split(DELIMETER, -1);
				
				for (Iterator iterator = ruleMap.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
					String key = entry.getValue() + "|" + record[entry.getKey()];
					if(CodeListCache.CODE_VALUE.containsKey(key)) {
						record[entry.getKey()] = CodeListCache.CODE_VALUE.get(key);
					} else {
						String codeName = dao.getCodeName(entry.getValue(), record[entry.getKey()]);
						if(CommonUtil.isNullString(codeName)) {
							codeName = record[entry.getKey()];
						}
						CodeListCache.CODE_VALUE.put(key, codeName);
						record[entry.getKey()] = codeName;
					}
				}
				
				return CommonUtil.join(record, DELIMETER) + NEWLINE;
			}
		}.convert();
		
		System.out.printf("Convert file %s success to file %s.\n", srcfile, destfile);
		return 0;
	}

	@Override
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("Usage : java -jar chinadaas-tools.jar convert <srcfile> <destfile> <rule>\n");
		buf.append("\trule specifies the indexs of column and convert type to convert\n");
		buf.append("Such as: \n");
		buf.append("\40\40java -jar chinadaas-tools.jar convert source.txt dest.txt 1:CA01,3:CA16,7:CA05 \n");
		return buf.toString();
	}
	
	static class CodeListCache {
		static Map<String, String> CODE_VALUE = new HashMap<String, String>();
	}

}

