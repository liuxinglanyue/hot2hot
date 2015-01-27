package org.hot2hot.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * fast
 * @author jiaojianfeng
 *
 */
public class PackageFromClassFast {
	private final static int MAGIC = 0xCAFEBABE;

	public static String getPackage(String fileName) {
		DataInputStream in = null;

		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
			if (in.readInt() != MAGIC) {
				throw new IOException(fileName + "非Java class文件!");
			}

			in.readUnsignedShort();// 次版本号
			in.readUnsignedShort();// 主版本号
			int i = in.readUnsignedShort();// 常量池长度
			Map<Integer, Integer> classInfoMap = new HashMap<Integer, Integer>();
			Map<Integer, String> classNameMap = new HashMap<Integer, String>();

			//常量池
			for (int j = 1; j < i; ++j) {
				int k = in.readUnsignedByte();
				switch (k) {
				case 7:
					classInfoMap.put(j, in.readUnsignedShort());
					break;
				case 6:
					in.readDouble();
					++j;
					break;
				case 9:
					in.readUnsignedShort();
					in.readUnsignedShort();
					break;
				case 4:
					in.readFloat();
					break;
				case 3:
					in.readInt();
					break;
				case 11:
					in.readUnsignedShort();
					in.readUnsignedShort();
					break;
				case 18:
					in.readUnsignedShort();
					in.readUnsignedShort();
					break;
				case 5:
					in.readLong();
					++j;
					break;
				case 15:
					in.readUnsignedByte();
					in.readUnsignedShort();
					break;
				case 16:
					in.readUnsignedShort();
					break;
				case 10:
					in.readUnsignedShort();
					in.readUnsignedShort();
					break;
				case 12:
					in.readUnsignedShort();
					in.readUnsignedShort();
					break;
				case 8:
					in.readUnsignedShort();
					break;
				case 1:
					classNameMap.put(j, in.readUTF());
					break;
				case 2:
				case 13:
				case 14:
				case 17:
				default:
					throw new RuntimeException("class文件错误！");
				}
			}
			
			//access flag
			in.readUnsignedShort();
			
			//this class
			int thisClazz = in.readUnsignedShort();
			
			return StringUtils.replace(classNameMap.get(classInfoMap.get(thisClazz)), "/", ".");

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getPackage("/Users/jiaojianfeng/Documents/empleyment/clazz/A.class"));
	}
}
