package com.hmofa.core.resources;


import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

import com.hmofa.core.exception.IOException;

public interface OutputResource extends Resource {

	/**
	 * <p>Discription:[获得  输出字节流]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	OutputStream getResourceAsOutStream() throws IOException;

	/**
	 * <p>Discription:[获得  输出字符流， 按默认编码]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Writer getResourceAsWriter() throws IOException;

	/**
	 * <p>Discription:[获得  输出字符流， 按指定编码]</p>
	 * @param charEncoding
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Writer getResourceAsWriter(String charEncoding) throws IOException;
	
	/**
	 * <p>Discription:[获得  输出字符流， 按指定编码]</p>
	 * @param charset
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Writer getResourceAsWriter(Charset charset) throws IOException;
}
