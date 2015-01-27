package org.hot2hot.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Class_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Utf8_info;
import com.sun.tools.classfile.ConstantPool.CPInfo;
import com.sun.tools.classfile.ConstantPoolException;

public class PackageFromClassFile {

	public static String getPackage(File paramFile) {
		try {
			ClassFile classFile = ClassFile.read(paramFile);
			CPInfo classInfo = classFile.constant_pool.get(classFile.this_class);
			if(classInfo instanceof CONSTANT_Class_info) {
				CPInfo cp = classFile.constant_pool.get(((CONSTANT_Class_info) classInfo).name_index);
				if(cp instanceof CONSTANT_Utf8_info) {
					return StringUtils.replace(((CONSTANT_Utf8_info)cp).value, "/", ".");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConstantPoolException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		File file = new File("/Users/jiaojianfeng/Documents/empleyment/clazz/A.class");
		System.out.println(getPackage(file));
	}
}
