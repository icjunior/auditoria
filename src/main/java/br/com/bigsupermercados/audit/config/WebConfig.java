package br.com.bigsupermercados.audit.config;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.cache.Caching;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

import br.com.bigsupermercados.audit.config.format.BigDecimalFormatter;
import br.com.bigsupermercados.audit.controller.LojasController;
import br.com.bigsupermercados.audit.controller.converter.LojaConverter;
import br.com.bigsupermercados.audit.controller.converter.SetorConverter;
import br.com.bigsupermercados.audit.controller.converter.TipoConverter;
import br.com.bigsupermercados.audit.thymeleaf.HtiDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = { LojasController.class })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
@EnableAsync
public class WebConfig implements ApplicationContextAware, WebMvcConfigurer {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(1);
		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());

		engine.addDialect(new LayoutDialect());
		engine.addDialect(new HtiDialect());
		engine.addDialect(new DataAttributeDialect());
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new LojaConverter());
		conversionService.addConverter(new TipoConverter());
		conversionService.addConverter(new SetorConverter());

		// NumberStyleFormatter bigDecimalFormatter = new
		// NumberStyleFormatter("#,##0.00");
		BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);

		// NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#.##0");
		BigDecimalFormatter integerFormatter = new BigDecimalFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);

		// API DE DATAS A PARTIR DO JAVA 8
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.registerFormatters(conversionService);

		return conversionService;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

	@Bean
	public CacheManager cacheManager() throws URISyntaxException {
		return new JCacheCacheManager(Caching.getCachingProvider()
				.getCacheManager(getClass().getResource("/env/ehcache.xml").toURI(), getClass().getClassLoader()));
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("classpath:/messages");
		bundle.setDefaultEncoding("UTF-8");
		return bundle;
	}

	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource());

		return validatorFactoryBean;
	}

	@Override
	public Validator getValidator() {
		return validator();
	}

}