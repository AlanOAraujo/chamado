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
@Table(name="rotina")
public class Rotina implements Model<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "rotina_id_rotina_rtn_seq", sequenceName = "rotina_id_rotina_rtn_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="rotina_id_rotina_rtn_seq")
	@Column(name = "id_rotina_rtn")
	private Long id;
	
	@Column(name="nm_rtnome_rtn")
	private String nome;
	
	@OneToMany(mappedBy = "rotina")
	private List<Atributo> atributoList = new ArrayList<>();
	
	public Rotina(String nome) {
		this.nome = nome;
	}
	
	public Rotina() { }
	
	public void adicionarAtributos(Atributo atributo) {
		
		if(atributo != null) {
			atributo.setRotina(this);
			this.getAtributoList().add(atributo);
		}
		
	}
	
	public static Rotina createRotina(String nome) {
		
		Rotina rotina = new Rotina(nome);
		return rotina;
		
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

	public List<Atributo> getAtributoList() {
		return atributoList;
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
		Rotina other = (Rotina) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rotina [id=" + id + ", nome=" + nome + "]";
	}

}
