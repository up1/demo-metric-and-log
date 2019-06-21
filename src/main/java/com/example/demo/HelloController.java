package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final MeterRegistry meterRegistry;

    @Autowired
    public HelloController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/hello")
    public String hello() {

        Counter counter = Counter
                .builder("hello-springboot")
                .description("Success case")
                .tag("result", "success")
                .register(meterRegistry);
        counter.increment();


        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warn message");
        logger.error("error message");
        return "Hello RESTFul API from Spring boot";
    }

    @GetMapping("/hello-error")
    public String error() {
        Counter counter = Counter
                .builder("hello-springboot")
                .description("Success case")
                .tag("result", "error")
                .register(meterRegistry);
        counter.increment();
        try {
            throw new Exception("Error");
        } catch (Exception e) {
            logger.error("error message with stack trace ", e);
        }
        return "Error";
    }

}
