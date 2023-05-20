package com.sanket.blogsapi.services.tokens.sst;

import com.sanket.blogsapi.services.tokens.TokenService;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("userTokenService")
public class UserTokenService implements TokenService {

    private final UsersService usersService;
    private final UserTokenRepository userTokenRepository;

    public UserTokenService(@Autowired UsersService usersService,
                            @Autowired UserTokenRepository userTokenRepository) {
        this.usersService = usersService;
        this.userTokenRepository = userTokenRepository;
    }

    /**
     * Creates a token for the given username
     *
     * @param username username of the user
     * @return token string
     */
    @Override
    public String createAuthToken(String username) {
        UserEntity user = usersService.findByUsername(username);
        // TODO: configure this as environment variable
        // 60 minutes validity
        long tokenExpirationInMillis = 1000L * 60 * 60;
        UserTokenEntity userTokenEntity = UserTokenEntity.builder()
                .user(user)
                .expiresAt(java.util.Date.from(java.time.Instant.now().plusMillis(tokenExpirationInMillis)))
                .build();
        UserTokenEntity savedTokenEntity = userTokenRepository.save(userTokenEntity);
        // the UUID string is the token
        return savedTokenEntity.getId().toString();
    }

    /**
     * Retrieves the username from the token string
     *
     * @param token token string
     * @return username
     */
    @Override
    public String getUsernameFromToken(String token) {
        Optional<UserTokenEntity> userTokenEntity = userTokenRepository.findById(UUID.fromString(token));
        if (userTokenEntity.isPresent()) {
            UserEntity user = userTokenEntity.get().getUser();
            if (user != null) {
                return user.getUsername();
            }
        }
        // TODO: generate security exception if token is not present etc
        // TODO: check expiry as well
        return null;
    }
}
