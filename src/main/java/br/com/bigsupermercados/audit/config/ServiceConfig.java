package br.com.bigsupermercados.audit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.bigsupermercados.audit.service.CadastroLojaService;
import br.com.bigsupermercados.audit.storage.FotoStorage;
import br.com.bigsupermercados.audit.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CadastroLojaService.class)
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorageLocal() {
		return new FotoStorageLocal();
	}
}
