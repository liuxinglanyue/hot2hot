package org.hot2hot.houseMD;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		int count = 0;
		while(true) {
			T t = new T();
			t.getStr("name", count++);
			
			/*InputStream in = Test.class.getClassLoader().getResourceAsStream(Type.getInternalName(T.class) + ".class");
            FileUtils.writeByteArrayToFile(new File("/tmp/jjf/T" + count + ".class"), IOUtils.toByteArray(new BufferedInputStream(in)));
			in.close();*/
			
			Thread.sleep(3000);
		}
	}
}
