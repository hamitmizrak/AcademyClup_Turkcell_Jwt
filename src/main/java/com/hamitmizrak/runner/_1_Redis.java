package com.hamitmizrak.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class _1_Redis implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("[redis-up] docker run çağrılıyor...");
        ProcessBuilder pb = isWindows()
                ? new ProcessBuilder("cmd", "/c", "docker", "run", "-d", "--name", "redis", "-p", "6379:6379", "redis:7-alpine")
                : new ProcessBuilder("bash", "-lc", "docker run -d --name redis -p 6379:6379 redis:7-alpine");
        pb.inheritIO();
        int code = pb.start().waitFor();
        if (code != 0) System.err.println("docker run exit code: " + code);
    }
    private boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }
}
