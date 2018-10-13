package bo.com.mondongo.bankapplication;

import bo.com.mondongo.bankapplication.dto.DTOModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private final ApplicationContext applicationContext;

    @Autowired
    public WebMvcConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                                                               .applicationContext(this.applicationContext).build();
        argumentResolvers.add(new DTOModelMapper(objectMapper));
    }
}