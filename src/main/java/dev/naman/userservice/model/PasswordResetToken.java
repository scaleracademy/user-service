package dev.naman.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PasswordResetToken {

    private static final int VALIDITY_TIME = 4 * 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class)
    private User user;

    private Date expiryTime;

    public PasswordResetToken()
    {
        super();
    }

    public PasswordResetToken(User user)
    {
        String token = generateRandomUniqueToken();

        this.token = token;
        this.user = user;

        this.expiryTime = calculateExpiryTime();
    }

    public void updateToken() {
        this.token = generateRandomUniqueToken();
        this.expiryTime = calculateExpiryTime();
    }

    private String generateRandomUniqueToken() {
        return UUID.randomUUID().toString();
    }

    private Date calculateExpiryTime() {

        Calendar calendar = Calendar.getInstance();

        Date currentTimeAndDate = new Date();

        calendar.setTimeInMillis(currentTimeAndDate.getTime());
        calendar.add(Calendar.MINUTE, VALIDITY_TIME);

        return calendar.getTime();
    }


}
