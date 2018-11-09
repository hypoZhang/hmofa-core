package com.hmofa.core.resources;

import java.io.File;
import java.net.URL;

import com.hmofa.core.resources.input.ClassPathResource;

import junit.runner.BaseTestRunner;
import junit.runner.Sorter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
				
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		String logoresource = "/junit/runner/logo.gif";
		ClassPathResource logo = new ClassPathResource(logoresource, classLoader);
		URL url = logo.getURL();
		System.out.println(url + " | " + logo.lastModified());
		
		logoresource = "smalllogo.gif";
		logo = (ClassPathResource)logo.createRelative(logoresource);
		url = logo.getURL();
		System.out.println(url + " | " + logo.lastModified());
		url = logo.getJarURL();
		System.out.println(url);
		
//		String[] relas = logo.listResourceName();
//		for (String rela : relas) {
//			System.out.println(rela);
//		}
		
		logoresource = "InputResource.class";
		logo = new ClassPathResource(logoresource, Resource.class);
		url = logo.getURL();
		System.out.println(url + " | " + logo.lastModified());
		
//		logoresource = "../../stylesheet.css";
//		logo = new ClassPathResource(logoresource, Sorter.class);
//		url = logo.getURL();
//		System.out.println(url + " | " + logo.lastModified());
		
		System.out.println("===================================");
		
		String file = "E:/unistudio/tees/hmofa-core/hmofa-core-resources/target/classes/com/hmofa/core/resources2/";
		String file2 = "/..";
		File dir = new File(file2);
		System.out.println("pathsep:" + File.pathSeparator);
		System.out.println("sep:" + File.separator);
		System.out.println("path:" + dir.getPath());
		System.out.println("absolute:" + dir.getCanonicalPath());
		System.out.println("dir:" + dir.isDirectory());
		System.out.println("name:" + dir.getName());
		System.out.println("last:" + dir.lastModified());
		System.out.println("length:" + dir.length());
		if (dir.exists()) {
			File[] ss = dir.listFiles();
			for (File s : ss) {
				System.out.println(s);
			}
		}
		
		
		System.out.println("=====================================");
		
		URL classFilePath = BaseTestRunner.class.getResource("");
		System.out.println(classFilePath);
		
		URL jarFilePath = BaseTestRunner.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println(jarFilePath);
		
	}
}
