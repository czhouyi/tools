package com.chinadaas.common.tools.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.chinadaas.common.tools.exception.RunnerException;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:51:42<br>
 * @author 开发者真实姓名[Andy]
 */
public class CommonUtil {
	
	/**
	 * desc: 字符首字母大写<br>
	 * date: 2014年10月27日 下午5:37:13<br>
	 * @author 开发者真实姓名[Andy]
	 * @param str
	 * @return
	 */
	public static String capticalFirst(String str) {
		StringBuffer b = new StringBuffer(str);
		b.setCharAt(0, Character.toUpperCase(b.charAt(0)));
		return b.toString();
	}
	
	/**
	 * desc: 数组转化成列表<br>
	 * date: 2014年10月27日 下午5:36:50<br>
	 * @author 开发者真实姓名[Andy]
	 * @param strs
	 * @return
	 */
	public static List<String> toList(String[] strs) {
		List<String> list = new ArrayList<String>();
		for (String str : strs) {
			list.add(str);
		}
		return list;
	}
	
	/**
	 * desc: 对象转字符串<br>
	 * date: 2014年10月30日 上午10:07:35<br>
	 * @author 开发者真实姓名[Andy]
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	
	/**
	 * desc: 判断字符串是否为空<br>
	 * date: 2014年11月3日 下午3:34:28<br>
	 * @author 开发者真实姓名[Andy]
	 * @param str
	 * @return
	 */
	public static boolean isNullString(String str) {
		return str == null || str.length() <= 0 || "null".equalsIgnoreCase(str);
	}
	
	/**
	 * desc: 去掉字符串中的空格字符（包括半全角）和制表字符<br>
	 * date: 2014年11月3日 下午3:34:43<br>
	 * @author 开发者真实姓名[Andy]
	 * @param str
	 * @return
	 */
	public static String removeBlank(String str) {
		str = str.replace((char) 12288, ' ');
		str = str.replaceAll("\\s|\t", "");
		return str;
	}
	
	/**
	 * desc: 组装字符串数组<br>
	 * date: 2014年10月30日 上午10:23:59<br>
	 * @author 开发者真实姓名[Andy]
	 * @param array
	 * @param delimeter
	 * @return
	 */
	public static String join(String[] array, String delimeter) {
		StringBuffer buf = new StringBuffer();
		for(String i : array) {
			if(buf.length() <= 0) {
				buf.append(i);
			} else {
				buf.append(delimeter).append(i);
			}
		}
		return buf.toString();
	}
	
	/**
	 * desc: 将数据集转化成json字符格式<br>
	 * date: 2014年11月3日 下午3:36:49<br>
	 * @author 开发者真实姓名[Andy]
	 * @param data
	 * @return
	 */
	public static String toJson(List<Map<String, Object>> data) {
		JSONArray array = new JSONArray();
		array.addAll(data);
		
		return array.toJSONString();
	}
	
	/**
	 * desc: 将数据集转化成xml字符串<br>
	 * date: 2014年11月3日 下午3:36:24<br>
	 * @author 开发者真实姓名[Andy]
	 * @param data
	 * @return
	 */
	public static String toXML(List<Map<String, Object>> data) {
		StringBuffer xmlbuf = new StringBuffer();
		String ls = System.getProperty("line.separator");
		
		xmlbuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(ls);
		xmlbuf.append("<root>").append(ls);
		for(Map<String, Object> d : data) {
			xmlbuf.append("<item>").append(ls);
			for (Iterator iterator = d.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				xmlbuf.append("<").append(entry.getKey()).append(">").append(entry.getValue());
				xmlbuf.append("</").append(entry.getKey()).append(">").append(ls);
			}
			xmlbuf.append("</item>").append(ls);
		}
		xmlbuf.append("</root>");
		
		return xmlbuf.toString();
	}
	
	/**
	 * desc: 将一个数据集转化成行记录的字符格式<br>
	 * date: 2014年11月3日 下午3:35:53<br>
	 * @author 开发者真实姓名[Andy]
	 * @param data
	 * @param delimeter
	 * @param header
	 * @return
	 */
	public static String toLineStr(List<Map<String, Object>> data, 
			String delimeter, boolean header) {
		StringBuffer buf = new StringBuffer();
		if (data == null || data.size() <= 0) {
			return null;
		}

		String ls = System.getProperty("line.separator");
		Set<String> cols = data.get(0).keySet();
		
		if(header) {
			StringBuffer ks = new StringBuffer();
			for (Iterator iterator = cols.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(ks.length() <= 0) {
					ks.append(key);
				} else {
					ks.append(delimeter).append(key);
				}
			}
			buf.append(ks);
			buf.append(ls);
		}
		
		for(Map<String, Object> d : data) {
			StringBuffer vs = new StringBuffer();
			for (Iterator iterator = cols.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(vs.length() <= 0) {
					vs.append(d.get(key));
				} else {
					vs.append(delimeter).append(d.get(key));
				}
			}
			buf.append(vs);
			buf.append(ls);
		}
		
		return buf.toString();
	}
	
	/**
	 * desc: 文件按比例取样<br>
	 * date: 2014年10月27日 下午5:36:11<br>
	 * @author 开发者真实姓名[Andy]
	 * @param srcfile
	 * @param destfile
	 * @param algorithm
	 * @param ratio
	 * @return
	 * @throws RunnerException
	 */
	public static int sampleFromFile(String srcfile, String destfile, String algorithm, double ratio) throws RunnerException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(srcfile));
			bw = new BufferedWriter(new FileWriter(destfile));
			String line = null;
			List<String> lines = new ArrayList<String>();
			do {
				line = br.readLine();
				if(line != null && line.length() > 0) {
					lines.add(line);
				}
			} while (line != null);
			int total = lines.size();
			int samplecnt = (int) Math.ceil(total * ratio);
			for (int i : sample(total, samplecnt, algorithm)) {
				bw.write(lines.get(i));
				bw.write(System.getProperty("line.separator"));
			}
			bw.flush();
			return samplecnt;
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
	
	/**
	 * desc: 从一个值域内取样。 0～total中取samplecnt个, 不能重复<br>
	 * date: 2014年10月11日 上午10:44:22<br>
	 * @author 开发者真实姓名[Andy]
	 * @param total
	 * @param samplecnt
	 * @return
	 */
	public static List<Integer> sample(int total, int samplecnt, String algorithm) {
		List<Integer> sample = new ArrayList<Integer>();
		
		if ("random".equalsIgnoreCase(algorithm)) {
			Random random = new Random();
			for (int i = 0; i < samplecnt; i++) {
				int r = -1;
				do {
					r = (int) Math.floor(random.nextDouble() * total);
				} while(sample.contains(r));
				sample.add(r);
			}
		} else if ("sequence".equalsIgnoreCase(algorithm)) {
			for (int i = 0; i < samplecnt; i++) {
				sample.add(i);
			}
		}
		
		return sample;
	}
	
	/**
	 * desc: 获取配置文件里设置的app名称和提示<br>
	 * date: 2014年10月27日 下午5:35:31<br>
	 * @author 开发者真实姓名[Andy]
	 * @return
	 */
	public static Map<String, String> getApps() {
		Resource resource = new ClassPathResource("/apps.properties");
		Map<String, String> apps = new TreeMap<String, String>();
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			for (Iterator iterator = props.entrySet().iterator(); iterator.hasNext();) {
				Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();
				apps.put(entry.getKey().toString(), entry.getValue().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return apps;
	}
	
}

