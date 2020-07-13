package practice.javaStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {
	
	//Empty Stream
	Stream<String> streamEmpty = Stream.empty();
	
	public Stream<String> streamOf(List<String> list){
		return list == null || list.isEmpty() ? Stream.empty() : list.stream();
	}
	
	// Stream of collection
	Collection<String> collection = Arrays.asList("a","b","c");
	Stream<String> streamOfCollection = collection.stream();
	
	//Creating an infinite stream is by using the iterate() method
	// 40,42, ---
	Stream<Integer> streamIterated = Stream.iterate(40, n-> n+2).limit(20);
	
	//Stream of string
	IntStream streamOfChars = "abc".chars();
	Stream<String> streamOfString = Pattern.compile(", ").splitAsStream("a, b,c");
	
	
	//Referencing a stream
	Stream<String> stream = Stream.of("a", "b", "c").filter(element -> element.contains("b"));
	Optional<String> anyElement = stream.findAny();
	
	/**
	 * Java 8 stream can't be reused
	 * Optional<String> firstElement = stream.findFirst();
	 * IllegalStateException:
	 */
	
	

}
