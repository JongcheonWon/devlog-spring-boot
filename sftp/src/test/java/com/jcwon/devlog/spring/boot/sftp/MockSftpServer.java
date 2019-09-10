package com.jcwon.devlog.spring.boot.sftp;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class MockSftpServer {

    private SshServer sshd;
    private Path tempDirectory;

    public void start(int port) throws IOException {
        // Creating an instance of SshServer(Default Configuration)
        sshd = SshServer.setUpDefaultServer();
        sshd.setPort(port);

        /*
         * The SSH employs a public key cryptography. A public-key cryptography, also known as asymmetric cryptography, is a class of
         * cryptographic algorithms which requires two separate keys, one of which is secret (or private) and one of which is public.1
         * Together they are known as a key-pair. In SSH, the public key cryptography is used in both directions (client to server and server to client),
         * so two key pairs are used. One key pair is known as a host (server) key, the other as a user (client) key.
         */
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("sftp/src/test/resources/hostkey.ser")));

        // Password 강제 인증
        sshd.setPasswordAuthenticator((username, password, session) -> true);
        // SFTP Subsystem Factory 설정
        sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory.Builder().build()));
        // Temporary Directory 생성
        tempDirectory = Files.createTempDirectory("testTemp");
        // SFTP Home Directory 설정
        sshd.setFileSystemFactory(new VirtualFileSystemFactory(tempDirectory));
//        // Download 될 임시 파일 생성
//        byte[] tempFileContents = "Test File Contents\n".getBytes();
//        Path tempFile = tempDirectory.resolve("tempFile.txt");
//        Files.write(tempFile, tempFileContents);
        // 기동
        sshd.start();
    }

    public void _createFile(String fileNm, String contents) throws IOException {
        Path tempFile = tempDirectory.resolve(fileNm);
        Files.write(tempFile, contents.getBytes());
    }

    public void stop() throws Exception {
        if (sshd.isStarted()) {
            sshd.stop();
        }else{
            throw new Exception("SSHD Server is not running.");
        }
    }
}
