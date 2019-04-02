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
@Table(name="atributo")
public class Atributo implements Model<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "atributo_id_atribu_atr_seq", sequenceName = "atributo_id_atribu_atr_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="atributo_id_atribu_atr_seq")
	@Column(name = "id_atribu_atr")
	private Long id;
	
	@Column(name="nm_atnome_atr")
	private String nomeAtributo;
	
	@Column(name="cd_codatr_atr")
	private String codAtributo;
	
	@ManyToOne
	@JoinColumn( name = "id_rotina_rtn", nullable = false, referencedColumnName="id_rotina_rtn" )
	private Rotina rotina;
	
	public Atributo(String nomeAtributo, String codAtributo, Rotina rotina) {
		this.nomeAtributo = nomeAtributo;
		this.codAtributo = codAtributo;
		this.rotina = rotina;
	}
	
	public Atributo() {	}
	
	public static Atributo createAtributo(String nomeAtributo, String codAtributo, Rotina rotina) {
		
		Atributo atributo = new Atributo(nomeAtributo, codAtributo, rotina);
		
		return atributo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeAtributo() {
		return nomeAtributo;
	}

	public void setNomeAtributo(String nomeAtributo) {
		this.nomeAtributo = nomeAtributo;
	}

	public String getCodAtributo() {
		return codAtributo;
	}

	public void setCodAtributo(String codAtributo) {
		this.codAtributo = codAtributo;
	}

	public Rotina getRotina() {
		return rotina;
	}

	public void setRotina(Rotina rotina) {
		this.rotina = rotina;
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
		Atributo other = (Atributo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Atributo [id=" + id + ", nomeAtributo=" + nomeAtributo + ", codAtributo=" + codAtributo + "]";
	}

}
