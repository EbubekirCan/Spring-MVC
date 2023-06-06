package com.tpe;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//web.xml yerine bu classı kullanıyoruz.
//dispatcher servletın tanımlanması, konfigürasyonu ile başlıyoruz.
//AbstractAnnotationConfigD... dispatcher servletın başlatılmasını, config ayarlarının bulunduğu dosyanın
//yerinin gösterilmesini sağlar.
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
/*
Dispatcher: Servlet WebAppContext->Controller,Handler Mapping,View Resolver
            Root WebAppContext->services,repositories
*/

    @Override//db'ye erişim(hibernate/jdbc) için gerekli config class
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
          RootContextConfig.class
        };
    }

    @Override//Controller,Handler Mapping,View Resolver(SpringMVC) ile ilgili config
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
          WebMvcConfig.class
        };
    }

    @Override//hangi url ile gelen istekler servlet tarafından karşılanacak
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
