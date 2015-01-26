

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
