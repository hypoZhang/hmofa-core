package com.hmofa.core.lang.coder;

import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.hmofa.core.exception.CharsetException;
import com.hmofa.core.exception.IOException;
import com.hmofa.core.lang.exception.Assert;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilObject;



/**
 * <pre>
 * 编解码字符, 支持流操作
 * </pre>
 * <dd>Description:[字符编码]</dd>
 * <dt>StringCoder</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2017-2-28</dd>
 * @version 1.0
 * @author 张海波
 */
public final class StringCoder {

	protected StringCoder() {
		this(DEFAULT_UNI_CHARSET);
	}

	protected StringCoder(String charsetName) {
		this(lookupCharset(charsetName));
	}

	protected StringCoder(Charset charset) {
		Assert.notNull(charset);
		this.charset = charset;
		cacheCharset(this.charset);
	}

	public CharsetDecoder getCharsetDecoder() {
		return charset.newDecoder();
	}

	public CharsetEncoder getCharsetEncoder() {
		return charset.newEncoder();
	}

	public String charsetName() {
		return UtilObject.isEmpty(charsetName) ? (charsetName = charsetName(charset)) : charsetName;
	}

	protected char[] decoder(byte[] ba, int off, int len) {
		CharsetDecoder charsetDecoder = getCharsetDecoder();
		int en = scale(len, charsetDecoder.maxCharsPerByte());
		char[] ca = new char[en];
		if (len == 0)
			return ca;
		charsetDecoder.reset();
		ByteBuffer bb = ByteBuffer.wrap(ba, off, len);
		CharBuffer cb = CharBuffer.wrap(ca);
		try {
			CoderResult cr = charsetDecoder.decode(bb, cb, true);
			if (!cr.isUnderflow())
				cr.throwException();
			cr = charsetDecoder.flush(cb);
			if (!cr.isUnderflow())
				cr.throwException();
		} catch (CharacterCodingException x) {
			throw new CharsetException(x.getMessage(), x);
		} catch (BufferUnderflowException x) {
			throw new CharsetException(x.getMessage(), x);
		} catch (BufferOverflowException x) {
			throw new CharsetException(x.getMessage(), x);
		}
		return safeTrim(ca, cb.position(), charset);
	}

	protected byte[] encoder(char[] ca, int off, int len) {
		CharsetEncoder charsetEncoder = getCharsetEncoder();
		int en = scale(len, charsetEncoder.maxBytesPerChar());
		byte[] ba = new byte[en];
		if (len == 0)
			return ba;
		charsetEncoder.reset();
		ByteBuffer bb = ByteBuffer.wrap(ba);
		CharBuffer cb = CharBuffer.wrap(ca, off, len);
		try {
			CoderResult cr = charsetEncoder.encode(cb, bb, true);
			if (!cr.isUnderflow())
				cr.throwException();
			cr = charsetEncoder.flush(bb);
			if (!cr.isUnderflow())
				cr.throwException();
		} catch (CharacterCodingException x) {
			throw new CharsetException(x);
		} catch (BufferUnderflowException x) {
			throw new CharsetException(x);
		} catch (BufferOverflowException x) {
			throw new CharsetException(x);
		}
		return safeTrim(ba, bb.position(), charset);
	}

	private String charsetName;
	private Charset charset;

	private int scale(int len, float expansionFactor) {
		return (int) (len * (double) expansionFactor);
	}

	private char[] safeTrim(char[] ca, int len, Charset cs) {
		if (len == ca.length && isSafe(cs))
			return ca;
		return Arrays.copyOf(ca, len);
	}

	private byte[] safeTrim(byte[] ba, int len, Charset cs) {
		if (len == ba.length && isSafe(cs))
			return ba;
		return Arrays.copyOf(ba, len);
	}

	private boolean isSafe(Charset cs) {
		SecurityManager sm = System.getSecurityManager();
		ClassLoader cl = cs.getClass().getClassLoader();
		return sm == null || cl == null;
	}

	public static StringCoder lookupStringCoder(String charsetName) {
		Assert.notNull(charsetName);
		return cacheStringCoder(charsetName);
	}

	public static StringCoder lookupStringCoder(Charset charset) {
		Assert.notNull(charset);
		return cacheStringCoder(charset);
	}

	/**
	 * <p>Discription:[查找字符集]</p>
	 * @param charsetName
	 * @return Charset
	 * @throws CharsetException 未找到则抛异常, 或发生异常
	 * @author zhanghaibo3  2015-11-5
	 */
	public static Charset lookupCharset(String charsetName) throws CharsetException {
		Assert.notNull(charsetName);
		return lookupCharset0(charsetName);
	}

	public static boolean isCharset(String charsetName) {
		Charset charset = null;
		try {
			charset = lookupCharset(charsetName);
		} catch (CharsetException e) {
			return false;
		}
		return charset != null;
	}

	public static String charsetName(Charset charset) {
		Assert.notNull(charset);
		return charsetName0(charset);
	}

	private static String charsetName0(Charset charset) {
		return charset.name();
	}

	private static Charset lookupCharset0(String charsetName) throws CharsetException {
		return cacheCharset(charsetName);
	}
	
	private static StringCoder cacheStringCoder(Object charsetName) {
		if (stringCoderCache.containsKey(charsetName))
			return stringCoderCache.get(charsetName);
		String csn = charsetName instanceof Charset ? charsetName((Charset) charsetName) : charsetName.toString().toLowerCase();
		synchronized (stringCoderCache) {
			if (stringCoderCache.containsKey(csn))
				return stringCoderCache.get(csn);
			StringCoder sc = new StringCoder(csn);
			stringCoderCache.put(charsetName, sc);
			stringCoderCache.put(sc.charsetName(), sc);
			stringCoderCache.put(sc.charsetName().toUpperCase(), sc);
			stringCoderCache.put(sc.charsetName().toLowerCase(), sc);
			return stringCoderCache.get(sc.charsetName());
		}
	}

	private static Charset cacheCharset(Object charsetName) {
		if (charsetCache.containsKey(charsetName))
			return charsetCache.get(charsetName);
		
		if (charsetName == null)
			return null;
		
		String csn = charsetName.toString();
		synchronized (charsetCache) {
			try {
				if (charsetCache.containsKey(csn))
					return charsetCache.get(csn);
				Charset charset = charsetName instanceof Charset ? (Charset) charsetName : Charset.forName(csn);
				csn = charsetName(charset);
				charsetCache.put(csn, charset);
				charsetCache.put(csn.toLowerCase(), charset);
				charsetCache.put(csn.toUpperCase(), charset);
				return charsetCache.get(csn);
			} catch (IllegalCharsetNameException ex) {
				throw new CharsetException(ex); // 字符集名称错误 (中文或其它字符)
			} catch (UnsupportedCharsetException x) {
				throw new CharsetException(x); // 不支持的字符集 (名称正确)
			} catch (IllegalArgumentException e) {
				throw new CharsetException(e); // 参数错误
			}
		}
	}

	private static char[] getCharByCharSequence(CharSequence charseq, int off, int len) {
		if (charseq instanceof String)
			return getChars(off, len, charseq.toString());
		if (charseq instanceof StringBuffer) {
			StringBuffer sb = ((StringBuffer) charseq);
			return getChars(off, len, sb);
		}
		if (charseq instanceof StringBuilder) {
			StringBuilder su = ((StringBuilder) charseq);
			return getChars(off, len, su);
		}
		throw new CharsetException();
	}

	private static char[] getChars(int off, int len, String sb) {
		char[] ca = new char[len - off];
		sb.getChars(off, len, ca, 0);
		return ca;
	}

	private static char[] getChars(int off, int len, StringBuffer sb) {
		char[] ca = new char[len - off];
		sb.getChars(off, len, ca, 0);
		return ca;
	}

	private static char[] getChars(int off, int len, StringBuilder sb) {
		char[] ca = new char[len - off];
		sb.getChars(off, len, ca, 0);
		return ca;
	}

	private static Map<String, Charset> charsetCache = new HashMap<String, Charset>();
	private static Map<Object, StringCoder> stringCoderCache = new HashMap<Object, StringCoder>();

	public static final String GBK = "GBK";

	public static final String BIG5 = "BIG5";

	public static final String GB2312 = "GB2312";

	public static final String GB18030 = "GB18030";

	public static final String UTF_8 = "UTF-8";

	/** UTF-16BE <br> 大尾 Big Endian（最低地址存放高位字节）字节顺序   <br> JVM 默认编码*/
	public static final String UCS2BE = "UTF-16BE";

	/** UTF-16LE <br> 小尾 Little endian（最高地址存放低位字节）字节顺序 */
	public static final String UCS2LE = "UTF-16LE";

	public static final String US_ASCII = "US-ASCII";

	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final Charset DEFAULT_OS_CHARSET = Charset.defaultCharset();
	
	public static final String DEFAULT_OS_CHARSET_NAME = charsetName(DEFAULT_OS_CHARSET);
	
	public static final Charset DEFAULT_UNI_CHARSET = DEFAULT_OS_CHARSET;

	public static final String DEFAULT_UNI_CHARSET_NAME = charsetName(DEFAULT_UNI_CHARSET);

	
	public static byte[] encode(CharSequence charseq) {
		return encode(charseq, DEFAULT_UNI_CHARSET);
	}

	public static byte[] encode(CharSequence charseq, String charsetName) {
		return encode(charseq, lookupCharset0(charsetName));
	}

	public static byte[] encode(CharSequence charseq, Charset charset) {
		if (UtilObject.isEmpty(charseq) || charset == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		return encode(charseq, 0, charseq.length(), charset);
	}

	public static byte[] encode(CharSequence charseq, int off, int len) {
		return encode(charseq, off, len, DEFAULT_UNI_CHARSET);
	}

	public static byte[] encode(CharSequence charseq, int off, int len, String charsetName) {
		return encode(charseq, off, len, lookupCharset0(charsetName));
	}

	/**
	 * <p>Discription:[字符编码]</p>
	 * charseq 或  charsetName 为空 则返回  new byte[0] <br>
	 * charsetName 为错误字符集，抛出异常
	 * @param charseq
	 * @param off
	 * @param len
	 * @param charset
	 * @return byte[]
	 * @author 张海波  2016-9-10
	 */
	public static byte[] encode(CharSequence charseq, int off, int len, Charset charset) {
		if (UtilObject.isEmpty(charseq) || charset == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		char[] ca = getCharByCharSequence(charseq, off, len);
		return encode(off, len, charset, ca);
	}

	public static byte[] encode(char... ca) {
		return encode(DEFAULT_UNI_CHARSET, ca);
	}

	public static byte[] encode(String charsetName, char... ca) {
		if (UtilArray.isEmpty(ca) || UtilObject.isEmpty(charsetName))
			return UtilArray.EMPTY_BYTE_ARRAY;
		return encode(lookupCharset0(charsetName), ca);
	}

	public static byte[] encode(Charset charset, char... ca) {
		if (UtilArray.isEmpty(ca) || charset == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		return encode(0, ca.length, charset, ca);
	}

	public static byte[] encode(int off, int len, char... ca) {
		return encode(off, len, DEFAULT_UNI_CHARSET, ca);
	}

	public static byte[] encode(int off, int len, String charsetName, char... ca) {
		return encode(off, len, UtilObject.isEmpty(charsetName) ? null : lookupCharset0(charsetName), ca);
	}

	public static byte[] encode(int off, int len, Charset charset, char... ca) {
		if (UtilArray.isEmpty(ca) || charset == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		StringCoder stringCoder = lookupStringCoder(charset);
		return stringCoder.encoder(ca, off, len);
	}

	public static boolean encode(OutputStream out, CharSequence charseq) {
		if (UtilObject.isEmpty(charseq))
			return false;
		return encode(out, charseq, 0, charseq.length(), DEFAULT_UNI_CHARSET);
	}

	public static boolean encode(OutputStream out, CharSequence charseq, String charsetName) {
		if (UtilObject.isEmpty(charseq) || UtilObject.isEmpty(charsetName))
			return false;
		return encode(out, charseq, 0, charseq.length(), lookupCharset0(charsetName));
	}

	public static boolean encode(OutputStream out, CharSequence charseq, Charset charset) {
		if (UtilObject.isEmpty(charseq) || charset == null)
			return false;
		return encode(out, charseq, 0, charseq.length(), charset);
	}

	public static boolean encode(OutputStream out, CharSequence charseq, int off, int len) {
		return encode(out, charseq, off, len, DEFAULT_UNI_CHARSET);
	}

	public static boolean encode(OutputStream out, CharSequence charseq, int off, int len, String charsetName) {
		return encode(out, charseq, off, len, UtilObject.isEmpty(charsetName) ? null : lookupCharset0(charsetName));
	}

	/**
	 * <p>Discription:[将字符串编码后直接输出]</p>
	 * @param out
	 * @param charseq
	 * @param off
	 * @param len
	 * @param charset
	 * @author 张海波  2017-2-28
	 */
	public static boolean encode(OutputStream out, CharSequence charseq, int off, int len, Charset charset) {
		if (UtilObject.isEmpty(charseq) || charset == null)
			return false;
		char[] ca = getCharByCharSequence(charseq, off, len);
		return encode(out, off, len, charset, ca);
	}

	public static boolean encode(OutputStream out, char... ca) {
		if (UtilArray.isEmpty(ca))
			return false;
		return encode(out, 0, ca.length, DEFAULT_UNI_CHARSET, ca);
	}

	public static boolean encode(OutputStream out, String charsetName, char... ca) {
		if (UtilArray.isEmpty(ca) || UtilObject.isEmpty(charsetName))
			return false;
		return encode(out, 0, ca.length, lookupCharset0(charsetName), ca);
	}

	public static boolean encode(OutputStream out, Charset charset, char... ca) {
		if (UtilArray.isEmpty(ca) || charset == null)
			return false;
		return encode(out, 0, ca.length, charset, ca);
	}

	public static boolean encode(OutputStream out, int off, int len, char... ca) {
		return encode(out, off, len, DEFAULT_UNI_CHARSET, ca);
	}

	public static boolean encode(OutputStream out, int off, int len, String charsetName, char... ca) {
		return encode(out, off, len, UtilObject.isEmpty(charsetName) ? null : lookupCharset0(charsetName), ca);
	}

	public static boolean encode(OutputStream out, int off, int len, Charset charset, char... ca) {
		if (UtilArray.isEmpty(ca) || charset == null)
			return false;
		byte[] bytes = encode(off, len, charset, ca);
		writeStream(out, bytes);
		return true;
	}

	private static void writeStream(OutputStream out, byte[] bytes) {
		try {
			out.write(bytes);
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
	}

	public static void transcoding(Reader in, Writer out) {
		char[] ca = new char[1024];
		int rlen = -1;
		try {
			while ((rlen = in.read(ca, 0, ca.length)) != -1)
				out.write(ca, 0, rlen);
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
	}

	public static String decode(byte... ba) {
		if (UtilArray.isEmpty(ba))
			return "";
		return new String(decodeChar(0, ba.length, DEFAULT_UNI_CHARSET, ba));
	}
	
	public static String decode(String charsetName, byte... ba) {
		if (UtilArray.isEmpty(ba) || UtilObject.isEmpty(charsetName))
			return "";
		return new String(decodeChar(0, ba.length, lookupCharset0(charsetName), ba));
	}
	
	public static String decode(Charset charset, byte... ba) {
		if (UtilArray.isEmpty(ba) || charset == null)
			return "";
		return new String(decodeChar(0, ba.length, charset, ba));
	}

	public static String decode(int off, int len, byte... ba) {
		if (UtilArray.isEmpty(ba))
			return "";
		return new String(decodeChar(off, len, DEFAULT_UNI_CHARSET, ba));
	}

	public static String decode(int off, int len, String charsetName, byte... ba) {
		if (UtilArray.isEmpty(ba) || UtilObject.isEmpty(charsetName))
			return "";
		return new String(decodeChar(off, len, lookupCharset0(charsetName), ba));
	}

	public static String decode(int off, int len, Charset charset, byte... ba) {
		if (UtilArray.isEmpty(ba) || charset == null)
			return "";
		return new String(decodeChar(off, len, charset, ba));
	}

	public static char[] decodeChar(byte... ba) {
		if (UtilArray.isEmpty(ba))
			return UtilArray.EMPTY_CHAR_ARRAY;
		return decodeChar(0, ba.length, DEFAULT_UNI_CHARSET, ba);
	}

	public static char[] decodeChar(String charsetName, byte... ba) {
		if (UtilArray.isEmpty(ba) || UtilObject.isEmpty(charsetName))
			return UtilArray.EMPTY_CHAR_ARRAY;
		return decodeChar(0, ba.length, lookupCharset0(charsetName), ba);
	}

	public static char[] decodeChar(Charset charset, byte... ba) {
		if (UtilArray.isEmpty(ba) || charset == null)
			return UtilArray.EMPTY_CHAR_ARRAY;
		return decodeChar(0, ba.length, charset, ba);
	}

	public static char[] decodeChar(int off, int len, byte... ba) {
		return decodeChar(off, len, DEFAULT_UNI_CHARSET, ba);
	}

	public static char[] decodeChar(int off, int len, String charsetName, byte... ba) {
		return decodeChar(off, len, UtilObject.isEmpty(charsetName) ? null : lookupCharset0(charsetName), ba);
	}

	public static char[] decodeChar(int off, int len, Charset charset, byte... ba) {
		if (UtilArray.isEmpty(ba) || charset == null)
			return UtilArray.EMPTY_CHAR_ARRAY;
		StringCoder stringCoder = lookupStringCoder(charset);
		return stringCoder.decoder(ba, off, len);
	}

}
