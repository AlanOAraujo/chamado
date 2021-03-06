var Pagga = Pagga || {};

Pagga.perfil = {};

Pagga.chamado = {};

Pagga.historico = {};

Pagga.usuario = {};

Pagga.perfilRotina = {};

Pagga.login = {};

Pagga.atributo = {};

Pagga.perfil.salva = function(codigo, descricao, perfilAtributoList){

	var perfil = {};
	
	if (codigo == ""){
		perfil = {
			descricao : descricao,
			perfilRotinaAtributo : []
		}
		
		perfilAtributoList.forEach(function(value){
			
			var attr = {
					id : value
			}
			perfil.perfilRotinaAtributo.push({
				atributo : attr
			});
		});
	} else {
		perfil = {
			id : codigo,
			descricao : descricao,
			perfilRotinaAtributo : []
		}
		perfilAtributoList.forEach(function(value){
			
			var attr = {
					id : value
			}
			perfil.perfilRotinaAtributo.push({
				atributo : attr
			});
		});
	}

	return $.ajax({
		  contentType: 'application/json',
		  type: "POST",
		  url: '../perfil',
		  data: JSON.stringify(perfil),
		  dataType: 'json'
	});
	
}

Pagga.perfil.exclui = function(codigo){

	var obj = {
			id : codigo
	};
	
	return $.ajax({
		  contentType: 'application/json',
		  type: "DELETE",
		  url: '../perfil',
		  data: JSON.stringify(obj),
		  dataType: 'json'
	});
	
}

Pagga.perfil.lista = function( ){
	let request = $.ajax({
	  method: "GET",
	  url: "../perfil",
	  dataType: "json"
	});
	
	return request; 
	
}

Pagga.perfil.lista.rotina = function( ){
	let request = $.ajax({
	  method: "GET",
	  url: "../perfil/rotina",
	  dataType: "json"
	});
	
	return request; 
}

Pagga.perfil.busca = function(descricao){
	
	var data = {
			descricao : descricao
			}
	
	return $.ajax({
		type: "POST",
		url: "../perfil/descricao",
		data: data,
		dataType: "text"
	});
}

Pagga.perfil.busca.id = function(id) {
	
	var data = {
			id : id
			}
	
	return $.ajax({
		type: "POST",
		url: "../perfil/id",
		data: data,
		dataType: "text"
	});
}

Pagga.usuario.salva = function(nome, email, cpf, senha, perfis){
	
	var data = {
			nome : nome,
			email : email,
			cpf : cpf,
			senha : senha,
			usuarioPerfilList : []
	};
	
	perfis.forEach(function(perfil) {
		data.usuarioPerfilList.push({
			perfil : {
				id : perfil
			}
		});
	});
	
	return $.ajax({
		  contentType: 'application/json',
		  type: "POST",
		  url: '../usuario',
		  data: JSON.stringify(data),
		  dataType: 'json'
	});
}

Pagga.usuario.altera = function(id, nome, email, senha, perfis){
	
	var data = {
			id : id,
			nome : nome,
			email : email,
			senha : senha,
			usuarioPerfilList : []
	};
	
	perfis.forEach(function(perfil) {
		data.usuarioPerfilList.push({
			perfil : {
				id : perfil
			}
		});
	});
	
	return $.ajax({
		  contentType: 'application/json',
		  type: "POST",
		  url: '../usuario/altera',
		  data: JSON.stringify(data),
		  dataType: 'json'
	});
}

Pagga.usuario.lista = function() {
	
	return $.ajax({
	  method: "GET",
	  url: "../usuario",
	  dataType: "json"
	});
	
}

Pagga.usuario.busca = function(id) {
	
	var data = {
			id : id
	}
	
	return $.ajax({
		type: "POST",
		url: "../usuario/id",
		data: data,
		dataType: "text"
	});
	
}

Pagga.usuario.busca.nome = function(nome) {
	
	var data = {
			nome : nome
	}
	
	return $.ajax({
		type: "POST",
		url: "../usuario/nome",
		data: data,
		dataType: "text"
	});
	
}

Pagga.usuario.ativo = function(id, nome, email, cpf){
	
	var data = {
		id : id,
		nome : nome,
		email : email,
		cpf : cpf,
	};
	
	let request = $.ajax({
		contentType: "application/json",
		type: "POST",
		url: "../usuario/ativa",
		data : JSON.stringify(data),
		dataType: "json"
	});
	
	return request;
}

Pagga.usuario.inativa = function(id, nome, email, cpf){
	
	var data = {
		id : id,
		nome : nome,
		email : email,
		cpf : cpf,
	};
	
	let request = $.ajax({
		contentType: "application/json",
		type: "POST",
		url: "../usuario/inativa",
		data : JSON.stringify(data),
		dataType: "json"
	});
	
	return request;
}


Pagga.chamado.lista = function( ){
	
	let request = $.ajax({
	  method: "GET",
	  url: "../chamado/historico",
	  dataType: "json"
	});
	
	return request; 
}


Pagga.chamado.status = function(status){
	
	let data = {
		status : status
	}
	
	let request = $.ajax({
	  method: "POST",
	  url: "../chamado/status",
	  data: data,
	  dataType: "text"
	});
	
	return request; 
}

Pagga.chamado.busca = function(filtro){
	let request = $.ajax({
		contentType: "application/json",
		type: "POST",
		url: "../chamado/filtro",
		data : JSON.stringify(filtro),
		dataType: "json"
	});
	
	return request;
}

Pagga.chamado.busca.id = function(id){
	
	let data = {
			id : id
	}
	
	return $.ajax({
		type: "POST",
		url: "../chamado/id",
		data : data,
		dataType: "text"
	});
}

Pagga.chamado.salvar = function(titulo, descricao, tipo, usuario, marcacoes){
	
	let chamado = {
			titulo : titulo,
			descricao : descricao,
			tipo : tipo,
			usuario : {
				id : usuario
			},
			marcacaoChamadoList : []
	}
	arrayMarcacoes = marcacoes.split(',');
	
	arrayMarcacoes.forEach(function(value){
		chamado.marcacaoChamadoList.push({
			descricao : value
		});
	});

	let request = $.ajax({
		contentType: "application/json",
		type: "POST",
		url: "../chamado",
		data : JSON.stringify(chamado),
		dataType: "json"
	});
	
	return request;
}

Pagga.chamado.anexo = function(data){
	let request = $.ajax({
		url: '../chamado/upload',
		data: data,
        processData: false,
        contentType: false,
        type: 'POST'
	});
	
	return request;
	
}

Pagga.historico.lista = function( ){
	
	return $.ajax({
	  method: "GET",
	  url: "../chamado/historico",
	  dataType: "json"
	});
}

Pagga.historico.salvar = function(e) {
	let request = $.ajax({
		type: "POST",
		url: '../chamado/historico',
		data: e,
		processData: false,
        contentType: false,
        cache: false
	});
	
	return request;
}

Pagga.historico.finalizar = function(e) {
	
	return $.ajax({
		type: "POST",
		url: '../chamado/finalizar',
		data: e,
		processData: false,
        contentType: false,
        cache: false
	});
}

Pagga.perfilRotina.salvar = function(idPerfil, idAtributo) {
	
	var perfilRotinaAtributo = {
			perfil:{
				id : idPerfil
			},
		    atributo:{
		    	id : idAtributo
		    }
	};
	
	let request = $.ajax({
		contentType: 'application/json',
		type: "POST",
		url: '../perfil-rotina-atributo',
		data: JSON.stringify(perfilRotinaAtributo),
		dataType: 'json'
	});
	
	return request;
}

Pagga.perfilRotina.exclui = function(id){

	var perfil = {
			id : id
	};
	
	return $.ajax({
		  contentType: 'application/json',
		  type: "DELETE",
		  url: '../perfil-rotina-atributo/perfil',
		  data: JSON.stringify(perfil),
		  dataType: 'json'
	});
	
}

Pagga.login.autentifica = function(cpf, senha) {
	
	var usuario = {
		cpf : cpf,
    	senha : senha
    }
	
	let request = $.ajax({
		contentType: 'application/json',
		type: "POST",
		url: '../login',
		data: JSON.stringify(usuario),
		dataType: 'json'
	});
	
	return request;
}

Pagga.login.verificaLogin = function( ){
	return $.ajax({
	  method: "GET",
	  url: "../login/verifica",
	  dataType: "json"
	});
}

Pagga.login.desloga = function( ){
	return $.ajax({
	  method: "GET",
	  url: "../login/desloga",
	  dataType: "json"
	});
}

Pagga.login.esquece = function(e){
	return $.ajax({
		type: "POST",
		url: '../login/senha',
		data: e,
		processData: false,
        contentType: false,
        cache: false
	});
}

Pagga.login.usuarioRole = function(){
	return $.ajax({
	  method: "GET",
	  url: "../login/role",
	  dataType: "json"
	});
}

Pagga.atributo.lista = function(){
	return $.ajax({
	  method: "GET",
	  url: "../atributo",
	  dataType: "json"
	});
}

Pagga.usuario.alterarSenha = function(e){
	return $.ajax({
		type: "POST",
		url: '../usuario/alterasenha',
		data: e,
		processData: false,
        contentType: false,
        cache: false
	});
}
