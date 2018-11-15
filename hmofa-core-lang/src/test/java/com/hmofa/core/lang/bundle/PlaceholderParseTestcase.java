package com.hmofa.core.lang.bundle;

import java.util.List;

import com.hmofa.core.lang.tuple.KeyValue;
import com.hmofa.core.lang.utils.UtilNumber;
import com.hmofa.core.lang.utils.UtilString;

import junit.framework.TestCase;

public class PlaceholderParseTestcase  extends TestCase {
	
	public void testSql() {
		String sql = "select * from table where user = ? and password = ?";
				
		PlaceholderParse parse = new PlaceholderParse(sql, Placeholder.SQL);
		
		System.out.println(parse.toAlreadyCodeParamNameString());
		System.out.println(parse.getOriginalString());
		
		StringFormat format = parse.stringFormat(true);
		format.addFormatValue("55555", "jjjjjj", 999999);
		
		System.out.println(format.toString());
	}
}
