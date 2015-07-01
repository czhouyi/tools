package com.chinadaas.common.tools.runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

import com.chinadaas.common.tools.dao.HBaseDao;
import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;
import com.chinadaas.common.tools.util.CommonUtil;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2014年11月6日 下午3:02:11<br>
 * @author 开发者真实姓名[Andy]
 */
public class Hbase extends Runner{
	
	private static Logger logger = Logger.getLogger(Hbase.class);

	public Object run() throws RunnerException, ParamException {
		if(params.length < 4) {
			throw new ParamException(help());
		}
		
		String type = params[1];
		String file = params[2];
		String tableName = params[3];
		
		HBaseDao dao = new HBaseDao();
		
		int cnt = 0;
		if("import".equalsIgnoreCase(type)) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String line = null;
				List<Put> puts = new ArrayList<Put>();
				List<String> cols = null;
				do {
					line = br.readLine();
					if(line != null && line.length() > 0) {
						if(cnt == 0) {
							cols = CommonUtil.toList(line.split("\t", -1));
						} else {
							String[] record = line.split("\t", -1);
							if(record.length < 2) {
								continue;
							}
							Put p = new Put(record[0].getBytes());
							for (int j = 1; j < record.length; j++) {
								String[] key = cols.get(j).split(":");
								p.add(key[0].getBytes(), key[1].getBytes(), record[j].getBytes());
							}
							puts.add(p);
						}
						
						if(cnt > 0 && cnt % 100 == 0) {
							dao.put(tableName, puts);
							puts.clear();
							logger.info((cnt-1) + " records have been put in hbase.");
						}
						cnt ++;
					}
				} while (line != null);
				
				if(!puts.isEmpty()) {
					dao.put(tableName, puts);
					puts.clear();
					logger.info((cnt-1) + " records have been put in hbase.");
				}
			} catch (Exception e) {
				throw new RunnerException(e.getMessage());
			} finally {
				try {
					if(br != null) {
						br.close();
					}
				} catch (IOException e) {
					throw new RunnerException(e.getMessage());
				}
			}
			System.out.printf("Import %d records from file %s to hbase table %s success.\n", cnt-1, file, tableName);
		} else if("export".equalsIgnoreCase(type)) {
			
		}
		
		return cnt-1;
	}

	@Override
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("Usage : java -jar chinadaas-tools.jar hbase <type> <file> <tableName>\n");
		buf.append("\t<type> specifies export data from hbase or import data to hbase\n");
		buf.append("\t<file> specifies file for exporting to or importing from\n");
		buf.append("\t<tableName> specifies the table in hbase.\n");
		buf.append("Such as: \n");
		buf.append("\40\40java -jar chinadaas-tools.jar import dat.txt dis_alqd \n");
		return buf.toString();
	}

}

