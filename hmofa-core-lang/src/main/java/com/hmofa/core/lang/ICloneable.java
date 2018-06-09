package com.hmofa.core.lang;

import com.hmofa.core.exception.CloneNotSupportedException;

/**
 * <dd>Description:[克隆接口]</dd>
 * <dt>ICloneable</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public interface ICloneable<E> extends Cloneable {

	/**
	 * <p>Discription:[克隆]</p>
	 * @return
	 * @throws CloneNotSupportException
	 * @author zhanghaibo3  2015-11-2
	 */
	E clone() throws CloneNotSupportedException;
	
}
