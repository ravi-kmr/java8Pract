package practice.javaConcurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		
		Thread t = new Thread(() -> System.out.println("1"));
		t.start();
		
		ExecutorService e = Executors.newCachedThreadPool();
		System.out.println(e.submit(() -> 5).get().toString());

		
	}

}
