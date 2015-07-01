package com.chinadaas.common.tools.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class BasicOperation {
	public static final Date DEFAULT_DATE = new Date(0, 0, 1);

	public static final String NUMBER_ZERO = "0";
	public static final String NUMBER_SERIES = "01234567890";
	public static final String DECIMAL_POINT = ".";

	public static final int CERNO_OLE_LEN = 15;
	public static final Pattern CERNO_OLD = Pattern.compile("^[1-9]\\d{7}" // 六位地址码
																			// +
																			// 两位年份
			+ "((0[1-9])|(1[0-2]))" // 两位月份
			+ "(((0[1-9])|([1-2]\\d)|(3[0-1])))" // 两位日期
			+ "\\d{3}$"); // 三位顺序码

	public static final int CERNO_NEW_LEN = 18;
	public static final Pattern CERNO_NEW = Pattern.compile("^[1-9]\\d{5}" // 六位地址码
			+ "[1-2]\\d{3}" // 四位年份
			+ "((0[1-9])|(1[0-2]))" // 两位月份
			+ "(((0[1-9])|([1-2]\\d)|(3[0-1])))" // 两位日期
			+ "\\d{3}" // 三位顺序码
			+ "[0-9xX]$"); // 一位校验码

	public static final int MIN_ID_LEN = 3;

	public static Set<String> invalidName = new HashSet<String>();
	static {
		invalidName.add("待查");
		invalidName.add("未知");
		invalidName.add("null");
		invalidName.add("公司");
		invalidName.add("有限公司");
		invalidName.add("有限责任公司");
	}

	public static Set<String> invalidCerno = new HashSet<String>();
	static {
		invalidCerno.add("123456789");
		invalidCerno.add("档案不清");
		invalidCerno.add("档案内无身份证号码");
		invalidCerno.add("档案缺失");
		invalidCerno.add("其他有效证件");
		invalidCerno.add("数据不全");
		invalidCerno.add("未登记身份证");
		invalidCerno.add("未明确的身份证号码");
		invalidCerno.add("无身份证资料");
		invalidCerno.add("无证件存档");
		invalidCerno.add("无证照存档");
		invalidCerno.add("无纸质档案");
		invalidCerno.add("无资料存档");
		invalidCerno.add("详见档案");
		invalidCerno.add("原始档案中无");
		invalidCerno.add("证件号码无法采集");
	}

	public static String strFormat(String value) {
		if (value == null)
			return "";

		value = value.replaceAll("\r|\n", " "); // 替换回车、换行
		value = value.replaceAll("\u0001", ""); // 替换标题开始
		value = value.trim();

		return value;
	}

	public static BigDecimal bigDecimalFormat(BigDecimal value) {
		if (value == null)
			value = BigDecimal.ZERO;

		String tmp = value.toString();
		tmp = formatDouble(tmp);
		value = new BigDecimal(tmp);
		return value;
	}

	@SuppressWarnings("deprecation")
	public static Date dateFormat(Date value) {
		if (value == null)
			return new Date(0, 0, 1);

		return value;
	}

	public static String nameFormat(String value) {
		if (value.length() < 2) // 长度小于2的名称认为是无效的姓名、企业名称、股东等
			return "";

		if (invalidName.contains(value.toLowerCase())) {
			return "";
		}

		// 过滤name中仅包含一种字符的
		int i = 1;
		int base = value.charAt(0);
		for (; i < value.length(); ++i) {
			if (value.charAt(i) != base)
				break;
		}
		if (i == value.length())
			return "";

		// 全角转半角
		char[] c = value.toCharArray();
		for (int index = 0; index < c.length; index++) {
			if (c[index] == '　')
				c[index] = ' ';
			else if ((c[index] > 65280) && (c[index] < 65375)) {
				c[index] = (char) (c[index] - 65248);
			}
		}
		value = String.valueOf(c);

		// 去除单、双引号
		value = value.replaceAll("\"|'", "");

		// 去除空白字符串
		value = value.replaceAll("\\s+", "");

		// 小写转大写
		value = value.toUpperCase();

		return value;
	}

	public static String nameFormat1(String value) {
		if (value.length() < 2) // 长度小于2的名称认为是无效的姓名、企业名称、股东等
			return "";

		if (invalidName.contains(value.toLowerCase())) {
			return "";
		}

		// 过滤name中仅包含一种字符的
		int i = 1;
		int base = value.charAt(0);
		for (; i < value.length(); ++i) {
			if (value.charAt(i) != base)
				break;
		}
		if (i == value.length())
			return "";

		// 全角转半角
		char[] c = value.toCharArray();
		for (int index = 0; index < c.length; index++) {
			if (c[index] == '　')
				c[index] = ' ';
			else if ((c[index] > 65280) && (c[index] < 65375)) {
				c[index] = (char) (c[index] - 65248);
			}
		}
		value = String.valueOf(c);

		// 单、双引号替换为空格字符串
		value = value.replaceAll("\"|'", " ").trim();

		// 空白字符串替转为空字符串
		// value = value.replaceAll("\\s+", "");

		// 小写转大写
		value = value.toUpperCase();

		return value;
	}

	public static String idFormat(String value) {
		// 过滤异常证件号码
		if (invalidCerno.contains(value))
			return "";

		// 去除证件号码中的空白字符、；、#
		value = value.replaceAll("\\s+|;|#", "");

		// 长度小于等于3的证件号码认为是无效的
		if (value.length() <= MIN_ID_LEN)
			return "";

		// 过滤cerno中仅包含一种字符的
		int i = 1;
		int base = value.charAt(0);
		for (; i < value.length(); ++i) {
			if (value.charAt(i) != base)
				break;
		}
		if (i == value.length())
			return "";

		// 过滤证件号码为连续数字的
		if (NUMBER_SERIES.indexOf(value) != -1)
			return "";

		return value;
	}

	public static String cernoFormat(String value) {
		if (value == null || value.isEmpty())
			return "";

		// 15位身份证转18位身份证
		if (value.length() == CERNO_OLE_LEN) {
			if (CERNO_OLD.matcher(value).matches()) {
				char[] strJY = { '1', '0', 'X', '9', '8', '7', '6', '5', '4',
						'3', '2' };
				int[] intJQ = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
						4, 2, 1 };
				StringBuilder buf = new StringBuilder("");
				buf.append(value.substring(0, 6));
				buf.append("19");
				buf.append(value.substring(6));
				int intTemp = 0;
				for (int i = 0; i < buf.toString().length(); ++i) {
					char ch = buf.toString().charAt(i);
					intTemp += ((int) (ch - '0')) * intJQ[i];
				}
				intTemp = intTemp % 11;
				if (intTemp > -1)
					buf.append(strJY[intTemp]);
				else
					buf.append("0");
				value = buf.toString();
			}
		} else if (value.length() == CERNO_NEW_LEN) {
			if (CERNO_NEW.matcher(value).matches()) {
				value = value.toUpperCase();
			}
		}

		return value;
	}

	public static String formatDouble(String value) {
		if (value == null || value.trim().length() == 0)
			return NUMBER_ZERO;

		value = value.trim();
		if (value.contains(DECIMAL_POINT)) {
			// 删除最后的0
			while (value.endsWith(NUMBER_ZERO))
				value = value.substring(0, value.length() - 1);

			// 删除小数点
			if (value.endsWith(DECIMAL_POINT))
				value = value.substring(0, value.length() - 1);
		}

		// 去除整数部分可能存在的一个或多个0
		while (value.startsWith(NUMBER_ZERO) && value.length() > 1)
			value = value.substring(1);

		// 小数点前补充0
		if (value.startsWith(DECIMAL_POINT))
			value = NUMBER_ZERO + value;

		return value;
	}
}