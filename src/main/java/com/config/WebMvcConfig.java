package com.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.converter.StringToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置自定义时间类型转换
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
    }

    /**
     * 消息内容转换配置
     * 配置fastJson返回json转换
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //调用父类的配置
        WebMvcConfigurer.super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);
    }

//    //配置文件上传Bean，SpringBoot不需要配置Bean，ajax上传文件直接在MultipartFile参数前加@RequestParam
//    @Bean(name = "multipartResolver")
//    public MultipartResolver multipartResolver(){
//        //创建文件上传对象
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        //设置编码格式
//        resolver.setDefaultEncoding("utf-8");
//        //设置resolver加载lazy，推迟文件解析，以便在Controller中捕获文件太大异常
//        resolver.setResolveLazily(true);
//        resolver.setMaxInMemorySize(40960);
//        //设置文件上传最大值10M
//        resolver.setMaxUploadSize(10 * 1024 * 1024);
//        return resolver;
//    }


}
