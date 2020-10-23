package dev.naman.userservice.service;

import dev.naman.userservice.dto.UserDto;
import dev.naman.userservice.event.SuccessfulRegistrationEvent;
import dev.naman.userservice.model.ResetPasswordToken;
import dev.naman.userservice.model.User;
import dev.naman.userservice.model.VerificationToken;
import dev.naman.userservice.repository.ResetPasswordTokenRepository;
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
    ResetPasswordTokenRepository resetPasswordTokenRepository;
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

    @Override
    public User resetPassword(UserDto userDto) {
        User user=userRepository.findByEmail(userDto.getEmail());
        if(user==null){
            return null;
        }
        ResetPasswordToken resetPasswordToken=new ResetPasswordToken(user);
        resetPasswordTokenRepository.save(resetPasswordToken);
        return user;
    }

    @Override
    public User newPassowrd(String token, UserDto userDto) {
        Optional<ResetPasswordToken>resetPasswordToken=resetPasswordTokenRepository.findByToken(token);
        if(resetPasswordToken.isEmpty()){
            return null;
        }
        if(resetPasswordToken.get().getExpiryTime().getTime()-new Date().getTime()>0){
            User user=resetPasswordToken.get().getUser();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            resetPasswordTokenRepository.delete(resetPasswordToken.get());
            return user;
        }
        else{
            return null;
        }
    }
}
