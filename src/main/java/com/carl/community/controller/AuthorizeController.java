package com.carl.community.controller;

import com.carl.community.dto.AccessTokenDTO;
import com.carl.community.dto.GitHubUser;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.User;
import com.carl.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zhaoq
 */
@Controller
public class AuthorizeController {

    private GitHubProvider gitHubProvider;

    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirectURI}")
    private String redirectURI;

    public AuthorizeController(GitHubProvider gitHubProvider, UserMapper userMapper) {
        this.gitHubProvider = gitHubProvider;
        this.userMapper = userMapper;
    }

    @GetMapping("callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectURI);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if (gitHubUser != null) {
            User user = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
        }
        return "redirect:/";
    }
}
