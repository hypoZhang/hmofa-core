package com.hmofa.core.resources;


import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import com.hmofa.core.exception.CharsetException;
import com.hmofa.core.exception.IOException;

public interface InputResource extends Resource {
	
	/**
	 * <p>Discription:[资源长度]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-06-06
	 */
	long contentLength() throws IOException;

	/**
	 * <p>Discription:[获得输入字节流]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	InputStream getResourceAsInputStream() throws IOException;

	/**
	 * <p>Discription:[获得 输入字符流，按默认编码]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Reader getResourceAsReader() throws IOException;

	/**
	 * <p>Discription:[获得 输入字符流，按指定编码]</p>
	 * @param charEncoding
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Reader getResourceAsReader(String charEncoding) throws IOException, CharsetException;

	/**
	 * <p>Discription:[获得 输入字符流，按指定编码]</p>
	 * @param charset
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Reader getResourceAsReader(Charset charset) throws IOException;
}
