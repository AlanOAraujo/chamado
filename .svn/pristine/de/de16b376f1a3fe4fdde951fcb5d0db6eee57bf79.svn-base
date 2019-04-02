package br.com.pagga.chamado.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.Path;

import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pagga.chamado.dao.ChamadoDAO;
import br.com.pagga.chamado.dao.HistoricoChamadoDAO;
import br.com.pagga.chamado.dao.MarcacaoChamadoDAO;
import br.com.pagga.chamado.dao.filter.ChamadoFilter;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.HistoricoChamado;
import br.com.pagga.chamado.model.MarcacaoChamado;
import br.com.pagga.chamado.model.StatusChamado;
import br.com.pagga.chamado.model.TipoChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.service.role.ControlePermissoes;
import br.com.pagga.chamado.util.StringUtil;

@SuppressWarnings("unused")
public class ChamadoService {

	private ChamadoDAO chamadoDAO;
	
	private HistoricoChamadoDAO historicoChamadoDAO;
	
	private MarcacaoChamadoDAO marcacaoChamadoDAO;
	
	private Chamado chamado;
	
	private MarcacaoChamado marcacaoChamado;
	
	@Inject
	private UsuarioLogado usuarioLogado;
	
	private static final Logger logger = LoggerFactory.getLogger(ChamadoService.class);

	@Inject
	public ChamadoService(ChamadoDAO chamadoDAO, MarcacaoChamadoDAO marcacaoChamadoDAO, HistoricoChamadoDAO historicoChamadoDAO) {

		this.chamadoDAO = chamadoDAO;
		this.marcacaoChamadoDAO = marcacaoChamadoDAO;
		this.historicoChamadoDAO = historicoChamadoDAO;
	}

	public void aberturaChamado(Chamado chamado) {
		
		if ( StringUtils.isBlank(chamado.getTitulo()))
			throw new BusinessException("Titulo do chamado é obrigatório");
		
		if ( StringUtils.isBlank(chamado.getDescricao()))
			throw new BusinessException("Descrição do chamado é obrigatório");
		
		if(chamado.getUsuario() == null)
			throw new BusinessException("Usuario não pode ser nulo");
		
		if(chamado.getMarcacaoChamadoList() == null || chamado.getMarcacaoChamadoList().isEmpty()) {
			throw new BusinessException("Por favor adicionar tag");
		}
		
		if(chamado.getDataAbertura() == null) {
			chamado.setDataAbertura(new Date());
		}
		
		if(chamado.getStatus() == null) {
			chamado.setStatus(StatusChamado.ABERTO);
		}
		
		chamadoDAO.save(chamado);
		
		for (MarcacaoChamado marcacaoChamado : chamado.getMarcacaoChamadoList()) {
			marcacaoChamado.setChamado(chamado);
			marcacaoChamadoDAO.save(marcacaoChamado);
		}
		
		if(!chamado.getHistoricoChamadoList().isEmpty() || chamado.getHistoricoChamadoList() != null) {
			for (HistoricoChamado historicoChamado : chamado.getHistoricoChamadoList()) {
				historicoChamado.setChamado(chamado);
				salvaHistoricoChamado(historicoChamado);
			}
		}
		
		logger.info("Chamado {} aberto", chamado.getId());
		
	}
	
	public void salvarChamado(Chamado chamado) {
		try {
			chamadoDAO.save(chamado);
			logger.info("chamado " + chamado.getId() + "salvo com sucesso");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void salvaHistoricoChamado(HistoricoChamado historicoChamado) {
		try {
			historicoChamadoDAO.save(historicoChamado);
			logger.info("Historico "+ historicoChamado.getId() + " relacionado ao chamado " + historicoChamado.getChamado().getId() 
					+ " aberto com sucesso");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void acrescentarComentarioChamado(HistoricoChamado historicoChamado) {
		
		if ( StringUtils.isBlank(historicoChamado.getComentario())) {
			throw new BusinessException("É preciso preencher o campo de comentório! ");
		}
		
		List<Atributo> listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		try {
			
			chamado = historicoChamado.getChamado();
			chamadoDAO.findById(chamado.getId());
			
			if(chamado.getStatus().equals(StatusChamado.ABERTO) && chamado.getDataPrevisao() != null){
				
				String permissaoSolucionarTicket = " ";
				
				for (Atributo atributo : listPermissoes) {
					if(atributo.getCodAtributo().equals("SLN_TCK")) {
						permissaoSolucionarTicket = atributo.getCodAtributo();
					}
				}
				
				if(permissaoSolucionarTicket != " " 
						&& permissaoSolucionarTicket.equals("SLN_TCK")) {
					chamado.setStatus(StatusChamado.EM_ATENDIMENTO);
					chamadoDAO.save(chamado);
				}else {
					throw new BusinessException("Você não possui a permissão. Caso tenho algo errado entre em contato com o administrador");
				}
				
			}
			
			historicoChamadoDAO.save(historicoChamado);
			logger.info("Historico salvo");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void finalizandoChamado(HistoricoChamado historicoChamado) {
		
		if ( StringUtils.isBlank(historicoChamado.getComentario()))
			throw new BusinessException("É preciso preencher o campo de comentário! ");
		
		List<Atributo> listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissaoFechar = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("FNZ_TCK")) {
				permissaoFechar = atributo.getCodAtributo();
			}
		}
		
		if(permissaoFechar != " " 
				&& permissaoFechar.equals("FNZ_TCK")) {
			
			chamado = historicoChamado.getChamado();
			
			if(!chamado.getStatus().equals(StatusChamado.FECHADO)) {
				chamado.setStatus(StatusChamado.FECHADO);
				
				chamadoDAO.save(chamado);
				
				historicoChamadoDAO.save(historicoChamado);
				logger.info("Chamado Finalizado");
			}else {
				throw new BusinessException("Este chamado já esta finalizado.");
			}
		}else {
			throw new BusinessException("Você não possui permissão para finalizar tickets");
		}
	}
	
	public List<Chamado> listaChamado() {
		try {
			return chamadoDAO.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public List<HistoricoChamado> listaHistorico(){
		try {
			return historicoChamadoDAO.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	
	public List<Chamado> buscaPorStatus(StatusChamado status) {
		try {
			return chamadoDAO.findByStatus(status);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public Chamado buscaPorID(long id) {
		try {
			return chamadoDAO.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public HistoricoChamado buscaHistoricoID(long id) {
		try {
			return historicoChamadoDAO.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public List<Chamado> listaPorFiltro(int start, int length, StatusChamado status, String filtro, UsuarioLogado usuarioLogado) {
		try {
			
			String filtro2 = ControlePermissoes.permissaoSchAllTck(filtro, usuarioLogado);
			
			ChamadoFilter chamadoFilter = aplicaFiltro(filtro2, status);			
			return chamadoDAO.findByFilter(start, length, status, chamadoFilter, usuarioLogado);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}
	
	private static ChamadoFilter aplicaFiltro(String filtro, StatusChamado status) {
		
		ChamadoFilter chamadoFilter = new ChamadoFilter();
		
		if("Solicitação".equals(filtro)) {
			
			chamadoFilter.withTipo(TipoChamado.SOLICITACAO);
			
		}else if("Incidente".equals(filtro)) {
			
			chamadoFilter.withTipo(TipoChamado.INCIDENTE);
			
		}else if( "Relatório".equals(filtro)) {
			
			chamadoFilter.withTipo(TipoChamado.RELATORIO);
			
		}else if("Dúvida".equals(filtro)) {
			
			chamadoFilter.withTipo(TipoChamado.DUVIDA);
			
		}else if ( !StringUtil.isNullOrEmpty(filtro) && filtro.matches("[a-zA-Z_].*")) {
			if(status == StatusChamado.ABERTO || status == StatusChamado.EM_ATENDIMENTO) {
				chamadoFilter.withNomeUsuario(filtro);
			} else {
				chamadoFilter.withNomeUsuarioFechamento(filtro);
			}
			
		}else if(  !StringUtil.isNullOrEmpty(filtro) && filtro.matches("[/-_%*\0-9].*")) {
			if (filtro.matches("[0-9]+")) {
				long idLong = Long.parseLong(filtro);
				chamadoFilter.withId(idLong);
			}else {
				if(status == StatusChamado.ABERTO) {
					
					chamadoFilter.with_DataAbertura(filtro);
				
				}else if(status == StatusChamado.EM_ATENDIMENTO){
					
					chamadoFilter.withDataPrevisao(filtro);
					
				}else {
					
					chamadoFilter.with_DataFechamento(filtro);
					
				}
			}
		}
		
		return chamadoFilter;
	}
	
	public Long countChamado(String filtro, StatusChamado status, UsuarioLogado usuarioLogado) {
		
		try {
			
			ChamadoFilter chamadoFilter = aplicaFiltro(filtro, status);
			
			return chamadoDAO.countChamadoFiltro(status, chamadoFilter, usuarioLogado);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return 0l;
	}

	public List<Chamado> listaChamadoPorUsuario(Usuario usuario) {
		try {
			return chamadoDAO.findByUsuario(usuario);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public Usuario buscaUsarioID(long id) {
		try {
			return chamadoDAO.getUsuarioDAO().findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	/*public static String permissaoFiltro(String stringFiltro, UsuarioLogado usuarioLogado) {
		
		List<Atributo> listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
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
	}*/
	
}
