package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.LoginRequest;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.LoginResponse;
import com.michael.socialmedia.dto.response.RegisterResponse;
import com.michael.socialmedia.enums.Role;
import com.michael.socialmedia.enums.TokenType;
import com.michael.socialmedia.exceptions.AccountDetailException;
import com.michael.socialmedia.exceptions.UserNotFoundException;
import com.michael.socialmedia.exceptions.WrongPasswordException;
import com.michael.socialmedia.repository.TokenRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.security.JwtFilter;
import com.michael.socialmedia.service.AuthenticationService;
import com.michael.socialmedia.token.Token;
import com.michael.socialmedia.utils.EmailUtils;
import com.michael.socialmedia.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private  final UserRepository userRepository;

    private final JwtFilter jwtFilter;

    private  final TokenRepository tokenRepository ;



    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        User user = mapToEntity(registerRequest);
        var newUser = userRepository.save(user);
        return Mapper.mapToResponse(newUser);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User appUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user"));

          if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw new WrongPasswordException("wrong password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword());
        String accessToken = jwtFilter.generateAccessToken(appUser);
        String refreshToken = jwtFilter.generateRefreshToken(appUser);
        revokedAllToken(appUser);
        saveUserToken(accessToken,appUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }




    private  void saveUserToken(String accessToken, User appUser) {
        Token token = Token.builder()
                .isRevoked(false)
                .isExpired(false)
                .token(accessToken)
                .user(appUser)
                .build();
        tokenRepository.save(token);

    }

    private void revokedAllToken(User appUser) {
        List <Token> tokenValid = tokenRepository.findAllByValidToken(appUser.getId());
        if(tokenValid.isEmpty()) {
            return;
        }
            tokenValid.forEach(
                    token ->
                    {
                        token.setExpired(true);
                        token.setRevoked(true);
                    });
            tokenRepository.saveAll(tokenValid);
        }


    public  User mapToEntity(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .profilePic(registerRequest.getProfilePic())
                .build();
    }



}
