package br.com.pagga.chamado.dao.filter;

import java.util.Date;

import br.com.pagga.chamado.model.StatusChamado;
import br.com.pagga.chamado.model.TipoChamado;
import br.com.pagga.chamado.model.Usuario;

public class ChamadoFilter {

	private Long id;
	
	private String titulo;
	
	private String descricao;
	
	private Date dataAbertura;
	
	private String _dataAbertura;
	
	private Date dataFechamento;
	
	private String _dataFechamento;
	
	private String dataPrevisao;
	
	private StatusChamado status;
	
	private String statusDescricao;
	
	private TipoChamado tipo;
	
	private String tipoDescricao;
	
	private Usuario usuario;
	
	private String nomeUsuario;
	
	private Usuario usuarioFechamento;
	
	private String nomeUsuarioFechamento;
	
	private int start;
	
	private int limit;

	public ChamadoFilter withId(Long id) {
		this.id = id;
		return this;
	}
	
	public ChamadoFilter withTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}
	
	public ChamadoFilter withDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public ChamadoFilter withDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
		return this;
	}
	
	public ChamadoFilter withDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
		return this;
	}

	public ChamadoFilter with_DataAbertura(String _dataAbertura) {
		this._dataAbertura = _dataAbertura;
		return this;
	}
	
	public ChamadoFilter with_DataFechamento(String _dataFechamento) {
		this._dataFechamento = _dataFechamento;
		return this;
	}
	
	public ChamadoFilter withDataPrevisao(String dataPrevisao) {
		this.dataPrevisao = dataPrevisao;
		return this;
	}
	
	public ChamadoFilter withStatusChamado(StatusChamado status) {
		this.status = status;
		return this;
	}
	
	public ChamadoFilter withTipo(TipoChamado tipo) {
		this.tipo = tipo;
		return this;
	}
	
	public ChamadoFilter withUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}
	
	public ChamadoFilter withNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
		return this;
	}

	public ChamadoFilter withUsuarioFechamento(Usuario usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
		return this;
	}
	
	public ChamadoFilter withNomeUsuarioFechamento(String nomeUsuarioFechamento) {
		this.nomeUsuarioFechamento = nomeUsuarioFechamento;
		return this;
	}
	
	public ChamadoFilter withStatusDescricao(String statusDescricao) {
		this.statusDescricao = statusDescricao;
		return this;
	}

	public ChamadoFilter withTipoDescricao(String tipoDescricao) {
		this.tipoDescricao = tipoDescricao;
		return this;
	}
	
	public ChamadoFilter withStart(int start) {
		this.start = start;
		return this;
	}

	public ChamadoFilter getLimit(int limit) {
		this.limit = limit;
		return this;
	}
	
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public TipoChamado getTipo() {
		return tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario getUsuarioFechamento() {
		return usuarioFechamento;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public String getNomeUsuarioFechamento() {
		return nomeUsuarioFechamento;
	}

	public String getStatusDescricao() {
		return statusDescricao;
	}

	public String getTipoDescricao() {
		return tipoDescricao;
	}

	public String get_dataAbertura() {
		return _dataAbertura;
	}

	public String get_dataFechamento() {
		return _dataFechamento;
	}

	public String getDataPrevisao() {
		return dataPrevisao;
	}

}
