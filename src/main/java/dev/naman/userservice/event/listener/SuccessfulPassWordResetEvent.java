package dev.naman.userservice.event.listener;

import dev.naman.userservice.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuccessfulPassWordResetEvent extends ApplicationEvent {

    private final User registeredUser;

    public SuccessfulPassWordResetEvent(User registeredUser) {
        super(registeredUser);
        this.registeredUser = registeredUser;
    }
}

