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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.github.braully.boleto.TagLayout.TagCreator.*;
import org.apache.log4j.Logger;
import org.jrimum.texgit.FixedField;
import org.jrimum.texgit.IFiller;
import org.jrimum.texgit.Record;
import org.jrimum.utilix.FileUtil;
import org.jrimum.utilix.Objects;

/**
 *
 * @author braully
 */
public class ArquivoFacade {

    public static Logger logger = Logger.getLogger(ArquivoFacade.class);
    /*
    
     */
    TagLayout template;

    public ArquivoFacade() {
    }

    public ArquivoFacade(TagLayout template) {
        this.template = template;
    }

    public List<String> renderLinhas() {
        List<String> linhas = new ArrayList<>();
        for (RegistroArquivo r : this.registros) {
            String render = r.render();
            linhas.add(render);
        }
        return linhas;
    }

    public void parse(List<String> linhas) {
        this.registros.clear();
        this.linhas.clear();
        if (linhas != null) {
            this.linhas.addAll(linhas);
        }
        for (String linha : linhas) {
            for (TagLayout tag : this.template.filhos) {
                RegistroArquivo reg = new RegistroArquivo(tag);
                reg.read(linha);
            }
        }
    }

//TODO: Melhorar isso
    public String render() {
        StringBuilder sb = new StringBuilder();
        List<String> linhas = this.renderLinhas();
        for (String linha : linhas) {
            sb.append(linha);
            sb.append(FileUtil.NEW_LINE);
        }
        return sb.toString();

    }

    public static class RegistroArquivo extends Record {

        public RegistroArquivo() {
        }

        public RegistroArquivo(TagLayout layoutRegistro) {
            this.setName(layoutRegistro.nome);
            layoutRegistro.filhos.stream().forEach(l -> add(l));
        }

        public String render() {
//            StringBuilder sb = new StringBuilder();
//            return sb.toString(); 
            return this.write();
        }

        /* Campos comuns na maioria dos registros na maioria dos layouts */
        public RegistroArquivo sequencialRegistro(Integer seq) {
            setValue(fsequencialRegistro().nome, seq);
            return this;
        }

        public Integer sequencialRegistro() {
            return this.getValueAsInteger();
        }

        public RegistroArquivo banco(String codigo, String nome) {
            setValue(fbancoCodigo().nome, codigo).setValue(fbancoNome().nome, nome);
            return this;
        }

        public String bancoCodigo() {
            return getValue(fbancoCodigo().nome);
        }

        public RegistroArquivo cedente(String nome, String cnpj) {
            setValue(fcedenteNome().nome, nome).setValue(fcedenteCnpj().nome, cnpj);
            return this;
        }

        public String cedenteCnpj() {
            return getValue(fcedenteCnpj().nome);
        }

        public RegistroArquivo convenio(String convenio, String agencia, String conta, String dac) {
            this.convenio(convenio).agencia(agencia).conta(conta).dac(dac);
            return this;
        }

        public RegistroArquivo convenio(String convenio) {
            return setValue(convenio);
        }

        public String convenio() {
            return getValue(fconvenio().nome);
        }

        public RegistroArquivo agencia(String agencia) {
            return setValue(agencia);
        }

        public String agencia() {
            return getValue(fagencia().nome);
        }

        public RegistroArquivo conta(String conta) {
            return setValue(conta);
        }

        public String conta() {
            return getValue(fconta().nome);
        }

        public RegistroArquivo dac(String dac) {
            return setValue(dac);
        }

        public RegistroArquivo setVal(String nomeAtributo, Object valor) {
            this.setValue(nomeAtributo, valor);
            return this;
        }

        protected String getValue() {
            //TODO: Melhorar isso; perda de performance
            String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
            /* Propriedade a ser setada é o nome do metodo que chamou */
            Object value = this.getValue(nomeMetodoAnterior);
            String ret = null;
            if (value != null) {
                ret = value.toString();
            }
            return ret;
        }

        protected String removeLeftZeros(String number) {
            if (number == null) {
                return null;
            }
            return number.replaceFirst("^0+(?!$)", "");
        }

        protected String trimNumberValue(String str) {
            if (str != null) {
                str = str.replaceAll("\\D", "");
                str = removeLeftZeros(str);
            }
            return str;
        }

        public Integer getValueAsInteger() {
            //TODO: Melhorar isso; perda de performance
            String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
            return getValueAsInteger(nomeMetodoAnterior);
        }

        public Integer getValueAsInteger(String nomefield) {
            /* Propriedade a ser setada é o nome do metodo que chamou */
            Object value = this.getValue(nomefield);
            Integer ret = null;
            if (value != null) {
                if (value instanceof Integer) {
                    ret = (Integer) value;
                } else {
                    ret = Integer.parseInt(trimNumberValue(value.toString()));
                }
            }
            return ret;
        }

        public Number getValueAsNumber() {
            //TODO: Melhorar isso; perda de performance
            String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
            /* Propriedade a ser setada é o nome do metodo que chamou */
            Object value = this.getValue(nomeMetodoAnterior);
            Number ret = null;
            if (value != null) {
                if (value instanceof Number) {
                    ret = (Number) value;
                } else {
                    ret = Long.parseLong(trimNumberValue(value.toString()));
                }
            }
            return ret;
        }

        protected RegistroArquivo setValue(Object valor) {
            //TODO: Melhorar isso; perda de performance
            String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
            /* Propriedade a ser setada é o nome do metodo que chamou */
            this.setValue(nomeMetodoAnterior, valor);
            return this;
        }

        private void add(TagLayout l) {
            FixedField fixedField = new FixedField();
            if (isValid(l.nome)) {
                fixedField.setName(l.nome);
            }
            String value = l.getAtr("value");
            if (isValid(value)) {
                fixedField.setValue(value);
            }
            Integer len = l.getInt("length");
            if (Objects.isNotNull(len)) {
                fixedField.setFixedLength(len);
            } else if ("field".equals(l.nome)) {
                throw new IllegalArgumentException("Field " + l + " sem comprimento (lenght) ");
            }
            Format format = (Format) l.getObj("format");
            if (Objects.isNotNull(format)) {
                fixedField.setFormatter(format);
            }
            IFiller filler = (IFiller) l.getObj("filler");
            if (Objects.isNotNull(filler)) {
                fixedField.setFiller(filler);
                fixedField.setBlankAccepted(true);
                fixedField.setValue("");
            }
            filler = (IFiller) l.getObj("padding");
            if (Objects.isNotNull(filler)) {
                fixedField.setFiller(filler);
            }
            super.add(fixedField);
            super.incLength(len);
            super.incSize();
        }

        private boolean isValid(String nome) {
            return nome != null;
        }
    }

    /* Reune atributos recorrentes nos registros do tipo cabecalho */
    public static class CabecalhoArquivo extends RegistroArquivo {

        public CabecalhoArquivo(TagLayout get) {
            super(get);
        }

        public CabecalhoArquivo agencia(String string) {
            return (CabecalhoArquivo) setValue(string);
        }

        public CabecalhoArquivo conta(String string) {
            return (CabecalhoArquivo) setValue(string);
        }

        public CabecalhoArquivo numeroConvenio(String string) {
            return (CabecalhoArquivo) setValue(string);
        }

        public CabecalhoArquivo cedente(String string) {
            return (CabecalhoArquivo) setValue(string);
        }

        public CabecalhoArquivo dataGeracao(String string) {
            return (CabecalhoArquivo) setValue(string);
        }

        public CabecalhoArquivo dataGeracao(Date data) {
            return (CabecalhoArquivo) setValue(data);
        }

        public CabecalhoArquivo cedenteCnpj(String string) {
            return (CabecalhoArquivo) setValue(string);
        }

        public CabecalhoArquivo sequencialArquivo(Integer i) {
            setValue(fsequencialArquivo().nome, i);
            return this;
        }

        public Integer sequencialArquivo() {
            return getValueAsInteger(fsequencialArquivo().nome);
        }

        public CabecalhoArquivo operacao(Object op) {
            setValue(foperacao().nome, op);
            return this;
        }

        public String operacao() {
            return getValue(foperacao().nome);
        }

        public CabecalhoArquivo servico(Object op) {
            setValue(fservico().nome, op);
            return this;
        }

        public String servico() {
            return getValue(fservico().nome);
        }

        public CabecalhoArquivo forma(Object op) {
            setValue(fforma().nome, op);
            return this;
        }

        public Object forma() {
            return getValue(fforma().nome);
        }
    }

    public static class TituloArquivo extends RegistroArquivo {

        public TituloArquivo(TagLayout get) {
            super(get);
        }

        /* 
                remessa.addTitulo().valor("").vencimento("")
                .numeroDocumento("").nossoNumero("")
                .dataEmissao("").carteira("")
                .sacado("Sacado da Silva Sauro").sacadoCpf("01234567891")
                .sacadoEndereco("")
                .instrucao("");
         */
        public TituloArquivo sacado(String nome, String cpf) {
            setValue(fsacadoNome().nome, nome).setValue(fsacadoCpf().nome, cpf);
            return this;
        }

        public String sacadoCpf() {
            return getValue(fsacadoCpf().nome);
        }

        public TituloArquivo valor(Object string) {
            return (TituloArquivo) setValue(fvalor().nome, string);
        }

        public String valor() {
            return getValue(fvalor().nome);
        }

        public TituloArquivo valorDesconto(Object string) {
            return (TituloArquivo) setValue(fvalorDesconto().nome, string);
        }

        public String valorDesconto() {
            return getValue(fvalorDesconto().nome);
        }

        public TituloArquivo valorAcrescimo(Object string) {
            return (TituloArquivo) setValue(fvalorAcrescimo().nome, string);
        }

        public String valorAcrescimo() {
            return getValue(fvalorAcrescimo().nome);
        }

        public TituloArquivo vencimento(String string) {
            return (TituloArquivo) setValue(string);
        }

        public String vencimento() {
            return getValue("vencimento");
        }

        public TituloArquivo numeroDocumento(Object string) {
            return (TituloArquivo) setValue(string);
        }

        public String numeroDocumento() {
            return getValue(fnumeroDocumento().nome);
        }

        public TituloArquivo codigoBarras(String codigoBarras) {
            return (TituloArquivo) setVal(fcodigoBarras().nome, codigoBarras);
        }

        public TituloArquivo nossoNumero(Object string) {
            return (TituloArquivo) setValue(string);
        }

        public String nossoNumero() {
            return getValue("nossoNumero");
        }

        public TituloArquivo dataVencimento(Object vencimento) {
            return (TituloArquivo) setValue(vencimento);
        }

        public TituloArquivo dataEmissao(Object emissao) {
            return (TituloArquivo) setValue(emissao);
        }

        public String dataOcorrencia(Object emissao) {
            return getValue(fdataOcorrencia().nome);
        }

        public Number valorOcorrencia(Object string) {
            return getValue(fvalorOcorrencia().nome);
        }

        public String ocorrencias(Object string) {
            return getValue(focorrencias().nome);
        }

        public TituloArquivo carteira(String string) {
            return (TituloArquivo) setValue(string);
        }

        public TituloArquivo sacado(String string) {
            return (TituloArquivo) setValue(string);
        }

        public TituloArquivo sacadoCpf(String string) {
            return (TituloArquivo) setValue(string);
        }

        public TituloArquivo sacadoEndereco(String string) {
            return (TituloArquivo) setValue(string);
        }

        public TituloArquivo instrucao(String string) {
            return (TituloArquivo) setValue(string);
        }
    }

    public static class RodapeArquivo extends RegistroArquivo {

        public RodapeArquivo(TagLayout get) {
            super(get);
        }

        public RodapeArquivo quantidadeRegistros(Number valorQuantidade) {
            return (RodapeArquivo) setValue(valorQuantidade);
        }

        public Number quantidadeRegistros() {
            return getValueAsNumber();
        }

        public RodapeArquivo valorTotalRegistros(Number valorTotal) {
            return (RodapeArquivo) setValue(valorTotal);
        }
    }

    /* 
    
     */
    List<RegistroArquivo> registros = new ArrayList<>();
    List<String> linhas = new ArrayList<>();

    public ArquivoFacade add(RegistroArquivo reg) {
        registros.add(reg);
        return this;
    }

    public RegistroArquivo get(String str) {
        return this.get(template.get(str));
    }

    public RegistroArquivo get(TagLayout tiporegistro) {
        //Melhorar isso, indexar via Map
        for (RegistroArquivo reg : registros) {
            if (reg.getName().equalsIgnoreCase(tiporegistro.nome)) {
                return reg;
            }
        }
        return null;
    }

    public List<RegistroArquivo> gets(TagLayout tiporegistro) {
        List<RegistroArquivo> regs = new ArrayList<>();
        //Melhorar isso, indexar via Map
        for (RegistroArquivo reg : registros) {
            if (reg.getName().equalsIgnoreCase(tiporegistro.nome)) {
                regs.add(reg);
            }
        }
        return regs;
    }
}
