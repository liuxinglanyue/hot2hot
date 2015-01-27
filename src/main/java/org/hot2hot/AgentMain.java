package org.hot2hot;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hot2hot.task.FileScanTask;


public class AgentMain {

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassRedefiner.setInstrumentation(inst);
        
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		FileScanTask task = new FileScanTask("/Users/jiaojianfeng/Documents/empleyment/clazz");
		
		executorService.scheduleWithFixedDelay(task, 1000, 2000, TimeUnit.MILLISECONDS);

    }
}
