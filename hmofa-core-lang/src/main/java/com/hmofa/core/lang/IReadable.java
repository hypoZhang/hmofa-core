package com.hmofa.core.lang;

import java.io.Serializable;

import org.dom4j.Branch;

/**
 * <dd>Description:[可读的]</dd>
 * <dt>IReadable</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-13</dd>
 * @version 1.0
 * @author hypo zhang
 */
public interface IReadable extends Serializable {
	
	void buildXMLSerialize(Branch parent);
	
	void buildJSONSerialize();
}
