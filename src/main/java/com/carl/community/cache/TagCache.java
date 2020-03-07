package com.carl.community.cache;

import com.carl.community.dto.TagDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaoq
 * @date 2020/3/7 18:50
 */
public class TagCache {

    public static List<TagDTO> get() {
        List<TagDTO> tagDTOList = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategory("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOList.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategory("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOList.add(framework);

        TagDTO server = new TagDTO();
        server.setCategory("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOList.add(server);

        TagDTO db = new TagDTO();
        db.setCategory("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOList.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategory("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagDTOList.add(tool);
        return tagDTOList;
    }

    public static String filterInvalid(String tags) {
        String[] splitTags = tags.split(";");
        List<String> tagList = get().stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
        return Arrays.stream(splitTags).filter(s -> StringUtils.isEmpty(s) || !tagList.contains(s))
                .collect(Collectors.joining(";"));
    }
}
