package br.com.ifsp.pi.lixt.utils.mail;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;

@Service
public class SenderMail {
	
	private final SmtpAuthenticator auth;
	
	private static final Logger logger = LoggerFactory.getLogger(SenderMail.class);
	
	@Autowired
	public SenderMail(@Qualifier("mailer") SmtpAuthenticator smtp) {
		this.auth = smtp;
	}
	
	public boolean sendEmail(MailDto mail) {
		
		try {
			if(!mail.isValid()) {
				throw new PreconditionFailedException("Corpo de email inválido");
			}

			MimeMessage message = this.configMessage(mail, this.config());
			Transport.send(message);
		
			logger.info("Email enviado com sucesso!");
			return true;
		}
		catch(Exception e) {
			logger.error("Erro ao enviar email: ".concat(e.getMessage()));
			return false;
		}
	}

	private Session config() {

		var props = new Properties();
		  
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", "true");
		
		return Session.getInstance(props, auth);
	}
	
	private MimeMessage configMessage(MailDto mail, Session session) throws MessagingException {
		var message = new MimeMessage(session);
				
		for(String recipient : mail.getRecipientsTO())
			message.addRecipient(RecipientType.TO, new InternetAddress(recipient));
		
		if(Objects.nonNull(mail.getRecipientsCC())) {
			for(String recipient : mail.getRecipientsCC())
				message.addRecipient(RecipientType.CC, new InternetAddress(recipient));
		}
		
		if(Objects.nonNull(mail.getRecipientsBCC())) {
			for(String recipient : mail.getRecipientsBCC())
				message.addRecipient(RecipientType.BCC, new InternetAddress(recipient));
		}
				
		message.setSentDate(mail.getDateSent());
		message.setSubject(mail.getTitle());
		
		var multipart = new MimeMultipart();
		
		var attachmentText = new MimeBodyPart();
		attachmentText.setContent(mail.getMsgHTML(),"text/html; charset=UTF-8");
		multipart.addBodyPart(attachmentText);
		
		if(Objects.nonNull(mail.getFiles())) {
			for(File file : mail.getFiles())
				this.setAttachment(multipart, file);
		}
		
		message.setContent(multipart);
		
		return message;
	}
	
	private void setAttachment(Multipart multipart, File file) throws MessagingException {
		
		try {
			var part = new MimeBodyPart();
			part.setDataHandler(new DataHandler(new FileDataSource(file)));
			part.setFileName(file.getName());
			multipart.addBodyPart(part);
			
		} catch (MessagingException e) {
			logger.error("Erro ao anexar ".concat(file.getName()).concat(" no email."));
		}
		
	}

}