package com.example.API.service;

import com.example.API.dto.request.AuthRequest;
import com.example.API.dto.request.IntrospectRequest;
import com.example.API.dto.request.LogoutRequest;
import com.example.API.dto.response.AuthResponse;
import com.example.API.dto.response.IntrospectResponse;
import com.example.API.entity.User;
import com.example.API.exception.AppException;
import com.example.API.exception.ErrorCode;
import com.example.API.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY =
            "i79vwtl1y14gnrzivwklqemp7u82k02m";

    Set<String> blacklistedTokens = new HashSet<>();
    Set<String> activeTokens = new HashSet<>();
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }


    public AuthResponse authenticate(AuthRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(),
                user.getPassword());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTIC);

        String  token = generateToken(user);
        activeTokens.add(token);
        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("deviate.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            System.err.println("Cannot generate token: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void logout(String token) {
        if (activeTokens.contains(token)) {
            blacklistedTokens.add(token);
            activeTokens.remove(token);
            System.out.println("Token đã được đánh dấu là không hợp lệ: " + token);
        } else {
            System.out.println("Token không hợp lệ hoặc đã được đăng xuất.");
        }
    }


    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }
}
