package com.carl.community.dto;

public class GitHubUser {

    private Long Id;
    private String name;
    private String dio;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDio() {
        return dio;
    }

    public void setDio(String dio) {
        this.dio = dio;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", dio='" + dio + '\'' +
                '}';
    }
}
