package com.mycompany.app.customer;

import com.mycompany.app.clients.fraud.FraudCheckResponse;
import com.mycompany.app.clients.fraud.FraudClient;
import com.mycompany.app.clients.notification.NotificationClient;
import com.mycompany.app.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	private final RestTemplate restTemplate;
	
	private final FraudClient fraudClient;
	private final NotificationClient notificationClient;
	
	public void registerCustomer(CustomerRegistrationRequest request) {
		Customer customer = Customer.builder()
				.firstname(request.firstName())
				.lastname(request.lastName())
				.email(request.email())
				.build();
		customerRepository.saveAndFlush(customer);
		
		
		
		FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

		if(fraudCheckResponse.isFraudster()){
			throw new IllegalStateException("fraudster");
		}
		
		notificationClient.sendNotification(
				new NotificationRequest(
						customer.getId(),
						customer.getEmail(),
						String.format("Hi %s, welcome to krishnan...", customer.getFirstname())
				)
		);
		
	
	}
}
