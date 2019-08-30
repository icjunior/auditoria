package br.com.bigsupermercados.audit.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.bigsupermercados.audit.model.Setor;

public class SetorConverter implements Converter<String, Setor> {

	@Override
	public Setor convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Setor setor = new Setor();
			setor.setCodigo(Long.valueOf(codigo));
			return setor;
		}
		return null;
	}
}
