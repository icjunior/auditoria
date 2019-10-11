package br.com.bigsupermercados.audit.mail;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.bigsupermercados.audit.dto.EmailDTO;
import br.com.bigsupermercados.audit.service.RelatorioService;

@Component
public class Mailer {

	private static Logger logger = LoggerFactory.getLogger(Mailer.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private RelatorioService relatorioService;

	@Async
	public void enviar(EmailDTO email) {
		try {
			byte[] relatorio = relatorioService.gerarRelatorioPorAuditoria(email.getCodigo());

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(email.getEmail());
			helper.setTo(email.getEmail());
			helper.setSubject("Relatório de auditoria - código: " + email.getCodigo());
			helper.setText("Olá " + email.getEmail() + " seu relatório da auditoria " + email.getCodigo()
					+ " acabou de chegar!!");

			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setDisposition(Part.ATTACHMENT);
			mbp.setDataHandler(new DataHandler(new ByteArrayDataSource(relatorio, "application/pdf")));
			mbp.setFileName("auditoria_" + email.getCodigo() + ".pdf");

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);

			mimeMessage.setContent(mp);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		} catch (Exception e) {
			logger.error("Erro enviando e-mail", e);
		}
	}
}
