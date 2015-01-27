package org.hot2hot.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ClassDumper {

    public static void dump(String className, byte[] classfile) {

        try {
            FileUtils.writeByteArrayToFile(new File("/tmp/" + className + ".class"), classfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}