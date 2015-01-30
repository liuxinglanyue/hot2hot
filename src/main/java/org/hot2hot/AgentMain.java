package org.hot2hot;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.hot2hot.jdk.ClassLoaderAdapter;
import org.hot2hot.task.FileScanTask;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;


public class AgentMain {

    public static void premain(String agentArgs, Instrumentation inst) {
    	if(null == agentArgs || agentArgs.trim().length() == 0) {
    		throw new RuntimeException("启动参数需要加上扫描文件路径！-javaagent:jarpath[=options]");
    	}
        ClassRedefiner.setInstrumentation(inst);
        System.out.println("扫描文件夹路径：" + agentArgs);
        
        //替换classloader
        InputStream is = ClassLoader.getSystemResourceAsStream(Type.getInternalName(ClassLoader.class) + ".class");

        try {
            ClassReader cr = new ClassReader(IOUtils.toByteArray(new BufferedInputStream(is)));
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new ClassLoaderAdapter(cw);
            cr.accept(cv, 0);
            byte[] transformedByte = cw.toByteArray();
            //ClassDumper.dump(Type.getInternalName(ClassLoader.class), transformedByte);
            ClassDefinition classDefinition = new ClassDefinition(ClassLoader.class, transformedByte);

            try {
                inst.redefineClasses(classDefinition);
            } catch (ClassNotFoundException | UnmodifiableClassException e) {
                e.printStackTrace(); 
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        
        //定时任务，扫描文件，替换
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		FileScanTask task = new FileScanTask(agentArgs);
		
		executorService.scheduleWithFixedDelay(task, 1000, 2000, TimeUnit.MILLISECONDS);

    }
}
