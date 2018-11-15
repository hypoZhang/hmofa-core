package com.hmofa.core.lang.format;

import java.io.Serializable;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.lang.tuple.KeyValue;

public class Compileholder  implements Serializable, Comparable<Compileholder> {
	
	private static final long serialVersionUID = -6491449471209820861L;

	public Compileholder(String string, boolean isPlaceholder, Placeholder placeholder) {
		kv = new KeyValue<String, Boolean>(string, isPlaceholder);
		this.placeholder = placeholder;
		
		if(isPlaceholder() && placeholder == null)
			throw new BaseRuntimeException("占位符 必须指定 占位符标识。 Placeholder is null ");
	}

	public String getString() {
		return kv.getKey();
	}

	public boolean isPlaceholder() {
		return kv.getValue();
	}

	public String getID() {
		return kv.getTupleUUID();
	}
	
	public String getVarName() {
		if(placeholder == null || isPaddingNo())
			return null;
		String paramName = null;
		if (placeholder.isPair())
			paramName = getString().substring(placeholder.getPrefix().length(), getString().length() - placeholder.getSuffix().length());
		else
			paramName = getString().substring(placeholder.getPrefix().length(), getString().length());
		return paramName;
	}

	public String toString() {
		return kv.toString();
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		return kv.equals(((Compileholder)obj).kv);
	}
	
	public int compareTo(Compileholder o) {
		return this.kv.compareTo(o.kv);
	}
	
	private boolean isPaddingNo() {
		return placeholder.isNoHaveName() || (placeholder.isNotEssential() && placeholder.holderString().equals(getString()));
	}

	private Placeholder placeholder;
	private KeyValue<String, Boolean> kv;

}
