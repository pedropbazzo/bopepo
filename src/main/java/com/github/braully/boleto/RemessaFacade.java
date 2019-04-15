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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author braully
 */
public class RemessaFacade {

    public static interface Registro {

    }

    public static class CabecalhoRemessa implements Registro {

        CabecalhoRemessa agencia(String string) {
            return this;
        }

        CabecalhoRemessa conta(String string) {
            return this;
        }

        CabecalhoRemessa numeroConvenio(String string) {
            return this;
        }

        CabecalhoRemessa cedente(String string) {
            return this;
        }

        CabecalhoRemessa dataGeracao(String string) {
            return this;
        }

        CabecalhoRemessa cedenteCnpj(String string) {
            return this;
        }
    }

    public static class TituloRemessa implements Registro {

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

    public static class RodapeRemessa implements Registro {

    }

    /* 
    
     */
    List<Registro> registros = new ArrayList<>();

    CabecalhoRemessa addCabecalho() {
        CabecalhoRemessa cabecalho = new CabecalhoRemessa();
        this.registros.add(cabecalho);
        return cabecalho;
    }

    TituloRemessa addTitulo() {
        TituloRemessa titulo = new TituloRemessa();
        this.registros.add(titulo);
        return titulo;
    }

    RodapeRemessa addRodape() {
        RodapeRemessa rodape = new RodapeRemessa();
        this.registros.add(rodape);
        return rodape;
    }

}
