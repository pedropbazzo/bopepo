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
public class RemessaFacade {

    public static Logger logger = Logger.getLogger(RemessaFacade.class);
    /*
    
     */
    TagLayout template;

    public RemessaFacade(TagLayout template) {
        this.template = template;
    }

    CabecalhoRemessa addNovoCabecalho() {
        CabecalhoRemessa cabecalho = novoCabecalho("cabecalho");
        this.add(cabecalho);
        return cabecalho;
    }

    CabecalhoRemessa addNovoCabecalhoLote() {
        CabecalhoRemessa cabecalho = novoCabecalho("cabecalhoLote");
        this.add(cabecalho);
        return cabecalho;
    }

    CabecalhoRemessa novoCabecalho(String tipoCabecalho) {
        CabecalhoRemessa cabecalho = new CabecalhoRemessa(template.get(tipoCabecalho));
        return cabecalho;
    }

    TituloRemessa addNovoTitulo() {
        TituloRemessa titulo = this.novoTitulo("titulo");
        this.add(titulo);
        return titulo;
    }

    TituloRemessa addNovoTituloJ() {
        TituloRemessa titulo = this.novoTitulo("tituloJ");
        this.add(titulo);
        return titulo;
    }

    TituloRemessa addNovoTituloJ52() {
        TituloRemessa titulo = this.novoTitulo("tituloJ52");
        this.add(titulo);
        return titulo;
    }

    TituloRemessa novoTitulo(String tipoTitulo) {
        TituloRemessa titulo = new TituloRemessa(template.get(tipoTitulo));
        return titulo;
    }

    RodapeRemessa addNovoRodape() {
        RodapeRemessa rodape = this.novoRodape("rodape");
        this.add(rodape);
        return rodape;
    }

    RodapeRemessa addNovoRodapeLote() {
        RodapeRemessa rodape = this.novoRodape("rodapeLote");
        this.add(rodape);
        return rodape;
    }

    RodapeRemessa novoRodape(String tipoRodape) {
        RodapeRemessa rodape = new RodapeRemessa(template.get(tipoRodape));
        return rodape;
    }

    public RegistroRemessa addNovoRegistro(String tipoRegistro) {
        RegistroRemessa novoRegistro = novoRegistro(tipoRegistro);
        this.add(novoRegistro);
        return novoRegistro;
    }

    public RegistroRemessa novoRegistro(String tipoRegistro) {
        TagLayout layoutRegistro = template.get(tipoRegistro);
        if (layoutRegistro == null) {
            throw new IllegalArgumentException("Não existe registro do tipo " + tipoRegistro + " no layout " + template);
        }
        return new RegistroRemessa(layoutRegistro);
    }

    public List<String> renderLinhas() {
        List<String> linhas = new ArrayList<>();
        for (RegistroRemessa r : this.registros) {
            String render = r.render();
            linhas.add(render);
        }
        return linhas;
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

    public static class RegistroRemessa extends Record {

        public RegistroRemessa() {
        }

        public RegistroRemessa(TagLayout layoutRegistro) {
            this.setName(layoutRegistro.nome);
            layoutRegistro.filhos.stream().forEach(l -> add(l));
        }

        public String render() {
//            StringBuilder sb = new StringBuilder();
//            return sb.toString(); 
            return this.write();
        }

        /* Campos comuns na maioria dos registros na maioria dos layouts */
        RegistroRemessa sequencialRegistro(Integer seq) {
            setValue(fsequencialRegistro().nome, seq);
            return this;
        }

        RegistroRemessa banco(String codigo, String nome) {
            setValue(fbancoCodigo().nome, codigo).setValue(fbancoNome().nome, nome);
            return this;
        }

        RegistroRemessa cedente(String nome, String cnpj) {
            setValue(fcedenteNome().nome, nome).setValue(fcedenteCnpj().nome, cnpj);
            return this;
        }

        RegistroRemessa convenio(String convenio, String agencia, String conta, String dac) {
            this.convenio(convenio).agencia(agencia).conta(conta).dac(dac);
            return this;
        }

        RegistroRemessa convenio(String convenio) {
            return setValue(convenio);
        }

        RegistroRemessa agencia(String agencia) {
            return setValue(agencia);
        }

        RegistroRemessa conta(String conta) {
            return setValue(conta);
        }

        RegistroRemessa dac(String dac) {
            return setValue(dac);
        }

        RegistroRemessa setVal(String nomeAtributo, Object valor) {
            this.setValue(nomeAtributo, valor);
            return this;
        }

        protected RegistroRemessa setValue(Object valor) {
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
    public static class CabecalhoRemessa extends RegistroRemessa {

        private CabecalhoRemessa(TagLayout get) {
            super(get);
        }

        CabecalhoRemessa agencia(String string) {
            return (CabecalhoRemessa) setValue(string);
        }

        CabecalhoRemessa conta(String string) {
            return (CabecalhoRemessa) setValue(string);
        }

        CabecalhoRemessa numeroConvenio(String string) {
            return (CabecalhoRemessa) setValue(string);
        }

        CabecalhoRemessa cedente(String string) {
            return (CabecalhoRemessa) setValue(string);
        }

        CabecalhoRemessa dataGeracao(String string) {
            return (CabecalhoRemessa) setValue(string);
        }

        CabecalhoRemessa dataGeracao(Date data) {
            return (CabecalhoRemessa) setValue(data);
        }

        CabecalhoRemessa cedenteCnpj(String string) {
            return (CabecalhoRemessa) setValue(string);
        }

        CabecalhoRemessa sequencialArquivo(Integer i) {
            setValue(fsequencialArquivo().nome, i);
            return this;
        }

        CabecalhoRemessa operacao(Object op) {
            setValue(foperacao().nome, op);
            return this;
        }

        CabecalhoRemessa servico(Object op) {
            setValue(fservico().nome, op);
            return this;
        }

        CabecalhoRemessa forma(Object op) {
            setValue(fforma().nome, op);
            return this;
        }
    }

    public static class TituloRemessa extends RegistroRemessa {

        private TituloRemessa(TagLayout get) {
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
        TituloRemessa sacado(String nome, String cpf) {
            setValue(fsacadoNome().nome, nome).setValue(fsacadoCpf().nome, cpf);
            return this;
        }

        TituloRemessa valor(Object string) {
            return (TituloRemessa) setValue(fvalor().nome, string);
        }

        TituloRemessa valorDesconto(Object string) {
            return (TituloRemessa) setValue(fvalorDesconto().nome, string);
        }

        TituloRemessa valorAcrescimo(Object string) {
            return (TituloRemessa) setValue(fvalorAcrescimo().nome, string);
        }

        TituloRemessa vencimento(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa numeroDocumento(Object string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa codigoBarras(String codigoBarras) {
            return (TituloRemessa) setVal(fcodigoBarras().nome, codigoBarras);
        }

        TituloRemessa nossoNumero(Object string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa dataVencimento(Object vencimento) {
            return (TituloRemessa) setValue(vencimento);
        }

        TituloRemessa dataEmissao(Object emissao) {
            return (TituloRemessa) setValue(emissao);
        }

        TituloRemessa carteira(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa sacado(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa sacadoCpf(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa sacadoEndereco(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa instrucao(String string) {
            return (TituloRemessa) setValue(string);
        }
    }

    public static class RodapeRemessa extends RegistroRemessa {

        private RodapeRemessa(TagLayout get) {
            super(get);
        }

        RodapeRemessa quantidadeRegistros(Number valorQuantidade) {
            return (RodapeRemessa) setValue(valorQuantidade);
        }

        RodapeRemessa valorTotalRegistros(Number valorTotal) {
            return (RodapeRemessa) setValue(valorTotal);
        }
    }

    /* 
    
     */
    List<RegistroRemessa> registros = new ArrayList<>();

    RemessaFacade add(RegistroRemessa reg) {
        registros.add(reg);
        return this;
    }
}
