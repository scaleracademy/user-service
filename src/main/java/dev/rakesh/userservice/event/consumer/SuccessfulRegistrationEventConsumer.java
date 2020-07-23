package dev.rakesh.userservice.event.consumer;

import dev.rakesh.userservice.event.SuccesfulRegistrationEvent;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.service.VerificationTokenService;
import dev.rakesh.userservice.util.JavaMailSenderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SuccessfulRegistrationEventConsumer implements ApplicationListener<SuccesfulRegistrationEvent> {

	private static final Logger logger = LoggerFactory.getLogger(SuccessfulRegistrationEventConsumer.class);

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	Environment environment;

	@Autowired
	private JavaMailSenderHelper javaMailSenderHelper;

	@Override
	public void onApplicationEvent(SuccesfulRegistrationEvent succesfulRegistrationEvent) {
		confirmRegistration(succesfulRegistrationEvent);
	}

	public void confirmRegistration(SuccesfulRegistrationEvent event) {

		User registrationUser = event.getRegisteredUser();
		VerificationToken verificationToken = new VerificationToken(registrationUser);
		verificationTokenService.save(verificationToken);

		String recipientAddress = registrationUser.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl
				= environment.getProperty("url.base.path") + "/user/verify?token=" + verificationToken.getToken();

		logger.info("sending mail");
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("Please click on the link to Verify Your Account" + "\r\n" +   confirmationUrl);
		email.setFrom(environment.getProperty("spring.mail.username"));
		javaMailSenderHelper.sendMail(email);
		logger.info("Sent Mail");
	}
}
