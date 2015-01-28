package org.hot2hot.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.hot2hot.ClassRedefiner;
import org.hot2hot.utils.Clazzs;
import org.hot2hot.utils.PackageFromClassFile;

/**
 * 文件扫描
 * @author jiaojianfeng
 *
 */
public class FileScanTask implements Runnable {
	private String filePath;
	
	public FileScanTask(final String filePath) {
		this.setFilePath(filePath);
	}

	@Override
	public void run() {
		try {
			//System.out.println(filePath);
			//System.out.println(Thread.currentThread().getContextClassLoader());
			File file = new File(filePath);
			if(!file.exists() || !file.isDirectory()) {
				return;
			}
			
			File[] classFiles = file.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					if(!name.endsWith(".class")) {
						return false;
					}
					return true;
				}
			});
			
			for(File classFile : classFiles) {
				FileInputStream fs = null;
				FileChannel channel = null;
				try {
					fs = new FileInputStream(classFile);
					channel = fs.getChannel();
					ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());
					while(channel.read(byteBuffer) > 0) {}
					
					byte[] b = byteBuffer.array();
					
					String clazzName = PackageFromClassFile.getPackage(classFile);
					System.out.println("替换：" + clazzName);
					
		            Class<?> clazz = Clazzs.getClazz(clazzName);
		            System.out.println(clazz.getClassLoader());
		            ClassRedefiner.redefine(clazz, b);
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
					if(channel != null) {
						try {
							channel.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(fs != null) {
						try {
							fs.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				classFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			//ScheduledExecutorService  bug
		}
		
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}