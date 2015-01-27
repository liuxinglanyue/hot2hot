package org.hot2hot.test;

public class A {

	private int age;
	private String name;
	
	public A() {
		
	}
	
	public A(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public void run() throws InterruptedException {
		this.age = 100;
		this.name = "ton1";
		
		for(int i=0; i<10; i++) {
			System.out.println(this.age + ":" + this.name);
			Thread.sleep(1000);
		}
		System.out.println("--------------------");
	}
	
}
