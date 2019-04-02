package br.com.pagga.chamado.model;

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
@Table(name ="marcacao_chamado")
public class MarcacaoChamado implements Model<Long> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "marcacao_chamado_id_marchm_mrc_seq", sequenceName = "marcacao_chamado_id_marchm_mrc_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="marcacao_chamado_id_marchm_mrc_seq")
	@Column(name = "id_marchm_mrc")
	private Long id;
	
	@Column(name = "ds_mrcdes_mrc")
	private String descricao;
	
	@ManyToOne
	@JoinColumn( name = "id_chamad_chm", nullable = false)
	private Chamado chamado;

	public static MarcacaoChamado create(String descricao, Chamado chamado) {
		
		MarcacaoChamado marcacao = new MarcacaoChamado();
		
		marcacao.setDescricao(descricao);
		marcacao.setChamado(chamado);
		
		return marcacao;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
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
		MarcacaoChamado other = (MarcacaoChamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MarcacaoChamado [id=" + id + ", descricao=" + descricao + "]";
	}

}
