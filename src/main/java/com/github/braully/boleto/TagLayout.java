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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jrimum.domkee.banco.IBanco;
import org.jrimum.texgit.Fillers;
import org.jrimum.texgit.IFiller;

/**
 *
 * @author strike
 */
public class TagLayout {

    public static class TagCreator {

        public static TagLayout field(String texto) {
            return tag(texto);
        }

        /* Fields mais comuns */
        public static TagLayout fbancoCodigo() {
            return field("bancoCodigo").padding(Fillers.ZERO_LEFT).length(3);
        }

        public static TagLayout fbanco() {
            return field("banco").padding(Fillers.WHITE_SPACE_RIGHT);
        }

        public static TagLayout fagencia() {
            return field("agencia").padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fdac() {
            return field("dac").length(1);
        }

        public static TagLayout fsacado() {
            return field("sacado").padding(Fillers.WHITE_SPACE_RIGHT).length(30);
        }

        public static TagLayout fsacadoCpf() {
            return field("sacadoCpf").length(14).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fcedente() {
            return field("cedente").padding(Fillers.WHITE_SPACE_RIGHT).length(30);
        }

        public static TagLayout fconvenio() {
            return field("convenio").padding(Fillers.ZERO_LEFT);
        }

        /**
         * Tipo de Inscrição: '0' = Isento / Não Informado '1' = CPF '2' = CGC /
         * CNPJ '3' = PIS / PASEP '9' = Outros
         */
        public static TagLayout ftipoInscricao() {
            return field("tipoInscricao").padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fcedenteCnpj() {
            return field("cedenteCnpj").padding(Fillers.ZERO_LEFT).length(14);
        }

        public static TagLayout fquantidadeRegistros() {
            return field("quantidadeRegistros").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorTotalRegistros() {
            return field("valorTotalRegistros").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout focorrencias() {
            return field("ocorrencias").length(10);
        }

        public static TagLayout flote() {
            return field("lote").length(4).type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalor() {
            return field("valor").type(Number.class).padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fvalorPagamento() {
            return field("valorPagamento").type(Number.class).padding(Fillers.ZERO_LEFT);
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

        public static TagLayout fzero() {
            return field("").filler(Fillers.ZERO_LEFT);
        }

        public static TagLayout fbranco() {
            return field("").filler(Fillers.WHITE_SPACE_LEFT);
        }

        public static TagLayout fconta() {
            return field("conta").padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fcodigoRegistro() {
            return field("codigoRegistro").length(1);
        }

        public static TagLayout fcodigoRetorno() {
            return field("codigoRetorno").length(1);
        }

        public static TagLayout fsequencialArquivo() {
            return field("sequencialArquivo").padding(Fillers.ZERO_LEFT);
        }

        public static TagLayout fcodigoArquivo() {
            return field("codigoArquivo").length(1);
        }

        public static TagLayout fdataGeracao() {
            return field("dataGeracao").type(Date.class).format(new SimpleDateFormat("ddMMyyyy")).length(8);
        }

        public static TagLayout fdataVencimento() {
            return field("dataVencimento").length(8).format(new SimpleDateFormat("ddMMyyyy"));
        }

        public static TagLayout fdataPagamento() {
            return field("dataPagamento").length(8).format(new SimpleDateFormat("ddMMyyyy"));
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
    final List<TagLayout> filhos;
    final Map<String, Object> atributos;

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
}
