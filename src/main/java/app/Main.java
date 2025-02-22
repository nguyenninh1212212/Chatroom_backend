package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        System.out.println("<==================Bản mới nhất=====================>");
        SpringApplication.run(Main.class, args);
    }
}
