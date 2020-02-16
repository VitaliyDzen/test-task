package task.security.service;

import static task.constants.ErrorMessage.EMAIL_DISABLED;
import static task.constants.ErrorMessage.INVALID_CREDENTIALS;
import static task.constants.ErrorMessage.USER_NOT_FOUND_WITH_EMAIL;

import java.util.ArrayList;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.dto.user.UserSaveDto;
import task.entity.User;
import task.repository.UserRepository;
import task.security.jwt.JwtTokenUtil;
import task.security.model.JwtRequest;
import task.security.model.JwtResponse;

@Service
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SecurityService(UserRepository userRepository,
        @Lazy PasswordEncoder passwordEncoder, ModelMapper modelMapper,
        @Lazy AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
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
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest)
        throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception(EMAIL_DISABLED, e);
        } catch (BadCredentialsException e) {
            throw new Exception(INVALID_CREDENTIALS, e);
        }
    }
}