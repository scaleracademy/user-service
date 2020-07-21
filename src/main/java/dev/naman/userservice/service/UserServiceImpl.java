package dev.naman.userservice.service;

import dev.naman.userservice.dto.ResetPasswordDto;
import dev.naman.userservice.dto.UserDto;
import dev.naman.userservice.event.ResetPasswordEvent;
import dev.naman.userservice.event.SuccessfulRegistrationEvent;
import dev.naman.userservice.exception.DuplicateEmailException;
import dev.naman.userservice.exception.IncorrectTokenException;
import dev.naman.userservice.exception.UnregisteredUserException;
import dev.naman.userservice.exception.UnverifiedUserException;
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
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public User registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new DuplicateEmailException();
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

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

        if (verificationToken.isEmpty()) {
            return null;
        }

        if (verificationToken.get().getExpiryTime().getTime() - new Date().getTime() > 0) {
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

        if (userRepository.findByEmail(userDto.getEmail()) == null) {
            throw new UnregisteredUserException();
        } else if (userRepository.findByEmail(userDto.getEmail()).isActive() == false) {
            throw new UnverifiedUserException();
        } else {
            User user = userRepository.findByEmail(userDto.getEmail());
            applicationEventPublisher.publishEvent(
                    new ResetPasswordEvent(user)
            );
            return user;

        }

    }

    @Override
    public User newPassword(ResetPasswordDto resetPasswordDto) {

        if(passwordResetTokenRepository.findByToken(resetPasswordDto.getToken()) == null) {
            throw new IncorrectTokenException();
        }
        else if (passwordResetTokenRepository.findByToken(resetPasswordDto.getToken()).getUser().getEmail()
                != userRepository.findByEmail(resetPasswordDto.getEmail()).getEmail()) {
            throw new IncorrectTokenException();
        }
        else {
            User user = userRepository.findByEmail(resetPasswordDto.getEmail());
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
            userRepository.save(user);
            passwordResetTokenRepository.delete(passwordResetTokenRepository.findByToken(resetPasswordDto.getToken()));
            return user;
        }
    }

}

