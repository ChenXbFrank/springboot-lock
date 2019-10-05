package com.zero.learn_spring_boot;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableCaching
//下面是自动注入的时候，排除数据库
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class LearnSpringBootApplication extends WebMvcConfigurationSupport{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//这是配置模板页面的路径   配置文件里面已经配置了  所以这里暂时不需要
		//registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
		//这里是配置静态资源文件的路径
		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 使用fastJson的方式一     需要  extends WebMvcConfigurationSupport
	 * 1.需要先定义一个convert转换消息的对象
	 * 2.添加fastJson的配置信息，比如：是否要格式化返回的json数据
	 * 3.在convert中添加配置信息
	 * 4.将convert添加到converts当中
	 * @param converters
	 *  这种方式才能解决乱码问题，下面的方式二我试了一下，不得行
	 */
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		//1.需要先定义一个convert转换消息对象
		FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
		//2.添加fastJson的配置信息，比如：是否要格式化返回的json数据
		FastJsonConfig fastConfig=new FastJsonConfig();
		fastConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		//处理中文乱码问题(不然出现中文乱码)
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		//3.在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastConfig);
		//4.将convert添加到converts当中
		converters.add(fastConverter);
	}

	/**
	 * 这种方式不需要 extends WebMvcConfigurationSupport
	 * 在这里使用@Bean注入 使用fastJson方式二
	 */
	/*@Bean
	public HttpMessageConverters fastJsonConverters() {
		//1.需要先定义一个convert转换消息对象
		FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
		//2.添加fastJson的配置信息，比如：是否要格式化返回的json数据
		FastJsonConfig fastConfig=new FastJsonConfig();
		fastConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		//处理中文乱码问题(不然出现中文乱码)
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		//3.在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastConfig);
		//4.将convert添加到converts当中
		HttpMessageConverter<?> converter=fastConverter;
		return new HttpMessageConverters(converter);
	}*/

	// 自定义拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				System.out.println("自定义拦截器............"+request.getRequestURI());
				return true;
			}

			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
								   ModelAndView modelAndView) throws Exception {

			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
										Exception ex) throws Exception {
			}
		};
		registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
	}


	public static void main(String[] args) {
		SpringApplication.run(LearnSpringBootApplication.class, args);
	}

}
