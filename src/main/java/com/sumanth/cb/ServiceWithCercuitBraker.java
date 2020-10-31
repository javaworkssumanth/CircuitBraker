package com.sumanth.cb;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;

public class ServiceWithCercuitBraker {
	// Create a custom configuration for a CircuitBreaker
	static CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().failureRateThreshold(50)
			.waitDurationInOpenState(Duration.ofMillis(10000)).permittedNumberOfCallsInHalfOpenState(2)
			.slidingWindowSize(4).recordExceptions(IOException.class, RuntimeException.class).build();

	// Create a CircuitBreakerRegistry with a custom global configuration
	static CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);

	static CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("name");

	public static void main(String args[]) {
		SomeService backendService = new SomeServiceImpl();
		Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker,()-> backendService.getData(""));
		for (int i = 0; i < 20; i++) {

			String result = Try.ofSupplier(decoratedSupplier).recover(throwable -> "Hello from Recovery").get();

			System.out.println(result);
			System.out.println(circuitBreaker.getMetrics().getFailureRate());
		}

	}

}
