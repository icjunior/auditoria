package br.com.bigsupermercados.audit.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.bigsupermercados.audit.model.Loja;

public class LojaConverter implements Converter<String, Loja> {

	@Override
	public Loja convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Loja loja = new Loja();
			loja.setCodigo(Long.valueOf(codigo));
			return loja;
		}
		return null;
	}

}
