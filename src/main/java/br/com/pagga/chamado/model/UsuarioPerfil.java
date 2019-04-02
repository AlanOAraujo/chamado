package br.com.pagga.chamado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil implements Model<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "usuario_perfil_id_usrprf_upf_seq", sequenceName = "usuario_perfil_id_usrprf_upf_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuario_perfil_id_usrprf_upf_seq")
	@Column(name = "id_usrprf_upf")
	private Long id;
	
	@ManyToOne
	@JoinColumn( name = "id_usuari_usr", nullable = false)
	Usuario usuario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "id_perfil_prf", nullable = false)
	Perfil perfil;
	
	public static UsuarioPerfil createUsuarioPerfil(Usuario usuario, Perfil perfil) {
		
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
				
		usuarioPerfil.setPerfil(perfil);
		usuarioPerfil.setUsuario(usuario);
		
		return usuarioPerfil;	
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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
		UsuarioPerfil other = (UsuarioPerfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioPerfil [id=" + id + "]";
	}
}

