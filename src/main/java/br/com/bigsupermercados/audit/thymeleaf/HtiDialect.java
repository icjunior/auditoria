package br.com.bigsupermercados.audit.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.bigsupermercados.audit.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.bigsupermercados.audit.thymeleaf.processor.MenuAttributeTagProcessor;
import br.com.bigsupermercados.audit.thymeleaf.processor.MessageElementTagProcessor;
import br.com.bigsupermercados.audit.thymeleaf.processor.OrderElementTagProcessor;
import br.com.bigsupermercados.audit.thymeleaf.processor.PaginationElementTagProcessor;

public class HtiDialect extends AbstractProcessorDialect {

	public HtiDialect() {
		super("Hti Software", "hti", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}
}
