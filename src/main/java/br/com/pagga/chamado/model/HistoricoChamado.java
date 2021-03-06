package br.com.pagga.chamado.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name ="historico_chamado")
public class HistoricoChamado implements Model<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "historico_chamado_id_hischm_his_seq", sequenceName = "historico_chamado_id_hischm_his_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="historico_chamado_id_hischm_his_seq")
	@Column(name = "id_hischm_his")
	private Long id;
	
	@Column(name = "ds_coment_his")
	private String comentario;
	
	@Column(name = "dt_coment_his")
	private Date dataComentario;
	
	@Column(name = "mm_hisanx_his")
	private byte[] anexo;
	
	@Column(name = "ds_nomanx_his")
	private String nomeAnexo;
	
	@ManyToOne
	@JoinColumn( name = "id_usuari_usr", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn( name = "id_chamad_chm", nullable = false)
	private Chamado chamado;

	public static HistoricoChamado create(Chamado chamado, String comentario, Usuario usuario) {
		
		HistoricoChamado historicoChamado = new HistoricoChamado();
		historicoChamado.setChamado(chamado);
		historicoChamado.setComentario(comentario);
		historicoChamado.setDataComentario(new Date());
		historicoChamado.setUsuario(usuario);
		
		return historicoChamado;
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getDataComentario() {
		return dataComentario;
	}

	public void setDataComentario(Date dataComentario) {
		this.dataComentario = dataComentario;
	}


	public byte[] getAnexo() {
		return anexo;
	}

	public void setAnexo(byte[] anexo) {
		this.anexo = anexo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}


	public String getNomeAnexo() {
		return nomeAnexo;
	}


	public void setNomeAnexo(String nomeAnexo) {
		this.nomeAnexo = nomeAnexo;
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
		HistoricoChamado other = (HistoricoChamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "HistoricoChamado [id=" + id + ", comentario=" + comentario + ", dataComentario=" + dataComentario + "]";
	}

}
