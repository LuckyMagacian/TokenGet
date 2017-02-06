package com.lanxi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanUtil {
	/**
	 * 获取类中所有字段
	 * @param clazz
	 * @return
	 */
	public static Map<String,Field> getAllFields(Class<?> clazz){
		Field[] fields=clazz.getDeclaredFields();
		Map<String, Field> map=new LinkedHashMap<String, Field>();
		for(Field each:fields){
			each.setAccessible(true);
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 获取类中 非static 字段 以linkedHashMap形式返回
	 * @param  clazz  传入的class对象
	 * @return map<name,Field> 
	 */
	public static Map<String, Field> getFieldsNoStatic(Class<?> clazz){
		Field[] fields=clazz.getDeclaredFields();
		Map<String, Field> map=new LinkedHashMap<String, Field>();
		for(Field each:fields){
			each.setAccessible(true);
			if(Modifier.isStatic(each.getModifiers()))
				continue;
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 获取类中所有方法
	 * @param clazz
	 * @return
	 */
	public static Map<String, Method> getAllMethods(Class<?> clazz){
		Map<String, Method> map=new LinkedHashMap<String, Method>();
		Method[] methods=clazz.getDeclaredMethods();
		for(Method each:methods){
			each.setAccessible(true);
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 获取类中 非static 方法 以linkedHashMap形式返回
	 * @param  clazz  传入的class对象
	 * @return map<name,Method>
	 */
	public static Map<String, Method> getMethodsNoStatic(Class<?> clazz){
		Map<String, Method> map=new LinkedHashMap<String, Method>();
		Method[] methods=clazz.getDeclaredMethods();
		for(Method each:methods){
			each.setAccessible(true);
			if(Modifier.isStatic(each.getModifiers()))
				continue;
			map.put(each.getName(), each);
		}
		return map;
	}
	
}
