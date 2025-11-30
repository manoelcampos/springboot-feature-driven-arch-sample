package sample.application.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Value("${FRONTEND_APP_URL}")
    private String frontendAppURL;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendAppURL)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }

    /**
     * Define o idioma da aplicação e assim permite que mensagens de erro
     * sejam traduzidas. Mensagens de erro do Hibernate Validation
     * são traduzidas no arquivo ValidationMessages_pt_BR.properties.
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        final var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
//        registry.addInterceptor(new JsonInterceptor())
//                .addPathPatterns("/vigencia-plano-trabalho/**");
    }
}
