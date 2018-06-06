package com.hmofa.core.exception;

public class ExceptionHelper {

	private ExceptionHelper() {
	}

	public static BaseRuntimeException wrap(Throwable ex) {
		return wrap(null, ex);
	}

	public static BaseRuntimeException wrap(String message) {
		return wrap(message, null);
	}

	// 建议 栈顶异常，应首先在  catch 捕获
	public static BaseRuntimeException wrap(String message, Throwable ex) {
		BaseRuntimeException bex = null;
		if (ex == null) {
			bex = new BaseRuntimeException(message);
		} else if (isEmpty(message) && ex instanceof BaseRuntimeException) {
			bex = (BaseRuntimeException) ex;
		} else if (ex instanceof NullPointerException) {
			bex = new com.hmofa.core.exception.NullArgumentException(message, ex);
		} else if (ex instanceof ArithmeticException) {
			
		} else if (ex instanceof IndexOutOfBoundsException) {
			
		} else if (ex instanceof ArrayStoreException) {
			
		} else if (ex instanceof ClassCastException) {
			
		} else if (ex instanceof ClassNotFoundException) {
			
		} else if (ex instanceof IllegalArgumentException) {
			
		}

		if (bex == null)
			bex = new BaseRuntimeException(message, ex);
		
		Throwable suppr = threadCasue.get();
		if (suppr == null) {
			threadCasue.set(bex); // 栈顶异常
		}
		return bex;
	}

	/**
	 * <p>Discription:[建议finally 块调用]</p>
	 * @param ex
	 * @return 栈顶异常 (最初捕获的)
	 * @author hypo zhang  2018-06-06
	 */
	public static BaseRuntimeException addSuppressed(Throwable ex) {
		BaseRuntimeException suppr = (BaseRuntimeException) threadCasue.get();
		if (suppr != null) {
			suppr.addSuppressed(ex);
		} else {
			suppr = wrap(null, ex);
		}
		return suppr;
	}

	private static boolean isEmpty(String message) {
		return message == null || message.length() == 0;
	}
	
	public static void removeLocal() {
		threadCasue.remove();
	}

	private final static ThreadLocal<BaseRuntimeException> threadCasue = new ThreadLocal<BaseRuntimeException>();
}
