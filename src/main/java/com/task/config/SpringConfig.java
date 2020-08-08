package com.task.config;

import com.task.converter.OptionDtoConverter;
import com.task.converter.TariffDtoConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

@Configuration
@ComponentScan("com.task")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    @Resource(name = "tariffDtoConverter")
    private TariffDtoConverter tariffDtoConverter;
    @Resource(name = "optionDtoConverter")
    private OptionDtoConverter optionDtoConverter;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       // registry.addViewController("/").setViewName("index");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
        // Register resource handler for images
        registry.
                addResourceHandler("/images/**").addResourceLocations("/WEB-INF/views/images/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return  mapper;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(tariffDtoConverter);
        registry.addConverter(optionDtoConverter);

    }
}
