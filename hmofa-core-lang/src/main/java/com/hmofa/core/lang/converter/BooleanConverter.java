package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilNumber;

/**
 * "true" -> true	"yes" -> true  "ok" -> true  "y" -> true [不区分大小写]
 * "1" "01" "001" "0001" "10" "100" "1000" -> true
 * <dd>Description:[转换为 boolean 类型]</dd>
 * <dt>BooleanConverter</dt>
 * <dd>Copyright: All Rights Reserved by GuangZhou Haiyi Software Ltd Co.</dd>
 * <dd>CreateDate: 2017-1-3</dd>
 * @version 1.0
 * @author zhanghaibo3
 */
public final class BooleanConverter implements IConvertible<Boolean> {

	public Boolean convert(Object ovalue) {
		String str = ovalue.toString().trim();
		if(!(ovalue instanceof Number) && UtilNumber.isInteger(str))
			ovalue = Converter.convert(ovalue, Integer.class);
		if (ovalue instanceof Number) {
			int ival = ((Number) ovalue).intValue();
			return ival == 1 || ival == 10 || ival == 100 || ival == 1000;
		}
		if ("Y".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "ok".equalsIgnoreCase(str)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

}
