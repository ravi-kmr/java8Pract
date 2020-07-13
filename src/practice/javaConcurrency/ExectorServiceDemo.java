package practice.javaConcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.naming.spi.DirStateFactory.Result;

public class ExectorServiceDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
	ExecutorService executor = Executors.newFixedThreadPool(10);

	Runnable runnableTask=() -> {
		try{TimeUnit.MILLISECONDS.sleep(300);
		System.out.println("Inside Runnable");
		}catch(InterruptedException e){
		e.printStackTrace();
		}
	};

	Callable<String> callableTask = () -> {
		TimeUnit.MILLISECONDS.sleep(300);
		return "Task's Execution";
	};

	List<Callable<String>> callableList = new ArrayList<>();
	callableList.add(callableTask);
	callableList.add(callableTask);
	callableList.add(callableTask);

	// execute() method is void
	executor.execute(runnableTask);

	// submit() submits a Callable or a Runnable task to an ExecutorService
	Future<String> future = executor.submit(callableTask);

	try{
		String result = future.get();
		System.out.println(result);
	}catch(InterruptedException|ExecutionException ex){
		ex.printStackTrace();
	}
	
		/*
		 * CompletableFuture<?> cf = CompletableFuture.runAsync(runnableTask,executor);
		 * cf.whenComplete((res, failure) -> { System.out.println("Via CF"+res); });
		 */
	// invokeAny() assigns a collection of tasks to an ExecutorService
	String result1 = executor.invokeAny(callableList);
	System.out.println(result1);
	// invokeAll() assigns a collection of tasks to an ExecutorService
	List<Future<String>> futures = executor.invokeAll(callableList);
	for ( Future<String> f: futures) {
		System.out.println(f.get());
	}
	
	
	/**
	 * executorService.shutdown(); -method returns a list of tasks that are waiting
	 * to be processed List<Runnable> notExecutedTasks =
	 * executorService.shutDownNow();
	 */

	executor.shutdown();
	try{
		if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) executor.shutdownNow();
	}catch(InterruptedException ie){
		executor.shutdownNow();
	}
}
	}
