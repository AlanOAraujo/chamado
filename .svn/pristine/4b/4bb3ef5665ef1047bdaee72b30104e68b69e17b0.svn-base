package br.com.pagga.chamado.service;

import br.com.pagga.chamado.email.Email;

public class EmailService {

	public static void emailEsqueceSenha(String emailUsuario, String nomeUsuario, String senhaUsuario, String cpf) {
		
		String assunto = "Recuperação de senha!";
		
		StringBuffer mensagemEmail = new StringBuffer();
		
		mensagemEmail.append("<h2 align='center'>Pagga</h2>");
		mensagemEmail.append("<br/>");
		mensagemEmail.append("Olá "+nomeUsuario+" como você esqueceu sua senha, estamos enviando uma nova para efetuar o acesso<br/>");
		mensagemEmail.append("Seu login: ");
		mensagemEmail.append(""+cpf+"");
		mensagemEmail.append("<br/>");
		mensagemEmail.append("Sua nova senha: ");
		mensagemEmail.append(""+senhaUsuario+"");
		mensagemEmail.append("<br/>");
		mensagemEmail.append("Caso possua alguma dúvida, peço que entre em contato com departamente de TI. ");
	    
		String mensagem = mensagemEmail.toString();
		
		Email.envioEmail(emailUsuario, assunto, mensagem);
		
	}
	
	public static void emailConfirmacaoCadastro(String emailUsuario, String nomeUsuario, String senhaUsuario, String cpf) {
		
		String assunto = "Confirmação de cadastro!";
		
		StringBuffer mensagemEmail = new StringBuffer();
		
		mensagemEmail.append("<h2 align='center'>Pagga</h2>");
		mensagemEmail.append("<br/>");
		mensagemEmail.append("Olá "+nomeUsuario+" agora você possui cadastro em nossa ferramenta de chamados<br/>");
		mensagemEmail.append("Para você acessar nossa ferramenta.<br/>");
		mensagemEmail.append("Seu login: ");
		mensagemEmail.append(""+cpf+"");
		mensagemEmail.append("<br/>");
		mensagemEmail.append("Sua nova senha: ");
		mensagemEmail.append(""+senhaUsuario+"");
		mensagemEmail.append("<br/>");
		mensagemEmail.append("Caso possua alguma dúvida, peço que entre em contato com departamente de TI. ");
	    
		String mensagem = mensagemEmail.toString();
		
		Email.envioEmail(emailUsuario, assunto, mensagem);
		
	}
	
}
