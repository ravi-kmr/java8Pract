package practice.javaConcurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
	  Map<String,String> map = new HashMap<>();
	    private StampedLock lock = new StampedLock();
	 
	    public void put(String key, String value){
	        long stamp = lock.writeLock();
	        try {
	            map.put(key, value);
	        } finally {
	            lock.unlockWrite(stamp);
	        }
	    }
	 
	    public String get(String key) throws InterruptedException {
	        long stamp = lock.readLock();
	        try {
	            return map.get(key);
	        } finally {
	            lock.unlockRead(stamp);
	        }
	    }
	}