package com.jcwon.devlog.spring.boot.sftp;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Component("sftpClient")
public class SftpClient {

    private final DefaultSftpSessionFactory sftpSessionFactory;

    public byte[] read(String source) throws IOException {

        byte[] bytes;

        try(SftpSession session = sftpSessionFactory.getSession()){
            InputStream is = session.readRaw(source);
            bytes = StreamUtils.copyToByteArray(is);
        }
        return bytes;
    }
}
