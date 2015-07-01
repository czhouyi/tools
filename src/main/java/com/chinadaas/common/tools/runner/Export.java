package com.chinadaas.common.tools.runner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.chinadaas.common.tools.dao.BaseDao;
import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;
import com.chinadaas.common.tools.util.CommonUtil;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:42:43<br>
 * @author 开发者真实姓名[Andy]
 */
public class Export extends Runner {

	public Object run() throws RunnerException, ParamException {
		if(params.length < 4) {
			throw new ParamException(help());
		}
		
		String outfile = params[1];
		String format = params[2];
		String sql = params[3];
		
		BaseDao dao = new BaseDao();
		List<Map<String, Object>> data = dao.query(sql);
		
		if(data == null || data.size() <= 0) {
			return 0;
		}
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outfile));
			
			String formatedstr = "";
			if ("json".equalsIgnoreCase(format)) {
				formatedstr = CommonUtil.toJson(data);
			} else if ("xml".equalsIgnoreCase(format)) {
				formatedstr = CommonUtil.toXML(data);
			} else if ("textfile".equalsIgnoreCase(format)) {
				formatedstr = CommonUtil.toLineStr(data, "\t", true);
			} else if ("csv".equalsIgnoreCase(format)) {
				formatedstr = CommonUtil.toLineStr(data, ",", true);
			}
			bw.write(formatedstr);
			bw.flush();
		} catch (Exception e) {
			throw new RunnerException(e.getMessage());
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				throw new RunnerException(e.getMessage());
			}
		}
		
		int cnt = data.size();
		System.out.printf("Export %d records to file %s success.\n", cnt, outfile);
		return cnt;
	}

	@Override
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("Usage : java -jar chinadaas-tools.jar export <outfile> <format> <sql>\n");
		buf.append("\toutfile specifies file path name of export file\n");
		buf.append("\tformat specifies export file format: json, xml, textfile, csv.\n");
		buf.append("\tsql specifies the data sql exported from database.\n");
		buf.append("Such as: \n");
		buf.append("\40\40java -jar chinadaas-tools.jar export export.xml xml \"select * from t_user\" \n");
		return buf.toString();
	}

}

