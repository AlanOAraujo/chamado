package br.com.pagga.chamado.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="perfil")
public class Perfil implements Model<Long> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "perfil_id_perfil_prf_seq", sequenceName = "perfil_id_perfil_prf_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="perfil_id_perfil_prf_seq")
	@Column(name = "id_perfil_prf")
	private Long id;
	
	@Column(name = "ds_pfdesc_prf")
	private String descricao;
	
	@OneToMany(mappedBy = "perfil")
	private List<UsuarioPerfil> usuarioPerfilList = new ArrayList<>();
	
	@OneToMany(mappedBy = "perfil")
	private List<PerfilRotinaAtributo> perfilRotinaAtributo = new ArrayList<>();
	
	public Perfil(String descricao) {
		this.descricao = descricao;
	}

	public Perfil() { }
	
	public static Perfil create(String descricao) {
		Perfil perfil = new Perfil(descricao);
		
		perfil.setDescricao(descricao);
		
		return perfil;
		
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

	public List<UsuarioPerfil> getUsuarioPerfilList() {
		return usuarioPerfilList;
	}

	public List<PerfilRotinaAtributo> getPerfilRotinaAtributo() {
		return perfilRotinaAtributo;
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", descricao=" + descricao + "]";
	}

}
