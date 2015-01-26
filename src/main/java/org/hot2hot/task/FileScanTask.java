package org.hot2hot.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.hot2hot.ClassRedefiner;

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
				System.out.println(classFile.getAbsolutePath());
				
				FileInputStream fs = null;
				FileChannel channel = null;
				try {
					fs = new FileInputStream(classFile);
					channel = fs.getChannel();
					ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());
					while(channel.read(byteBuffer) > 0) {}
					
					byte[] b = byteBuffer.array();
					
					String clazzName = classFile.getName();
					clazzName = clazzName.substring(0, clazzName.indexOf("."));
				
				
		            Class clazz = Class.forName(clazzName);
		            ClassRedefiner.redefine(clazz, b);
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (ClassNotFoundException e) {
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