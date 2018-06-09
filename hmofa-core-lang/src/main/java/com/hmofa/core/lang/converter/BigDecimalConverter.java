package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

public final class BigDecimalConverter implements IConvertible<BigDecimal> {

	public BigDecimal convert(Object value) {
		return new BigDecimal(value.toString());
	}

}
