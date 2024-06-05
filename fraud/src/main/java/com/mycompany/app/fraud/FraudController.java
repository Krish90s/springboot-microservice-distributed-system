package com.mycompany.app.fraud;

import com.mycompany.app.clients.fraud.FraudCheckResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {
	
	private final FraudCheckService fraudCheckService;
	

	@GetMapping(path = "{customerId}")
	public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerID) {
		boolean isFraudulentCheck = fraudCheckService.isFraudulentCustomer(customerID);
		log.info("fraud check request for customer {}", customerID);
		return new FraudCheckResponse(isFraudulentCheck);
	}
}
