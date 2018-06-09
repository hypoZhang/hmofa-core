package com.hmofa.core.lang;


import java.io.Serializable;
import java.io.Writer;

import com.hmofa.core.exception.IOException;


/**
 * <dd>Description:[可序列化接口]</dd>
 * <dt>ISerializable</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public interface Serialization extends Serializable {
	
	/**
	 * <p>Discription:[输出XML字符串]</p>
	 * @return
	 * @author 张海波  2015-11-2
	 * @throws
	 */
	String toXML();
	
	/**
	 * <p>Discription:[输出JSON字符串]</p>
	 * @return
	 * @author 张海波  2015-11-2
	 * @throws
	 */
	String toJSON();
	
	/**
	 * <p>Discription:[以XML格式输出流]</p>
	 * @param out
	 * @author 张海波  2015-11-2
	 * @throws
	 */
	void toXML(Writer out) throws IOException;
	
	/**
	 * <p>Discription:[以JSON格式输出流]</p>
	 * @param out
	 * @author 张海波  2015-11-2
	 * @throws
	 */
	void toJSON(Writer out) throws IOException;
	
	
	/**
	 * <p>Discription:[转换为字符串]</p>
	 * @return
	 * @author zhanghaibo3  2015-11-2
	 */
	String toString();
}
