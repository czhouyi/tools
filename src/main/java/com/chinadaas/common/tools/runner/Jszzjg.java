package com.chinadaas.common.tools.runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.chinadaas.common.tools.dao.HBaseDao;
import com.chinadaas.common.tools.exception.ParamException;
import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2015年4月20日 下午3:02:11<br>
 * @author 开发者真实姓名[Andy]
 */
public class Jszzjg extends Runner {
	
	private static Logger logger = Logger.getLogger(Jszzjg.class);
	
	private HBaseDao dao = new HBaseDao();
	
	private static final byte[] TABLE_FAMILY = Bytes.toBytes("f");
	private static final byte[] ALL_QUALIFIER = Bytes.toBytes("A");
	
	private List<String[]> notExist(List<String[]> rvs) throws RunnerException {
		List<Get> gets = new ArrayList<Get>();
		for (String[] rv : rvs) {
			Get g = new Get(Bytes.toBytes(rv[0]));
			gets.add(g);
		}
		Result[] results = dao.get("zzjg_detail", gets);
		List<String> existRow = new ArrayList<String>();
		for (Result r : results) {
			existRow.add(Bytes.toString(r.getRow()));
		}
		List<String[]> ne = new ArrayList<String[]>();
		for (String[] rv : rvs) {
			if (!existRow.contains(rv[0])) {
				ne.add(rv);
			}
		}
		return ne;
	}
	
	private void saveInfo(List<String[]> rvs) throws IOException, RunnerException {
		List<Put> puts = new ArrayList<Put>();
		for (String[] rv : rvs) {
			Put p = new Put(Bytes.toBytes(rv[0]));
			p.add(TABLE_FAMILY, ALL_QUALIFIER, Bytes.toBytes(rv[2]));
			puts.add(p);
		}
		dao.put("zzjg_detail", puts);
	}
	
	private void saveIndex(List<String[]> rvs) throws IOException, RunnerException {
		List<Put> puts = new ArrayList<Put>();
		for (String[] rv : rvs) {
			Put p = new Put(Bytes.toBytes(rv[1]));
			p.add(TABLE_FAMILY, Bytes.toBytes(rv[0]), null);
			puts.add(p);
		}
		dao.put("zzjg_detail_zch", puts);
	}
	
	class InsertJob implements Runnable {
		
		private List<String[]> record;
		private int id;
		
		public InsertJob(int id, List<String[]> record) {
			this.id = id;
			this.record = record;
		}

		public void run() {
			System.out.printf("Thread %d starting...\n", id);
			for (int i = 0, cnt = record.size(); i < cnt; i+=100) {
				List<String[]> sub = null;
				if (i + 100 > cnt) {
					sub = record.subList(i, cnt);
				} else {
					sub = record.subList(i, i+100);
				}
				System.out.printf("Thread %d batch insert from %d to %d.\n", id, i, i+100);
				try {
					List<String[]> ne = notExist(sub);
					saveInfo(ne);
					saveIndex(ne);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RunnerException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("Thread %d completed.\n", id);
		}
		
	}

	public Object run() throws RunnerException, ParamException {
		if(params.length < 2) {
			throw new ParamException(help());
		}
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(params[1]));
			String line = reader.readLine();
			
			List<String[]> rvs = new ArrayList<String[]>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sdf.format(new Date());
			while(line != null && !line.isEmpty()) {
				String[] item = line.split(",");
				if (item.length == 3) {
					item[1] = item[2];
					item[2] = String.format(
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001%s\u00011\u00016\u0001%s\u0001%s\u00011",
							item[1], today, today);
					rvs.add(item);
				}
				
				line = reader.readLine();
			}
			
			ExecutorService pool = Executors.newFixedThreadPool(100);
			for (int i = 0, id = 0, len = rvs.size(); i < len; i+=10000, id++) {
				if (i + 10000 >= len) {
					pool.execute(new InsertJob(id, rvs.subList(i, len)));
				} else {
					pool.execute(new InsertJob(id, rvs.subList(i, i + 10000)));
				}
			}
			pool.shutdown();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("Usage : java -jar chinadaas-tools.jar jszzjg\n");
		buf.append("Such as: \n");
		buf.append("\40\40java -jar chinadaas-tools.jar jszzjg zzjg.csv \n");
		return buf.toString();
	}

}

