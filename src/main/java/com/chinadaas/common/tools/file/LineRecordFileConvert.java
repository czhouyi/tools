package com.chinadaas.common.tools.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2014年10月30日 上午9:50:04<br>
 * @author 开发者真实姓名[Andy]
 */
public abstract class LineRecordFileConvert implements FileConvert {
	
	private String srcfile;
	private String destfile;
	
	public LineRecordFileConvert(String srcfile, String destfile) {
		this.srcfile = srcfile;
		this.destfile = destfile;
	}
	
	public void convert() throws RunnerException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(srcfile));
			bw = new BufferedWriter(new FileWriter(destfile));
			String line = null;
			do {
				line = br.readLine();
				if(line != null && line.length() > 0) {
					line = process(line);
					bw.write(line);
				}
			} while (line != null);
			bw.flush();
		} catch (Exception e) {
			throw new RunnerException(e.getMessage());
		} finally {
			try {
				if(br != null) {
					br.close();
				}
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				throw new RunnerException(e.getMessage());
			}
		}
	}

	
	public abstract String process(String line) throws RunnerException;
}

