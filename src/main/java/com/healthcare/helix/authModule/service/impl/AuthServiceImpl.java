package com.healthcare.helix.authModule.service.impl;

import com.healthcare.helix.authModule.dto.request.LoginRequest;
import com.healthcare.helix.authModule.dto.request.RefreshTokenRequest;
import com.healthcare.helix.authModule.dto.request.RegisterRequest;
import com.healthcare.helix.authModule.dto.response.LoginResponse;
import com.healthcare.helix.authModule.dto.response.RefreshTokenResponse;
import com.healthcare.helix.authModule.dto.response.RegisterResponse;
import com.healthcare.helix.authModule.entity.RefreshTokenEntity;
import com.healthcare.helix.authModule.entity.User;
import com.healthcare.helix.authModule.repository.RefreshTokenRepository;
import com.healthcare.helix.authModule.repository.UserRepository;
import com.healthcare.helix.authModule.service.AuthService;
import com.healthcare.helix.common.exception.InvalidCredentialsException;
import com.healthcare.helix.common.exception.InvalidRefreshTokenException;
import com.healthcare.helix.common.exception.UserAlreadyExistsException;
import com.healthcare.helix.common.security.JwtService;
import com.healthcare.helix.common.security.UserPrincipal;
import com.healthcare.helix.userModule.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final UserProfileService userProfileService;

    @Transactional
    public RegisterResponse register(RegisterRequest request){
        // Check if email already exists
        if(repo.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }
        // Check if mobile already exists
        if(repo.existsByMobileNumber(request.getMobileNumber())){
            throw new UserAlreadyExistsException("Mobile number already registered:  " + request.getMobileNumber());
        }
        User user = User.builder()
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName()) // optional
                .dateOfBirth(request.getDateOfBirth())//DD-MM-YYYY
                .gender(request.getGender())//"M", "F", "O"
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = repo.save(user);


        // Wiring User Profile Module Service and saving a basic profile in User Module DB
        userProfileService.createBasicProfile(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getMobileNumber(),
                savedUser.getRole().name(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getDateOfBirth(),
                savedUser.getGender()
        );

        return RegisterResponse.builder()
                .userId(savedUser.getId())
                .message("User registered successfully.")
                .build();
    }


    @Transactional /*
    I'm using transactional here, because
    I'm also saving refresh token in DB before sending response top user
    So during save token in DB, if there is error then I
     */
    public LoginResponse login(LoginRequest loginRequest){
        try{
            /* Step 1: User email + password authenticate */
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            /* Step 2: After Authentication successful, finding UserPrincipal */
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            /* Step 3: Generating JWT Token Here */
            String accessToken = jwtService.generateAccessToken(userPrincipal);
            String refreshToken = jwtService.generateRefreshToken(userPrincipal);

            /* Saving Refresh token in DB */
            saveRefreshTokenInDB(refreshToken, userPrincipal.getUserId());

            /* Step 4: Response returning */
            return new LoginResponse(
                    accessToken,
                    refreshToken,
                    "Bearer",
                    900L, // 15 minutes in seconds
                    userPrincipal.getRole(),
                    userPrincipal.getUserId()
            );
        }catch (BadCredentialsException e){
            throw new InvalidCredentialsException("Invalid Username or Password, Please try again.");
        }
    }

    public void saveRefreshTokenInDB(String refreshToken, UUID userId){
        RefreshTokenEntity token = new RefreshTokenEntity();
        token.setToken(refreshToken);
        token.setUserId(userId);

        Date expiry  = jwtService.extractExpiration(refreshToken);
        token.setExpiryDate(expiry.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        refreshTokenRepository.save(token);
    }

    public RefreshTokenResponse refreshTokenFromRequest(RefreshTokenRequest request){
        String refreshToken = request.getRefreshToken();

        /* Checking if refresh token is valid */
        if(!jwtService.isTokenValid(refreshToken)) throw new InvalidRefreshTokenException("Invalid refresh token");

        /* Checking if refresh token exist in DB */
        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));

        /* Checking if refresh token is revoked */
        if(tokenEntity.isRevoked()) throw new InvalidRefreshTokenException("Refresh token has been revoked");

        /* Generating new Access token and returning */
        String username = jwtService.extractUsernameFromToken(refreshToken);
        UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        String accessToken = jwtService.generateAccessToken(user);
        return new RefreshTokenResponse(accessToken);
    }
}
