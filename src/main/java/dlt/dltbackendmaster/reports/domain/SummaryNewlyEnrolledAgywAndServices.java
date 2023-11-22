package dlt.dltbackendmaster.reports.domain;

/**
 * Novos Agyw, vulnerabilidades e sub-servicos
 * 
 * @author Francisco Macuacua
 *
 */
public class SummaryNewlyEnrolledAgywAndServices {
	private String index;
	private String provincia;
	private String distrito;
	private String nui;
	private String idade_actual;
	private String faixa_etaria_actual;
	private String numero_vulnerabilidades;
	private String agyw_prev;
	private String referencias_clinicas_nao_atendidas;
	private String referencias_comunitarias_nao_atendidas;
	private String sessoes_recursos_sociais;
	private String data_ultima_sessao_recursos_sociais;
	private String sessoes_prevencao_hiv;
	private String data_ultima_sessao_hiv;
	private String sessoes_prevencao_vbg;
	private String data_ultima_sessao_vbg;
	private String sessoes_educativas_incl_ssr_na_saaj;
	private String data_ultima_sessao_saaj;
	private String sessoes_literacia_financeira;
	private String data_ultima_sessao_literacia_financeira;
	private String aconselhamento_testagem_saude;
	private String data_ultima_sessao_ats;
	private String promocao_provisao_preservativos;;
	private String data_ultima_sessao_promocao_provisao_preservativos;
	private String promocao_provisao_contracepcao;
	private String data_ultima_sessao_promocao_provisao_contracepcao;
	private String abordagens_socio_economicas_combinadas;
	private String data_ultima_sessao_abordagens_socio_economicas_combinadas;
	private String subsidio_escolar;
	private String data_ultima_sessao_subsidio_escolar;
	private String cuidados_pos_violencia_comunitarios;;
	private String data_ultima_sessao_cuidados_pos_violencia_comunitarios;
	private String cuidados_pos_violencia_clinicos;
	private String data_ultima_sessao_cuidados_pos_violencia_clinicos;
	private String outros_servicos_saaj;
	private String data_ultima_sessao_outros_servicos_saaj;
	private String prep;
	private String data_ultima_sessao_prep;

	public SummaryNewlyEnrolledAgywAndServices(String index, String provincia, String distrito, String nui,
			String idade_actual, String faixa_etaria_actual, String numero_vulnerabilidades, String agyw_prev,
			String referencias_clinicas_nao_atendidas, String referencias_comunitarias_nao_atendidas,
			String sessoes_recursos_sociais, String data_ultima_sessao_recursos_sociais, String sessoes_prevencao_hiv,
			String data_ultima_sessao_hiv, String sessoes_prevencao_vbg, String data_ultima_sessao_vbg,
			String sessoes_educativas_incl_ssr_na_saaj, String data_ultima_sessao_saaj,
			String sessoes_literacia_financeira, String data_ultima_sessao_literacia_financeira,
			String aconselhamento_testagem_saude, String data_ultima_sessao_ats, String promocao_provisao_preservativos,
			String data_ultima_sessao_promocao_provisao_preservativos, String promocao_provisao_contracepcao,
			String data_ultima_sessao_promocao_provisao_contracepcao, String abordagens_socio_economicas_combinadas,
			String data_ultima_sessao_abordagens_socio_economicas_combinadas, String subsidio_escolar,
			String data_ultima_sessao_subsidio_escolar, String cuidados_pos_violencia_comunitarios,
			String data_ultima_sessao_cuidados_pos_violencia_comunitarios, String cuidados_pos_violencia_clinicos,
			String data_ultima_sessao_cuidados_pos_violencia_clinicos, String outros_servicos_saaj,
			String data_ultima_sessao_outros_servicos_saaj, String prep, String data_ultima_sessao_prep) {
		super();
		this.index = index;
		this.provincia = provincia;
		this.distrito = distrito;
		this.nui = nui;
		this.idade_actual = idade_actual;
		this.faixa_etaria_actual = faixa_etaria_actual;
		this.numero_vulnerabilidades = numero_vulnerabilidades;
		this.agyw_prev = agyw_prev;
		this.referencias_clinicas_nao_atendidas = referencias_clinicas_nao_atendidas;
		this.referencias_comunitarias_nao_atendidas = referencias_comunitarias_nao_atendidas;
		this.sessoes_recursos_sociais = sessoes_recursos_sociais;
		this.data_ultima_sessao_recursos_sociais = data_ultima_sessao_recursos_sociais;
		this.sessoes_prevencao_hiv = sessoes_prevencao_hiv;
		this.data_ultima_sessao_hiv = data_ultima_sessao_hiv;
		this.sessoes_prevencao_vbg = sessoes_prevencao_vbg;
		this.data_ultima_sessao_vbg = data_ultima_sessao_vbg;
		this.sessoes_educativas_incl_ssr_na_saaj = sessoes_educativas_incl_ssr_na_saaj;
		this.data_ultima_sessao_saaj = data_ultima_sessao_saaj;
		this.sessoes_literacia_financeira = sessoes_literacia_financeira;
		this.data_ultima_sessao_literacia_financeira = data_ultima_sessao_literacia_financeira;
		this.aconselhamento_testagem_saude = aconselhamento_testagem_saude;
		this.data_ultima_sessao_ats = data_ultima_sessao_ats;
		this.promocao_provisao_preservativos = promocao_provisao_preservativos;
		this.data_ultima_sessao_promocao_provisao_preservativos = data_ultima_sessao_promocao_provisao_preservativos;
		this.promocao_provisao_contracepcao = promocao_provisao_contracepcao;
		this.data_ultima_sessao_promocao_provisao_contracepcao = data_ultima_sessao_promocao_provisao_contracepcao;
		this.abordagens_socio_economicas_combinadas = abordagens_socio_economicas_combinadas;
		this.data_ultima_sessao_abordagens_socio_economicas_combinadas = data_ultima_sessao_abordagens_socio_economicas_combinadas;
		this.subsidio_escolar = subsidio_escolar;
		this.data_ultima_sessao_subsidio_escolar = data_ultima_sessao_subsidio_escolar;
		this.cuidados_pos_violencia_comunitarios = cuidados_pos_violencia_comunitarios;
		this.data_ultima_sessao_cuidados_pos_violencia_comunitarios = data_ultima_sessao_cuidados_pos_violencia_comunitarios;
		this.cuidados_pos_violencia_clinicos = cuidados_pos_violencia_clinicos;
		this.data_ultima_sessao_cuidados_pos_violencia_clinicos = data_ultima_sessao_cuidados_pos_violencia_clinicos;
		this.outros_servicos_saaj = outros_servicos_saaj;
		this.data_ultima_sessao_outros_servicos_saaj = data_ultima_sessao_outros_servicos_saaj;
		this.prep = prep;
		this.data_ultima_sessao_prep = data_ultima_sessao_prep;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getData_ultima_sessao_cuidados_pos_violencia_comunitarios() {
		return data_ultima_sessao_cuidados_pos_violencia_comunitarios;
	}

	public void setData_ultima_sessao_cuidados_pos_violencia_comunitarios(
			String data_ultima_sessao_cuidados_pos_violencia_comunitarios) {
		this.data_ultima_sessao_cuidados_pos_violencia_comunitarios = data_ultima_sessao_cuidados_pos_violencia_comunitarios;
	}

	public String getCuidados_pos_violencia_clinicos() {
		return cuidados_pos_violencia_clinicos;
	}

	public void setCuidados_pos_violencia_clinicos(String cuidados_pos_violencia_clinicos) {
		this.cuidados_pos_violencia_clinicos = cuidados_pos_violencia_clinicos;
	}

	public String getData_ultima_sessao_promocao_provisao_contracepcao() {
		return data_ultima_sessao_promocao_provisao_contracepcao;
	}

	public void setData_ultima_sessao_promocao_provisao_contracepcao(
			String data_ultima_sessao_promocao_provisao_contracepcao) {
		this.data_ultima_sessao_promocao_provisao_contracepcao = data_ultima_sessao_promocao_provisao_contracepcao;
	}

	public String getCuidados_pos_violencia_comunitarios() {
		return cuidados_pos_violencia_comunitarios;
	}

	public void setCuidados_pos_violencia_comunitarios(String cuidados_pos_violencia_comunitarios) {
		this.cuidados_pos_violencia_comunitarios = cuidados_pos_violencia_comunitarios;
	}

	public String getAbordagens_socio_economicas_combinadas() {
		return abordagens_socio_economicas_combinadas;
	}

	public void setAbordagens_socio_economicas_combinadas(String abordagens_socio_economicas_combinadas) {
		this.abordagens_socio_economicas_combinadas = abordagens_socio_economicas_combinadas;
	}

	public String getData_ultima_sessao_cuidados_pos_violencia_clinicos() {
		return data_ultima_sessao_cuidados_pos_violencia_clinicos;
	}

	public void setData_ultima_sessao_cuidados_pos_violencia_clinicos(
			String data_ultima_sessao_cuidados_pos_violencia_clinicos) {
		this.data_ultima_sessao_cuidados_pos_violencia_clinicos = data_ultima_sessao_cuidados_pos_violencia_clinicos;
	}

	public String getData_ultima_sessao_abordagens_socio_economicas_combinadas() {
		return data_ultima_sessao_abordagens_socio_economicas_combinadas;
	}

	public void setData_ultima_sessao_abordagens_socio_economicas_combinadas(
			String data_ultima_sessao_abordagens_socio_economicas_combinadas) {
		this.data_ultima_sessao_abordagens_socio_economicas_combinadas = data_ultima_sessao_abordagens_socio_economicas_combinadas;
	}

	public String getData_ultima_sessao_ats() {
		return data_ultima_sessao_ats;
	}

	public void setData_ultima_sessao_ats(String data_ultima_sessao_ats) {
		this.data_ultima_sessao_ats = data_ultima_sessao_ats;
	}

	public String getData_ultima_sessao_promocao_provisao_preservativos() {
		return data_ultima_sessao_promocao_provisao_preservativos;
	}

	public void setData_ultima_sessao_promocao_provisao_preservativos(
			String data_ultima_sessao_promocao_provisao_preservativos) {
		this.data_ultima_sessao_promocao_provisao_preservativos = data_ultima_sessao_promocao_provisao_preservativos;
	}

	public String getData_ultima_sessao_prep() {
		return data_ultima_sessao_prep;
	}

	public void setData_ultima_sessao_prep(String data_ultima_sessao_prep) {
		this.data_ultima_sessao_prep = data_ultima_sessao_prep;
	}

	public String getPromocao_provisao_contracepcao() {
		return promocao_provisao_contracepcao;
	}

	public void setPromocao_provisao_contracepcao(String promocao_provisao_contracepcao) {
		this.promocao_provisao_contracepcao = promocao_provisao_contracepcao;
	}

	public String getData_ultima_sessao_subsidio_escolar() {
		return data_ultima_sessao_subsidio_escolar;
	}

	public void setData_ultima_sessao_subsidio_escolar(String data_ultima_sessao_subsidio_escolar) {
		this.data_ultima_sessao_subsidio_escolar = data_ultima_sessao_subsidio_escolar;
	}

	public String getPromocao_provisao_preservativos() {
		return promocao_provisao_preservativos;
	}

	public void setPromocao_provisao_preservativos(String promocao_provisao_preservativos) {
		this.promocao_provisao_preservativos = promocao_provisao_preservativos;
	}

	public String getAconselhamento_testagem_saude() {
		return aconselhamento_testagem_saude;
	}

	public void setAconselhamento_testagem_saude(String aconselhamento_testagem_saude) {
		this.aconselhamento_testagem_saude = aconselhamento_testagem_saude;
	}

	public String getSubsidio_escolar() {
		return subsidio_escolar;
	}

	public void setSubsidio_escolar(String subsidio_escolar) {
		this.subsidio_escolar = subsidio_escolar;
	}

	public String getData_ultima_sessao_hiv() {
		return data_ultima_sessao_hiv;
	}

	public void setData_ultima_sessao_hiv(String data_ultima_sessao_hiv) {
		this.data_ultima_sessao_hiv = data_ultima_sessao_hiv;
	}

	public String getData_ultima_sessao_literacia_financeira() {
		return data_ultima_sessao_literacia_financeira;
	}

	public void setData_ultima_sessao_literacia_financeira(String data_ultima_sessao_literacia_financeira) {
		this.data_ultima_sessao_literacia_financeira = data_ultima_sessao_literacia_financeira;
	}

	public String getNui() {
		return nui;
	}

	public void setNui(String nui) {
		this.nui = nui;
	}

	public String getIdade_actual() {
		return idade_actual;
	}

	public void setIdade_actual(String idade_actual) {
		this.idade_actual = idade_actual;
	}

	public String getAgyw_prev() {
		return agyw_prev;
	}

	public void setAgyw_prev(String agyw_prev) {
		this.agyw_prev = agyw_prev;
	}

	public String getReferencias_clinicas_nao_atendidas() {
		return referencias_clinicas_nao_atendidas;
	}

	public void setReferencias_clinicas_nao_atendidas(String referencias_clinicas_nao_atendidas) {
		this.referencias_clinicas_nao_atendidas = referencias_clinicas_nao_atendidas;
	}

	public String getSessoes_prevencao_hiv() {
		return sessoes_prevencao_hiv;
	}

	public void setSessoes_prevencao_hiv(String sessoes_prevencao_hiv) {
		this.sessoes_prevencao_hiv = sessoes_prevencao_hiv;
	}

	public String getSessoes_prevencao_vbg() {
		return sessoes_prevencao_vbg;
	}

	public void setSessoes_prevencao_vbg(String sessoes_prevencao_vbg) {
		this.sessoes_prevencao_vbg = sessoes_prevencao_vbg;
	}

	public String getData_ultima_sessao_recursos_sociais() {
		return data_ultima_sessao_recursos_sociais;
	}

	public void setData_ultima_sessao_recursos_sociais(String data_ultima_sessao_recursos_sociais) {
		this.data_ultima_sessao_recursos_sociais = data_ultima_sessao_recursos_sociais;
	}

	public String getSessoes_literacia_financeira() {
		return sessoes_literacia_financeira;
	}

	public void setSessoes_literacia_financeira(String sessoes_literacia_financeira) {
		this.sessoes_literacia_financeira = sessoes_literacia_financeira;
	}

	public String getOutros_servicos_saaj() {
		return outros_servicos_saaj;
	}

	public void setOutros_servicos_saaj(String outros_servicos_saaj) {
		this.outros_servicos_saaj = outros_servicos_saaj;
	}

	public String getReferencias_comunitarias_nao_atendidas() {
		return referencias_comunitarias_nao_atendidas;
	}

	public void setReferencias_comunitarias_nao_atendidas(String referencias_comunitarias_nao_atendidas) {
		this.referencias_comunitarias_nao_atendidas = referencias_comunitarias_nao_atendidas;
	}

	public String getNumero_vulnerabilidades() {
		return numero_vulnerabilidades;
	}

	public void setNumero_vulnerabilidades(String numero_vulnerabilidades) {
		this.numero_vulnerabilidades = numero_vulnerabilidades;
	}

	public String getPrep() {
		return prep;
	}

	public void setPrep(String prep) {
		this.prep = prep;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getFaixa_etaria_actual() {
		return faixa_etaria_actual;
	}

	public void setFaixa_etaria_actual(String faixa_etaria_actual) {
		this.faixa_etaria_actual = faixa_etaria_actual;
	}

	public String getSessoes_recursos_sociais() {
		return sessoes_recursos_sociais;
	}

	public void setSessoes_recursos_sociais(String sessoes_recursos_sociais) {
		this.sessoes_recursos_sociais = sessoes_recursos_sociais;
	}

	public String getData_ultima_sessao_vbg() {
		return data_ultima_sessao_vbg;
	}

	public void setData_ultima_sessao_vbg(String data_ultima_sessao_vbg) {
		this.data_ultima_sessao_vbg = data_ultima_sessao_vbg;
	}

	public String getSessoes_educativas_incl_ssr_na_saaj() {
		return sessoes_educativas_incl_ssr_na_saaj;
	}

	public void setSessoes_educativas_incl_ssr_na_saaj(String sessoes_educativas_incl_ssr_na_saaj) {
		this.sessoes_educativas_incl_ssr_na_saaj = sessoes_educativas_incl_ssr_na_saaj;
	}

	public String getData_ultima_sessao_outros_servicos_saaj() {
		return data_ultima_sessao_outros_servicos_saaj;
	}

	public void setData_ultima_sessao_outros_servicos_saaj(String data_ultima_sessao_outros_servicos_saaj) {
		this.data_ultima_sessao_outros_servicos_saaj = data_ultima_sessao_outros_servicos_saaj;
	}

	public String getData_ultima_sessao_saaj() {
		return data_ultima_sessao_saaj;
	}

	public void setData_ultima_sessao_saaj(String data_ultima_sessao_saaj) {
		this.data_ultima_sessao_saaj = data_ultima_sessao_saaj;
	}

}
