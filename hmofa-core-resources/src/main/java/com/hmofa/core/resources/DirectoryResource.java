package com.hmofa.core.resources;

import java.io.File;
import java.util.List;

public interface DirectoryResource extends Resource {

	/**
	 * <p>Discription:[资源是否为目录]</p>
	 * @return
	 * @author hypo zhang  2018-06-06
	 */
	boolean isDirectory();
	
	/**
	 * <p>Discription:[资源为目录时，获取目录下所有资源名]</p>
	 * @return
	 * @author hypo zhang  2018-06-06
	 */
	List<String> list();
	
	/**
	 * <p>Discription:[资源为目录时，获取目录下所有资源全路径]</p>
	 * @return
	 * @author hypo zhang  2018-06-06
	 */
	List<File> listFile();
}
