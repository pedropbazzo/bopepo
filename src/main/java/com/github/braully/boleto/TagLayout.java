/*
 * Copyright 2019 Projeto JRimum.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.braully.boleto;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.commons.lang3.SerializationUtils;
import org.jrimum.domkee.banco.IBanco;
import org.jrimum.texgit.Fillers;
import org.jrimum.texgit.IFiller;

/**
 *
 * @author strike
 */
public class TagLayout implements Serializable {

    boolean isAttr(String id) {
        return is(this.atributos.get(id));
    }

    private boolean is(Object get) {
        boolean ret = false;
        if (get != null) {
            try {
                if (get instanceof Boolean) {
                    ret = (Boolean) get;
                } else {
                    ret = Boolean.parseBoolean(get.toString());
                }
            } catch (Exception e) {

            }
        }
        return ret;
    }

    public static class TagCreator {

        public static TagLayout field(String texto) {
            return tag(texto);
        }

        /* Fields mais comuns */
        /**
         * Codigo de 3 digitos do banco na FEBRABAN
         *
         * @return
         */
        public static TagLayout fbancoCodigo() {
            return field("bancoCodigo").padding(Fillers.ZERO_LEFT).length(3);
        }

        /**
         * Nome do banco
         *
         * @return
         */
        public static TagLayout fbancoNome() {
            return field("bancoNome").padding(Fillers.WHITE_SPACE_RIGHT);
        }

        /**
         * Agência Mantenedora da Conta 53 57 5-Num*G008. Com Dígito Verificador
         * da Agência 58 58 1-Alfa*G009
         *
         * @return
         */
        public static TagLayout fagencia() {
            return field("agencia").padding(Fillers.ZERO_LEFT);
        }

        /**
         * Dígito Verificador da Ag/Conta72721-Alfa*G012 Dígito Verificador da
         * Agência / Conta Corrente Código adotado pelo Banco responsável pela
         * conta corrente, para verificação da autenticidade do par Código da
         * Agência / Número da Conta Corrente. Para os Bancos que se utilizam de
         * duas posições para o Dígito Verificador do Número da Conta Corrente,
         * preencher este campo com a 2ª posição deste dígito.
         *
         * @return
         */
        public static TagLayout fdac() {
            return field("dac").length(1);
        }

        /**
         * Código de Barras Código adotado pela FEBRABAN para identificar o
         * Título. Especificações do Código de Barras do Boleto de Pagamento de
         * Cobrança - Ficha de Compensação (Modelo CADOC 24044-4, Carta-Circular
         * Bacen Nrº 2.926, de 25.07.2000).
         *
         * @return
         */
        public static TagLayout fcodigoBarras() {
            return field("codigoBarras").padding(Fillers.ZERO_LEFT);
        }

        /**
         * Alias para facilitar o campo de acesso ao campo do nome do sacado
         *
         * @return
         */
        public static TagLayout fsacadoNome() {
            return field("sacadoNome").padding(Fillers.WHITE_SPACE_RIGHT).length(30);
        }

        /**
         * C004: Código de Movimento Remessa Código adotado pela FEBRABAN, para
         * identificar o tipo de movimentação enviado nos //registros do arquivo
         * de remessa. Cada Banco definirá os campos a serem alterados para o
         * código de movimento '31'
         *
         * Domínio: '01' = Entrada de Títulos '02' = Pedido de Baixa ‘03’ =
         * Protesto para Fins Falimentares '04' = Concessão de Abatimento '05' =
         * Cancelamento de Abatimento '06' = Alteração de Vencimento '07' =
         * Concessão de Desconto '08' = Cancelamento de Desconto '09' =
         * Protestar '10' = Sustar Protesto e Baixar Título '11' = Sustar
         * Protesto e Manter em Carteira ‘12’ = Alteração de Juros de Mora ‘13’
         * = Dispensar Cobrança de Juros de Mora ‘14’ = Alteração de
         * Valor/Percentual de Multa ‘15’ = Dispensar Cobrança de Multa ‘16’ =
         * Alteração de Valor/Data de Desconto ‘17’ = Não conceder Desconto ‘18’
         * = Alteração do Valor de Abatimento ‘19’ = Prazo Limite de Recebimento
         * - Alterar ‘20’ = Prazo Limite de Recebimento - Dispensar ‘21’ =
         * Alterar número do título dado pelo Beneficiário ‘22’ = Alterar número
         * controle do Participante ‘23’ = Alterar dados do Pagador ‘24’ =
         * Alterar dados do Sacador/Avalista '30' = Recusa da Alegação do
         * Pagador '31' = Alteração de Outros Dados '33' = Alteração dos Dados
         * do Rateio de Crédito '34' = Pedido de Cancelamento dos Dados do
         * Rateio de Crédito '35' = Pedido de Desagendamento do Débito
         * Automático '40' = Alteração de Carteira ‘41’ = Cancelar protesto ‘42’
         * = Alteração de Espécie de Título ‘43’ = Transferência de
         * carteira/modalidade de cobrança ‘44’ = Alteração de contrato de
         * cobrança ‘45’ = Negativação Sem Protesto ‘46’ = Solicitação de Baixa
         * de Título Negativado Sem Protesto ‘47’ = Alteração do Valor Nominal
         * do Título ‘48’ = Alteração do Valor Mínimo/ Percentual ‘49’ =
         * Alteração do Valor Máximo/Percentual
         */
        public static TagLayout fmovimentoCodigo() {
            return field("movimentoCodigo").length(2);
        }

        /**
         * Na maioria das vezes o sacado ou o pagador é uma pessoa fisica, por
         * tanto esse metodo de alias.
         *
         * @return
         */
        public static TagLayout fsacadoCpf() {
            return field("sacadoCpf").length(14).padding(Fillers.ZERO_LEFT);
        }

        /**
         * ConvênioCódigo do Convênio no Banco335220-Alfa*G007. Código adotado
         * pelo Banco para identificar o Contrato entre este e a Empresa
         * Cliente.
         *
         * @return
         */
        public static TagLayout fconvenio() {
            return field("convenio").padding(Fillers.ZERO_LEFT);
        }

        /**
         * Tipo de Inscrição: '0' = Isento / Não Informado '1' = CPF '2' = CGC /
         * CNPJ '3' = PIS / PASEP '9' = Outros
         */
        public static TagLayout ftipoInscricaoCedente() {
            return field("tipoInscricaoCedente").length(1).padding(Fillers.ZERO_LEFT);
        }

        /**
         * Tipo de Inscrição: '0' = Isento / Não Informado '1' = CPF '2' = CGC /
         * CNPJ '3' = PIS / PASEP '9' = Outros
         *
         * @return
         */
        public static TagLayout ftipoInscricao() {
            return field("tipoInscricao").length(1).padding(Fillers.ZERO_LEFT);
        }

        /**
         * Alias para facilidade, quase sempre o Favorecido é uma Pessoa
         * Juridica.
         */
        public static TagLayout fcedenteCnpj() {
            return field("cedenteCnpj").padding(Fillers.ZERO_LEFT).length(14);
        }

        public static TagLayout fcedenteNome() {
            return field("cedenteNome").padding(Fillers.WHITE_SPACE_RIGHT).length(30);
        }

        public static TagLayout fquantidadeRegistros() {
            return field("quantidadeRegistros").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorTotalRegistros() {
            return field("valorTotalRegistros").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        /**
         *
         * Código das Ocorrências para Retorno/Remessa.
         *
         * Código adotado pela FEBRABAN para identificar as ocorrências
         * detectadas no processamento. Pode-se informar até 5 ocorrências
         * simultaneamente, cada uma delas codificada com dois dígitos, conforme
         * relação abaixo.
         *
         * Domínio: '00' = Crédito ou Débito Efetivado → Este código indica que
         * o pagamento foi confirmado '01' = Insuficiência de Fundos - Débito
         * Não Efetuado '02' = Crédito ou Débito Cancelado pelo Pagador/Credor
         * '03' = Débito Autorizado pela Agência - Efetuado 'AA' = Controle
         * Inválido 'AB' = Tipo de Operação Inválido 'AC' = Tipo de Serviço
         * Inválido 'AD' = Forma de Lançamento Inválida 'AE' = Tipo/Número de
         * Inscrição Inválido 'AF' = Código de Convênio Inválido 'AG' =
         * Agência/Conta Corrente/DV Inválido 'AH' = Nº Seqüencial do Registro
         * no Lote Inválido 'AI' = Código de Segmento de Detalhe Inválido 'AJ' =
         * Tipo de Movimento Inválido 'AK' = Código da Câmara de Compensação do
         * Banco Favorecido/Depositário Inválido 'AL' = Código do Banco
         * Favorecido, Instituição de Pagamento ou Depositário Inválido 'AM' =
         * Agência Mantenedora da Conta Corrente do Favorecido Inválida 'AN' =
         * Conta Corrente/DV/Conta de Pagamento do Favorecido Inválido 'AO' =
         * Nome do Favorecido Não Informado 'AP' = Data Lançamento Inválido 'AQ'
         * = Tipo/Quantidade da Moeda Inválido 'AR' = Valor do Lançamento
         * Inválido 'AS' = Aviso ao Favorecido - Identificação Inválida 'AT' =
         * Tipo/Número de Inscrição do Favorecido Inválido 'AU' = Logradouro do
         * Favorecido Não Informado 'AV' = Nº do Local do Favorecido Não
         * Informado 'AW' = Cidade do Favorecido Não Informada 'AX' =
         * CEP/Complemento do Favorecido Inválido 'AY' = Sigla do Estado do
         * Favorecido Inválida 'AZ' = Código/Nome do Banco Depositário Inválido
         * 'BA' = Código/Nome da Agência Depositária Não Informado 'BB' = Seu
         * Número Inválido 'BC' = Nosso Número Inválido 'BD' = Inclusão Efetuada
         * com Sucesso 'BE' = Alteração Efetuada com Sucesso 'BF' = Exclusão
         * Efetuada com Sucesso 'BG' = Agência/Conta Impedida Legalmente ‘BH’=
         * Empresa não pagou salário ‘BI’ = Falecimento do mutuário ‘BJ’ =
         * Empresa não enviou remessa do mutuário ‘BK’= Empresa não enviou
         * remessa no vencimento ‘BL’ = Valor da parcela inválida ‘BM’=
         * Identificação do contrato inválida ‘BN’ = Operação de Consignação
         * Incluída com Sucesso ‘BO’ = Operação de Consignação Alterada com
         * Sucesso ‘BP’ = Operação de Consignação Excluída com Sucesso ‘BQ’ =
         * Operação de Consignação Liquidada com Sucesso ‘BR’ = Reativação
         * Efetuada com Sucesso ‘BS’ = Suspensão Efetuada com Sucesso 'CA' =
         * Código de Barras - Código do Banco Inválido 'CB' = Código de Barras -
         * Código da Moeda Inválido 'CC' = Código de Barras - Dígito Verificador
         * Geral Inválido 'CD' = Código de Barras - Valor do Título Inválido
         * 'CE' = Código de Barras - Campo Livre Inválido 'CF' = Valor do
         * Documento Inválido 'CG' = Valor do Abatimento Inválido 'CH' = Valor
         * do Desconto Inválido 'CI' = Valor de Mora Inválido 'CJ' = Valor da
         * Multa Inválido 'CK' = Valor do IR Inválido 'CL' = Valor do ISS
         * Inválido 'CM' = Valor do IOF Inválido 'CN' = Valor de Outras Deduções
         * Inválido 'CO' = Valor de Outros Acréscimos Inválido 'CP' = Valor do
         * INSS Inválido 'HA' = Lote Não Aceito 'HB' = Inscrição da Empresa
         * Inválida para o Contrato 'HC' = Convênio com a Empresa
         * Inexistente/Inválido para o Contrato 'HD' = Agência/Conta Corrente da
         * Empresa Inexistente/Inválido para o Contrato 'HE' = Tipo de Serviço
         * Inválido para o Contrato 'HF' = Conta Corrente da Empresa com Saldo
         * Insuficiente 'HG' = Lote de Serviço Fora de Seqüência 'HH' = Lote de
         * Serviço Inválido `HI` = Arquivo não aceito `HJ` = Tipo de Registro
         * Inválido `HK` = Código Remessa / Retorno Inválido `HL` = Versão de
         * layout inválida `HM` = Mutuário não identificado `HN` = Tipo do
         * beneficio não permite empréstimo `HO` = Beneficio cessado/suspenso
         * `HP` = Beneficio possui representante legal `HQ` = Beneficio é do
         * tipo PA (Pensão alimentícia) `HR` = Quantidade de contratos permitida
         * excedida `HS` = Beneficio não pertence ao Banco informado `HT` =
         * Início do desconto informado já ultrapassado `HU`= Número da parcela
         * inválida `HV`= Quantidade de parcela inválida `HW`= Margem
         * consignável excedida para o mutuário dentro do prazo do contrato `HX`
         * = Empréstimo já cadastrado `HY` = Empréstimo inexistente `HZ` =
         * Empréstimo já encerrado `H1` = Arquivo sem trailer `H2` = Mutuário
         * sem crédito na competência `H3` = Não descontado – outros motivos
         * `H4` = Retorno de Crédito não pago `H5` = Cancelamento de empréstimo
         * retroativo `H6` = Outros Motivos de Glosa ‘H7’ = Margem consignável
         * excedida para o mutuário acima do prazo do contrato ‘H8’ = Mutuário
         * desligado do empregador ‘H9’ = Mutuário afastado por licença ‘IA’ =
         * Primeiro nome do mutuário diferente do primeiro nome do movimento do
         * censo ou diferente da base de Titular do Benefício ‘IB’ = Benefício
         * suspenso/cessado pela APS ou Sisobi ‘IC’ = Benefício suspenso por
         * dependência de cálculo ‘ID’ = Benefício suspenso/cessado pela
         * inspetoria/auditoria ‘IE’ = Benefício bloqueado para empréstimo pelo
         * beneficiário ‘IF’ = Benefício bloqueado para empréstimo por TBM ‘IG’
         * = Benefício está em fase de concessão de PA ou desdobramento ‘IH’ =
         * Benefício cessado por óbito ‘II’ = Benefício cessado por fraude ‘IJ’
         * = Benefício cessado por concessão de outro benefício ‘IK’ = Benefício
         * cessado: estatutário transferido para órgão de origem ‘IL’ =
         * Empréstimo suspenso pela APS ‘IM’ = Empréstimo cancelado pelo banco
         * ‘IN’ = Crédito transformado em PAB ‘IO’ = Término da consignação foi
         * alterado ‘IP’ = Fim do empréstimo ocorreu durante período de
         * suspensão ou concessão ‘IQ’ = Empréstimo suspenso pelo banco ‘IR’ =
         * Não averbação de contrato – quantidade de parcelas/competências
         * informadas ultrapassou a data limite da extinção de cota do
         * dependente titular de benefícios 'TA' = Lote Não Aceito - Totais do
         * Lote com Diferença 'YA' = Título Não Encontrado 'YB' = Identificador
         * Registro Opcional Inválido 'YC' = Código Padrão Inválido 'YD' =
         * Código de Ocorrência Inválido 'YE' = Complemento de Ocorrência
         * Inválido 'YF' = Alegação já Informada Observação: As ocorrências
         * iniciadas com 'ZA' tem caráter informativo para o cliente 'ZA' =
         * Agência / Conta do Favorecido Substituída ‘ZB’ = Divergência entre o
         * primeiro e último nome do beneficiário versus primeiro e último nome
         * na Receita Federal ‘ZC’ = Confirmação de Antecipação de Valor ‘ZD’ =
         * Antecipação parcial de valor ‘ZE’ = Título bloqueado na base ‘ZF’ =
         * Sistema em contingência – título valor maior que referência ‘ZG’ =
         * Sistema em contingência – título vencido ‘ZH’ = Sistema em
         * contingência – título indexado ‘ZI’ = Beneficiário divergente ‘ZJ’ =
         * Limite de pagamentos parciais excedido ‘ZK’ = Boleto já liquidado
         *
         */
        public static TagLayout focorrencias() {
            //TODO: Melhorar isso, criar um Enum
            return field("ocorrencias").filler(Fillers.WHITE_SPACE_LEFT).length(10);
        }

        /**
         * Lote de Serviço Número seqüencial para identificar univocamente um
         * lote de serviço. Criado e controlado pelo responsável pela geração
         * magnética dos dados contidos no arquivo. Preencher com '0001' para o
         * primeiro lote do arquivo. Para os demais: número do lote anterior
         * acrescido de 1. O número não poderá ser repetido dentro do arquivo.
         * Se registro for Header do Arquivo preencher com '0000' Se registro
         * for Trailer do Arquivo preencher com '9999'
         *
         * @return
         */
        public static TagLayout flote() {
            return field("lote").length(4).type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalor() {
            return field("valor").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorPagamento() {
            return field("valorPagamento").type(Number.class).filler(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorOcorrencia() {
            return field("valorOcorrencia").type(Number.class).filler(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorDesconto() {
            return field("valorDesconto").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorAcrescimo() {
            return field("valorAcresciomo").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fnumeroDocumento() {
            return field("numeroDocumento").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fnossoNumero() {
            return field("nossoNumero").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fsequencialRegistro() {
            return field("sequencialRegistro").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        /**
         * Código adotado pela FEBRABAN, para identificar o tipo de movimentação
         * enviada no arquivo. Domínio: '0' = Indica INCLUSÃO ‘1’ = Indica
         * CONSULTA ‘2’ = Indica SUSPENSÃO '3' = Indica ESTORNO (somente para
         * retorno) ‘4’ = Indica REATIVAÇÃO '5' = Indica ALTERAÇÃO ‘7` = Indica
         * LIQUIDAÇAO '9' = Indica EXCLUSÃO
         */
        public static TagLayout fmovimentoTipo() {
            return field("movimentoTipo").length(1);
        }

        /**
         * Código adotado pela FEBRABAN para identificar a moeda referenciada no
         * Título. Domínio: '01' = Reservado para Uso Futuro '02' = Dólar
         * Americano Comercial (Venda) '03' = Dólar Americano Turismo (Venda)
         * '04' = ITRD '05' = IDTR '06' = UFIR Diária '07' = UFIR Mensal '08' =
         * FAJ-TR '09' = Real '10' = TR '11' = IGPM '12' = CDI '13' = Percentual
         * do CDI ‘14’ = Euro
         */
        public static TagLayout fcodigoMoeda() {
            return field("codigoMoeda").value("09").length(2);
        }

        /**
         * Domínio: '01' = Crédito em Conta Corrente/Salário '02' = Cheque
         * Pagamento / Administrativo '03' = DOC/TED (1) (2) '04' = Cartão
         * Salário (somente para Tipo de Serviço = '30') '05' = Crédito em Conta
         * Poupança '10' = OP à Disposição ‘11’ = Pagamento de Contas e Tributos
         * com Código de Barras ‘16’ = Tributo - DARF Normal ‘17’ = Tributo -
         * GPS (Guia da Previdência Social) ‘18’ = Tributo - DARF Simples ‘19’ =
         * Tributo - IPTU – Prefeituras '20' = Pagamento com Autenticação ‘21’ =
         * Tributo – DARJ ‘22’ = Tributo - GARE-SP ICMS ‘23’ = Tributo - GARE-SP
         * DR ‘24’ = Tributo - GARE-SP ITCMD ‘25’ = Tributo - IPVA ‘26’ =
         * Tributo - Licenciamento ‘27’ = Tributo – DPVAT '30' = Liquidação de
         * Títulos do Próprio Banco '31' = Pagamento de Títulos de Outros Bancos
         * '40' = Extrato de Conta Corrente '41' = TED – Outra Titularidade (1)
         * '43' = TED – Mesma Titularidade (1) ‘44’ = TED para Transferência de
         * Conta Investimento '50' = Débito em Conta Corrente '70' = Extrato
         * para Gestão de Caixa ‘71’ = Depósito Judicial em Conta Corrente ‘72’
         * = Depósito Judicial em Poupança ‘73’ = Extrato de Conta Investimento
         * ‘80’= Pagamento de tributos municipais ISS – LCP 157 – próprio Banco
         * ‘81’= Pagamento de Tributos Municipais ISS – LCP 157 – outros Bancos
         */
        public static TagLayout fforma() {
            return field("forma").length(2).padding(Fillers.ZERO_LEFT);
        }

        /**
         * Esse field ira gerar zeros conforme o tamanho do Length.
         *
         * @return
         */
        public static TagLayout fzero() {
            return field("").filler(Fillers.ZERO_LEFT);
        }

        /**
         * Esse field ira gerar espaços em branco.
         *
         * @return
         */
        public static TagLayout fbranco() {
            return field("").filler(Fillers.WHITE_SPACE_LEFT);
        }

        /**
         * G010 Número da Conta Corrente G010
         *
         * Número adotado pelo Banco, para identificar univocamente a conta
         * corrente utilizada pelo Cliente. G011 Dígito Verificador da Conta
         * G011 Código adotado pelo responsável pela conta corrente, para
         * verificação da autenticidade do Número da Conta Corrente. Para os
         * Bancos que se utilizam de duas posições para o Dígito Verificador do
         * Número da Conta Corrente, preencher este campo com a 1a posição deste
         * dígito. Exemplo : Número C/C = 45981-36 Neste caso → Dígito
         * Verificador da Conta = 3
         *
         * @return
         */
        public static TagLayout fconta() {
            return field("conta").padding(Fillers.ZERO_LEFT);
        }

        /**
         * Código adotado pela FEBRABAN para identificar o tipo de registro.
         * Domínio: '0' = Header de Arquivo '1' = Header de Lote '2' = Registros
         * Iniciais do Lote '3' = Detalhe '4' = Registros Finais do Lote '5' =
         * Trailer de Lote '9' = Trailer de Arquivo
         *
         * @return
         */
        public static TagLayout fcodigoRegistro() {
            return field("codigoRegistro").length(1).id(true);
        }

        /**
         * Domínio: 'C' = Lançamento a Crédito 'D' = Lançamento a Débito 'E' =
         * Extrato para Conciliação 'G' = Extrato para Gestão de Caixa 'I' =
         * Informações de Títulos Capturados do Próprio Banco 'R' = Arquivo
         * Remessa 'T' = Arquivo Retorno
         *
         * @return
         */
        public static TagLayout foperacao() {
            return field("operacao").length(1);
        }

        /**
         * Domínio: '01' = Cobrança '03' = Boleto de Pagamento Eletrônico '04' =
         * Conciliação Bancária '05' = Débitos '06' = Custódia de Cheques '07' =
         * Gestão de Caixa '08' = Consulta/Informação Margem '09' = Averbação da
         * Consignação/Retenção '10' = Pagamento Dividendos ‘11’ = Manutenção da
         * Consignação ‘12’ = Consignação de Parcelas ‘13’ = Glosa da
         * Consignação (INSS) ‘14’ = Consulta de Tributos a pagar '20' =
         * Pagamento Fornecedor ‘22’ = Pagamento de Contas, Tributos e Impostos
         * ‘23’ = Interoperabilidade entre Contas de Instituições de Pagamentos
         * ‘25’ = Compror ‘26’ = Compror Rotativo '29' = Alegação do Pagador
         * '30' = Pagamento Salários ‘32’ = Pagamento de honorários ‘33’ =
         * Pagamento de bolsa auxílio ‘34’ = Pagamento de prebenda (remuneração
         * a padres e sacerdotes) '40' = Vendor '41' = Vendor a Termo '50' =
         * Pagamento Sinistros Segurados '60' = Pagamento Despesas Viajante em
         * Trânsito '70' = Pagamento Autorizado '75' = Pagamento Credenciados
         * ‘77’ = Pagamento de Remuneração '80' = Pagamento Representantes /
         * Vendedores Autorizados '90' = Pagamento Benefícios '98' = Pagamentos
         * Diversos
         *
         * @return
         */
        public static TagLayout fservico() {
            return field("servico").length(2).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fcodigoRetorno() {
            return field("codigoRetorno").length(1);
        }

        public static TagLayout fsequencialArquivo() {
            return field("sequencialArquivo").padding(Fillers.ZERO_LEFT);
        }

        /**
         * Código Remessa / Retorno 1431431-NumG015 Código adotado pela FEBRABAN
         * para qualificar o envio ou devolução de arquivo entre a Empresa
         * Cliente e o Banco prestador dos Serviços. Domínio: '1' = Remessa
         * (Cliente -> Banco) '2' = Retorno (Banco -> Cliente)
         *
         * @return
         */
        public static TagLayout fcodigoArquivo() {
            return field("codigoArquivo").length(1);
        }

        public static TagLayout fdataGeracao() {
            return field("dataGeracao").type(Date.class).format(new SimpleDateFormat("ddMMyyyy")).length(8);
        }

        public static TagLayout fdataOcorrencia() {
            return field("dataOcorrencia").length(8).format(new SimpleDateFormat("ddMMyyyy"));
        }

        public static TagLayout fdataVencimento() {
            return field("dataVencimento").length(8).format(new SimpleDateFormat("ddMMyyyy"));
        }

        /**
         * Data Pagamento Data do Pagamento 145 152 8 - Num P009 Data do
         * Pagamento Data do pagamento do compromisso. Utilizar o formato
         * DDMMAAAA, onde: DD = dia MM = mês AAAA = ano
         *
         * @return
         */
        public static TagLayout fdataPagamento() {
            return field("dataPagamento").filler(Fillers.ZERO_LEFT).length(8).format(new SimpleDateFormat("ddMMyyyy"));
        }

        public static TagLayout cabecalho(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout cabecalhoLote(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout titulo(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout tituloJ(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout tituloJ52(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout rodape(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout rodapeLote(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout group(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout descricao(String texto) {
            return tagin().withValue(texto);
        }

        public static TagLayout versao(String texto) {
            return tagin().withValue(texto);
        }

        public static TagLayout nome(String texto) {
            return tagin().withValue(texto);
        }

        public static TagLayout layout(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout banco(IBanco banco) {
            return tagin().withValue(banco);
        }

        public static TagLayout servico(CNABServico servico) {
            return tagin().withValue(servico);
        }

        public static TagLayout cnab(CNAB cnab) {
            return tagin().withValue(cnab);
        }

        public static TagLayout flatfile(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        private static TagLayout tagin() {
            String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
            return tag(nomeMetodoAnterior);
        }

        public static TagLayout tag(String nome) {
            return new TagLayout(nome);
        }
    }

    String nome;
    Object value;
    List<TagLayout> filhos;
    Map<String, Object> atributos;

    TagLayout get(TagLayout tag) {
        return get(tag.nome);
    }

    TagLayout get(String strfilho) {
        TagLayout fi = null;
        for (TagLayout filho : filhos) {
            if (filho.nome.equalsIgnoreCase(strfilho)) {
                fi = filho;
                break;
            }
        }
        return fi;
    }

    String getAtr(String stratt) {
        String ret = null;
        Object att = atributos.get(stratt);
        if (att != null) {
            ret = att.toString();
        }
        return ret;
    }

    Integer getInt(String stratt) {
        Integer ret = null;
        Object obj = atributos.get(stratt);
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            if (obj instanceof Integer) {
                ret = (Integer) obj;
            } else {
                ret = ((Number) obj).intValue();
            }
        } else {
            ret = Integer.parseInt(obj.toString());
        }
        return ret;
    }

    Object getObj(String stratt) {
        return atributos.get(stratt);
    }

    Object getValue(String nome) {
        Object ret = null;
        ret = this.atributos.get(nome);
        if (ret == null) {
            TagLayout fi = this.get(nome);
            if (fi != null) {
                ret = fi.getValue();
            }
        }
        return ret;
    }

    public TagLayout(String nome) {
        this.nome = nome;
        this.filhos = new ArrayList<>();
        this.atributos = new TreeMap<>();
    }

    public TagLayout setAttr(String nome, Object valor) {
        return this.atr(nome, valor);
    }

    public TagLayout atr(String nome, Object valor) {
        atributos.put(nome, valor);
        return this;
    }

    public TagLayout type(Class tipo) {
        return setAttr(tipo);
    }

    public TagLayout filler(IFiller padding) {
        return setAttr(padding);
    }

    public TagLayout padding(IFiller padding) {
        return setAttr(padding);
    }

    public TagLayout format(Format padding) {
        return setAttr(padding);
    }

    /**
     * Set value and lenght from string val
     */
    public TagLayout valLen(String val) {
        this.value(val);
        this.length(val.length());
        return this;
    }

    public TagLayout id(boolean bol) {
        return setAttr(bol);
    }

    public TagLayout length(int len) {
        return setAttr(len);
    }

    public TagLayout position(int len) {
        return setAttr(len);
    }

    public TagLayout value(Object len) {
        return setAttr(len);
    }

    public TagLayout withValue(Object texto) {
        this.value = texto;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public String getNome() {
        return nome;
    }

    public TagLayout with(TagLayout... filhos) {
        if (filhos != null) {
            for (TagLayout filho : filhos) {
                this.filhos.add(filho);
            }
        }
        return this;
    }

    protected TagLayout setAttr(Object valor) {
        //TODO: Melhorar isso;
        String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
        /* Propriedade a ser setada é o nome do metodo que chamou */
        this.atr(nomeMetodoAnterior, valor);
        return this;
    }

    public TagLayout clone() {
        return SerializationUtils.clone(this);
    }

    //TODO: Melhorar isso, procurar alguma lib que faça o clone e já transforme o objeto em imutavel
    public TagLayout cloneReadonly() {
        TagLayout clone = this.clone();
        colecoesImutaveis(clone);
        return clone;
    }

    private void colecoesImutaveis(TagLayout clone) {
        clone.atributos = Collections.unmodifiableMap(clone.atributos);
        clone.filhos = Collections.unmodifiableList(clone.filhos);
        for (TagLayout tf : clone.filhos) {
            colecoesImutaveis(tf);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TagLayout other = (TagLayout) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

}
