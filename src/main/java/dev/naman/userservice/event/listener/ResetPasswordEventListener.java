package dev.naman.userservice.event.listener;

import dev.naman.userservice.event.ResetPasswordEvent;
import dev.naman.userservice.model.PasswordResetToken;
import dev.naman.userservice.model.User;
import dev.naman.userservice.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordEventListener implements ApplicationListener<ResetPasswordEvent> {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public void onApplicationEvent(ResetPasswordEvent resetPasswordEvent) {

        User registeredUser = resetPasswordEvent.getRegisteredUser();
        PasswordResetToken token = new PasswordResetToken((registeredUser));
        passwordResetTokenRepository.save(token);

        //TODO: Send email with this token.
    }
}
