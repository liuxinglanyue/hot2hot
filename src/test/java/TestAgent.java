
/**
 * 启动参数配置 javaagent
 * -javaagent:hot2hot.jar
 * 
 * @author jiaojianfeng
 *
 */
public class TestAgent {

	public static void main(String[] args) throws InterruptedException {
		A a = new A();
		a.run();
		
		while(true) {
			Thread.sleep(20000);
			a.run();
		}
	}
}
