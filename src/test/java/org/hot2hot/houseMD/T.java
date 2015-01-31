package org.hot2hot.houseMD;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.Type;

public class T {
	public String getStr(String name, int count) throws IOException {
        System.out.println(name);

        InputStream in = Test.class.getClassLoader().getResourceAsStream(Type.getInternalName(T.class) + ".class");
        FileUtils.writeByteArrayToFile(new File("/tmp/jjf/T" + count + ".class"), IOUtils.toByteArray(new BufferedInputStream(in)));
		in.close();
        
        return name;
    }
}
