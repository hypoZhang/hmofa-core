package com.hmofa.core.exception.nestable;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import com.hmofa.core.exception.NullArgumentException;

public class NexceptionUtils {
	
	private NexceptionUtils() {
	}
	
	public static void addCauseMethodName(String methodName) {
		if (isNotEmpty(methodName) && !isCauseMethodName(methodName)) {
			List<String> list = getCauseMethodNameList();
			if (list.add(methodName))
				CAUSE_METHOD_NAMES = toArray(list);
		}
	}

	public static void removeCauseMethodName(String methodName) {
		if (isNotEmpty(methodName)) {
			List<String> list = getCauseMethodNameList();
			if (list.remove(methodName))
				CAUSE_METHOD_NAMES = toArray(list);
		}
	}

	public static boolean setCause(Throwable target, Throwable cause) {
		if (target == null)
			throw new NullArgumentException("target");
		Object causeArgs[] = { cause };
		boolean modifiedTarget = false;
		if (THROWABLE_INITCAUSE_METHOD != null)
			try {
				THROWABLE_INITCAUSE_METHOD.invoke(target, causeArgs);
				modifiedTarget = true;
			} catch (IllegalAccessException ignored) {
			} catch (InvocationTargetException ignored) {
			}
		try {
			Method setCauseMethod = target.getClass().getMethod("setCause", new Class[] { java.lang.Throwable.class });
			setCauseMethod.invoke(target, causeArgs);
			modifiedTarget = true;
		} catch (NoSuchMethodException ignored) {
		} catch (IllegalAccessException ignored) {
		} catch (InvocationTargetException ignored) {
		}
		return modifiedTarget;
	}

	private static String[] toArray(List<String> list) {
		return (String[]) list.toArray(new String[list.size()]);
	}

	private static ArrayList<String> getCauseMethodNameList() {
		return new ArrayList<String>(Arrays.asList(CAUSE_METHOD_NAMES));
	}

	public static boolean isCauseMethodName(String methodName) {
		return indexOf(CAUSE_METHOD_NAMES, 0, methodName) >= 0;
	}

	public static Throwable getCause(Throwable throwable) {
		return getCause(throwable, CAUSE_METHOD_NAMES);
	}

	public static Throwable getCause(Throwable throwable, String methodNames[]) {
		if (throwable == null)
			return null;
		Throwable cause = getCauseUsingWellKnownTypes(throwable);
		if (cause == null) {
			if (methodNames == null)
				methodNames = CAUSE_METHOD_NAMES;
			for (int i = 0; i < methodNames.length; i++) {
				String methodName = methodNames[i];
				if (methodName == null)
					continue;
				cause = getCauseUsingMethodName(throwable, methodName);
				if (cause != null)
					break;
			}
			if (cause == null)
				cause = getCauseUsingFieldName(throwable, "detail");
		}
		return cause;
	}

	public static Throwable getRootCause(Throwable throwable) {
		List<Throwable> list = getThrowableList(throwable);
		return list.size() >= 2 ? list.get(list.size() - 1) : null;
	}

	private static Throwable getCauseUsingWellKnownTypes(Throwable throwable) {
		if (throwable instanceof Nestable)
			return ((Nestable) throwable).getCause();
		if (throwable instanceof SQLException)
			return ((SQLException) throwable).getNextException();
		if (throwable instanceof InvocationTargetException)
			return ((InvocationTargetException) throwable).getTargetException();
		else
			return null;
	}

	private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName) {
		Method method = null;
		try {
			Class<?>[] cnull = null;
			method = throwable.getClass().getMethod(methodName, cnull);
		} catch (NoSuchMethodException ignored) {
		} catch (SecurityException ignored) {
		}
		if (method != null && (java.lang.Throwable.class).isAssignableFrom(method.getReturnType()))
			try {
				return (Throwable) method.invoke(throwable, EMPTY_OBJECT_ARRAY);
			} catch (IllegalAccessException ignored) {
			} catch (IllegalArgumentException ignored) {
			} catch (InvocationTargetException ignored) {
			}
		return null;
	}

	private static Throwable getCauseUsingFieldName(Throwable throwable, String fieldName) {
		Field field = null;
		try {
			field = throwable.getClass().getField(fieldName);
		} catch (NoSuchFieldException ignored) {
		} catch (SecurityException ignored) {
		}
		if (field != null && (java.lang.Throwable.class).isAssignableFrom(field.getType()))
			try {
				return (Throwable) field.get(throwable);
			} catch (IllegalAccessException ignored) {
			} catch (IllegalArgumentException ignored) {
			}
		return null;
	}

	public static boolean isThrowableNested() {
		return THROWABLE_CAUSE_METHOD != null;
	}

	public static boolean isNestedThrowable(Throwable throwable) {
		if (throwable == null)
			return false;
		if (throwable instanceof Nestable)
			return true;
		if (throwable instanceof SQLException)
			return true;
		if (throwable instanceof InvocationTargetException)
			return true;
		if (isThrowableNested())
			return true;
		Class<?> cls = throwable.getClass();
		int i = 0;
		Class<?>[] cnull = null;
		for (int isize = CAUSE_METHOD_NAMES.length; i < isize; i++)
			try {
				Method method = cls.getMethod(CAUSE_METHOD_NAMES[i], cnull);
				if (method != null && (java.lang.Throwable.class).isAssignableFrom(method.getReturnType()))
					return true;
			} catch (NoSuchMethodException ignored) {
			} catch (SecurityException ignored) {
			}

		try {
			Field field = cls.getField("detail");
			if (field != null)
				return true;
		} catch (NoSuchFieldException ignored) {
		} catch (SecurityException ignored) {
		}
		return false;
	}

	public static int getThrowableCount(Throwable throwable) {
		return getThrowableList(throwable).size();
	}

	public static Throwable[] getThrowables(Throwable throwable) {
		List<Throwable> list = getThrowableList(throwable);
		return (Throwable[]) list.toArray(new Throwable[list.size()]);
	}

	public static List<Throwable> getThrowableList(Throwable throwable) {
		List<Throwable> list;
		for (list = new ArrayList<Throwable>(); throwable != null && !list.contains(throwable); throwable = getCause(throwable))
			list.add(throwable);
		return list;
	}

	public static int indexOfThrowable(Throwable throwable, Class<?> clazz) {
		return indexOf(throwable, clazz, 0, false);
	}

	public static int indexOfThrowable(Throwable throwable, Class<?> clazz, int fromIndex) {
		return indexOf(throwable, clazz, fromIndex, false);
	}

	public static int indexOfType(Throwable throwable, Class<?> type) {
		return indexOf(throwable, type, 0, true);
	}

	public static int indexOfType(Throwable throwable, Class<?> type, int fromIndex) {
		return indexOf(throwable, type, fromIndex, true);
	}

	private static int indexOf(Throwable throwable, Class<?> type, int fromIndex, boolean subclass) {
		if (throwable == null || type == null)
			return -1;
		if (fromIndex < 0)
			fromIndex = 0;
		Throwable throwables[] = getThrowables(throwable);
		if (fromIndex >= throwables.length)
			return -1;
		if (subclass) {
			for (int i = fromIndex; i < throwables.length; i++)
				if (type.isAssignableFrom(throwables[i].getClass()))
					return i;

		} else {
			for (int i = fromIndex; i < throwables.length; i++)
				if (type.equals(throwables[i].getClass()))
					return i;
		}
		return -1;
	}
	
	/**
	 * 打印异常堆栈
	 * @param exception
	 * @return
	 */
	public static String printException(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		try {
			throwable.printStackTrace(writer);
			return sw.getBuffer().toString();
		} finally {
			writer.close();
		}
	}

	public static void printRootCauseStackTrace(Throwable throwable) {
		printRootCauseStackTrace(throwable, System.err);
	}

	public static void printRootCauseStackTrace(Throwable throwable, PrintStream stream) {
		if (throwable == null)
			return;
		if (stream == null)
			throw new IllegalArgumentException("The PrintStream must not be null");
		String trace[] = getRootCauseStackTrace(throwable);
		for (int i = 0; i < trace.length; i++)
			stream.println(trace[i]);

		stream.flush();
	}

	public static void printRootCauseStackTrace(Throwable throwable, PrintWriter writer) {
		if (throwable == null)
			return;
		if (writer == null)
			throw new IllegalArgumentException("The PrintWriter must not be null");
		String trace[] = getRootCauseStackTrace(throwable);
		for (int i = 0; i < trace.length; i++)
			writer.println(trace[i]);

		writer.flush();
	}

	public static String[] getRootCauseStackTrace(Throwable throwable) {
		if (throwable == null)
			return EMPTY_STRING_ARRAY;
		Throwable throwables[] = getThrowables(throwable);
		int count = throwables.length;
		ArrayList<String> frames = new ArrayList<String>();
		List<String> nextTrace = getStackFrameList(throwables[count - 1]);
		for (int i = count; --i >= 0;) {
			List<String> trace = nextTrace;
			if (i != 0) {
				nextTrace = getStackFrameList(throwables[i - 1]);
				removeCommonFrames(trace, nextTrace);
			}
			if (i == count - 1)
				frames.add(throwables[i].toString());
			else
				frames.add(" [wrapped] " + throwables[i].toString());
			for (int j = 0; j < trace.size(); j++)
				frames.add(trace.get(j));
		}
		return (String[]) frames.toArray(new String[0]);
	}

	public static void removeCommonFrames(List<String> causeFrames, List<String> wrapperFrames) {
		if (causeFrames == null || wrapperFrames == null)
			throw new IllegalArgumentException("The List must not be null");
		int causeFrameIndex = causeFrames.size() - 1;
		for (int wrapperFrameIndex = wrapperFrames.size() - 1; causeFrameIndex >= 0 && wrapperFrameIndex >= 0; wrapperFrameIndex--) {
			String causeFrame = (String) causeFrames.get(causeFrameIndex);
			String wrapperFrame = (String) wrapperFrames.get(wrapperFrameIndex);
			if (causeFrame.equals(wrapperFrame))
				causeFrames.remove(causeFrameIndex);
			causeFrameIndex--;
		}
	}

	public static String getFullStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		Throwable ts[] = getThrowables(throwable);
		for (int i = 0; i < ts.length; i++) {
			ts[i].printStackTrace(pw);
			if (isNestedThrowable(ts[i]))
				break;
		}

		return sw.getBuffer().toString();
	}

	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public static String[] getStackFrames(Throwable throwable) {
		if (throwable == null)
			return EMPTY_STRING_ARRAY;
		else
			return getStackFrames(getStackTrace(throwable));
	}

	static String[] getStackFrames(String stackTrace) {
		String linebreak = newLine();
		StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
		List<String> list = new ArrayList<String>();
		for (; frames.hasMoreTokens(); list.add(frames.nextToken()))
			;
		return toArray(list);
	}

	static List<String> getStackFrameList(Throwable t) {
		String stackTrace = getStackTrace(t);
		String linebreak = newLine();
		StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
		List<String> list = new ArrayList<String>();
		boolean traceStarted = false;
		while (frames.hasMoreTokens()) {
			String token = frames.nextToken();
			int at = token.indexOf("at");
			if (at != -1 && token.substring(0, at).trim().length() == 0) {
				traceStarted = true;
				list.add(token);
				continue;
			}
			if (traceStarted)
				break;
		}
		return list;
	}

	public static String getMessage(Throwable th) {
		if (th == null) {
			return "";
		} else {
			String clsName = getShortClassName(th == null ? null : th.getClass().getName());
			String msg = th.getMessage();
			return clsName + ": " + Nvl(msg, "");
		}
	}
	
	public static String getRootCauseMessage(Throwable th) {
		Throwable root = getRootCause(th);
		root = root != null ? root : th;
		return getMessage(root);
	}

	static final String WRAPPED_MARKER = " [wrapped] ";
	private static String CAUSE_METHOD_NAMES[] = { "getCause", "getNextException", "getTargetException", "getException",
			"getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException",
			"getLinkedCause", "getThrowable" };
	private static final Method THROWABLE_CAUSE_METHOD;
	private static final Method THROWABLE_INITCAUSE_METHOD;

	static {
		Method causeMethod;
		Class<?>[] c = null;
		try {
			causeMethod = (java.lang.Throwable.class).getMethod("getCause", c);
		} catch (Exception e) {
			causeMethod = null;
		}
		THROWABLE_CAUSE_METHOD = causeMethod;
		try {
			causeMethod = (java.lang.Throwable.class).getMethod("initCause", new Class[] { java.lang.Throwable.class });
		} catch (Exception e) {
			causeMethod = null;
		}
		THROWABLE_INITCAUSE_METHOD = causeMethod;
	}
	
	
	public static final boolean isNotWhitespace(CharSequence str) {
		return !isWhitespace(str);
	}

	private static final boolean isWhitespace(CharSequence str) {
		if (isEmpty(str))
			return true;
		BigDecimal length = new BigDecimal(str.length());
		int center = length.divide(divnum2, 0, RoundingMode.HALF_UP).intValue(); // 从前和中间开始取，减少循环
		for (int index = 0; index < center; index++) {
			char ch1 = str.charAt(index);
			int index2 = index + center;
			index2 = index2 >= str.length() ? str.length() - 1 : index2;
			char ch2 = str.charAt(index2);
			if (!Character.isWhitespace(ch1) || !Character.isWhitespace(ch2))
				return false;
		}
		return true;
	}
	
	private static final String newLine() {
		return "";
	}
	
	private static final String Nvl(String value, String defaultValue) {
		return value == null ? defaultValue : value;
	}
	
	private static final boolean isNotEmpty(CharSequence str) {
		return !isEmpty(str);
	}

	private static final boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}
	
	private final static int indexOf(Object objectToFind, int startIndex, Object... array) {
		if (array == null)
			return -1;
		if (startIndex < 0)
			startIndex = 0;
		for (int index = startIndex; index < array.length; index++) {
			if (equals(objectToFind, array[index]))
				return index;
		}
		return -1;
	}
	
	private final static boolean equals(Object obj1, Object obj2) {
		return obj1 == null ? obj2 == null : obj1.equals(obj2);
	}
	
	private static String getShortClassName(String className) {
		if (isEmpty(className))
			return "";
		int lastDot = 0;
		char chars[] = className.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '.')
				lastDot = i + 1;
			else if (chars[i] == '$')
				chars[i] = '.';
		}
		return new String(chars, lastDot, chars.length - lastDot);
	}
	
	private static final BigDecimal divnum2 = new BigDecimal("2");
	
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
}
