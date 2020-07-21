package dev.naman.userservice.event;

import dev.naman.userservice.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResetPasswordEvent extends ApplicationEvent {

    private final User registeredUser;

    public ResetPasswordEvent(User registeredUser) {
        super(registeredUser);
        this.registeredUser = registeredUser;
    }
}
