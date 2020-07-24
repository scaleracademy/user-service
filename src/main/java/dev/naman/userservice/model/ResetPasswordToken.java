package dev.naman.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ResetPasswordToken {
    private static final int VALIDITY_TIME=1*60; // in minutes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(targetEntity = User.class)
    private User user;
    private Date expiryTime;
    public ResetPasswordToken(){

    }
    public ResetPasswordToken(User user){
        String token=generateRandomUniqueToken();
        this.token=token;
        this.user=user;
        this.expiryTime=calculateExpiryTime();

    }

    public void updateToken(){
        this.token=generateRandomUniqueToken();
        this.expiryTime=calculateExpiryTime();

    }
    private String generateRandomUniqueToken(){
        return UUID.randomUUID().toString();
    }
    private Date calculateExpiryTime(){
        Calendar calendar= Calendar.getInstance();

        Date currentTimeandDate= new Date();

        calendar.setTimeInMillis(currentTimeandDate.getTime());
        calendar.add(calendar.MINUTE, VALIDITY_TIME);

        return calendar.getTime();
    }
}
