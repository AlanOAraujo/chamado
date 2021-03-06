CREATE TABLE usuario(
    id_usuari_usr SERIAL PRIMARY KEY ,
    nm_usrnom_usr TEXT NOT NULL, 
    nm_usrcpf_usr TEXT NOT NULL,
    cd_usenha_usr TEXT NOT NULL,
    nm_uemail_usr TEXT NOT NULL,
    dt_udtcad_usr TIMESTAMP NOT NULL DEFAULT now(),
    bl_usrsit_usr BOOLEAN NOT NULL DEFAULT true);

comment on table usuario is 'Tabela que armazena as informações de usuários';
comment on column usuario.id_usuari_usr is 'Identificador da tabela de usuário';
comment on column usuario.nm_usrnom_usr is 'Nome do usuário';
comment on column usuario.nm_usrcpf_usr is 'CPF do usuário';
comment on column usuario.cd_usenha_usr is 'Senha do usuário';
comment on column usuario.nm_uemail_usr is 'E-mail do usuário';
comment on column usuario.dt_udtcad_usr is 'Data da criação da conta usuário';
comment on column usuario.bl_usrsit_usr is 'Campo referente a situação da conta, por padrão esta como true';

CREATE TABLE perfil(
	id_perfil_prf SERIAL PRIMARY KEY,
	ds_pfdesc_prf TEXT NOT NULL);

comment on table perfil is 'Tabela que armazena as informações do perfil dos usuários';
comment on column perfil.ds_pfdesc_prf is 'Campo texto do nome do perfil';

CREATE TABLE rotina(
	id_rotina_rtn SERIAL PRIMARY KEY,
	nm_rtnome_rtn TEXT not null);
comment on table rotina is 'Tabela que armazena as informações para definir o menu permitido por perfil';
comment on column rotina.id_rotina_rtn is 'Identificador da tabela rotina';
comment on column rotina.nm_rtnome_rtn is 'Campo texto para definir o nome da rotina';

CREATE TABLE atributo(
	id_atribu_atr SERIAL PRIMARY KEY,
	id_rotina_rtn BIGINT not null,
	nm_atnome_atr TEXT not null,
	cd_codatr_atr TEXT not null,
	constraint rotina_fk foreign key(id_rotina_rtn) references rotina(id_rotina_rtn)
);

comment on table atributo is 'Tabela que armazena as informações das permições que define as rotinas';
comment on column atributo.id_atribu_atr is 'Identificador da tabela atributo';
comment on column atributo.id_rotina_rtn is 'Identificador do relacionamento com a tabela rotina';
comment on column atributo.nm_atnome_atr is 'Campo referenciando o nome do atributo';
comment on column atributo.cd_codatr_atr is 'Campo text para Identificar os atributos em tela seguindo um padrao EXC_USU ';


CREATE TABLE usuario_perfil(
	id_usrprf_upf SERIAL PRIMARY KEY,
	id_usuari_usr BIGINT not null,
	id_perfil_prf BIGINT not null,
	CONSTRAINT fk_usuario FOREIGN KEY(id_usuari_usr) REFERENCES usuario(id_usuari_usr),
	CONSTRAINT fk_perfil FOREIGN KEY(id_perfil_prf) REFERENCES perfil(id_perfil_prf));

comment on table usuario_perfil is 'Tabela com as refencias das tabelas usuario e perfil';
comment on column usuario_perfil.id_usrprf_upf is 'Identificador da tabela usuario_perfil';
comment on column usuario_perfil.id_usuari_usr is 'Identificador do relacionamento com a tabela usuario';
comment on column usuario_perfil.id_perfil_prf is 'Identificador do relacionamento com a tabela perfil';


CREATE TABLE perfil_rotina_atributo(
	id_prfrtn_prt SERIAL PRIMARY KEY,
	id_rotina_rtn BIGINT not null,
	id_perfil_prf BIGINT not null,
	id_atribu_atr BIGINT not null,
	CONSTRAINT fk_rotina_id FOREIGN KEY(id_rotina_rtn) REFERENCES rotina(id_rotina_rtn),
	CONSTRAINT fk_perfil_id FOREIGN KEY(id_perfil_prf) REFERENCES perfil(id_perfil_prf),
	constraint fk_perfil_rot_atr foreign key(id_atribu_atr) references atributo(id_atribu_atr));

comment on table perfil_rotina_atributo is 'Tabela com as refencias das tabelas perfil, rotina e atributo';
comment on column perfil_rotina_atributo.id_prfrtn_prt is 'Identificador da tabela perfil_rotina_atributo';
comment on column perfil_rotina_atributo.id_rotina_rtn is 'Identificador do relacionamento com a tabela rotina';
comment on column perfil_rotina_atributo.id_perfil_prf is 'Identificador do relacionamento com a tabela perfil';
comment on column perfil_rotina_atributo.id_atribu_atr is 'Identificador do relacionamento com a tabela atributo';

create type status_chamado as enum ('ABERTO', 'EM_ATENDIMENTO', 'FECHADO');
comment on type status_chamado is 'Tabela do tipo enum para definir os status do chamado';

create type tipo_chamado as enum('INCIDENTE', 'SOLICITACAO', 'RELATORIO', 'DUVIDA');
comment on type tipo_chamado is 'Tabela do tipo enum para informar qual o tipo do caso';

create table chamado(
	id_chamad_chm SERIAL primary key,
	ds_chmttl_chm text not null,
	ds_chmdes_chm text not null,
	ds_chmdfn_chm text,
	id_usuari_usr BIGINT not null,
	dt_dtaber_chm TIMESTAMP NOT NULL DEFAULT now(),
	dt_dtfech_chm TIMESTAMP,
	dt_dtprev_chm TIMESTAMP,
	status status_chamado not null,
	tipo tipo_chamado not null,
	id_usufec_chm BIGINT,
	CONSTRAINT id_usuari_usr FOREIGN KEY(id_usuari_usr) references usuario(id_usuari_usr),
	CONSTRAINT id_usufec_chm FOREIGN KEY(id_usuari_usr) references usuario(id_usuari_usr));

comment on table chamado is 'Tabela para armazenar as ocorrencias enviadas pelos usuarios';
comment on column chamado.id_chamad_chm is 'Identificador da tabela chamado';
comment on column chamado.ds_chmttl_chm is 'Campo para informar o titulo do chamado';
comment on column chamado.ds_chmdes_chm is 'Campo que guarda as informacoes detalhadas do chamado';
comment on column chamado.ds_chmdfn_chm is 'Campo que guarda a descrição do por que finalizou o chamado';
comment on column chamado.id_usuari_usr is 'Identificador do relacionamento com a tabela usuário';
comment on column chamado.dt_dtaber_chm is 'Armazena a data de aberta do chamado';
comment on column chamado.dt_dtfech_chm is 'Armazena a data de fechamento do chamado';
comment on column chamado.dt_dtprev_chm is 'Armazena a data de previsão para concluir o chamado';
comment on column chamado.status is 'Refencia da tabela enum status_chamado';
comment on column chamado.tipo is 'Refencia da tabela enum tipo_chamado';
comment on column chamado.id_usufec_chm is 'Campo que refencia novamente a tabela usuario, mais esta com o intuito de armazena o usuario que finalizou o chamado';

	
create table historico_chamado(
	id_hischm_his serial primary key,
	id_chamad_chm BIGINT not null,
	ds_coment_his text not null,
	id_usuari_usr BIGINT not null,
	dt_coment_his TIMESTAMP not null,
	mm_hisanx_his BYTEA,
	ds_nomanx_his text,
	constraint id_chamad_chm foreign key(id_chamad_chm) references chamado(id_chamad_chm),
	constraint id_usuari_usr foreign key(id_usuari_usr) references usuario(id_usuari_usr));

comment on table historico_chamado is 'Tabela para armazenar todo o historia de informacoes do chamado';
comment on column historico_chamado.id_hischm_his is 'Identificador da tabela historico';
comment on column historico_chamado.id_chamad_chm is 'Identificador da refencia a tabela chamado';
comment on column historico_chamado.dt_coment_his is 'Campo que guarda as informacoes detalhadas do historico de conversas';
comment on column historico_chamado.id_usuari_usr is 'Identificador do relacionamento com a tabela usuário';
comment on column historico_chamado.dt_coment_his is 'Armazena a data dos comentarios do historico';
comment on column historico_chamado.mm_hisanx_his is 'Armazena os arquivos enviados em anexo';
comment on column historico_chamado.ds_nomanx_his is 'Campo destinado a guardar o nome do arquivo em anexo';

create table marcacao_chamado(
	id_marchm_mrc serial primary key,
	id_chamad_chm BIGINT not null,
	ds_mrcdes_mrc text not null,
	constraint id_chamad_chm foreign key(id_chamad_chm) references chamado(id_chamad_chm));

comment on table marcacao_chamado is 'Tabela para armazenar os marcadores dos chamados';
comment on column marcacao_chamado.id_marchm_mrc is 'Identificador da tabela marcacao';
comment on column marcacao_chamado.id_chamad_chm is 'Identificador da refencia a tabela chamado';
comment on column marcacao_chamado.ds_mrcdes_mrc is 'Campo que guarda as informacoes dos nomes marcadores';

insert into usuario(nm_usrnom_usr, nm_usrcpf_usr, cd_usenha_usr,
nm_uemail_usr)
values ( 'Administrador', '38016846017', '123456', 'administrador@pagga.com.br');

insert into perfil(ds_pfdesc_prf)
values ('Administrador');

insert into rotina(nm_rtnome_rtn) values ('Cadastro');

insert into rotina(nm_rtnome_rtn) values ('Usuário');

insert into rotina(nm_rtnome_rtn) values ('Perfil');

insert into rotina(nm_rtnome_rtn) values ('Indicadores');

insert into rotina(nm_rtnome_rtn) values ('Abrir Ticket');

insert into rotina(nm_rtnome_rtn) values ('Acompanhar Ticket');

insert into usuario_perfil(id_usuari_usr, id_perfil_prf)
values ((select id_usuari_usr from usuario where nm_usrcpf_usr = '38016846017'), 
(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'));

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Cadastro'),
	'Cadastro de Usuários', 'CAD_USR');

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Cadastro'),
	'Cadastro de Perfis', 'CAD_PRF');	
	
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Acesso a Usuários', 'ACS_USR');
	
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Editar Usuário', 'EDT_USR');
		
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Alterar Situação do Usuário', 'STU_USR');
			
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Alterar E-mail do Usuário', 'ATR_EML');
			
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Alterar Senha do Usuário', 'ATR_SNH');
		
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Adicionar/Remover Perfil de Acesso ao Usuário', 'PRF_ACS');
		
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir Alterar Nome do Usuário', 'ATR_NOM');

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
	'Permitir criar novo Usuário', 'NOV_USR');

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
	'Permitir Excluir Perfil', 'EXC_PRF');	
		
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
	'Permitir Adicionar/Remover Rotina ao Perfil', 'ADD_RTN');
		
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
	'Permitir Alterar Descrição ao Perfil', 'DSC_PRF');

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
	'Permitir criar novo perfil', 'NOV_PRF');
		
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Indicadores'),
	'Permitir Acesso a Indicadores', 'ACS_IND');
			
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Indicadores'),
	'Permitir Acesso a todos os tickets', 'ALL_TCK');
	
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Indicadores'),
	'Permitir Pesquisar por Todos os Tickets', 'SCH_ALL_TCK');

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Abrir Ticket'),
	'Permitir Abertura de Ticket', 'OPN_TCK');

insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Acompanhar Ticket'),
	'Permitir Finalizar Tickets', 'FNZ_TCK');
	
insert into atributo(id_rotina_rtn, nm_atnome_atr, cd_codatr_atr)
values ((select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Acompanhar Ticket'),
	'Permitir Solucionar Tickets', 'SLN_TCK');

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Cadastro'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'CAD_USR')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Cadastro'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'CAD_PRF')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ACS_USR')
	);
	
insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'EDT_USR')
	);	

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'STU_USR')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ATR_EML')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ATR_SNH')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'PRF_ACS')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ATR_NOM')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Usuário'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'NOV_USR')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'NOV_PRF')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'EXC_PRF')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ADD_RTN')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Perfil'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'DSC_PRF')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Indicadores'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ACS_IND')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Indicadores'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'ALL_TCK')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Indicadores'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'SCH_ALL_TCK')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Abrir Ticket'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'OPN_TCK')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Acompanhar Ticket'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'FNZ_TCK')
	);

insert into perfil_rotina_atributo(id_rotina_rtn, id_perfil_prf, id_atribu_atr) 
	values(
		(select id_rotina_rtn from rotina where nm_rtnome_rtn = 'Acompanhar Ticket'),
		(select id_perfil_prf from perfil where ds_pfdesc_prf = 'Administrador'),
		(select id_atribu_atr from atributo where cd_codatr_atr = 'SLN_TCK')
	);