package com.hmofa.core.exception.nestable;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface Nestable {

	/**
	 * <p>Discription:[取异常]</p>
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	Throwable getCause();

	/**
	 * <p>Discription:[异常消息]</p>
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	String getMessage();

	/**
	 * <p>Discription:[异常栈指定异常消息]</p>
	 * @param i
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	String getMessage(int i);

	/**
	 * <p>Discription:[异常栈所有消息]</p>
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	String[] getMessages();

	/**
	 * <p>Discription:[异常栈指定异常]</p>
	 * @param i
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	Throwable getThrowable(int i);

	/**
	 * <p>Discription:[异常栈个数]</p>
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	int getThrowableCount();

	/**
	 * <p>Discription:[异常栈所有异常]</p>
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	Throwable[] getThrowables();

	/**
	 * <p>Discription:[找符合类型的异常]</p>
	 * @param class1
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	int indexOfThrowable(Class<?> class1);

	/**
	 * <p>Discription:[从指定位置开始找符合类型的异常]</p>
	 * @param class1
	 * @param i
	 * @return
	 * @author hypo zhang  2018-05-31
	 */
	int indexOfThrowable(Class<?> class1, int i);

	void printStackTrace(PrintWriter printwriter);

	void printStackTrace(PrintStream printstream);

	/**
	 * 通过调用原生 打印方法
	 * <p>Discription:[Throwable 支持  getCause 适用]</p>
	 * @param printwriter
	 * @author hypo zhang  2018-05-31
	 */
	void printPartialStackTrace(PrintWriter printwriter);
}
