package com.njeport.track;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.njeport.track"}, exclude = {SecurityAutoConfiguration.class})
public class EportStarter {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EportStarter.class);
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
    }
}