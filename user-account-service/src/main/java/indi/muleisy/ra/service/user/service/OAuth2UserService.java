package indi.muleisy.ra.service.user.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2UserService implements org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final ClientRegistrationRepository clientRegistrationRepository;

    public OAuth2UserService(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();

        // Custom logic to extract user information
        String clientName = clientRegistration.getClientName();
        Map<String, Object> attributes = oauth2User.getAttributes();

        // Create or update the user in the database
        // For example, extract QQ-specific information
        String openid = (String) attributes.get("openid");
        String nickname = (String) attributes.get("nickname");
        String avatarUrl = (String) attributes.get("figureurl_qq_1");

        // Here you can save the user information in your database

        return new DefaultOAuth2User(
                oauth2User.getAuthorities(),
                oauth2User.getAttributes(),
                "nickname"); // Use nickname or any other attribute as the username
    }
}
