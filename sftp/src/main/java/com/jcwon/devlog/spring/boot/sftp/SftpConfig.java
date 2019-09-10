package com.jcwon.devlog.spring.boot.sftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.util.Properties;

@Configuration
public class SftpConfig {

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private int port;

    @Value("${sftp.user}")
    private String user;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.remote.home}")
    private String remoteHome;


    @Bean
    public DefaultSftpSessionFactory sftpSessionFactory(){
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUser(user);
        factory.setPassword(password);

        Properties props = new Properties();
        props.setProperty("StrictHostKeyChecking", "no");
        factory.setSessionConfig(props);

        factory.setAllowUnknownKeys(true);

        return factory;
    }
}
