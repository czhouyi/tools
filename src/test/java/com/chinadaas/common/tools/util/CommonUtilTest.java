package com.chinadaas.common.tools.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinadaas.common.tools.exception.RunnerException;

import junit.framework.TestCase;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月11日 上午10:09:30<br>
 * @author 开发者真实姓名[Andy]
 */
public class CommonUtilTest extends TestCase {

	public void testCapticalFirst() {
		assertEquals("CommonUtil", CommonUtil.capticalFirst("commonUtil"));
	}

	public void testToList() {
		String[] arr = {"a", "b", "c"};
		List<String> expected = new ArrayList<String>();
		expected.add("a"); expected.add("b"); expected.add("c");
		List<String> actual = CommonUtil.toList(arr);
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
		assertEquals(expected.get(2), actual.get(2));
	}
	
	public void testGetString() {
		assertEquals("", CommonUtil.getString(null));
		assertEquals("", CommonUtil.getString(""));
		assertEquals("string", CommonUtil.getString("string"));
	}
	
	public void testIsNullString () {
		assertTrue(CommonUtil.isNullString(null));
		assertTrue(CommonUtil.isNullString(""));
		assertTrue(CommonUtil.isNullString("null"));
		assertFalse(CommonUtil.isNullString("string"));
	}
	
	public void testRemoveBlank() {
		String s12288 = "hello　world";
		String space = "hello world";
		String stab = "hello	world";
		assertFalse(s12288.equals(space));
		assertFalse(s12288.equals(stab));
		assertEquals("helloworld", CommonUtil.removeBlank(s12288));
		assertEquals("helloworld", CommonUtil.removeBlank(space));
		assertEquals("helloworld", CommonUtil.removeBlank(stab));
	}
	
	public void testJoin() {
		String[] array = new String[] {"1", "2", "3", "4"};
		String delimeter = "|";
		assertEquals("1|2|3|4", CommonUtil.join(array, delimeter));
	}
	
	private List<Map<String, Object>> data() {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> d1 = new HashMap<String, Object>();
		d1.put("1", "d1-item1");d1.put("2", "d1-item2");d1.put("3", "d1-item3");
		Map<String, Object> d2 = new HashMap<String, Object>();
		d2.put("1", "d2-item1");d2.put("2", "d2-item2");d2.put("3", "d2-item3");
		Map<String, Object> d3 = new HashMap<String, Object>();
		d3.put("1", "d3-item1");d3.put("2", "d3-item2");d3.put("3", "d3-item3");
		data.add(d1);data.add(d2);data.add(d3);
		return data;
	}
	
	public void testToJson() {
		String rstr = CommonUtil.toJson(data());
//		System.out.println(rstr);
		assertNotNull(rstr);
	}
	
	public void testToXML() {
		String rstr = CommonUtil.toXML(data());
//		System.out.println(rstr);
		assertNotNull(rstr);
	}
	
	public void testToLineStr() {
		String rstr = CommonUtil.toLineStr(data(), ",", true);
//		System.out.println(rstr);
		assertNotNull(rstr);
	}

	public void testSampleFromFile() {
		BufferedReader br = null;
		try {
			int rs = CommonUtil.sampleFromFile("source.txt", "target.txt", "random", 0.5);
			assertTrue(rs > 0);
			
			br = new BufferedReader(new FileReader("target.txt"));
			String line = null;
			int i = 0;
			do {
				line = br.readLine();
				if(line != null && line.length() > 0) {
					i ++;
				}
			} while (line != null);
			
			assertEquals(rs, i);
			
		} catch (RunnerException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}
	
	public void testSample() {
		List<Integer> sample = CommonUtil.sample(20, 10, "random");
		assertEquals(10, sample.size());
		
		// 校验重复
		Set<Integer> s = new HashSet<Integer>(sample);
		
		assertEquals(sample.size(), s.size());
	}
	
	public void testGetApps() {
		Map<String, String> apps = CommonUtil.getApps();
		assertTrue(apps.size() > 0);
	}

}

