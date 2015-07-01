package com.chinadaas.common.tools.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2015年4月20日 下午6:15:58<br>
 * @author 开发者真实姓名[Andy]
 */
public class Regno {
	private BaseDao dao = new BaseDao();

	public void update() throws RunnerException {
		
		Map<String, String> map = new HashMap<String, String>();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("name_regno.txt"));
			String line = reader.readLine();
			while (line != null && !line.isEmpty()) {
				String[] nc = line.split("\t");
				if (nc.length < 2) {
					line = reader.readLine();
					continue;
				}
				map.put(nc[0].trim(), nc[1].trim());
				line = reader.readLine();
			}
			
			System.out.println("load data complete.");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			reader = new BufferedReader(new FileReader("or.csv"));
			String line = reader.readLine();
			int cnt = 0;
			while (line != null && !line.isEmpty()) {
				String[] nc = line.split(",");
				if (nc.length < 2) {
					line = reader.readLine();
					continue;
				}
				if (map.get(nc[1].trim()) != null)
					dao.update("update qy_zzjg set regno='"+map.get(nc[1].trim())+"' where name='"+nc[1].trim()+"'");
				line = reader.readLine();
				if (cnt %1000 == 0) {
					System.out.println(cnt);
				}
				cnt ++;
			}
			
			System.out.println("load data complete.");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) throws RunnerException {
		Regno r = new Regno();
		r.update();
	}
}

