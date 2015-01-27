package org.hot2hot.utils;

import org.apache.commons.lang.StringUtils;

@Deprecated
public class PackageFromBytes {
	
	private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static String getPackage(byte[] clazz) {
		StringBuilder pack = new StringBuilder();
		//300 是根据包名的一般长度设置的。
		char[] cs = encodeHex(clazz, 300);
		
		int i = 32;
		while(true) {
			String hexStr = new String(cs, i, 2);
			int c = Integer.parseInt(hexStr, 16);
			pack.append((char)c);
			
			i += 2;
			if(!(c >= 97 && c <= 122 || c >= 65 && c <= 90 || c == 47 || c>= 48 && c <=57 || c == 36)) {
				break;
			}
		}
		return StringUtils.replace(pack.toString(), "/", ".");
	}
	
	private static char[] encodeHex(byte[] data, int think) {
		int l = data.length;
		l = think < l ? think : l;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS_UPPER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_UPPER[0x0F & data[i]];
		}
		return out;
	}
}
