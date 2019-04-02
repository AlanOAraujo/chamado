package br.com.pagga.chamado.service.role;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.LoginService;

public class ControlePermissoes {
	
	protected static List<Atributo> listPermissoes = new ArrayList<>();

	@Inject
	public ControlePermissoes() {
	}
	
	/* Permitir Pesquisar por Todos os Tickets COD. SCH_ALL_TCK */
	public static String permissaoSchAllTck(String stringFiltro, UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissaoPesquisa = " ";
		String filtroPermissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("SCH_ALL_TCK")) {
				permissaoPesquisa = atributo.getCodAtributo();
			}
		}
		
		if(stringFiltro != null && permissaoPesquisa.equals("SCH_ALL_TCK")) {
			filtroPermissao = stringFiltro;
		}else {
			filtroPermissao = null;
		}
		
		return filtroPermissao;
	}

	
	/* Permitir Alterar Senha do Usuario COD. ATR_SNH */
	public static String permissaoAtrSnh(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ATR_SNH")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
		
	}
	
	/* Permitir Alterar Email do Usuario COD. ATR_EML */
	public static String permissaoAtrEml(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ATR_EML")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
		
	}
	
	/* Permitir Alterar Nome do Usuario COD. ATR_NOM */
	public static String permissaoAtrNom(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ATR_NOM")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
		
	}
	
	/* Permitir Adicionar/Remover Perfil de Acesso ao Usuario COD. PRF_ACS */
	public static String permissaoPrfAcs(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("PRF_ACS")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
		
	}
	
	/* Permitir Editar Usuario COD. EDT_USR */
	public static String permissaoEdtUsr(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("EDT_USR")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
	}
	
	/* Permitir Alterar Situacao do Usuário COD. STU_USR */
	public static String permissaoStuUsr(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("STU_USR")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
	}
	
	/* Cadastro de Usuarios COD. CAD_USR */
	public static String permissaoCadUsr(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("CAD_USR")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
	}
	
	/* Cadastro de Perfis COD. CAD_PRF */
	public static String permissaoCadPrf(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("CAD_PRF")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
	}
	
	/* Permitir Excluir Perfil COD. EXC_PRF */
	public static String permissaoExcPrf(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("EXC_PRF")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
	}
	
	/* Permitir Adicionar/Remover Rotina ao Perfil COD. ADD_RTN */
	public static String permissaoAddRtn(UsuarioLogado usuarioLogado) {
		
		listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissao = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ADD_RTN")) {
				permissao = atributo.getCodAtributo();
			}
		}
		
		return permissao;
	}
	
}
