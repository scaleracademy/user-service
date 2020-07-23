package dev.rakesh.userservice.event;

import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuccesfulRegistrationEvent extends ApplicationEvent {

	@Autowired
	private User registeredUser;

	public SuccesfulRegistrationEvent(User registeredUser) {
		super(registeredUser);
 		this.registeredUser = registeredUser;
	}
}
