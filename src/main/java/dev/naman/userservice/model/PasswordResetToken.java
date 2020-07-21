package dev.naman.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token; // 12u63868237931093808310

    @OneToOne(targetEntity = User.class)
    private User user;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(User user) {
        String token = getUniqueToken();
        this.token = token;
        this.user = user;
    }

    private String getUniqueToken() {
        return UUID.randomUUID().toString();
    }

}
