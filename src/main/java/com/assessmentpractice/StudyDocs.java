package com.assessmentpractice;

import java.util.HashMap;
import java.util.Map;

public class StudyDocs {

    public static final String GRASP_SUMMARY = 
        "GRASP (General Responsibility Assignment Software Patterns) are guidelines for assigning " +
        "responsibilities to classes and objects in object-oriented design.";

    public static final Map<String, String> GRASP_PRINCIPLES = createGraspPrinciples();

    private static Map<String, String> createGraspPrinciples() {
        Map<String, String> principles = new HashMap<>();
        principles.put("Information Expert", 
            "Assign responsibility to the class that has the information needed to fulfill it.");
        principles.put("Creator", 
            "Assign class B the responsibility to create instance of class A if B contains, aggregates, " +
            "records, or closely uses A.");
        principles.put("Controller", 
            "Assign responsibility for handling system events to a controller class that represents " +
            "the overall system or a use case scenario.");
        principles.put("Low Coupling", 
            "Assign responsibilities to minimize dependencies between classes.");
        principles.put("High Cohesion", 
            "Assign responsibilities so that classes have focused, related responsibilities.");
        principles.put("Polymorphism", 
            "Use polymorphic operations to handle alternatives based on type.");
        principles.put("Pure Fabrication", 
            "Assign responsibilities to artificial classes when needed for low coupling and high cohesion.");
        principles.put("Indirection", 
            "Assign responsibility to intermediate object to mediate between components.");
        principles.put("Protected Variations", 
            "Protect elements from variations in other elements by wrapping with stable interface.");
        return principles;
    }

    public static final String EXCEPTIONS_SUMMARY = 
        "Exception handling is a mechanism to handle runtime errors and maintain normal application flow. " +
        "Java provides checked exceptions (must be caught or declared) and unchecked exceptions (RuntimeException).";

    public static final Map<String, String> EXCEPTION_BEST_PRACTICES = createExceptionBestPractices();

    private static Map<String, String> createExceptionBestPractices() {
        Map<String, String> practices = new HashMap<>();
        practices.put("Use Specific Exceptions", 
            "Catch and throw specific exception types rather than generic Exception.");
        practices.put("Don't Swallow Exceptions", 
            "Always log or handle exceptions properly, never catch and ignore.");
        practices.put("Clean Up Resources", 
            "Use try-with-resources or finally blocks to clean up resources.");
        practices.put("Document Exceptions", 
            "Use @throws JavaDoc to document exceptions that methods can throw.");
        practices.put("Create Custom Exceptions", 
            "Create custom exception classes for domain-specific error conditions.");
        practices.put("Don't Use Exceptions for Flow Control", 
            "Exceptions should be exceptional; don't use them for normal control flow.");
        practices.put("Include Context in Messages", 
            "Provide meaningful error messages with context about what went wrong.");
        return practices;
    }

    public static final String STORED_PROC_SQL = 
        "-- Example PostgreSQL stored procedure\n" +
        "CREATE OR REPLACE FUNCTION decrease_stock(\n" +
        "    p_product_id INT,\n" +
        "    p_quantity INT\n" +
        ") RETURNS BOOLEAN AS $$\n" +
        "DECLARE\n" +
        "    current_stock INT;\n" +
        "BEGIN\n" +
        "    -- Lock the row for update\n" +
        "    SELECT stock INTO current_stock\n" +
        "    FROM products\n" +
        "    WHERE product_id = p_product_id\n" +
        "    FOR UPDATE;\n" +
        "\n" +
        "    -- Check if enough stock available\n" +
        "    IF current_stock < p_quantity THEN\n" +
        "        RETURN FALSE;\n" +
        "    END IF;\n" +
        "\n" +
        "    -- Decrease stock\n" +
        "    UPDATE products\n" +
        "    SET stock = stock - p_quantity\n" +
        "    WHERE product_id = p_product_id;\n" +
        "\n" +
        "    RETURN TRUE;\n" +
        "END;\n" +
        "$$ LANGUAGE plpgsql;";

    public static final Map<String, String> DB_OPTIMIZATION_NOTES = createDbOptimizationNotes();

    private static Map<String, String> createDbOptimizationNotes() {
        Map<String, String> notes = new HashMap<>();
        notes.put("Indexing", 
            "Create indexes on frequently queried columns, especially foreign keys and WHERE clause columns.");
        notes.put("Query Optimization", 
            "Use EXPLAIN ANALYZE to understand query execution plans and optimize accordingly.");
        notes.put("Connection Pooling", 
            "Use connection pools (HikariCP) to reuse database connections efficiently.");
        notes.put("Batch Operations", 
            "Use batch inserts/updates to reduce round trips to database.");
        notes.put("N+1 Problem", 
            "Avoid N+1 queries by using JOIN or fetch strategies appropriately.");
        notes.put("Caching", 
            "Cache frequently accessed data using application-level or database-level caching.");
        return notes;
    }

    public static final String AWS_S3_GUIDE = 
        "To integrate AWS S3:\n" +
        "1. Add AWS SDK dependency: software.amazon.awssdk:s3\n" +
        "2. Configure credentials using AWS credential provider chain\n" +
        "3. Create S3Client instance\n" +
        "4. Use PutObjectRequest for uploads\n" +
        "5. Use GetObjectRequest for downloads\n" +
        "6. Consider using pre-signed URLs for temporary access\n" +
        "7. Implement proper error handling and retry logic\n" +
        "8. Use multipart upload for large files\n" +
        "9. Set appropriate bucket policies and IAM permissions\n" +
        "10. Enable versioning and lifecycle policies for production";
}
