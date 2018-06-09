package com.hmofa.core.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

public interface Resource {

	/**
	 * <p>Discription:[取资源URL]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	URL getURL() throws IOException;
	
	/**
	 * <p>Discription:[取资源 URI]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	URI getURI() throws IOException;
	
	/**
	 * <p>Discription:[最后修改时间]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	long lastModified() throws IOException;
	
	/**
	 * <p>Discription:[资源类型]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	String getResourceType() throws IOException;
	
	/**
	 * <p>Discription:[当前路径，创建资源]</p>
	 * @param relative
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Resource createRelative(String relative) throws IOException;
	
	/**
	 * <p>Discription:[当前资源路径下，所有资源]</p>
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	String[] listResource();
	
	/**
	 * <p>Discription:[不带路径的 资源名称]</p>
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	String getResourceName();
	
	/**
	 * <p>Discription:[资源描述信息]</p>
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	String getDescription();
	
	/**
	 * <p>Discription:[是否存在]</p>
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	boolean exists();
	
	/**
	 * <p>Discription:[是否可打开 (重复获取) ]</p>
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	boolean isOpenable();
	
	/**
	 * <p>Discription:[是否被独占]</p>
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	boolean isExclusiveAccess();
	
	/**
	 * <p>Discription:[资源的默认编码]</p>
	 * @return
	 * @throws IOException
	 * @author hypo zhang  2018-05-30
	 */
	Charset getCharset();
	
}

/*
资源类型:
本地磁盘资源: 含以下三种  类装载路径资源、压缩资源、本地文件系统资源

类装载路径资源 :  classpath (一般是 Jar资源，classes类编译路径资源)
压缩资源： Zip 、Rar 等压缩包内资源  
本地文件系统资源: filepath  (C: D:)  非压缩或 Jar资源        | 示： file:/E:/uepstudio/wunispace/uniesp-test/WebRoot/WEB-INF/classes/


内存数据资源:	 byte[]  String 等

网络资源：  ftp  http https 等协议


首先URI是uniform resource identifier，统一资源标识符，用来唯一的标识一个资源。
   而URL是uniform resource locator，统一资源定位器，它是一种具体的URI，即URL可以用来标识一个资源，而且还指明了如何locate这个资源。
   而URN是uniform resource name，统一资源命名，是通过名字来标识资源，比如mailto:java-net@java.sun.com。
   也就是说，URI是以一种抽象的，高层次概念定义统一资源标识，而URL和URN则是具体的资源标识的方式。URL和URN都是一种URI。

  在Java的URI中，一个URI实例可以代表绝对的，也可以是相对的，只要它符合URI的语法规则。
  而URL类则不仅符合语义，还包含了定位该资源的信息，因此它不能是相对的，schema必须被指定。

URL的格式URL的格式由下列三部分组成： 
 第一部分是协议（或称为服务方式）； 
 第二部分是存有该资源的主机IP地址或域名（有时也包括端口号）； 
 第三部分是主机资源的具体地址。，如目录和文件名等。

 第一部分和第二部分之间用"://"符号隔开，第二部分和第三部分用"/"符号隔开。第一部分和第二部分是不可缺少的，第三部分有时可以省略。 

URL示例 
 文件的URL： 用URL表示文件时，服务器方式用file表示，后面要有主机IP地址、文件的存取路径（即目录）和文件名等信息。有时可以省略目录和文件名，但“/”符号不能省略。
 例一：file://10.255.255.52/pub/files/foobar.txt 
            代表存放主机 10.255.255.52 上的pub/files/目录下的一个文件，文件名是foobar.txt。 
 例二：file://10.255.255.52/pub
            代表主机 10.255.255.52 上的目录/pub。 
 例三：file://10.255.255.52/ 
            代表主机 10.255.255.52 上的根目录。 

*/
