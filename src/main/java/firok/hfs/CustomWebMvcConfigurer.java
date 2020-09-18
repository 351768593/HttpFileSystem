package firok.hfs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public BufferedImageHttpMessageConverter getBufferedImageConverter()
    {
        return new BufferedImageHttpMessageConverter();
    }
}