package com.pmath.spring.cloud.jwt.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.pmath.spring.cloud.jwt.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
 /*   @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor(){
        return new AuthenticationInterceptor();
    }
*/
    /**
     * 配置消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*springboot2 需先删除jackson*/
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
            }
        }
        //需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(Charset.forName("UTF-8"));
        config.setDateFormat("yyyyMMdd HH:mm:ssS");
        //设置允许返回为null的属性
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        fastJsonConverter.setFastJsonConfig(config);
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConverter.setSupportedMediaTypes(list);
        converters.add(fastJsonConverter);
    }
}
