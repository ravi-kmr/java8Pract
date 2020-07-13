package practice.javaStream;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.Comparator;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.*;


public class CollectorsDemo {
	public static void main(String[] args) {
	List<String> givenList = Arrays.asList("a", "bb", "ccc", "dd");
	//To List
	List<String> resultList = givenList.stream().collect(toList());
	
	//To Collection
	List<String> resultLinkedList = givenList.stream().collect(toCollection(LinkedList::new));

	//Function.identity() is just a shortcut for defining a function that accepts and returns the same value.
	Map<String, Integer> resultMap = givenList.stream().collect(toMap(Function.identity(), String::length));

	//"a bb ccc dd"
	String resultWithSpace = givenList.stream().collect(joining(" "));
	
	//"PRE-a bb ccc dd-POST"
	String resultWithPrePost = givenList.stream().collect(joining(" ", "PRE-", "-POST"));
	
	// Counting is a simple collector that allows simply counting of all Stream elements.
	Long resultCount = givenList.stream().collect(counting());

	/**
	 * SummarizingDouble/Long/Int is a collector that returns a special class containing statistical information about numerical data in a Stream of extracted elements.
	 */
	DoubleSummaryStatistics resultStat = givenList.stream().collect(summarizingDouble(String::length));
	resultStat.getAverage();
	resultStat.getCount();
	resultStat.getMax();
	resultStat.getMin();
	resultStat.getSum();
	
	DoubleSummaryStatistics dstats = DoubleStream.of(5.33d,2.34d,5.32d,2.31d,3.51d).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
	System.out.println("Max:"+dstats.getMax()+", Min:"+dstats.getMin());
	System.out.println("Count:"+dstats.getCount()+", Sum:"+dstats.getSum());
	System.out.println("Average:"+dstats.getAverage());	
	
	//Collectors.maxBy()/minBy()
	Optional<String> resultMaxMin = givenList.stream().collect(maxBy(Comparator.naturalOrder()));

	//Collectors.groupingBy()
	Map<Integer, Set<String>> resultGrouping = givenList.stream().collect(groupingBy(String::length, toSet()));

	// Partitioning By - {false=["a", "bb", "dd"], true=["ccc"]}
	Map<Boolean, List<String>> result = givenList.stream().collect(partitioningBy(s -> s.length() > 2));
	
	}
			
}
