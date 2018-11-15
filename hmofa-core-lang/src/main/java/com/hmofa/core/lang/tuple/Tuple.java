package com.hmofa.core.lang.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.hmofa.core.exception.IllegalArgumentException;

public abstract class Tuple implements Iterable<Object>, Serializable, Comparable<Tuple> {

	private static final long serialVersionUID = 7356642269953815356L;

	private final List<Object> valueList;
	
	private String tupleUUID = UUID.randomUUID().toString().replace("-", "");

	public abstract int getSize();

	protected Tuple(final Object... values) {
		this.valueList = Arrays.asList(values);
	}

	public String getTupleUUID() {
		return tupleUUID;
	}
	
	public final Object getValue(final int pos) {
		if (pos >= getSize()) {
			throw new IllegalArgumentException("Cannot retrieve position " + pos + " in " + this.getClass().getSimpleName()
					+ ". Positions for this class start with 0 and end with " + (getSize() - 1));
		}
		return this.valueList.get(pos);
	}

	public final boolean contains(final Object value) {
		for (final Object val : this.valueList) {
			if (val == null) {
				if (value == null) {
					return true;
				}
			} else {
				if (val.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	public final boolean containsAll(final Collection<?> collection) {
		for (final Object value : collection) {
			if (!contains(value)) {
				return false;
			}
		}
		return true;
	}

	public final boolean containsAll(final Object... values) {
		if (values == null) {
			throw new IllegalArgumentException("Values array cannot be null");
		}
		for (final Object value : values) {
			if (!contains(value)) {
				return false;
			}
		}
		return true;
	}

	public final int indexOf(final Object value) {
		int i = 0;
		for (final Object val : this.valueList) {
			if (val == null) {
				if (value == null) {
					return i;
				}
			} else {
				if (val.equals(value)) {
					return i;
				}
			}
			i++;
		}
		return -1;
	}

	public final int lastIndexOf(final Object value) {
		for (int i = getSize() - 1; i >= 0; i--) {
			final Object val = this.valueList.get(i);
			if (val == null) {
				if (value == null) {
					return i;
				}
			} else {
				if (val.equals(value)) {
					return i;
				}
			}
		}
		return -1;
	}

	public final List<Object> toList() {
		return Collections.unmodifiableList(new ArrayList<Object>(this.valueList));
	}

	public final Object[] toArray() {
		return this.valueList.toArray();
	}
	
	public final Iterator<Object> iterator() {
		return this.valueList.iterator();
	}

	public final String toString() {
		return this.valueList.toString();
	}

	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.valueList == null) ? 0 : this.valueList.hashCode());
		return result;
	}

	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Tuple other = (Tuple) obj;
		return this.valueList.equals(other.valueList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final int compareTo(final Tuple o) {
		final int tLen = valueList.size();
		final int oLen = o.valueList.size();

		for (int i = 0; i < tLen && i < oLen; i++) {
			Object thisObj = valueList.get(i);
			Object cmpaObj = o.valueList.get(i);
			if(!(thisObj instanceof Comparable && cmpaObj instanceof Comparable))
				continue;
			
			final Comparable tElement = (Comparable) valueList.get(i);
			final Comparable oElement = (Comparable) o.valueList.get(i);
			final int comparison = tElement.compareTo(oElement);
			if (comparison != 0) {
				return comparison;
			}
		}
		return (Integer.valueOf(tLen)).compareTo(Integer.valueOf(oLen));
	}

	public static <L, V> LabelValue<L, V> withLV(final L label, final V value) {
		return new LabelValue<L, V>(label, value);
	}

	public static <K, V> KeyValue<K, V> withKV(final K key, final V value) {
		return new KeyValue<K, V>(key, value);
	}

	public static <A> Unit<A> with(final A value0) {
		return new Unit<A>(value0);
	}

	public static <A, B> Pair<A, B> with(final A value0, final B value1) {
		return new Pair<A, B>(value0, value1);
	}

	public static <A, B, C> Triplet<A, B, C> with(final A value0, final B value1, final C value2) {
		return new Triplet<A, B, C>(value0, value1, value2);
	}

	public static <A, B, C, D> Quartet<A, B, C, D> with(final A value0, final B value1, final C value2, final D value3) {
		return new Quartet<A, B, C, D>(value0, value1, value2, value3);
	}

	public static <A, B, C, D, E> Quintet<A, B, C, D, E> with(final A value0, final B value1, final C value2, final D value3, final E value4) {
		return new Quintet<A, B, C, D, E>(value0, value1, value2, value3, value4);
	}

	public static <A, B, C, D, E, F> Sextet<A, B, C, D, E, F> with(final A value0, final B value1, final C value2, final D value3, final E value4,
			final F value5) {
		return new Sextet<A, B, C, D, E, F>(value0, value1, value2, value3, value4, value5);
	}

	public static <A, B, C, D, E, F, G> Septet<A, B, C, D, E, F, G> with(final A value0, final B value1, final C value2, final D value3,
			final E value4, final F value5, final G value6) {
		return new Septet<A, B, C, D, E, F, G>(value0, value1, value2, value3, value4, value5, value6);
	}

	public static <A, B, C, D, E, F, G, H> Octet<A, B, C, D, E, F, G, H> with(final A value0, final B value1, final C value2, final D value3,
			final E value4, final F value5, final G value6, final H value7) {
		return new Octet<A, B, C, D, E, F, G, H>(value0, value1, value2, value3, value4, value5, value6, value7);
	}

	public static <A, B, C, D, E, F, G, H, I> Ennead<A, B, C, D, E, F, G, H, I> with(final A value0, final B value1, final C value2, final D value3,
			final E value4, final F value5, final G value6, final H value7, final I value8) {
		return new Ennead<A, B, C, D, E, F, G, H, I>(value0, value1, value2, value3, value4, value5, value6, value7, value8);
	}

	public static <A, B, C, D, E, F, G, H, I, J> Decade<A, B, C, D, E, F, G, H, I, J> with(final A value0, final B value1, final C value2,
			final D value3, final E value4, final F value5, final G value6, final H value7, final I value8, final J value9) {
		return new Decade<A, B, C, D, E, F, G, H, I, J>(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9);
	}
}
