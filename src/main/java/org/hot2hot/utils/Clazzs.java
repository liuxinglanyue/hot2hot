package org.hot2hot.utils;

import java.util.HashMap;
import java.util.Map;

public class Clazzs {
	private final static Map<String, Class<?>> clazz_map = new HashMap<String, Class<?>>();
	
	private Clazzs() {}
	
	public static Class<?> getClazz(String key) {
		return clazz_map.get(key);
	}
	
	public static void setClazz(String key, Class<?> clazz) {
		clazz_map.put(key, clazz);
	}
}
