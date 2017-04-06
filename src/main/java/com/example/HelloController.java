package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.feature.Feature1;
import com.example.feature.Feature2;

@RestController
public class HelloController {
	private final Feature1 feature1;
	private final Feature2 feature2;

	public HelloController(Feature1 feature1, Feature2 feature2) {
		this.feature1 = feature1;
		this.feature2 = feature2;
	}

	@GetMapping("/")
	Map<String, Object> hello() {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Hello");
		if (feature1.isEnabled()) {
			response.put("feature1", feature1.feature());
		}
		if (feature2.isEnabled()) {
			response.put("feature2", feature2.feature());
		}
		return response;
	}
}
