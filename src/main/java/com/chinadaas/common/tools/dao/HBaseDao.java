package com.chinadaas.common.tools.dao;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.log4j.Logger;

import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2014年11月6日 下午1:25:45<br>
 * @author 开发者真实姓名[Andy]
 */
public class HBaseDao {
	
	private static Logger logger = Logger.getLogger(HBaseDao.class);
	
	private static Configuration conf;
	private static HConnection connection;
	
	static {
		if(conf == null) {
			conf = HBaseConfiguration.create();
			try {
				if(connection == null || connection.isAborted() || connection.isClosed() || !connection.isMasterRunning()) {
					connection = HConnectionManager.createConnection(conf);
				}
			} catch (ZooKeeperConnectionException e) {
				logger.error(e.getMessage());
			} catch (MasterNotRunningException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public boolean existTable(String tableName) throws RunnerException {
		try {
			return connection.isTableAvailable(tableName.getBytes());
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	public HTableInterface getTable(String tableName) throws RunnerException {
		try {
			return connection.getTable(tableName);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	public Result get(String tableName, Get get) throws RunnerException {
		HTableInterface table = getTable(tableName);
		try {
			return table.get(get);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	public Result[] get(String tableName, List<Get> gets) throws RunnerException {
		HTableInterface table = getTable(tableName);
		try {
			return table.get(gets);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	public void put(String tableName, Put put) throws RunnerException {
		HTableInterface table = getTable(tableName);
		try {
			table.put(put);
			table.flushCommits();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		}
	}
	
	public void put(String tableName, List<Put> puts) throws RunnerException {
		HTableInterface table = getTable(tableName);
		try {
			table.put(puts);
			table.flushCommits();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RunnerException(e.getMessage());
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

