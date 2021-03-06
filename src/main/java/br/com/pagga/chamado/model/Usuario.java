package br.com.pagga.chamado.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "usuario")
public class Usuario implements Model<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "usuario_id_usuari_usr_seq", sequenceName = "usuario_id_usuari_usr_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuario_id_usuari_usr_seq")
	@Column(name = "id_usuari_usr")
	private Long id;
	
	@Column(name = "nm_usrnom_usr")
	private String nome;
	
	@Column(name = "nm_usrcpf_usr")
	private String cpf;
	
	@Column(name = "cd_usenha_usr")
	private String senha;
	
	@Column(name = "nm_uemail_usr")
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_udtcad_usr")
	private Date dataCadastro;
	
	@Column(name = "bl_usrsit_usr")
	private boolean situacao;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<UsuarioPerfil> usuarioPerfilList = new ArrayList<>();
	
	public Usuario(String nome, String cpf, String senha, Date dataCadastro, String email, boolean situacao) {
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
		this.email = email;
		this.dataCadastro = dataCadastro;
		this.situacao = situacao;
	}
	
	public Usuario() {
	
	}
	
	public boolean ativarUsuario(Usuario usuario) {
		return this.situacao = true;
	}
	
	public boolean inativaUsuario(Usuario usuario) {
		return this.situacao = false;
	}

	public static Usuario create(String nome, String cpf, String senha, String email) {
		
		Usuario usuario = new Usuario(nome, cpf, senha, new Date(), email, true);
		
		return usuario;
	}
	
	public void addPerfil(UsuarioPerfil usuarioPerfil) {
		
		if(usuarioPerfilList != null) {
			usuarioPerfil.setUsuario(this);
			usuarioPerfilList.add(usuarioPerfil);
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public List<UsuarioPerfil> getUsuarioPerfilList() {
		return usuarioPerfilList;
	}

	public void setUsuarioPerfilList(List<UsuarioPerfil> usuarioPerfilList) {
		this.usuarioPerfilList = usuarioPerfilList;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", senha=" + senha + ", email=" + email
				+ ", dataCadastro=" + dataCadastro + ", situacao=" + situacao + "]";
	}
}
