package com.hmofa.core.lang.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum JavaVersion {

	JAVA_0_9(1.5F, "0.9", 44, 0, "Java 0.9"), 
	JAVA_1_1(1.1F, "1.1", 45, 0, "Java 1.1"), 
	JAVA_1_2(1.2F, "1.2", 46, 0, "Java 1.2"), 
	JAVA_1_3(1.3F, "1.3", 47, 0, "Java 1.3"),
	JAVA_1_4(1.4F, "1.4", 48, 0, "Java 1.4"), 
	JAVA_1_5(1.5F, "1.5", 49, 0, "Java 5"), 
	JAVA_1_6(1.6F, "1.6", 50, 0, "Java 6"), 
	JAVA_1_7(1.7F, "1.7", 51, 0, "Java 7"), 
	JAVA_1_8(1.8F, "1.8", 52, 0, "Java 8"),
	
	;

	private JavaVersion(float value, String name, int majorVersion, int minorVersion, String language) {
		this.value = value;
		this.name = name;
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.language = language;
		this.version = language + " version " + name + " : " + majorVersion + "." + minorVersion;
	}

	public float getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String toString() {
		return version;
	}

	public boolean atLeast(JavaVersion requiredVersion) {
		return value >= requiredVersion.value;
	}

	private float value;
	private String name;
	private int majorVersion;
	private int minorVersion;
	private String language;
	private String version;
	
	public static JavaVersion getJavaVersion(String nom) {
		return get(nom);
	}

	static JavaVersion get(String nom) {
		if (enumUnmodifiableMap.containsKey(nom))
			return enumUnmodifiableMap.get(nom);
		return null;
	}

	private static final Map<String, JavaVersion> enumUnmodifiableMap = getEnumUnmodifiableMap();
	
	private static final Map<String, JavaVersion> getEnumUnmodifiableMap() {
		Map<String, JavaVersion> enumMap = new HashMap<String, JavaVersion>();
		
		enumMap.put("0.9", JAVA_0_9);
		enumMap.put("1.1", JAVA_1_1);
		enumMap.put("1.2", JAVA_1_2);
		enumMap.put("1.3", JAVA_1_3);
		enumMap.put("1.4", JAVA_1_4);
		enumMap.put("1.5", JAVA_1_5);
		enumMap.put("1.6", JAVA_1_6);
		enumMap.put("1.7", JAVA_1_7);
		enumMap.put("1.8", JAVA_1_8);
		
		return Collections.unmodifiableMap(enumMap);
	}
}
