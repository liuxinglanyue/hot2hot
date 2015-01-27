package org.hot2hot.classfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.hot2hot.utils.PackageFromBytes;

public class ReadClassFile {

	public static void main(String[] args) throws IOException {
		FileInputStream fs = null;
		FileChannel channel = null;
		File file = new File("/Users/jiaojianfeng/Documents/sourceCode/openSource/hot2hot/target/classes/org/hot2hot/AgentMain.class");
		try {
			fs = new FileInputStream(file);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());
			while(channel.read(byteBuffer) > 0) {}
			
			byte[] b = byteBuffer.array();
			System.out.println(PackageFromBytes.getPackage(b));
			
			//System.out.println(Hex.encodeHex(b, false));
			
		} catch(Exception e) {
			
		} finally {
			if(null != fs) {
				channel.close();
			}
			if(null != channel) {
				fs.close();
			}
		}
	}
}
