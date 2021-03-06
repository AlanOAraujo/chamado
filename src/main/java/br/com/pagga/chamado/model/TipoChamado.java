package br.com.pagga.chamado.model;

public enum TipoChamado {

	INCIDENTE("Incidente"), 
	SOLICITACAO("Solicitação"), 
	RELATORIO("Relatório"),
	DUVIDA("Dúvida");
	
	private String descricao;

	private TipoChamado(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
		
}
