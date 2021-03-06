package br.com.pagga.chamado.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import br.com.pagga.chamado.type.PGEnumUserType;

@Entity
@Table(name ="chamado")
@TypeDef( name = "psql_enum", typeClass = PGEnumUserType.class )
public class Chamado implements Model<Long> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "chamado_id_chamad_chm_seq", sequenceName = "chamado_id_chamad_chm_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chamado_id_chamad_chm_seq")
	@Column(name = "id_chamad_chm")
	private Long id;
	
	@Column(name = "ds_chmttl_chm")
	private String titulo;
	
	@Column(name = "ds_chmdes_chm")
	private String descricao;
	
	@Column(name = "ds_chmdfn_chm")
	private String descricaoFechamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_dtaber_chm")
	private Date dataAbertura;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_dtfech_chm")
	private Date dataFechamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_dtprev_chm")
	private Date dataPrevisao;
	
	@Enumerated(EnumType.STRING)
	@Type( type = "psql_enum" )
	@Column( name = "status", columnDefinition = "status_chamado" )
	private StatusChamado status;
	
	@Enumerated(EnumType.STRING)
	@Type( type = "psql_enum" )
	@Column( name = "tipo", columnDefinition = "tipo_chamado" )
	private TipoChamado tipo;
	
	@ManyToOne
	@JoinColumn( name = "id_usuari_usr", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn( name = "id_usufec_chm", referencedColumnName="id_usuari_usr")
	private Usuario usuarioFechamento;
	
	@OneToMany( mappedBy = "chamado" )
	private List<HistoricoChamado> historicoChamadoList = new ArrayList<>();
	
	@OneToMany( mappedBy = "chamado" )
	private List<MarcacaoChamado> marcacaoChamadoList = new ArrayList<>();

	public static Chamado create(String titulo, String descricao, TipoChamado tipoChamado, Usuario usuario) {
		
		Chamado chamado = new Chamado();
		chamado.setTitulo(titulo);
		chamado.setDescricao(descricao);
		chamado.setDataAbertura(new Date());
		chamado.setTipo(tipoChamado);
		chamado.setStatus(StatusChamado.ABERTO);
		chamado.setUsuario(usuario);
		
		return chamado;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getDescricaoFechamento() {
		return descricaoFechamento;
	}

	public void setDescricaoFechamento(String descricaoFechamento) {
		this.descricaoFechamento = descricaoFechamento;
	}

	public Date getDataPrevisao() {
		return dataPrevisao;
	}

	public void setDataPrevisao(Date dataPrevisao) {
		this.dataPrevisao = dataPrevisao;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	public TipoChamado getTipo() {
		return tipo;
	}

	public void setTipo(TipoChamado tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioFechamento() {
		return usuarioFechamento;
	}

	public void setUsuarioFechamento(Usuario usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}
	
	public List<HistoricoChamado> getHistoricoChamadoList() {
		return historicoChamadoList;
	}
	
	public void setHistoricoChamadoList(List<HistoricoChamado> historicoChamadoList) {
		this.historicoChamadoList = historicoChamadoList;
	}
	
	public List<MarcacaoChamado> getMarcacaoChamadoList() {
		return marcacaoChamadoList;
	}

	public void setMarcacaoChamadoList(List<MarcacaoChamado> marcacaoChamadoList) {
		this.marcacaoChamadoList = marcacaoChamadoList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Chamado [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", descricaoFechamento="
				+ descricaoFechamento + ", dataAbertura=" + dataAbertura + ", dataFechamento=" + dataFechamento
				+ ", dataPrevisao=" + dataPrevisao + ", status=" + status + ", tipo=" + tipo + "]";
	}

}
