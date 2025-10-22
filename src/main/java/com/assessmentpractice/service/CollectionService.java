package com.assessmentpractice.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CollectionService {

    public Map<String, Object> getListExamples() {
        Map<String, Object> examples = new HashMap<>();
        
        // ArrayList
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Fast random access");
        arrayList.add("O(1) get operation");
        arrayList.add("Good for iteration");
        examples.put("ArrayList", Map.of(
            "description", "Dynamic array, fast random access, not thread-safe",
            "use_case", "General-purpose list when you need fast access by index",
            "example", arrayList
        ));

        // LinkedList
        List<String> linkedList = new LinkedList<>();
        linkedList.add("Fast insertion/deletion at ends");
        linkedList.add("O(1) add/remove at head/tail");
        linkedList.add("Can be used as Queue or Deque");
        examples.put("LinkedList", Map.of(
            "description", "Doubly-linked list, fast insertions/deletions, not thread-safe",
            "use_case", "When you frequently add/remove elements at beginning or end",
            "example", linkedList
        ));

        // CopyOnWriteArrayList
        List<String> cowList = new CopyOnWriteArrayList<>();
        cowList.add("Thread-safe");
        cowList.add("Best for read-heavy scenarios");
        cowList.add("Copy on modification");
        examples.put("CopyOnWriteArrayList", Map.of(
            "description", "Thread-safe variant, creates copy on modification",
            "use_case", "Concurrent reads with rare modifications",
            "example", cowList
        ));

        return examples;
    }

    public Map<String, Object> getSetExamples() {
        Map<String, Object> examples = new HashMap<>();

        // HashSet
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Fast operations O(1)");
        hashSet.add("No ordering guarantee");
        hashSet.add("Allows null");
        examples.put("HashSet", Map.of(
            "description", "Hash table based, no ordering, allows null, not thread-safe",
            "use_case", "When you need fast lookup and don't care about order",
            "example", hashSet
        ));

        // LinkedHashSet
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("First element");
        linkedHashSet.add("Second element");
        linkedHashSet.add("Third element");
        examples.put("LinkedHashSet", Map.of(
            "description", "Maintains insertion order, slightly slower than HashSet",
            "use_case", "When you need predictable iteration order",
            "example", linkedHashSet
        ));

        // TreeSet
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("C - Third");
        treeSet.add("A - First");
        treeSet.add("B - Second");
        examples.put("TreeSet", Map.of(
            "description", "Red-black tree, sorted order, O(log n) operations",
            "use_case", "When you need elements in sorted order",
            "example", treeSet
        ));

        // ConcurrentSkipListSet
        Set<String> skipListSet = new ConcurrentSkipListSet<>();
        skipListSet.add("Thread-safe sorted set");
        skipListSet.add("Based on skip list");
        skipListSet.add("Good concurrent performance");
        examples.put("ConcurrentSkipListSet", Map.of(
            "description", "Thread-safe sorted set, based on skip list",
            "use_case", "Concurrent access with sorted order requirement",
            "example", skipListSet
        ));

        return examples;
    }

    public Map<String, Object> getMapExamples() {
        Map<String, Object> examples = new HashMap<>();

        // HashMap
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("performance", "O(1) average case");
        hashMap.put("ordering", "No guarantee");
        hashMap.put("null_keys", "Allows one null key");
        examples.put("HashMap", Map.of(
            "description", "Hash table based, fast, allows null key/values, not thread-safe",
            "use_case", "General-purpose map when thread safety not needed",
            "example", hashMap
        ));

        // LinkedHashMap
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("first", "Insertion order maintained");
        linkedHashMap.put("second", "Slightly slower than HashMap");
        linkedHashMap.put("third", "Can be used as LRU cache");
        examples.put("LinkedHashMap", Map.of(
            "description", "Maintains insertion/access order",
            "use_case", "When iteration order matters or implementing LRU cache",
            "example", linkedHashMap
        ));

        // TreeMap
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("c_third", "Sorted by key");
        treeMap.put("a_first", "Red-black tree");
        treeMap.put("b_second", "O(log n) operations");
        examples.put("TreeMap", Map.of(
            "description", "Red-black tree, sorted by keys, O(log n) operations",
            "use_case", "When you need keys in sorted order",
            "example", treeMap
        ));

        // ConcurrentHashMap
        Map<String, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("thread_safety", "Lock striping for high concurrency");
        concurrentMap.put("performance", "Better than synchronized map");
        concurrentMap.put("null", "Does not allow null keys or values");
        examples.put("ConcurrentHashMap", Map.of(
            "description", "Thread-safe, high performance for concurrent access",
            "use_case", "Multi-threaded applications requiring shared map",
            "example", concurrentMap
        ));

        return examples;
    }
}
