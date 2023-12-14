package dlt.dltbackendmaster.reports.domain;

/**
 * Novos Agyw, vulnerabilidades e sub-servicos
 * 
 * @author Francisco Macuacua
 *
 */
public class NewlyEnrolledAgywAndServices {

	private String index;
	private String provincia;
	private String distrito;
	private String onde_mora;
	private String ponto_entrada;
	private String organizacao;
	private String data_inscricao;
	private String data_registo;
	private String registado_por;
	private String data_actualizacao;
	private String actualizado_por;
	private String nui;
	private String sexo;
	private String idade_registo;
	private String idade_actual;
	private String faixa_registo;
	private String faixa_actual;
	private String data_nascimento;
	private String agyw_prev;
	private String com_quem_mora;
	private String sustenta_casa;
	private String e_orfa;
	private String vai_escola;
	private String tem_deficiencia;
	private String tipo_deficiencia;
	private String foi_casada;
	private String esteve_gravida;
	private String tem_filhos;
	private String gravida_amamentar;
	private String trabalha;
	private String teste_hiv;
	private String area_servico;
	private String a_servico;
	private String sub_servico;
	private String pacote_servico;
	private String ponto_entrada_servico;
	private String localizacao;
	private String data_servico;
	private String provedor;
	private String observacoes;
	private String servico_status;

	public NewlyEnrolledAgywAndServices(String index, String provincia, String distrito, String onde_mora,
			String ponto_entrada, String organizacao, String data_inscricao, String data_registo, String registado_por,
			String data_actualizacao, String actualizado_por, String nui, String sexo, String idade_registo,
			String idade_actual, String faixa_registo, String faixa_actual, String data_nascimento, String agyw_prev,
			String com_quem_mora, String sustenta_casa, String e_orfa, String vai_escola, String tem_deficiencia,
			String tipo_deficiencia, String foi_casada, String esteve_gravida, String tem_filhos,
			String gravida_amamentar, String trabalha, String teste_hiv, String area_servico, String a_servico,
			String sub_servico, String pacote_servico, String ponto_entrada_servico, String localizacao,
			String data_servico, String provedor, String observacoes, String servico_status) {
		super();
		this.index = index;
		this.provincia = provincia;
		this.distrito = distrito;
		this.onde_mora = onde_mora;
		this.ponto_entrada = ponto_entrada;
		this.organizacao = organizacao;
		this.data_inscricao = data_inscricao;
		this.data_registo = data_registo;
		this.registado_por = registado_por;
		this.data_actualizacao = data_actualizacao;
		this.actualizado_por = actualizado_por;
		this.nui = nui;
		this.sexo = sexo;
		this.idade_registo = idade_registo;
		this.idade_actual = idade_actual;
		this.faixa_registo = faixa_registo;
		this.faixa_actual = faixa_actual;
		this.data_nascimento = data_nascimento;
		this.agyw_prev = agyw_prev;
		this.com_quem_mora = com_quem_mora;
		this.sustenta_casa = sustenta_casa;
		this.e_orfa = e_orfa;
		this.vai_escola = vai_escola;
		this.tem_deficiencia = tem_deficiencia;
		this.tipo_deficiencia = tipo_deficiencia;
		this.foi_casada = foi_casada;
		this.esteve_gravida = esteve_gravida;
		this.tem_filhos = tem_filhos;
		this.gravida_amamentar = gravida_amamentar;
		this.trabalha = trabalha;
		this.teste_hiv = teste_hiv;
		this.area_servico = area_servico;
		this.a_servico = a_servico;
		this.sub_servico = sub_servico;
		this.pacote_servico = pacote_servico;
		this.ponto_entrada_servico = ponto_entrada_servico;
		this.localizacao = localizacao;
		this.data_servico = data_servico;
		this.provedor = provedor;
		this.observacoes = observacoes;
		this.servico_status = servico_status;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

	public String getOnde_mora() {
		return onde_mora;
	}

	public void setOnde_mora(String onde_mora) {
		this.onde_mora = onde_mora;
	}

	public String getPonto_entrada() {
		return ponto_entrada;
	}

	public void setPonto_entrada(String ponto_entrada) {
		this.ponto_entrada = ponto_entrada;
	}

	public String getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(String organizacao) {
		this.organizacao = organizacao;
	}

	public String getData_registo() {
		return data_registo;
	}

	public void setData_registo(String data_registo) {
		this.data_registo = data_registo;
	}

	public String getRegistado_por() {
		return registado_por;
	}

	public void setRegistado_por(String registado_por) {
		this.registado_por = registado_por;
	}

	public String getData_actualizacao() {
		return data_actualizacao;
	}

	public void setData_actualizacao(String data_actualizacao) {
		this.data_actualizacao = data_actualizacao;
	}

	public String getActualizado_por() {
		return actualizado_por;
	}

	public void setActualizado_por(String actualizado_por) {
		this.actualizado_por = actualizado_por;
	}

	public String getNui() {
		return nui;
	}

	public void setNui(String nui) {
		this.nui = nui;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getIdade_registo() {
		return idade_registo;
	}

	public void setIdade_registo(String idade_registo) {
		this.idade_registo = idade_registo;
	}

	public String getIdade_actual() {
		return idade_actual;
	}

	public void setIdade_actual(String idade_actual) {
		this.idade_actual = idade_actual;
	}

	public String getFaixa_registo() {
		return faixa_registo;
	}

	public void setFaixa_registo(String faixa_registo) {
		this.faixa_registo = faixa_registo;
	}

	public String getFaixa_actual() {
		return faixa_actual;
	}

	public void setFaixa_actual(String faixa_actual) {
		this.faixa_actual = faixa_actual;
	}

	public String getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getAgyw_prev() {
		return agyw_prev;
	}

	public void setAgyw_prev(String agyw_prev) {
		this.agyw_prev = agyw_prev;
	}

	public String getCom_quem_mora() {
		return com_quem_mora;
	}

	public void setCom_quem_mora(String com_quem_mora) {
		this.com_quem_mora = com_quem_mora;
	}

	public String getSustenta_casa() {
		return sustenta_casa;
	}

	public void setSustenta_casa(String sustenta_casa) {
		this.sustenta_casa = sustenta_casa;
	}

	public String getE_orfa() {
		return e_orfa;
	}

	public void setE_orfa(String e_orfa) {
		this.e_orfa = e_orfa;
	}

	public String getVai_escola() {
		return vai_escola;
	}

	public void setVai_escola(String vai_escola) {
		this.vai_escola = vai_escola;
	}

	public String getTem_deficiencia() {
		return tem_deficiencia;
	}

	public void setTem_deficiencia(String tem_deficiencia) {
		this.tem_deficiencia = tem_deficiencia;
	}

	public String getTipo_deficiencia() {
		return tipo_deficiencia;
	}

	public void setTipo_deficiencia(String tipo_deficiencia) {
		this.tipo_deficiencia = tipo_deficiencia;
	}

	public String getFoi_casada() {
		return foi_casada;
	}

	public void setFoi_casada(String foi_casada) {
		this.foi_casada = foi_casada;
	}

	public String getEsteve_gravida() {
		return esteve_gravida;
	}

	public void setEsteve_gravida(String esteve_gravida) {
		this.esteve_gravida = esteve_gravida;
	}

	public String getTem_filhos() {
		return tem_filhos;
	}

	public void setTem_filhos(String tem_filhos) {
		this.tem_filhos = tem_filhos;
	}

	public String getGravida_amamentar() {
		return gravida_amamentar;
	}

	public void setGravida_amamentar(String gravida_amamentar) {
		this.gravida_amamentar = gravida_amamentar;
	}

	public String getTrabalha() {
		return trabalha;
	}

	public void setTrabalha(String trabalha) {
		this.trabalha = trabalha;
	}

	public String getTeste_hiv() {
		return teste_hiv;
	}

	public void setTeste_hiv(String teste_hiv) {
		this.teste_hiv = teste_hiv;
	}

	public String getArea_servico() {
		return area_servico;
	}

	public void setArea_servico(String area_servico) {
		this.area_servico = area_servico;
	}

	public String getA_servico() {
		return a_servico;
	}

	public void setA_servico(String a_servico) {
		this.a_servico = a_servico;
	}

	public String getSub_servico() {
		return sub_servico;
	}

	public void setSub_servico(String sub_servico) {
		this.sub_servico = sub_servico;
	}

	public String getPacote_servico() {
		return pacote_servico;
	}

	public void setPacote_servico(String pacote_servico) {
		this.pacote_servico = pacote_servico;
	}

	public String getPonto_entrada_servico() {
		return ponto_entrada_servico;
	}

	public void setPonto_entrada_servico(String ponto_entrada_servico) {
		this.ponto_entrada_servico = ponto_entrada_servico;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getData_servico() {
		return data_servico;
	}

	public void setData_servico(String data_servico) {
		this.data_servico = data_servico;
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getServico_status() {
		return servico_status;
	}

	public void setServico_status(String servico_status) {
		this.servico_status = servico_status;
	}

	public String getData_inscricao() {
		return data_inscricao;
	}

	public void setData_inscricao(String data_inscricao) {
		this.data_inscricao = data_inscricao;
	}
}
