package dev.rakesh.userservice.event.consumer;

import dev.rakesh.userservice.event.ForgotPasswordEvent;
import dev.rakesh.userservice.event.SuccesfulRegistrationEvent;
import dev.rakesh.userservice.model.ResetPasswordToken;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.service.ResetPasswordTokenService;
import dev.rakesh.userservice.util.JavaMailSenderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordEventConsumer implements ApplicationListener<ForgotPasswordEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ResetPasswordEventConsumer.class);

	@Autowired
	private ResetPasswordTokenService resetPasswordTokenService;

	@Autowired
	Environment environment;

	@Autowired
	private JavaMailSenderHelper javaMailSenderHelper;

	@Override
	public void onApplicationEvent(ForgotPasswordEvent forgotPasswordEvent) {
		resendPasswordToken(forgotPasswordEvent);
	}

	public void resendPasswordToken(ForgotPasswordEvent event) {

		User user = event.getUser();
		ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);
		resetPasswordTokenService.save(resetPasswordToken);

		String recipientAddress = user.getEmail();
		String subject = "Password Resend Link ";
		String confirmationUrl
				= environment.getProperty("url.base.path") + "/user/reset-password?token=" + resetPasswordToken.getToken();

		logger.info("Sending mail");
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("Please click on the link to Reset Your Password" + "\r\n" +   confirmationUrl);
		email.setFrom(environment.getProperty("spring.mail.username"));

		javaMailSenderHelper.sendMail(email);
		logger.info("Sent Mail");

	}
}
