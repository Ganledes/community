package com.carl.community.controller;

import com.carl.community.dto.AccessTokenDTO;
import com.carl.community.dto.GitHubUser;
import com.carl.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhaoq
 */
@Controller
public class AuthorizeController {

    private final GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirectURI}")
    private String redirectURI;

    public AuthorizeController(GitHubProvider gitHubProvider) {
        this.gitHubProvider = gitHubProvider;
    }

    @GetMapping("callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectURI);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        return "index";
    }
}
