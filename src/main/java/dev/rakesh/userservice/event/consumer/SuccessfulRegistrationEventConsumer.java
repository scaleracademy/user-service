package dev.rakesh.userservice.event.consumer;

import dev.rakesh.userservice.event.SuccesfulRegistrationEvent;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SuccessfulRegistrationEventConsumer implements ApplicationListener<SuccesfulRegistrationEvent> {

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Override
	public void onApplicationEvent(SuccesfulRegistrationEvent succesfulRegistrationEvent) {

		User registrationUser = succesfulRegistrationEvent.getRegisteredUser();
		VerificationToken verificationToken = new VerificationToken(registrationUser);

		verificationTokenService.save(verificationToken);
	}
}
