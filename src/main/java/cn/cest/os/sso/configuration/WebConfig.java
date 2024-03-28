//package cn.cest.os.sso.Configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 和springmvc的webmvc拦截配置一样
// * @author BIANP
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(LoginInterceptor())
////                .addPathPatterns("/**")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/code")
//                .excludePathPatterns("/login1");
//
//    }
//    @Bean
//    public LoginInterceptor LoginInterceptor() {
//        return new LoginInterceptor();
//    }
//}
