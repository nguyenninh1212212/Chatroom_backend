    package app.config;
    
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    
    @Configuration
    public class WebConfig {
    
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**") // Áp dụng cho tất cả API
                            .allowedOriginPatterns(
                                    "http://localhost:[*]",
                                    "http://127.0.0.1:[*]",
                                    "http://host.docker.internal:[*]"
                            )  // Chỉ định các origin cụ thể
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức được phép
                            .allowedHeaders("*") // Chấp nhận tất cả headers
                            .allowCredentials(true); // Cho phép gửi cookies
                }
            };
        }
    }
