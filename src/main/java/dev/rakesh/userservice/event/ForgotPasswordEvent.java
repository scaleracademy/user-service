package dev.rakesh.userservice.event;

import dev.rakesh.userservice.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;


@Setter
@Getter
public class ForgotPasswordEvent  extends ApplicationEvent {

	@Autowired
	User user;

	public ForgotPasswordEvent(User user) {
		super(user);
		this.user = user;
	}
}
