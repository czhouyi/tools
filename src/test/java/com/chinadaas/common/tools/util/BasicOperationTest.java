package com.chinadaas.common.tools.util;

import java.math.BigDecimal;
import java.sql.Date;

import junit.framework.TestCase;

/**
 * projectName: chinadaas-tools<br>
 * desc: TODO<br>
 * date: 2014年10月27日 下午4:10:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class BasicOperationTest extends TestCase {

	public void testStrFormat() {
		assertEquals("", BasicOperation.strFormat(null));
		assertEquals("test trim", BasicOperation.strFormat("   test trim    "));
		assertEquals("testsplit", BasicOperation.strFormat("test\u0001split"));
		assertEquals("test  newline", BasicOperation.strFormat("test\r\nnewline"));
		assertEquals("testmore", BasicOperation.strFormat("  test\u0001more\r\n "));
	}

	public void testBigDecimalFormat() {
		assertEquals(BigDecimal.ZERO, BasicOperation.bigDecimalFormat(null));
		assertEquals(BigDecimal.ZERO, BasicOperation.bigDecimalFormat(BigDecimal.ZERO));
		assertEquals(BigDecimal.TEN, BasicOperation.bigDecimalFormat(BigDecimal.TEN));
	}

	@Deprecated
	public void testDateFormat() {
		assertEquals(new Date(0, 0, 1), BasicOperation.dateFormat(null));
	}

	public void testNameFormat() {
		assertEquals("", BasicOperation.nameFormat("益"));
		for(String s : BasicOperation.invalidName) {
			assertEquals("", BasicOperation.nameFormat(s));
		}
		assertEquals("", BasicOperation.nameFormat("法法法法法法法法"));
		assertEquals("北京中数智汇科技有限公司", BasicOperation.nameFormat("北京中数智汇  科技有限公司"));
		assertEquals("北京(中数智汇)科技有限公司", BasicOperation.nameFormat("北京（中数智汇）科技有限公司"));
		assertEquals("北京中数智汇科技有限公司", BasicOperation.nameFormat("北京\"中数智汇\"科技有限公司"));
	}

	public void testNameFormat1() {
		assertEquals("", BasicOperation.nameFormat1("益"));
		for(String s : BasicOperation.invalidName) {
			assertEquals("", BasicOperation.nameFormat1(s));
		}
		assertEquals("", BasicOperation.nameFormat1("法法法法法法法法"));
		assertEquals("北京(中数智汇)科技有限公司", BasicOperation.nameFormat1("北京（中数智汇）科技有限公司"));
		assertEquals("北京 中数智汇 科技有限公司", BasicOperation.nameFormat1("北京\"中数智汇\"科技有限公司"));
	}

	public void testIdFormat() {
		for(String s : BasicOperation.invalidCerno) {
			assertEquals("", BasicOperation.idFormat(s));
		}
		assertEquals("", BasicOperation.idFormat("345"));
		assertEquals("", BasicOperation.idFormat("4444444444444"));
		assertEquals("", BasicOperation.idFormat("3456789"));
		assertEquals("42145849321", BasicOperation.idFormat("421458;49321;"));
		assertEquals("42145849321", BasicOperation.idFormat("421458#49321#"));
		assertEquals("42145849321", BasicOperation.idFormat("421458 49321"));
	}

	public void testCernoFormat() {
		assertEquals("", BasicOperation.cernoFormat(null));
	}

	public void testFormatDouble() {
		assertEquals("0", BasicOperation.formatDouble(null));
		assertEquals("0", BasicOperation.formatDouble(""));
		assertEquals("2", BasicOperation.formatDouble("2.0000"));
		assertEquals("2.45", BasicOperation.formatDouble("02.45"));
		assertEquals("0.49", BasicOperation.formatDouble(".49"));
		assertEquals("100.39", BasicOperation.formatDouble("100.390"));
	}

}

