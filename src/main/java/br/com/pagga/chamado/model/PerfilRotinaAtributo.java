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
@Table(name="perfil_rotina_atributo")
public class PerfilRotinaAtributo implements Model<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "perfil_rotina_atributo_id_prfrtn_prt_seq", sequenceName = "perfil_rotina_atributo_id_prfrtn_prt_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="perfil_rotina_atributo_id_prfrtn_prt_seq")
	@Column(name = "id_prfrtn_prt")
	private Long id;
	
	@ManyToOne
	@JoinColumn( name = "id_perfil_prf", nullable = false, referencedColumnName="id_perfil_prf" )
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn( name = "id_rotina_rtn", nullable = false, referencedColumnName="id_rotina_rtn" )
	private Rotina rotina;
	
	@ManyToOne
	@JoinColumn( name = "id_atribu_atr", nullable = false, referencedColumnName="id_atribu_atr" )
	private Atributo atributo;

	public static PerfilRotinaAtributo createPerfilRotinaAtributo(Perfil perfil, Rotina rotina, Atributo atributo) {
		PerfilRotinaAtributo perfilRotinaAtributo = new PerfilRotinaAtributo();
		
		perfilRotinaAtributo.setPerfil(perfil);
		perfilRotinaAtributo.setRotina(rotina);
		perfilRotinaAtributo.setAtributo(atributo);
		
		return perfilRotinaAtributo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Rotina getRotina() {
		return rotina;
	}

	public void setRotina(Rotina rotina) {
		this.rotina = rotina;
	}

	public Atributo getAtributo() {
		return atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
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
		PerfilRotinaAtributo other = (PerfilRotinaAtributo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PerfilRotinaAtributo [id=" + id + "]";
	}
}
