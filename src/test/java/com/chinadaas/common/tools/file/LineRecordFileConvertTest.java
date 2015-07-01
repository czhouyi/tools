package com.chinadaas.common.tools.file;

import junit.framework.TestCase;

import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2014年10月30日 上午10:58:13<br>
 * @author 开发者真实姓名[Andy]
 */
public class LineRecordFileConvertTest extends TestCase {
	
	public void testConvert() {
		FileConvert fc = new LineRecordFileConvert("gh20140519.txt", "gh20140519_2.txt") {
			
			@Override
			public String process(String line) {
				return String.format("line length: %d\n", line.length());
			}
		};
		
		try {
			fc.convert();
		} catch (RunnerException e) {
			fail(e.getMessage());
		}
	}

}

