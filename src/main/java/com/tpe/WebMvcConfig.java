package com.tpe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan("com.tpe")//opsiyonel
@EnableWebMvc//MVC için config etkinleştirmek
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean//view name e karşılık gelen view dosyasının çözümlenmesi:viewresolver
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver=new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);//JavaStandartTagLibrary:JSP dosyaları içinde daha az kod yazmamızı sağlar
        resolver.setPrefix("/WEB-INF/views/");//view dosyaları nerde(dizin)
        resolver.setSuffix(".jsp");//view dosyalarının uzantısı
        return resolver;
    }

    //css,image statik olan kaynakların dispatchera gönderilmesine gerek yok
    //ex:http://localhost:8080/SpringMVC/resources/css/style.css
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").//bu pathdeki kaynakları statik olarak sun
                 addResourceLocations("/resources/").//kaynakların yeri
                 setCachePeriod(0);//cacheleme için belirli bir periyod süresi verilebilir.
    }
}
