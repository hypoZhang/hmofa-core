package com.hmofa.core.lang.bundle;


import com.hmofa.core.lang.format.Placeholder;
import com.hmofa.core.lang.format.PlaceholderParse;
import com.hmofa.core.lang.format.StringFormat;

import junit.framework.TestCase;

public class PlaceholderParseTestcase  extends TestCase {
	
	public void testVar() {
		String sql = "select * from table where user = ${.username} and password = ${password} ${}";
				
		PlaceholderParse parse = new PlaceholderParse(sql, Placeholder.VAR);
		
		System.out.println(parse.toAlreadyCodeParamNameString());
		System.out.println(parse.getOriginalString());
		
		StringFormat format = parse.stringFormat(true);
		format.addFormatValue("55555", "jjjjjj", 999999);
		
		format.addFormatValueBy(".username", "yntdc");
		format.addFormatValueBy("password", "11111");
		
		String actual = format.toString();
		System.out.println(actual);
		
		System.out.println();
		
		String expected = "select * from table where user = yntdc and password = 11111 ${} 55555 jjjjjj 999999";
		assertEquals(expected, actual);
	}
	
	public void testSql() {
		String sql = "select * from table where user = ? and password = ?";
				
		PlaceholderParse parse = new PlaceholderParse(sql, Placeholder.SQL);
		
		System.out.println(parse.toAlreadyCodeParamNameString());
		System.out.println(parse.getOriginalString());
		
		StringFormat format = parse.stringFormat(true);
		format.addFormatValue("55555", "jjjjjj", 999999);
		
		format.addFormatValueBy("username", "yntdc");
		format.addFormatValueBy("password", "11111");
		
		String actual = format.toString();
		System.out.println(actual);
		
		System.out.println();
		
		String expected = "select * from table where user = 55555 and password = jjjjjj 999999";
		assertEquals(expected, actual);
	}
	
	public void testParamName() {
		String sql = "select * from table where user = :username and password = :password  and id = :.id and dept = :_.efel and sex = :_sex.ss";
		
		PlaceholderParse parse = new PlaceholderParse(sql, Placeholder.PARAMNAME);
		
		System.out.println(parse.toAlreadyCodeParamNameString());
		System.out.println(parse.getOriginalString());
		
		StringFormat format = parse.stringFormat();
		format.addFormatValue("55555", "jjjjjj", 999999);
		
		format.addFormatValueBy("username", "yntdc");
		format.addFormatValueBy("password", "11111");
		format.addFormatValueBy("_sex.ss", "0");
		
		String actual = format.toString();
		System.out.println(actual);
		
		System.out.println();
		
		String expected = "select * from table where user = yntdc and password = 11111  and id = :.id and dept = :_.efel and sex = 0";
		assertEquals(expected, actual);
	}
	
	public void testMsg() {
		String sql = "select * from table where user = {} and password = {}  {5} {00}";
		
		PlaceholderParse parse = new PlaceholderParse(sql, Placeholder.MSG);
		
		System.out.println(parse.toAlreadyCodeParamNameString());
		System.out.println(parse.getOriginalString());
		
		StringFormat format = parse.stringFormat(true);
		format.addFormatValue("55555", "jjjjjj", 999999);
		
		format.addFormatValueBy("5", "88888");
		
		String actual = format.toString();
		System.out.println(actual);
		
		System.out.println();
		
		String expected = "select * from table where user = 55555 and password = jjjjjj  88888 {00} 999999";
		assertEquals(expected, actual);
	}
}
