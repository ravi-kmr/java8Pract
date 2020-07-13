package practice.javaConcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
	
	private List<Integer> list = new ArrayList<>();
	private final int LIMIT = 5;
	private final int BOTTOM = 0;
	
	Lock lock = new ReentrantLock();
	
	public void producer(Integer value) throws Exception{
		synchronized(lock) {
			while(true) {
				if(list.size() == LIMIT) {
					System.out.println("REMOVING");
					lock.wait();
				}else {
					System.out.println("Adding");
					list.add(value);
					lock.notify();
				}
			}
		}
	}
	public void consumer(Integer value) throws Exception{
		synchronized(lock) {
			while(true) {
				if(list.size() == BOTTOM) {
					System.out.println("Adding");
					lock.wait();
				}else {
					System.out.println("Removing");
					list.remove(value);
					lock.notify();
				}
			}
		}
	}
}
