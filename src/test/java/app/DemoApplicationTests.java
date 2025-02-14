package app;  // Đảm bảo package trùng với Main.java

import org.springframework.context.annotation.ComponentScan;
import org.testng.annotations.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
@ComponentScan("app")

public class DemoApplicationTests {
    @Test
    void contextLoads() {
    }
}
