package org.hot2hot;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class ClassRedefiner {

    private static Instrumentation inst;

    public static void setInstrumentation(Instrumentation inst) {
        ClassRedefiner.inst = inst;
    }

    public static void redefine(Class<?> klass, byte[] classFile) {
        try {
            inst.redefineClasses(new ClassDefinition(klass, classFile));
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }
}
