package dev.naman.userservice.event.listener;

import dev.naman.userservice.event.SuccessfulRegistrationEvent;
import dev.naman.userservice.model.PasswordResetToken;
import dev.naman.userservice.model.User;
import dev.naman.userservice.model.VerificationToken;
import dev.naman.userservice.repository.PasswordResetTokenRepository;
import dev.naman.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class SuccessfulPasswordResetEventLister implements ApplicationListener<SuccessfulRegistrationEvent> {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void onApplicationEvent(SuccessfulRegistrationEvent successfulRegistrationEvent) {

        User registeredUser = successfulRegistrationEvent.getRegisteredUser();

        PasswordResetToken token = new PasswordResetToken(registeredUser);

        passwordResetTokenRepository.save(token);

        // TODO: Send email to the user

        // TODO: Allocate resources for the user

    }
}
