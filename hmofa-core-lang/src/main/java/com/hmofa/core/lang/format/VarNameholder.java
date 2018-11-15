package com.hmofa.core.lang.format;

import java.io.Serializable;

import com.hmofa.core.lang.tuple.KeyValue;

public class VarNameholder implements Serializable, Comparable<VarNameholder> {

	private static final long serialVersionUID = 8800860333631411531L;

	public VarNameholder(String string, boolean isAutoNO) {
		kv = new KeyValue<String, Boolean>(string, isAutoNO);
	}

	public String getString() {
		return kv.getKey();
	}

	public boolean isAutoNO() {
		return kv.getValue();
	}

	public String getID() {
		return kv.getTupleUUID();
	}
	
	public String toString() {
		return kv.toString();
	}

	public int hashCode() {
		return kv.hashCode();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		return kv.equals(((VarNameholder)obj).kv);
	}
	
	public int compareTo(VarNameholder o) {
		return this.kv.compareTo(o.kv);
	}
	
	private KeyValue<String, Boolean> kv;
	
}
