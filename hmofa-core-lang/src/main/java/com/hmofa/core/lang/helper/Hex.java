package com.hmofa.core.lang.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.exception.IOException;
import com.hmofa.core.exception.IllegalArgumentException;
import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.exception.Assert;
import com.hmofa.core.lang.utils.UtilArray;

public class Hex {

	public static final String toHexString(int hexv) {
		String hex = Integer.toHexString(hexv);
		if (hex.length() == 1)
			hex = "0".concat(hex);
		return hex;
	}

	public static final String toHexString(byte... bytes) {
		return toHexString(0, bytes == null ? 0 : bytes.length, bytes);
	}

	public static final String toHexString(int offset, int len, byte... bytes) {
		if (UtilArray.isEmpty(bytes))
			return "";
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = offset; i < len; i++)
			ret.append(toHexString(bytes[i]));
		return ret.toString();
	}

	public static final String toHexString(byte by) {
		String hex = Integer.toHexString(by & 0xFF); // 如b[i]有符号执行无符号扩展
		if (hex.length() == 1) // 16进制一般为 A8两位字面量表示,少于两位用0替补
			hex = "0".concat(hex); // hex = '0' + hex;
		return hex;
	}

	public static final String toHexString(Byte by) {
		return by == null ? "" : toHexString(by.byteValue());
	}

	public static final String toHexString(Byte... bytes) {
		return toHexString(0, bytes == null ? 0 : bytes.length, bytes);
	}

	public static final String toHexString(int offset, int len, Byte... bytes) {
		if (UtilArray.isEmpty(bytes))
			return "";
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = offset; i < len; i++)
			ret.append(toHexString(bytes[i]));
		return ret.toString();
	}

	public static final String toHexString(InputStream in) throws IOException {
		StringBuilder ret = new StringBuilder();
		try {
			int i = -1;
			InputStream inStream = in;
			while ((i = inStream.read()) != -1)
				ret.append(toHexString(i));
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
		return ret.toString();
	}

	public static final void toHexString(InputStream in, Writer out) {
		try {
			int i = -1;
			InputStream inStream = in;
			Writer writer = out;
			while ((i = inStream.read()) != -1)
				writer.append(toHexString(i));
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
	}

	public static final byte[] toBytesByHex(String hexString) {
		int mod = hexString.length() % 2;
		if (mod != 0)
			throw new BaseRuntimeException();
		int length = hexString.length() / 2;
		byte[] hexBytes = new byte[length];
		for (int i = 0; i < length; ++i)
			hexBytes[i] = toBytesByHexChar(hexString.charAt(i * 2), hexString.charAt(i * 2 + 1));
		return hexBytes;
	}

	public static final byte[] toBytesByHex(Reader inHex) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
		char[] highBitChar = new char[1];
		char[] lowBitChar = new char[1];
		try {
			Reader reader = inHex;
			while (reader.read(highBitChar, 0, 1) != -1) {
				if (reader.read(lowBitChar, 0, 1) == -1)
					throw new BaseRuntimeException();
				bout.write(toBytesByHexChar(highBitChar[0], lowBitChar[0]));
			}
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
		return bout.toByteArray();
	}

	public static final void toBytesByHex(Reader inHex, OutputStream out) {
		char[] highBitChar = new char[1];
		char[] lowBitChar = new char[1];
		try {
			OutputStream bout = out;
			Reader reader = inHex;
			while (reader.read(highBitChar, 0, 1) != -1) {
				if (reader.read(lowBitChar, 0, 1) == -1)
					throw new BaseRuntimeException();
				bout.write(toBytesByHexChar(highBitChar[0], lowBitChar[0]));
			}
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
	}

	protected static byte toBytesByHexChar(char highBit, char lowBit) {
		byte b1 = Byte.decode("0x" + highBit).byteValue();
		b1 = (byte) (b1 << 4);
		byte b2 = Byte.decode("0x" + lowBit).byteValue();
		return (byte) (b1 ^ b2);
	}

	//////////////////////  base64 编码 ////////////////////////////

	private static final char pad = '=';

	private static final char[] nibble2code = { 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',	'S', 'T', 
			'U', 'V', 'W', 'X', 'Y', 'Z', 
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
			'u', 'v', 'w', 'x', 'y', 'z', 
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'+', '/' };

	private static byte[] code2nibble = null;

	static {
		code2nibble = new byte[256];
		for (int i = 0; i < 256; i++)
			code2nibble[i] = -1;
		
		for (byte b = 0; b < 64; b++)
			code2nibble[(byte) nibble2code[b]] = b;
		
		code2nibble[(byte) pad] = 0;
	}

	public static final String encodeBase64(CharSequence charseq) {
		return encodeBase64(charseq, StringCoder.DEFAULT_UNI_CHARSET_NAME);
	}

	public static final String encodeBase64(CharSequence charseq, Charset charset) {
		return encodeBase64(charseq, StringCoder.charsetName(charset));
	}

	public static final String encodeBase64(CharSequence charseq, String charsetName) {
		return encodeBase64(StringCoder.encode(charseq, charsetName));
	}
	
	public static final String encodeBase64(Byte... bytes) {
		return encodeBase64(UtilArray.toPrimitive(bytes));
	}

	public static final String encodeBase64(byte... bytes) {
		return UtilArray.isEmpty(bytes) ? "" : encodeBase64(new ByteArrayInputStream(bytes));
	}

	public static final String encodeBase64(InputStream inResource) {
		StringWriter out = new StringWriter();
		encodeBase64(inResource, out);
		return out.toString();
	}

	public static final void encodeBase64(InputStream inResource, Writer outResource) {
		try {
			int i = -1;
			byte b0, b1, b2;
			byte[] bytes = new byte[3];
			Writer writer = outResource;
			InputStream in = inResource;
			while ((i = in.read(bytes, 0, 3)) != -1) {
				switch (i) {
				case 3:
					b0 = bytes[0];
					b1 = bytes[1];
					b2 = bytes[2];
					writer.append(nibble2code[(b0 >>> 2) & 0x3f]);
					writer.append(nibble2code[(b0 << 4) & 0x3f | (b1 >>> 4) & 0x0f]);
					writer.append(nibble2code[(b1 << 2) & 0x3f | (b2 >>> 6) & 0x03]);
					writer.append(nibble2code[b2 & 077]);
					break;
				case 2:
					b0 = bytes[0];
					b1 = bytes[1];
					writer.append(nibble2code[(b0 >>> 2) & 0x3f]);
					writer.append(nibble2code[(b0 << 4) & 0x3f | (b1 >>> 4) & 0x0f]);
					writer.append(nibble2code[(b1 << 2) & 0x3f]);
					writer.append(pad);
					break;
				case 1:
					b0 = bytes[0];
					writer.append(nibble2code[(b0 >>> 2) & 0x3f]);
					writer.append(nibble2code[(b0 << 4) & 0x3f]);
					writer.append(pad);
					writer.append(pad);
					break;
				default:
					break;
				}
			}
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
	}

	public static final byte[] decodeBase64(char... ch) {
		char[] chars = ch == null ? UtilArray.EMPTY_CHAR_ARRAY : ch;
		return decodeBase64(new String(chars));
	}
	
	public static final byte[] decodeBase64(CharSequence charseqBase64) {
		String base64 = charseqBase64 == null ? "" : charseqBase64.toString();		
		return decodeBase64(new StringReader(base64), base64.length());
	}

	public static final String decodeBase64(CharSequence charseqBase64, String charsetName) {
		return decodeBase64(charseqBase64, StringCoder.lookupCharset(charsetName));
	}

	public static final String decodeBase64(CharSequence charseqBase64, Charset charset) {
		byte[] bytes = decodeBase64(charseqBase64);
		return StringCoder.decode(charset, bytes);
	}

	public static final byte[] decodeBase64(Reader inResource, int len) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		decodeBase64(inResource, len, bout);
		return bout.toByteArray();
	}
	
	public static final void decodeBase64(Reader inResource, int len, OutputStream outResource) {
		Assert.notNull(inResource);
		Assert.notNull(outResource);
		try {
			long bLen = len == -1 ? 100 : len;
			if (bLen % 4 != 0)
				throw new IllegalArgumentException("Input block size is not 4 (Error base64 encoded)");
			
			int i = -1;
			byte b0, b1, b2, b3;
			char[] ch = new char[4];
			OutputStream out = outResource;
			Reader in = inResource;
			while ((i = in.read(ch, 0, 4)) != -1) {
				if (i != 4)
					throw new IllegalArgumentException("Input block size is not 4 (Error base64 encoded)");
				i = trimPad(i, ch);
				switch (i) {
				case 4:
					b0 = code2nibble[ch[0]];
					b1 = code2nibble[ch[1]];
					b2 = code2nibble[ch[2]];
					b3 = code2nibble[ch[3]];
					if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
						throw new IllegalArgumentException("Error base64 encoded");
					
					out.write((byte) (b0 << 2 | b1 >>> 4));
					out.write((byte) (b1 << 4 | b2 >>> 2));
					out.write((byte) (b2 << 6 | b3));
					break;
				case 3:
					b0 = code2nibble[ch[0]];
					b1 = code2nibble[ch[1]];
					b2 = code2nibble[ch[2]];
					if (b0 < 0 || b1 < 0 || b2 < 0)
						throw new IllegalArgumentException("Error base64 encoded");

					out.write((byte) (b0 << 2 | b1 >>> 4));
					out.write((byte) (b1 << 4 | b2 >>> 2));
					break;
				case 2:
					b0 = code2nibble[ch[0]];
					b1 = code2nibble[ch[1]];
					if (b0 < 0 || b1 < 0)
						throw new IllegalArgumentException("Error base64 encoded");

					out.write((byte) (b0 << 2 | b1 >>> 4));
					break;
				case 1:
					throw new IllegalArgumentException("Error base64 encoded");
				default:
					break;
				}
			}
		} catch (java.io.IOException e) {
			throw new IOException(e);
		} finally {
			try {
				inResource.close();
			} catch (java.io.IOException e) {
				throw new IOException(e);
			} finally {
				try {
					outResource.close();
				} catch (java.io.IOException e) {
					throw new IOException(e);
				}
			}
		}
	}

	private static int trimPad(int i, char[] ch) {
		for (int j = 3; j >= 0; j--) {
			if (ch[j] == pad)
				i--;
			else
				break;
		}
		return i;
	}
	
}
