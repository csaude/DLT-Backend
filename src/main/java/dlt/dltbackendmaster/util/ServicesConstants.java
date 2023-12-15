package dlt.dltbackendmaster.util;

import java.util.Arrays;
import java.util.List;

/**
 * Services and SubServices Constants
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class ServicesConstants
{
    // PROMOÇÃO E/OU PROVISÃO DE PRESERVATIVOS
    public static final Integer PROVISAO_PRESERVATIVOS_MASCULINOS = 2;

    public static final Integer PROVISAO_PRESERVATIVOS_FEMININOS = 52;

    public static final Integer PROMOCAO_PRESERVATIVOS_MASCULINOS = 107;

    public static final Integer PROMOCAO_PRESERVATIVOS_FEMININOS = 108;

    // PROMOÇÃO E/OU PROVISÃO DE CONTRACEPÇÃO (PLANEAMENTO FAMILIAR)
    public static final Integer PILULA = 4;

    public static final Integer METODOS_INJECTAVEIS = 5;

    public static final Integer IMPLANTE = 6;

    public static final Integer DIU = 7;

    public static final Integer CONTRACEPCAO_EMERGENCIA = 96;

    // CUIDADOS PÓS-VIOLÊNCIA (US)
    public static final Integer PPE_INICIOU = 8;

    public static final Integer APSS_US = 9;

    public static final Integer REFERENCIA_POLICIA = 10;

    public static final Integer ACONSELHAMENTO_LIVES_US = 11;

    public static final Integer CUIDADOS_POS_VIOLENCIA_CONTRACEPCAO_EMERGENCIA = 12;

    public static final Integer TRATAMENTO_LESOES = 13;

    public static final Integer REFERENCIA_MEDICINA_LEGAL = 14;

    public static final Integer PPE_CONCLUIU = 97;

    public static final Integer PROFILAXIA_ITS = 15;

    // ACONSELHAMENTO E TESTAGEM EM SAÚDE
    public static final Integer ACONSELHADO_NAO_TESTADO = 26;

    public static final Integer ACONSELHADO_TESTADO_NEGATIVO = 67;

    public static final Integer ACONSELHADO_TESTADO_POSITIVO = 68;

    // SUBSÍDIO ESCOLAR
    public static final Integer APOIO_ESCOLAR = 35;

    public static final Integer MATERIAL_ESCOLAR = 36;

    public static final Integer UNIFORME_ESCOLAR = 37;

    // ABORDAGENS SÓCIO-ECONÓMICAS COMBINADAS
    public static final Integer GRUPO_PUPANCA = 84;

    public static final Integer SIYAKHA_LIGHT = 235;

    public static final Integer SIYAKHA_COMPREHENSIVE = 214;

    // OUTROS SERVIÇOS DE SAAJ
    public static final Integer RASTREIO_ITS = 69;

    public static final Integer TRATAMENTO_ITS = 70;

    public static final Integer CPN = 71;

    public static final Integer TARV = 72;

    public static final Integer TESTE_GRAVIDEZ = 73;

    public static final Integer RCCU = 74;

    public static final Integer EDUCACAO_CIRCUNCISAO = 92;

    public static final Integer EDUCACAO_CONTRACEPCAO = 93;

    public static final Integer EDUCACAO_SAUDE_MENTAL = 95;

    public static final Integer CPP = 151;

    // SESSÕES EDUCATIVAS INCLUINDO SAÚDE SEXUAL E REPRODUTIVA (NO SAAJ)
    public static final Integer EDUCACAO_HIGIENE_MENSTRUAL = 77;

    public static final Integer EDUCACAO_SEXUALIDADE = 88;

    public static final Integer EDUCACAO_ITS_HIV = 89;

    public static final Integer EDUCACAO_PREVENCAO_VBG = 90;

    public static final Integer EDUCACAO_NUTRICAO = 91;

    // MOBILIZAÇÃO COMUNITÁRIA E MUDANÇA DE NORMAS SOCIAIS (CONTEXTUAL)
    public static final Integer HISTORIA_XILUVA = 152;

    public static final Integer CONVERS_HOMEM = 153;

    public static final Integer GUIAO_FACILITACAO_DIALOGOS_COMUNITARIOS = 154;

    // PROGRAMAÇÃO PARA PAIS/CUIDADORES DE BENEFICIÁRIAS DO DREAMS (CONTEXTUAL)
    public static final Integer PAPO_FAMILIA = 155;

    // CUIDADOS PÓS VIOLÊNCIA (COMUNIDADE)
    public static final Integer APSS_CM = 127;

    public static final Integer APOIO_LEGAL = 128;

    public static final Integer ACONSELHAMENTO_LIVES_CM = 145;

    public static final Integer GRUPOS_APOIO = 146;

    public static final Integer PROTECAO_CRIANCA = 147;

    public static final Integer FORTALECIMENTO_ECONOMICO = 148;

    // AVANTE RAPARIGA - RECURSOS SOCIAIS - RAMJ FORA DA ESCOLA 09-14 ANOS
    public static final Integer RAPARIGAS_O_QUE_ESPERAR = 157;

    public static final Integer DELES_DELAS = 158;

    public static final Integer PERMANECER_VOLTAR_ESCOLA = 159;

    public static final Integer RAPARIGAS_FALAR_ALTO = 160;

    public static final Integer TOMAR_BOAS_DECISOES = 161;

    public static final Integer VALORES_DINHEIRO_PRENDAS = 162;

    public static final Integer RAPARIGAS_PLANEAR_META = 163;

    public static final Integer RAPARIGAS_MODULO_1 = 164;

    // AVANTE ESTUDANTE - RECURSOS SOCIAIS - RAMJ NA ESCOLA 09-14 ANOS
    public static final Integer ESTUDANTES_O_QUE_ESPERAR = 165;

    public static final Integer PONTOS_FORTES_METAS = 166;

    public static final Integer DENTRO_FORA_CAIXA = 167;

    public static final Integer ESTUDANTES_AMIZADES_SAUDAVEIS = 168;

    public static final Integer ESTUDANTES_FALAR_ALTO = 169;

    public static final Integer ADULTOS_APOIANTES = 170;

    public static final Integer TOMAR_DECISOES_CERTAS = 171;

    public static final Integer VALORES_DINHEIRO = 172;

    public static final Integer CONSEQUENCIAS_BEBIDAS_ALCOOLICAS = 173;

    public static final Integer RESPEITAR_PASSADO = 174;

    public static final Integer ESTUDANTES_PLANEAR_META = 175;

    public static final Integer ESTUDANTES_MODULO_1 = 176;

    // GUIA DE FACILITAÇÃO PARA CLUBES DE JOVENS SOBRE A PREVENÇÃO DO HIV/VIOLÊNCIA - RECURSOS SOCIAIS - RAMJ FORA & NA ESCOLA 15+ ANOS
    public static final Integer GENERO = 177;

    public static final Integer ALCOOL_DROGAS = 178;

    // AVANTE RAPARIGA - PREVENÇÃO DO HIV - RAMJ FORA DA ESCOLA 09-14 ANOS
    public static final Integer RAPARIGAS_CORPO_MUDAR = 179;

    public static final Integer RAPARIGAS_COMO_OCORRE_GRAVIDEZ = 180;

    public static final Integer EVITAR_GRAVIDEZ_INDESEJADA = 181;

    public static final Integer SACO_OPACO_ITS_HIV = 182;

    public static final Integer RAPARIGAS_MODULO_2 = 183;

    // AVANTE ESTUDANTE - PREVENÇÃO DO HIV - RAMJ NA ESCOLA 09-14 ANOS
    public static final Integer ESTUDANTES_CORPO_MUDAR_1 = 184;

    public static final Integer ESTUDANTES_CORPO_MUDAR_2 = 185;

    public static final Integer ESTUDANTES_COMO_OCORRE_GRAVIDEZ = 186;

    public static final Integer SACO_OPACO_HIV = 187;

    public static final Integer QUE_FAZER_SENTIMENTOS = 188;

    public static final Integer PRONTA_ACTIVIDADE_SEXUAL = 189;

    public static final Integer NAO_RELACOES_SEXUAIS = 190;

    public static final Integer FALAR_ADULTOS_SOBRE_SEXO = 191;

    public static final Integer RELACOES_HOMENS_MAIS_VELHOS = 192;

    public static final Integer RELACOES_SEXUAIS_UM_COM_OUTRO = 193;

    public static final Integer ESTOU_EM_RISCO = 194;

    public static final Integer ESTUDANTES_MODULO_2 = 195;

    // GUIA DE FACILITAÇÃO PARA CLUBES DE JOVENS SOBRE A PREVENÇÃO DO HIV/VIOLÊNCIA - PREVENÇÃO DO HIV - RAMJ FORA & NA ESCOLA 15+ ANOS
    public static final Integer SEXUALIDADE = 201;

    public static final Integer ITS = 203;

    public static final Integer PREVENCAO_HIV = 203;

    public static final Integer HIV_EDUCACAO_TRATAMENTO = 204;

    public static final Integer GRAVIDEZ_CONTRACEPCAO = 205;

    public static final Integer RISCO_PARCEIROS_MULTIPLOS = 206;

    public static final Integer PROFILAXIA_PREP = 216;

    // AVANTE RAPARIGA - PREVENÇÃO DE VIOLÊNCIA - RAMJ FORA DA ESCOLA 09-14 ANOS
    public static final Integer RAPARIGAS_AMIZADES_SAUDAVEIS = 196;

    public static final Integer COMO_COMUNICAR_ADULTOS = 197;

    public static final Integer COMO_COMUNICAR_PARCEIRO = 198;

    public static final Integer AVANCOS_INDESEJADOS = 199;

    public static final Integer RAPARIGAS_MODULO_3 = 200;

    // AVANTE ESTUDANTE - PREVENÇÃO DE VIOLÊNCIA - RAMJ NA ESCOLA 09-14 ANOS
    public static final Integer NAO_QUERO_RELACOES_SEXUAIS = 207;

    public static final Integer COMUNICACAO_PARA_PROTECCAO = 208;

    public static final Integer ESTUDANTES_MODULO_3 = 209;

    // GUIA DE FACILITAÇÃO PARA CLUBES DE JOVENS SOBRE A PREVENÇÃO DO HIV/VIOLÊNCIA - PREVENÇÃO DE VIOLÊNCIA - RAMJ FORA & NA ESCOLA 15+ ANOS
    public static final Integer CASAMENTOS_PREMATUROS = 210;

    public static final Integer PROTECCAO_SOCIAL = 211;

    public static final Integer VBG = 212;

    // INTERVENÇÃO DIRIGIDA AOS PARCEIROS MASCULINOS DA RAMJ (CONTEXTUAL)
    public static final Integer GRASSROOTS_SOCCER = 213;

    // PrEP
    public static final Integer PROVISAO_PREP = 156;

    // LITERACIA FINANCEIRA
    public static final Integer LITERACIA_FINANCEIRA = 215;
    
    
    // LITERACIA FINANCEIRA - AFLATOUN
    public static final Integer CONCEITO_POUPANCA = 217;
    
    public static final Integer POUPANCA_DINHEIRO = 218;
    
    public static final Integer GESTAO_RECURSOS = 219;
    
    public static final Integer CONSEQUENCIAS_FINANCEIRAS = 220;
    
    public static final Integer NECESSIDADES_DESEJOS = 221;
    
    public static final Integer DINHEIRO_COMO_RECURSO = 222;
    
    public static final Integer ESCOLHAS_FINANCEIRAS = 223;
    
    // LITERACIA FINANCEIRA - AFLATEEN
    public static final Integer ORCAMENTOS_DINHEIRO_PODER = 224;
    
    public static final Integer APRENDENDO_ECONOMIZAR = 225;
    
    public static final Integer APRENDENDO_GASTAR = 226;
    
    public static final Integer CRIANDO_ORCAMENTO = 227;
    
    public static final Integer OPCOES_POUPANCA = 228;
    
    public static final Integer POUPADORES_INTELIGENTES = 229;
    
    public static final Integer DINHEIRO_EMPRESTADO = 230;
    
    public static final Integer FLUXOS_DINHEIRO = 231;
    
    public static final Integer IMAGINANDO_MEU_FUTURO = 232;

    public static final List<Integer> ESTUDANTES_RECURSOS_SOCIAIS_MANDATORY = Arrays.asList(ESTUDANTES_FALAR_ALTO,
                                                                                            ADULTOS_APOIANTES,
                                                                                            ESTUDANTES_MODULO_1);

    public static final List<Integer> ESTUDANTES_RECURSOS_SOCIAIS_NON_MANDATORY = Arrays.asList(ESTUDANTES_O_QUE_ESPERAR,
                                                                                                PONTOS_FORTES_METAS,
                                                                                                DENTRO_FORA_CAIXA,
                                                                                                ESTUDANTES_AMIZADES_SAUDAVEIS,
                                                                                                TOMAR_DECISOES_CERTAS,
                                                                                                VALORES_DINHEIRO,
                                                                                                CONSEQUENCIAS_BEBIDAS_ALCOOLICAS,
                                                                                                ESTUDANTES_PLANEAR_META,
                                                                                                RESPEITAR_PASSADO);

    public static final List<Integer> RAPARIGAS_RECURSOS_SOCIAIS_MANDATORY = Arrays.asList(RAPARIGAS_MODULO_1);

    public static final List<Integer> RAPARIGAS_RECURSOS_SOCIAIS_NON_MANDATORY = Arrays.asList(RAPARIGAS_O_QUE_ESPERAR,
                                                                                               DELES_DELAS,
                                                                                               PERMANECER_VOLTAR_ESCOLA,
                                                                                               RAPARIGAS_FALAR_ALTO,
                                                                                               TOMAR_BOAS_DECISOES,
                                                                                               VALORES_DINHEIRO_PRENDAS,
                                                                                               RAPARIGAS_PLANEAR_META);

    public static final List<Integer> GUIAO_FACILITACAO_RECURSOS_SOCIAIS = Arrays.asList(GENERO, ALCOOL_DROGAS);

    public static final List<Integer> ESTUDANTES_HIV_PREVENTION = Arrays.asList(ESTUDANTES_CORPO_MUDAR_1,
                                                                                ESTUDANTES_CORPO_MUDAR_2,
                                                                                ESTUDANTES_COMO_OCORRE_GRAVIDEZ,
                                                                                SACO_OPACO_HIV,
                                                                                QUE_FAZER_SENTIMENTOS,
                                                                                PRONTA_ACTIVIDADE_SEXUAL,
                                                                                NAO_RELACOES_SEXUAIS,
                                                                                FALAR_ADULTOS_SOBRE_SEXO,
                                                                                RELACOES_HOMENS_MAIS_VELHOS,
                                                                                RELACOES_SEXUAIS_UM_COM_OUTRO,
                                                                                ESTOU_EM_RISCO,
                                                                                ESTUDANTES_MODULO_2);

    public static final List<Integer> RAPARIGAS_HIV_PREVENTION = Arrays.asList(RAPARIGAS_CORPO_MUDAR,
                                                                               RAPARIGAS_COMO_OCORRE_GRAVIDEZ,
                                                                               EVITAR_GRAVIDEZ_INDESEJADA,
                                                                               SACO_OPACO_ITS_HIV,
                                                                               RAPARIGAS_MODULO_2);

    public static final List<Integer> GUIAO_FACILITACAO_HIV_PREVENTION_MANDATORY = Arrays.asList(SEXUALIDADE,
                                                                                                 ITS,
                                                                                                 PREVENCAO_HIV,
                                                                                                 HIV_EDUCACAO_TRATAMENTO,
                                                                                                 GRAVIDEZ_CONTRACEPCAO,
                                                                                                 RISCO_PARCEIROS_MULTIPLOS);

    public static final List<Integer> GUIAO_FACILITACAO_HIV_PREVENTION_NON_MANDATORY = Arrays.asList(PROFILAXIA_PREP);

    public static final List<Integer> ESTUDANTES_VIOLENCE_PREVENTION = Arrays.asList(NAO_QUERO_RELACOES_SEXUAIS,
                                                                                     COMUNICACAO_PARA_PROTECCAO,
                                                                                     ESTUDANTES_MODULO_3);

    public static final List<Integer> RAPARIGAS_VIOLENCE_PREVENTION = Arrays.asList(RAPARIGAS_AMIZADES_SAUDAVEIS,
                                                                                    COMO_COMUNICAR_ADULTOS,
                                                                                    COMO_COMUNICAR_PARCEIRO,
                                                                                    AVANCOS_INDESEJADOS,
                                                                                    RAPARIGAS_MODULO_3);

    public static final List<Integer> GUIAO_FACILITACAO_VIOLENCE_PREVENTION = Arrays.asList(CASAMENTOS_PREMATUROS,
                                                                                            PROTECCAO_SOCIAL,
                                                                                            VBG);

    public static final List<Integer> ESTUDANTES_MANDATORY = Arrays.asList(ESTUDANTES_FALAR_ALTO,
                                                                           ADULTOS_APOIANTES,
                                                                           ESTUDANTES_CORPO_MUDAR_1,
                                                                           ESTUDANTES_CORPO_MUDAR_2,
                                                                           ESTUDANTES_COMO_OCORRE_GRAVIDEZ,
                                                                           SACO_OPACO_HIV,
                                                                           QUE_FAZER_SENTIMENTOS,
                                                                           PRONTA_ACTIVIDADE_SEXUAL,
                                                                           NAO_RELACOES_SEXUAIS,
                                                                           FALAR_ADULTOS_SOBRE_SEXO,
                                                                           RELACOES_HOMENS_MAIS_VELHOS,
                                                                           RELACOES_SEXUAIS_UM_COM_OUTRO,
                                                                           ESTOU_EM_RISCO,
                                                                           NAO_QUERO_RELACOES_SEXUAIS,
                                                                           COMUNICACAO_PARA_PROTECCAO,
                                                                           ESTUDANTES_MODULO_1,
                                                                           ESTUDANTES_MODULO_2,
                                                                           ESTUDANTES_MODULO_3);

    public static final List<Integer> ESTUDANTES_NON_MANDATORY = Arrays.asList(ESTUDANTES_O_QUE_ESPERAR,
                                                                               PONTOS_FORTES_METAS,
                                                                               DENTRO_FORA_CAIXA,
                                                                               ESTUDANTES_AMIZADES_SAUDAVEIS,
                                                                               TOMAR_DECISOES_CERTAS,
                                                                               VALORES_DINHEIRO,
                                                                               CONSEQUENCIAS_BEBIDAS_ALCOOLICAS,
                                                                               ESTUDANTES_PLANEAR_META,
                                                                               RESPEITAR_PASSADO);

    public static final List<Integer> RAPARIGAS_MANDATORY = Arrays.asList(RAPARIGAS_MODULO_1,
                                                                          RAPARIGAS_CORPO_MUDAR,
                                                                          RAPARIGAS_COMO_OCORRE_GRAVIDEZ,
                                                                          EVITAR_GRAVIDEZ_INDESEJADA,
                                                                          SACO_OPACO_ITS_HIV,
                                                                          RAPARIGAS_MODULO_2,
                                                                          RAPARIGAS_AMIZADES_SAUDAVEIS,
                                                                          COMO_COMUNICAR_ADULTOS,
                                                                          COMO_COMUNICAR_PARCEIRO,
                                                                          AVANCOS_INDESEJADOS,
                                                                          RAPARIGAS_MODULO_3);

    public static final List<Integer> RAPARIGAS_NON_MANDATORY = Arrays.asList(RAPARIGAS_O_QUE_ESPERAR,
                                                                              DELES_DELAS,
                                                                              PERMANECER_VOLTAR_ESCOLA,
                                                                              RAPARIGAS_FALAR_ALTO,
                                                                              TOMAR_BOAS_DECISOES,
                                                                              VALORES_DINHEIRO_PRENDAS,
                                                                              RAPARIGAS_PLANEAR_META);

    public static final List<Integer> GUIAO_FACILITACAO_MANDATORY = Arrays.asList(SEXUALIDADE,
                                                                                  ITS,
                                                                                  PREVENCAO_HIV,
                                                                                  HIV_EDUCACAO_TRATAMENTO,
                                                                                  GRAVIDEZ_CONTRACEPCAO,
                                                                                  RISCO_PARCEIROS_MULTIPLOS,
                                                                                  CASAMENTOS_PREMATUROS,
                                                                                  PROTECCAO_SOCIAL,
                                                                                  VBG);

    public static final List<Integer> GUIAO_FACILITACAO_NON_MANDATORY = Arrays.asList(GENERO,
                                                                                      ALCOOL_DROGAS,
                                                                                      PROFILAXIA_PREP);

    public static final List<Integer> PROMOCAO_PROVISAO_PRESERVATIVOS = Arrays.asList(PROVISAO_PRESERVATIVOS_MASCULINOS,
                                                                                      PROVISAO_PRESERVATIVOS_FEMININOS,
                                                                                      PROMOCAO_PRESERVATIVOS_MASCULINOS,
                                                                                      PROMOCAO_PRESERVATIVOS_FEMININOS);

    public static final List<Integer> PROMOCAO_PROVISAO_CONTRACEPCAO = Arrays.asList(PILULA,
                                                                                     METODOS_INJECTAVEIS,
                                                                                     IMPLANTE,
                                                                                     DIU,
                                                                                     CONTRACEPCAO_EMERGENCIA);

    public static final List<Integer> CUIDADOS_POS_VIOLENCIA_US = Arrays.asList(PPE_INICIOU,
                                                                                APSS_US,
                                                                                REFERENCIA_POLICIA,
                                                                                ACONSELHAMENTO_LIVES_US,
                                                                                CUIDADOS_POS_VIOLENCIA_CONTRACEPCAO_EMERGENCIA,
                                                                                TRATAMENTO_LESOES,
                                                                                REFERENCIA_MEDICINA_LEGAL,
                                                                                PPE_CONCLUIU,
                                                                                PROFILAXIA_ITS);

    public static final List<Integer> ACONSELHAMENTO_TESTAGEM_SAUDE = Arrays.asList(ACONSELHADO_NAO_TESTADO,
                                                                                    ACONSELHADO_TESTADO_NEGATIVO,
                                                                                    ACONSELHADO_TESTADO_POSITIVO);

    public static final List<Integer> SUBSIDIO_ESCOLAR = Arrays.asList(APOIO_ESCOLAR,
                                                                       MATERIAL_ESCOLAR,
                                                                       UNIFORME_ESCOLAR);

    public static final List<Integer> ABORDAGENS_SOCIO_ECONOMICAS_COMBINADAS = Arrays.asList(GRUPO_PUPANCA,
                                                                                             SIYAKHA_LIGHT,
                                                                                             SIYAKHA_COMPREHENSIVE);

    public static final List<Integer> OUTROS_SERVICOS_SAAJ = Arrays.asList(RASTREIO_ITS,
                                                                           TRATAMENTO_ITS,
                                                                           CPN,
                                                                           TARV,
                                                                           TESTE_GRAVIDEZ,
                                                                           RCCU,
                                                                           EDUCACAO_CIRCUNCISAO,
                                                                           EDUCACAO_CONTRACEPCAO,
                                                                           EDUCACAO_SAUDE_MENTAL,
                                                                           CPP);

    public static final List<Integer> SESSOES_EDUCATIVAS_SAAJ_MANDATORY = Arrays.asList(EDUCACAO_HIGIENE_MENSTRUAL,
                                                                                        EDUCACAO_SEXUALIDADE,
                                                                                        EDUCACAO_ITS_HIV,
                                                                                        EDUCACAO_PREVENCAO_VBG);

    public static final List<Integer> SESSOES_EDUCATIVAS_SAAJ_NON_MANDATORY = Arrays.asList(EDUCACAO_NUTRICAO);

    public static final List<Integer> MOBILIZACAO_COMUNITARIA_MUDANCA_NORMAS = Arrays.asList(HISTORIA_XILUVA,
                                                                                             CONVERS_HOMEM,
                                                                                             GUIAO_FACILITACAO_DIALOGOS_COMUNITARIOS);

    public static final List<Integer> CUIDADOS_POS_VIOLENCIA_COMUNIDADE = Arrays.asList(APSS_CM,
                                                                                        APOIO_LEGAL,
                                                                                        ACONSELHAMENTO_LIVES_CM,
                                                                                        GRUPOS_APOIO,
                                                                                        PROTECAO_CRIANCA,
                                                                                        FORTALECIMENTO_ECONOMICO);

    public static final List<Integer> LITERACIA_FINANCEIRA_AFLATOUN = Arrays.asList(CONCEITO_POUPANCA,
                                                                                        POUPANCA_DINHEIRO,
                                                                                        GESTAO_RECURSOS,
                                                                                        CONSEQUENCIAS_FINANCEIRAS,
                                                                                        NECESSIDADES_DESEJOS,
                                                                                        DINHEIRO_COMO_RECURSO,
                                                                                        ESCOLHAS_FINANCEIRAS);

    public static final List<Integer> LITERACIA_FINANCEIRA_AFLATEEN = Arrays.asList(ORCAMENTOS_DINHEIRO_PODER,
                                                                                        APRENDENDO_ECONOMIZAR,
                                                                                        APRENDENDO_GASTAR,
                                                                                        CRIANDO_ORCAMENTO,
                                                                                        OPCOES_POUPANCA,
                                                                                        POUPADORES_INTELIGENTES,
                                                                                        DINHEIRO_EMPRESTADO,
                                                                                        FLUXOS_DINHEIRO,
                                                                                        IMAGINANDO_MEU_FUTURO);
}
