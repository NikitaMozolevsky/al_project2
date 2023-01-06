package com.nikita.al_fp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
public class ThymeleafExtension {

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public ThymeleafExtension(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @PostConstruct
    public void extension() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\IdeaProjects\\al_project2\\al_fp\\src\\main\\resources\\templates");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(templateEngine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        templateEngine.addTemplateResolver(resolver);
    }
}