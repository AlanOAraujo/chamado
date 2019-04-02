package br.com.pagga.chamado.model;

import java.io.Serializable;

public interface Model<ID extends Serializable> extends Serializable {

	public ID getId();
}
