package com.sanket.blogsapi.security.oauth;

import com.sanket.blogsapi.users.AuthProvider;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersRepository;
import com.sanket.blogsapi.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service(value = "customOAuth2UserService")
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersService usersService;
    private final UsersRepository usersRepository;

    @Autowired
    public CustomOAuth2UserService(UsersService usersService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("loadUser");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Optional<UserEntity> userOptional = usersRepository.findByEmail(oAuth2User.getAttribute("email").toString());
        UserEntity savedUser;

        String email = oAuth2User.getAttribute("email").toString();
        String name = oAuth2User.getAttribute("name").toString();
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String profilePic = oAuth2User.getAttribute("picture").toString();

        UserEntity user;
        if (!userOptional.isPresent()) {
            // create new user
            user = usersService.createOAuthUser(
                    email,
                    name,
                    provider,
                    profilePic
            );
        } else {
            // update user
            user = userOptional.get();
            user.setEmail(email);
            user.setName(name);
            user.setAuthProvider(AuthProvider.valueOf(provider));
            user.setImageUrl(profilePic);
        }
        savedUser = usersRepository.save(user);
        return new OAuth2User() {
            @Override
            public <A> A getAttribute(String name) {
                return oAuth2User.getAttribute(name);
            }

            @Override
            public Map<String, Object> getAttributes() {
                return oAuth2User.getAttributes();
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Set.of(new SimpleGrantedAuthority("ROLE_USER"));
            }

            @Override
            public String getName() {
                return savedUser.getEmail();
            }
        };
    }
}
