package com.chinadaas.common.tools.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;

import com.chinadaas.common.tools.exception.RunnerException;

import junit.framework.TestCase;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年11月6日 上午11:28:46<br>
 * @author 开发者真实姓名[Andy]
 */
public class HBaseDaoTest extends TestCase {
	
	public void testGetTable() {
		HBaseDao dao = new HBaseDao();
		try {
			String tableName = "dis_alqd";
			if(dao.existTable(tableName)) {
				HTableInterface table = dao.getTable(tableName);
				assertNotNull(table);
			}
		} catch (RunnerException e) {
			fail(e.getMessage());
		}
	}
	
	public void testGet() {
		HBaseDao dao = new HBaseDao();
		try {
			String tableName = "dis_alqd";
			
			Get g1 = new Get("r1".getBytes());
			Get g2 = new Get("r2".getBytes());
			Get g3 = new Get("r3".getBytes());

			List<Get> gs = new ArrayList<Get>();
			gs.add(g1); gs.add(g2);
			
			if(dao.existTable(tableName)) {
				dao.get(tableName, gs);
				dao.get(tableName, g3);
			}
			
		} catch (RunnerException e) {
			fail(e.getMessage());
		}
	}
	
	public void testPut() {
		
	}
	
}

