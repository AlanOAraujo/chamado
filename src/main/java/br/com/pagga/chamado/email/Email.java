package br.com.pagga.chamado.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private String SERVIDOR_SMTP = "smtp.gmail.com";
	private Integer SERVIDOR_PORTA = 587;
	private String CONTA_REMETENTE = "aaraujo@pagga.com.br";
	private String CONTA_SENHA = "cx@L271247!";
	
	private String destinatario;
	private String assunto;
	private String mensagem;
	
	public static Email envioEmail(String destinatario,String assunto,String mensagem) {
		
		Email email = new Email();
		
		Properties properties = new Properties();
		
		properties.put("mail.debug", "true");
		
		properties.put("mail.transport.protocol", "smtp"); //Define protocolo de envio como SMTP
		properties.put("mail.smtp.host", email.getSERVIDOR_SMTP()); //Servidor SMTP
		properties.put("mail.smtp.auth", "true"); //Ativa autenticacao
		properties.put("mail.smtp.user", email.getCONTA_REMETENTE()); //Usuario
		properties.put("mail.smtp.port", email.getSERVIDOR_PORTA()+""); //Porta
		properties.put("mail.smtp.socketFactory.port", email.getSERVIDOR_PORTA()+""); //Mesma porta para o socket
		properties.put("mail.smtp.socketFactory.fallback", "true");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.starttls.enable","true"); //Habilita TLS
		
		//Criando uma sessao ja passando as propriedades e criando um autenticador
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(email.getCONTA_REMETENTE(), email.getCONTA_SENHA());
	         }
	    });
		
		try {
			//Criando o email
			MimeMessage message = new MimeMessage(session);
			
			//Remetente
            message.setFrom(new InternetAddress(email.getCONTA_REMETENTE()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            
            //Assunto e data
            message.setSubject(assunto);
            message.setSentDate(new Date());
            
            //Mensagem no corpo, e formatos que podem ser enviados
            message.setContent(mensagem, "text/html; charset=utf-8");

            //Criando o Transport
			Transport transport = session.getTransport("smtp");
			
			//Conectando
			transport.connect(email.getSERVIDOR_SMTP(), email.getCONTA_REMETENTE(), email.getCONTA_SENHA());
			
			message.saveChanges(); 

			//Enviando msg
			transport.sendMessage(message, message.getAllRecipients());
			
			transport.close();
            
		}
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		
		return email;
		
	}
	
	
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getSERVIDOR_SMTP() {
		return SERVIDOR_SMTP;
	}
	public Integer getSERVIDOR_PORTA() {
		return SERVIDOR_PORTA;
	}
	public String getCONTA_REMETENTE() {
		return CONTA_REMETENTE;
	}
	public String getCONTA_SENHA() {
		return CONTA_SENHA;
	}
	
}
