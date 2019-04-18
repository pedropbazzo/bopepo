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
import java.util.List;
import org.apache.log4j.Logger;
import org.jrimum.texgit.FixedField;
import org.jrimum.texgit.IFiller;
import org.jrimum.texgit.Record;
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
        CabecalhoRemessa cabecalho = novoCabecalho();
        this.add(cabecalho);
        return cabecalho;
    }

    CabecalhoRemessa novoCabecalho() {
        CabecalhoRemessa cabecalho = new CabecalhoRemessa(template.get("cabecalho"));
        return cabecalho;
    }

    TituloRemessa addNovoTitulo() {
        TituloRemessa titulo = this.novoTitulo();
        this.add(titulo);
        return titulo;
    }

    TituloRemessa novoTitulo() {
        TituloRemessa titulo = new TituloRemessa(template.get("titulo"));
        return titulo;
    }

    RodapeRemessa addNovoRodape() {
        RodapeRemessa rodape = this.novoRodape();
        this.add(rodape);
        return rodape;
    }

    RodapeRemessa novoRodape() {
        RodapeRemessa rodape = new RodapeRemessa(template.get("rodape"));
        return rodape;
    }

    public RegistroRemessa novoRegistro(String tipoRegistro) {
        TagLayout layoutRegistro = template.get(tipoRegistro);
        return new RegistroRemessa(layoutRegistro);
    }

    String render() {
        StringBuilder sb = new StringBuilder();

        for (RegistroRemessa r : this.registros) {
            sb.append(r.render());
        }
        return sb.toString();
    }

    public static class RegistroRemessa extends Record {

        public RegistroRemessa() {
        }

        public RegistroRemessa(TagLayout layoutRegistro) {
            layoutRegistro.filhos.stream().forEach(l -> add(l));
        }

        public String render() {
//            StringBuilder sb = new StringBuilder();
//            return sb.toString(); 
            return this.write();
        }

        protected RegistroRemessa setValue(String valor) {
            //TODO: Melhorar isso;
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
            }
            Format format = (Format) l.getObj("format");
            if (Objects.isNotNull(format)) {
                fixedField.setFormatter(format);
            }
            IFiller filler = (IFiller) l.getObj("filler");
            if (Objects.isNotNull(filler)) {
                fixedField.setFiller(filler);
            }
            super.add(fixedField);
            super.incSize();
        }

        private boolean isValid(String nome) {
            return nome != null && !nome.trim().isEmpty();
        }
    }

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

        CabecalhoRemessa cedenteCnpj(String string) {
            return (CabecalhoRemessa) setValue(string);
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
        TituloRemessa valor(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa vencimento(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa numeroDocumento(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa nossoNumero(String string) {
            return (TituloRemessa) setValue(string);
        }

        TituloRemessa dataEmissao(String string) {
            return (TituloRemessa) setValue(string);
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
    }

    /* 
    
     */
    List<RegistroRemessa> registros = new ArrayList<>();

    RemessaFacade add(RegistroRemessa reg) {
        registros.add(reg);
        return this;
    }
}
