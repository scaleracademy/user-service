package dev.rakesh.userservice.event.consumer;

import dev.rakesh.userservice.event.ForgotPasswordEvent;
import dev.rakesh.userservice.model.ResetPasswordToken;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordEventConsumer implements ApplicationListener<ForgotPasswordEvent> {

	@Autowired
	private ResetPasswordTokenService resetPasswordTokenService;

	@Override
	public void onApplicationEvent(ForgotPasswordEvent forgotPasswordEvent) {
		User user = forgotPasswordEvent.getUser();
		ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);

		resetPasswordTokenService.save(resetPasswordToken);
	}
}
