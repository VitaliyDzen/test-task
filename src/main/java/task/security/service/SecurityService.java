package task.security.service;

import static task.constants.ErrorMessage.BAD_EMAIL_OR_PASSWORD;
import static task.constants.ErrorMessage.REFRESH_TOKEN_NOT_VALID;
import static task.constants.ErrorMessage.USER_ALREADY_REGISTERED_WITH_THIS_EMAIL;
import static task.constants.ErrorMessage.USER_NOT_FOUND_BY_EMAIL;
import static task.constants.ErrorMessage.USER_NOT_FOUND_WITH_EMAIL;

import io.jsonwebtoken.ExpiredJwtException;
import java.util.ArrayList;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.dto.user.UserSaveDto;
import task.entity.User;
import task.exception.BadEmailOrPasswordException;
import task.exception.BadRefreshTokenException;
import task.exception.UserAlreadyRegisteredException;
import task.repository.UserRepository;
import task.security.dto.AccessRefreshTokensDto;
import task.security.dto.SignInDto;
import task.security.dto.SuccessSignInDto;
import task.security.jwt.JwtTool;

@Service
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTool jwtTool;

    @Autowired
    public SecurityService(UserRepository userRepository,
        @Lazy PasswordEncoder passwordEncoder, ModelMapper modelMapper, JwtTool jwtTool) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtTool = jwtTool;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!(user.isPresent())) {
            throw new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email);
        }
        return new org.springframework.security.core.userdetails.User(
            user.get().getEmail(),
            user.get().getPassword(),
            new ArrayList<>());
    }

    public User saveUser(UserSaveDto userDto) {
        if (!(userRepository.findByEmail(userDto.getEmail()).isPresent())) {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRefreshTokenKey(jwtTool.generateTokenKey());
            return userRepository.save(user);
        } else {
            throw new UserAlreadyRegisteredException(USER_ALREADY_REGISTERED_WITH_THIS_EMAIL);
        }
    }

    public SuccessSignInDto signIn(SignInDto signInDto) {
        User user = userRepository
            .findByEmail(signInDto.getEmail())
            .filter(u -> isPasswordCorrect(signInDto, u))
            .orElseThrow(() -> new BadEmailOrPasswordException(BAD_EMAIL_OR_PASSWORD));
        String accessToken = jwtTool.createAccessToken(user.getEmail());
        String refreshToken = jwtTool.createRefreshToken(user);
        return new SuccessSignInDto(user.getId(), accessToken, refreshToken, user.getName());
    }

    private boolean isPasswordCorrect(SignInDto signInDto, User user) {
        if (user.getPassword().isEmpty()) {
            return false;
        }
        return passwordEncoder.matches(signInDto.getPassword(), user.getPassword());
    }

    @Transactional
    public AccessRefreshTokensDto updateAccessTokens(String refreshToken) {
        String email;
        try {
            email = jwtTool.getEmailOutOfAccessToken(refreshToken);
        } catch (ExpiredJwtException | ArrayIndexOutOfBoundsException e) {
            throw new BadRefreshTokenException(REFRESH_TOKEN_NOT_VALID + e);
        }
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new BadEmailOrPasswordException(USER_NOT_FOUND_BY_EMAIL + email));
        String newRefreshTokenKey = jwtTool.generateTokenKey();
        userRepository.updateUserRefreshToken(newRefreshTokenKey, user.getId());
        if (jwtTool.isTokenValid(refreshToken, user.getRefreshTokenKey())) {
            user.setRefreshTokenKey(newRefreshTokenKey);
            return new AccessRefreshTokensDto(
                jwtTool.createAccessToken(user.getEmail()),
                jwtTool.createRefreshToken(user)
            );
        }
        throw new BadRefreshTokenException(REFRESH_TOKEN_NOT_VALID);
    }

}