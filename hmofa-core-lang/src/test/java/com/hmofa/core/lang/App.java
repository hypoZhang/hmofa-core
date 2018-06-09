package com.hmofa.core.lang;

import java.util.Locale;

import com.hmofa.core.lang.converter.Converter;
import com.hmofa.core.lang.enums.JavaVersion;
import com.hmofa.core.lang.tuple.Pair;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        System.out.println( JavaVersion.valueOf("JAVA_0_9"));
        
        JavaVersion[] arr = JavaVersion.values();
        for(JavaVersion version: arr) {    
        	
        	System.out.println(version + "|" + version.ordinal());
        }
        
       String s = Converter.convert(arr[0], String.class, "pattern", "yyyy-mm-dd", "locale", Locale.getDefault());
    }
}
