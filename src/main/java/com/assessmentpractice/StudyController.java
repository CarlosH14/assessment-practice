package com.assessmentpractice;

import com.assessmentpractice.service.CacheService;
import com.assessmentpractice.service.CollectionService;
import com.assessmentpractice.service.ConcurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ConcurrencyService concurrencyService;

    @GetMapping("/grasp")
    public Map<String, Object> getGraspPrinciples() {
        Map<String, Object> response = new HashMap<>();
        response.put("topic", "GRASP Principles");
        response.put("summary", StudyDocs.GRASP_SUMMARY);
        response.put("principles", StudyDocs.GRASP_PRINCIPLES);
        return response;
    }

    @GetMapping("/collections")
    public Map<String, Object> getCollectionsExamples() {
        Map<String, Object> response = new HashMap<>();
        response.put("topic", "Java Collections Framework");
        response.put("lists", collectionService.getListExamples());
        response.put("sets", collectionService.getSetExamples());
        response.put("maps", collectionService.getMapExamples());
        return response;
    }

    @GetMapping("/exceptions")
    public Map<String, Object> getExceptionsInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("topic", "Exception Handling");
        response.put("summary", StudyDocs.EXCEPTIONS_SUMMARY);
        response.put("best_practices", StudyDocs.EXCEPTION_BEST_PRACTICES);
        return response;
    }

    @GetMapping("/db/procs")
    public Map<String, Object> getStoredProcedures() {
        Map<String, Object> response = new HashMap<>();
        response.put("topic", "Stored Procedures");
        response.put("example_sql", StudyDocs.STORED_PROC_SQL);
        response.put("optimization_notes", StudyDocs.DB_OPTIMIZATION_NOTES);
        return response;
    }

    @GetMapping("/db/transaction-demo")
    public Map<String, Object> getTransactionDemo() {
        Map<String, Object> response = new HashMap<>();
        response.put("topic", "Transaction Management");
        response.put("note", "To enable full transaction demos, configure a persistent database (PostgreSQL) and create a service with @Transactional methods.");
        response.put("example", "Use @Transactional annotation on service methods to ensure ACID properties");
        response.put("isolation_levels", new String[]{
            "READ_UNCOMMITTED", "READ_COMMITTED", "REPEATABLE_READ", "SERIALIZABLE"
        });
        response.put("propagation_types", new String[]{
            "REQUIRED", "REQUIRES_NEW", "SUPPORTS", "NOT_SUPPORTED", "MANDATORY", "NEVER", "NESTED"
        });
        return response;
    }

    @GetMapping("/concurrency/exec/async")
    public CompletableFuture<Map<String, Object>> getConcurrencyAsync() {
        return concurrencyService.asyncExample();
    }

    @GetMapping("/concurrency/executor")
    public Map<String, Object> getExecutorDemo() {
        return concurrencyService.executorServiceDemo();
    }

    @PostMapping("/cache/put")
    public Map<String, Object> cachePut(@RequestParam String key, @RequestParam String value) {
        cacheService.put(key, value);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Value cached for key: " + key);
        return response;
    }

    @GetMapping("/cache/get")
    public Map<String, Object> cacheGet(@RequestParam String key) {
        String value = cacheService.get(key);
        Map<String, Object> response = new HashMap<>();
        response.put("key", key);
        response.put("value", value);
        response.put("found", value != null);
        return response;
    }

    @GetMapping("/cloud/aws-s3-demo")
    public Map<String, Object> getAwsS3Demo() {
        Map<String, Object> response = new HashMap<>();
        response.put("topic", "AWS S3 Integration");
        response.put("guide", StudyDocs.AWS_S3_GUIDE);
        response.put("note", "To enable S3 integration, add AWS SDK dependencies and configure credentials");
        return response;
    }
}
