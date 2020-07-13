package practice.javaConcurrency;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompletableFutureTest {
	
	CompletableFuture<String> completableFuture;
 
	@BeforeEach
	void createCompletableFuture() {
		completableFuture = new CompletableFuture<>();
	}
	
	@Test
	void testComplete() {	
		boolean result = completableFuture.complete("Future result value");
		assertTrue(result);
		assertTrue(completableFuture.isDone());
		assertEquals("Future result value", completableFuture.getNow(null));
	}
	
	@Test
	void testCompleteableFutureAsSimpleFuture() throws InterruptedException, ExecutionException {	
		// CompletableFuture<String> completableFuture = new CompletableFuture<>();
	 
	    Executors.newCachedThreadPool().submit(() -> {
	        Thread.sleep(500);
	        completableFuture.complete("Hello");
	        return null;
	    });
	    
	    String result = completableFuture.get();
	    assertEquals("Hello",result);
	}
	
	@Test
	void testCFCancel() throws InterruptedException, ExecutionException {
		Executors.newCachedThreadPool().submit(() ->{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			completableFuture.cancel(false);
		});
		assertTrue("Was not cancelled", completableFuture.cancel(true));
		assertTrue("Was not completed exceptionally", completableFuture.isCompletedExceptionally());		
	}
	
	@Test
	void testTwoCF() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future1 = future.thenApply(s -> s + " World");
		assertEquals("Hello World", future1.get());
	}
	
	/**
	 * The thenCompose method together with thenApply implement basic building blocks of the monadic pattern.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	void combiningFuture() throws InterruptedException, ExecutionException {
	completableFuture = CompletableFuture.supplyAsync(() -> "Hello").thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
	assertEquals("Hello World", completableFuture.get());
	}
	
	@Test
	void runningMultipleFuture() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");
		CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
		combinedFuture.get();
		assertTrue(future1.isDone());
		assertTrue(future2.isDone());
		assertTrue(future3.isDone());
	}
	
	@Test
	void handlingException() throws InterruptedException, ExecutionException {
		String name = null;
		
		CompletableFuture<String> completableFuture =  CompletableFuture.supplyAsync(() -> {
		      if (name == null) {
		          throw new RuntimeException("Computation error!");
		      }
		      return "Hello, " + name;
		  }).handle((s, t) -> s != null ? s : "Hello, Stranger!");
		assertEquals("Hello, Stranger!", completableFuture.get());
	}
	

}
