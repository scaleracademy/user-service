package dev.naman.userservice.service;

import dev.naman.userservice.dto.UserDto;
import dev.naman.userservice.event.SuccessfulPassWordResetEvent;
import dev.naman.userservice.event.SuccessfulRegistrationEvent;
import dev.naman.userservice.model.PasswordResetToken;
import dev.naman.userservice.model.User;
import dev.naman.userservice.model.VerificationToken;
import dev.naman.userservice.repository.PasswordResetTokenRepository;
import dev.naman.userservice.repository.UserRepository;
import dev.naman.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public User registerUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()) != null) {
            // TODO: throw Exception
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // TODO: Encrypt the Password

        User savedUser = userRepository.save(user);

        applicationEventPublisher.publishEvent(
                new SuccessfulRegistrationEvent(savedUser)
        );

        return savedUser;
    }

    @Override
    public User resetPassWord(User savedUser) {
        // TODO: Generate token for password reset.
        applicationEventPublisher.publishEvent(
                new SuccessfulPassWordResetEvent(savedUser)
        );

        savedUser = userRepository.findById(savedUser.getId()).get();
        return savedUser;
    }

    @Override
    public User setPassWord(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        // Token is not present
        if(passwordResetToken == null)
        {
            return null ;
        }
        else if(passwordResetToken.getExpiryTime().getTime()-new Date().getTime() > 0 )
        {
            User verifiedUser = passwordResetToken.getUser();

            // update password
            verifiedUser.setPassword(passwordEncoder.encode(newPassword));

            //save to the DB.
            userRepository.save(verifiedUser);

            // delete token.
            passwordResetTokenRepository.delete(passwordResetToken);

            return verifiedUser;


        } else
        {
            return  null;
        }

    }

    @Override
    public User validateUser(String token) {
        // TODO: Check token repo if there is that token
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken.isEmpty()) {
            return null;
        }

        if(verificationToken.get().getExpiryTime().getTime() - new Date().getTime() > 0) {
            // the token is not yes expired

            User verifiedUser = verificationToken.get().getUser();
            verifiedUser.setActive(true);

            userRepository.save(verifiedUser);

            verificationTokenRepository.delete(verificationToken.get());

            return verifiedUser;

        } else {
            return null;
        }



    }
}
