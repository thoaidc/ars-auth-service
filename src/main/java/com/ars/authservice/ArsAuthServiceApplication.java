package com.ars.authservice;

import com.dct.base.config.CRLFLogConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@ComponentScan({"com.dct.base", "com.ars.authservice"})
public class ArsAuthServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(ArsAuthServiceApplication.class);
    private static final String SSL_KEY = "server.ssl.key-store";

    /**
     * Main method, used to run the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ArsAuthServiceApplication.class);
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        ConfigurableApplicationContext context = app.run(args);
        Environment env = context.getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String applicationName = env.getProperty("spring.application.name");
        String[] activeProfile = env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles();
        String protocol = Optional.ofNullable(env.getProperty(SSL_KEY)).map(key -> "https").orElse("http");
        String hostAddress = "localhost";
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::hasText)
                .orElse("/");

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            """
            \n----------------------------------------------------------
            \tApplication '{}' is running! Access URLs:
            \tLocal: \t\t{}://localhost:{}{}
            \tExternal: \t{}://{}:{}{}
            \tProfile(s): {}
            \n----------------------------------------------------------
            """,
            applicationName,
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            activeProfile
        );
    }
}
