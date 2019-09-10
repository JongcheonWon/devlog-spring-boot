package com.jcwon.devlog.spring.boot.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MockSftpServerTest {

    private final int PORT = 23224;
    private MockSftpServer mockSftpServer;

    @Before
    public void setUp() throws Exception {
        mockSftpServer = new MockSftpServer();
        mockSftpServer.start(PORT);
        mockSftpServer._createFile("tempFile.txt", "Test File Contents\n");
    }

    @Test
    public void name() throws Exception {

        JSch jsch = new JSch();

        // Temp Connecting information
        Session session = jsch.getSession("user1", "localhost", PORT);
        // Strict Host Key Checking disable
        session.setConfig("StrictHostKeyChecking", "no");
        // SFTP Connect
        session.connect();
        // SFTP Channel Open (SFTP Protocol Supports)
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;

        StringBuilder buf = new StringBuilder();
        // SFTP Home Directory 의 파일 Read
        try (BufferedInputStream bis = new BufferedInputStream(channelSftp.get("tempFile.txt"))) {
            byte[] bytes = new byte[512];

            int len;
            while ((len = bis.read(bytes)) > 0) {
                buf.append(new String(bytes, 0, len));
            }
        }


        Assert.assertEquals("Test File Contents\n", buf.toString());
        System.out.println(buf.toString());
    }

    @Test
    public void name1() throws Exception {

        JSch jsch = new JSch();

        // Temp Connecting information
        Session session = jsch.getSession("user1", "localhost", PORT);
        // Strict Host Key Checking disable
        session.setConfig("StrictHostKeyChecking", "no");
        // SFTP Connect
        session.connect();
        // SFTP Channel Open (SFTP Protocol Supports)
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;

        StringBuilder buf = new StringBuilder();
        // SFTP Home Directory 의 파일 Read
        try (InputStream is = channelSftp.get("tempFile.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
        }

        Assert.assertEquals("Test File Contents", buf.toString());
        System.out.println(buf.toString());
    }

    @After
    public void tearDown() throws Exception {
        mockSftpServer.stop();
    }
}
