package org.mlagdevelopment.messenger.thelostone.server.service;


import org.mlagdevelopment.messenger.thelostone.server.domain.User;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.LoginRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.RegisterRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.UserResponse;
import org.mlagdevelopment.messenger.thelostone.server.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) throws IllegalArgumentException  {
        if(userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException ("Username arleady taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setUiName(request.uiName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getUiName(),
                saved.getCreatedAt()
        );
    }


    @Transactional
    public UserResponse authenticate(LoginRequest request){
        if(!userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException ("Invalid credentials");
        }

        User user = userRepository.findByUsername(request.username()).get();

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");

        } else {
            return new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getUiName(),
                    user.getCreatedAt()
            );
        }


    }


}
