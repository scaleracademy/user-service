package dev.rakesh.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name = "token")
public class VerificationToken {

	private static final int VALIDITY_TIME = 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String token;

	@OneToOne(targetEntity = User.class)
	private User user;

	private Date expiryTime;

	public VerificationToken(User user) {

		this.token = generateRandomString();
		this.user = user;
		this.expiryTime = calculateExpiryTime();
	}

	public void updateToken() {
		this.token = generateRandomString();
		this.expiryTime = calculateExpiryTime();
	}

	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	private Date calculateExpiryTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, VALIDITY_TIME);

		return calendar.getTime();
	}

}
