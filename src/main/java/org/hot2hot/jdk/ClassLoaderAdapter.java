package org.hot2hot.jdk;

import java.security.ProtectionDomain;

import org.hot2hot.utils.Clazzs;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Transform the {@link ClassLoader0}.
 * 
 * @author hotcode.huangt 13-6-28 PM9:03
 */
public class ClassLoaderAdapter extends ClassVisitor {

    public ClassLoaderAdapter(ClassVisitor cv){
        super(Opcodes.ASM4, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if (name.equals("defineClass")
            && desc.equals(Type.getMethodDescriptor(Type.getType(Class.class), Type.getType(String.class),
                                                    Type.getType(byte[].class), Type.INT_TYPE, Type.INT_TYPE,
                                                    Type.getType(ProtectionDomain.class)))) {
            return new MethodVisitor(Opcodes.ASM4, mv) {

                @Override
                public void visitCode() {
                	super.visitCode();
                    //if 判断
                    Label jump = new Label();
                    Label tryLabel = new Label();
                    /*mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(Clazzs.class),
                            "getClazz", "(Ljava/lang/String;)Ljava/lang/Class;");
                    mv.visitJumpInsn(Opcodes.IFNONNULL, jump);*/
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("com.sun.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("java.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("javax.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("org.omg.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("org.w3c.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("org.xml.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitLdcInsn("sun.");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class),
                            "startsWith", "(Ljava/lang/String;)Z");
                    mv.visitJumpInsn(Opcodes.IFNE, jump);
                    
                    //
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitVarInsn(Opcodes.ALOAD, 5);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ClassLoader.class),
                            "preDefineClass", "(Ljava/lang/String;Ljava/security/ProtectionDomain;)Ljava/security/ProtectionDomain;");
                    mv.visitVarInsn(Opcodes.ASTORE, 5);
                    mv.visitInsn(Opcodes.ACONST_NULL);
                    
                    mv.visitVarInsn(Opcodes.ASTORE, 6);
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ALOAD, 5);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ClassLoader.class),
                            "defineClassSourceLocation", "(Ljava/security/ProtectionDomain;)Ljava/lang/String;");
                    
                    mv.visitVarInsn(Opcodes.ASTORE, 7);
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitVarInsn(Opcodes.ALOAD, 2);
                    mv.visitVarInsn(Opcodes.ILOAD, 3);
                    mv.visitVarInsn(Opcodes.ILOAD, 4);
                    mv.visitVarInsn(Opcodes.ALOAD, 5);
                    mv.visitVarInsn(Opcodes.ALOAD, 7);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ClassLoader.class),
                            "defineClass1", "(Ljava/lang/String;[BIILjava/security/ProtectionDomain;Ljava/lang/String;)Ljava/lang/Class;");
                    mv.visitVarInsn(Opcodes.ASTORE, 6);
                    mv.visitJumpInsn(Opcodes.GOTO, tryLabel);
                    mv.visitVarInsn(Opcodes.ASTORE, 8);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitVarInsn(Opcodes.ALOAD, 2);
                    mv.visitVarInsn(Opcodes.ILOAD, 3);
                    mv.visitVarInsn(Opcodes.ILOAD, 4);
                    mv.visitVarInsn(Opcodes.ALOAD, 5);
                    mv.visitVarInsn(Opcodes.ALOAD, 8);
                    mv.visitVarInsn(Opcodes.ALOAD, 7);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ClassLoader.class),
                            "defineTransformedClass", "(Ljava/lang/String;[BIILjava/security/ProtectionDomain;Ljava/lang/ClassFormatError;Ljava/lang/String;)Ljava/lang/Class;");
                    mv.visitVarInsn(Opcodes.ASTORE, 6);
                    mv.visitLabel(tryLabel);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ALOAD, 6);
                    mv.visitVarInsn(Opcodes.ALOAD, 5);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ClassLoader.class),
                            "postDefineClass", "(Ljava/lang/Class;Ljava/security/ProtectionDomain;)V");

                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitVarInsn(Opcodes.ALOAD, 6);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(Clazzs.class),
                            "setClazz", "(Ljava/lang/String;Ljava/lang/Class;)V");
                    
                    /*mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitVarInsn(Opcodes.ALOAD, 2);
                    mv.visitVarInsn(Opcodes.ILOAD, 3);
                    mv.visitVarInsn(Opcodes.ILOAD, 4);
                    mv.visitVarInsn(Opcodes.ALOAD, 5);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ClassLoader.class),
                            "defineClass", "(Ljava/lang/String;[BIILjava/security/ProtectionDomain;)Ljava/lang/Class;");
                    
                    mv.visitVarInsn(Opcodes.ASTORE, 6);
                    
                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                    mv.visitVarInsn(Opcodes.ALOAD, 6);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(Clazzs.class),
                                       "setClazz", "(Ljava/lang/String;Ljava/lang/Class;)V");*/
                    mv.visitVarInsn(Opcodes.ALOAD, 6);
                    mv.visitInsn(Opcodes.ARETURN);
                    
                    mv.visitLabel(jump);
                }

            };
        }

        return mv;
    }
}
