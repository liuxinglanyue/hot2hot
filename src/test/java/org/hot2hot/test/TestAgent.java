package org.hot2hot.test;

/**
 * 启动参数配置 javaagent
 * -javaagent:hot2hot.jar
 * 
 * @author jiaojianfeng
 *
 */
public class TestAgent {

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		//Clazzs.setClazz(A.class.getName(), A.class);
		A a = new A();
		a.run();
		
		while(true) {
			Thread.sleep(20000);
			a.run();
		}
	}
}
