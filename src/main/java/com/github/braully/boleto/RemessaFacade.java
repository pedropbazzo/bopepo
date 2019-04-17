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
import org.jrimum.texgit.IFlatFile;
import org.jrimum.texgit.Record;

/**
 *
 * @author braully
 */
public class RemessaFacade {

    public static Logger logger = Logger.getLogger(RemessaFacade.class);
    /*
    
     */
    IFlatFile layoutFlatFile;

    String render() {
        StringBuilder sb = new StringBuilder();

        if (layoutFlatFile == null) {
            logger.warn("Layout de remessa está vazio");
            return "";
        }

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
            super.add(new FixedField(l.nome, l.getAtr("valor"), l.getInt("length"), (Format) l.getObj("format"), (IFiller) l.getObj("filler")));
        }

    }

    public static class CabecalhoRemessa extends RegistroRemessa {

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

        /* 
                remessa.addTitulo().valor("").vencimento("")
                .numeroDocumento("").nossoNumero("")
                .dataEmissao("").carteira("")
                .sacado("Sacado da Silva Sauro").sacadoCpf("01234567891")
                .sacadoEndereco("")
                .instrucao("");
         */
        TituloRemessa valor(String string) {
            return this;
        }

        TituloRemessa vencimento(String string) {
            return this;
        }

        TituloRemessa numeroDocumento(String string) {
            return this;
        }

        TituloRemessa nossoNumero(String string) {
            return this;
        }

        TituloRemessa dataEmissao(String string) {
            return this;
        }

        TituloRemessa carteira(String string) {
            return this;
        }

        TituloRemessa sacado(String string) {
            return this;
        }

        TituloRemessa sacadoCpf(String string) {
            return this;
        }

        TituloRemessa sacadoEndereco(String string) {
            return this;
        }

        TituloRemessa instrucao(String string) {
            return this;
        }
    }

    public static class RodapeRemessa extends RegistroRemessa {

    }

    /* 
    
     */
    List<RegistroRemessa> registros = new ArrayList<>();

    RemessaFacade add(RegistroRemessa reg) {
        registros.add(reg);
        return this;
    }
}
