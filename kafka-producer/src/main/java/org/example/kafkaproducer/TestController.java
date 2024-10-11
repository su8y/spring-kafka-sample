package org.example.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	TestProducer testProducer;

	@GetMapping("test/{event}")
	public ResponseEntity testCreate(@PathVariable(name = "event") String event) {
		try {
			testProducer.create(event);
			return ResponseEntity.ok(String.format("SUCCESS SEND MESSAGE [%s]", event));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("SERVER ERROR");
		}
	}

}
