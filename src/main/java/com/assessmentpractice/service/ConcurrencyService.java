package com.assessmentpractice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class ConcurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrencyService.class);

    /**
     * Demonstrates ExecutorService usage with thread pool
     */
    public Map<String, Object> executorServiceDemo() {
        Map<String, Object> result = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        try {
            // Submit multiple tasks
            Future<String> task1 = executor.submit(() -> {
                Thread.sleep(100);
                return "Task 1 completed";
            });
            
            Future<String> task2 = executor.submit(() -> {
                Thread.sleep(200);
                return "Task 2 completed";
            });
            
            Future<String> task3 = executor.submit(() -> {
                Thread.sleep(150);
                return "Task 3 completed";
            });
            
            // Wait for results
            result.put("task1", task1.get(5, TimeUnit.SECONDS));
            result.put("task2", task2.get(5, TimeUnit.SECONDS));
            result.put("task3", task3.get(5, TimeUnit.SECONDS));
            result.put("status", "All tasks completed successfully");
            
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("Error in executor demo", e);
            result.put("error", e.getMessage());
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        return result;
    }

    /**
     * Demonstrates Spring's @Async annotation for asynchronous execution
     */
    @Async
    public CompletableFuture<Map<String, Object>> asyncExample() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            logger.info("Async method started on thread: {}", Thread.currentThread().getName());
            
            // Simulate some work
            Thread.sleep(1000);
            
            result.put("message", "Async operation completed");
            result.put("thread", Thread.currentThread().getName());
            result.put("timestamp", System.currentTimeMillis());
            
            logger.info("Async method completed");
            
        } catch (InterruptedException e) {
            logger.error("Async operation interrupted", e);
            Thread.currentThread().interrupt();
            result.put("error", "Operation interrupted");
        }
        
        return CompletableFuture.completedFuture(result);
    }

    /**
     * WARNING: This method demonstrates a potential deadlock scenario for study purposes.
     * DO NOT call this method in production code!
     * 
     * This shows how deadlocks can occur when two threads acquire locks in different orders.
     */
    public void deadlockExample() {
        // WARNING: For study purposes only - demonstrates deadlock
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                logger.info("Thread 1: Holding lock 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) { }
                logger.info("Thread 1: Waiting for lock 2...");
                synchronized (lock2) {
                    logger.info("Thread 1: Acquired both locks");
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                logger.info("Thread 2: Holding lock 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) { }
                logger.info("Thread 2: Waiting for lock 1...");
                synchronized (lock1) {
                    logger.info("Thread 2: Acquired both locks");
                }
            }
        });
        
        // Note: Starting these threads will cause a deadlock - for study only!
        // thread1.start();
        // thread2.start();
        
        logger.warn("Deadlock example method called - threads not started to prevent actual deadlock");
    }

    /**
     * Demonstrates CompletableFuture for async composition
     */
    public Map<String, Object> completableFutureDemo() {
        Map<String, Object> result = new HashMap<>();
        
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Step 1";
        });
        
        CompletableFuture<String> future2 = future1.thenApply(s -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return s + " -> Step 2";
        });
        
        CompletableFuture<String> future3 = future2.thenApply(s -> s + " -> Step 3");
        
        try {
            result.put("result", future3.get(5, TimeUnit.SECONDS));
            result.put("status", "Completed");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("CompletableFuture demo error", e);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
}
