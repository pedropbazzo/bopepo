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

    public static class TagCreator {

        public static TagLayout field(String texto) {
            return tag(texto);
        }

        /* Fields mais comuns */
        public static TagLayout fbancoCodigo() {
            return field("bancoCodigo").padding(Fillers.ZERO_LEFT).length(3);
        }

        public static TagLayout fbancoNome() {
            return field("bancoNome").padding(Fillers.WHITE_SPACE_RIGHT);
        }

        /**
         * Agência Mantenedora da Conta 53 57 5-Num*G008. Dígito Verificador da
         * Agência 58 58 1-Alfa*G009
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

        public static TagLayout focorrencias() {
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

        public static TagLayout fzero() {
            return field("").filler(Fillers.ZERO_LEFT);
        }

        public static TagLayout fbranco() {
            return field("").filler(Fillers.WHITE_SPACE_LEFT);
        }

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
            return field("codigoRegistro").length(1);
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
}
