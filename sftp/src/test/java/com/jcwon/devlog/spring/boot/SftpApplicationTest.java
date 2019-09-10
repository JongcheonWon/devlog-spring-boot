package com.jcwon.devlog.spring.boot;

import com.jcwon.devlog.spring.boot.sftp.MockSftpServer;
import com.jcwon.devlog.spring.boot.sftp.SftpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"sftp.host = localhost", "sftp.port = 10022", "sftp.user = testUser", "sftp.password = testPassword",
        "sftp.remote.home = /"})
public class SftpApplicationTest {

    @Autowired
    private SftpClient sftpClient;
    private MockSftpServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockSftpServer();
        server.start(10022);
    }

    @Test
    public void downloadReservedFileTest() throws IOException {
        byte[] bytes = sftpClient.read("tempFile.txt");

        System.out.println(new String(bytes));

        assertArrayEquals("Test File Contents\n".getBytes(), bytes);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
}