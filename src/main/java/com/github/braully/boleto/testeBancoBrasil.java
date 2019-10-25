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

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.banco.ContaBancaria;
import org.jrimum.domkee.banco.Titulo;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.banco.Agencia;
import org.jrimum.domkee.banco.Carteira;
import org.jrimum.domkee.banco.Cedente;
import org.jrimum.domkee.banco.NumeroDaConta;
import org.jrimum.domkee.banco.Sacado;
import org.jrimum.domkee.banco.SacadorAvalista;
import org.jrimum.domkee.banco.TipoDeTitulo;
import org.jrimum.domkee.banco.Titulo.Aceite;
import org.jrimum.domkee.pessoa.CEP;
import org.jrimum.domkee.pessoa.Endereco;
import org.jrimum.domkee.pessoa.UnidadeFederativa;

/**
 *
 * @author root
 */
public class testeBancoBrasil {

    public static void main(String[] args) {
        
        Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");

                /*
                 * INFORMANDO DADOS SOBRE O SACADO.
                 */
                 Sacado sacado = new Sacado("JavaDeveloper Pronto Para Férias", "222.222.222-22");

                // Informando o endereço do sacado.
                Endereco enderecoSac = new Endereco();
                enderecoSac.setUF(UnidadeFederativa.RN);
                enderecoSac.setPais("Brasil");
                enderecoSac.setLocalidade("Natal");
                enderecoSac.setCep(new CEP("59064-120"));
                enderecoSac.setBairro("Grande Centro");
                enderecoSac.setLogradouro("Rua poeta dos programas");
                enderecoSac.setNumero("1");
                sacado.addEndereco(enderecoSac);
                
                SacadorAvalista sacadorAvalista = new SacadorAvalista("JRimum Enterprise", "00.000.000/0001-91");

                // Informando o endereço do sacador avalista.
                Endereco enderecoSacAval = new Endereco();
                enderecoSacAval.setUF(UnidadeFederativa.DF);
                enderecoSacAval.setLocalidade("Brasília");
                enderecoSacAval.setCep(new CEP("59000-000"));
                enderecoSacAval.setBairro("Grande Centro");
                enderecoSacAval.setLogradouro("Rua Eternamente Principal");
                enderecoSacAval.setNumero("001");
                sacadorAvalista.addEndereco(enderecoSacAval);
                

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setBanco(BancosSuportados.BANCO_DO_BRASIL.create());
        contaBancaria.setAgencia(new Agencia(1234, "1"));
        contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
        contaBancaria.setCarteira(new Carteira(30));

        Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
        titulo.setNossoNumero("12345678901");
        titulo.setContaBancaria(contaBancaria);
        titulo.setValor(BigDecimal.valueOf(0.23));
        titulo.setDataDoDocumento(new Date());
        titulo.setDataDoVencimento(new Date());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Aceite.A);
        titulo.setDesconto(new BigDecimal(0.05));
        titulo.setDeducao(BigDecimal.ZERO);
        titulo.setMora(BigDecimal.ZERO);
        titulo.setAcrecimo(BigDecimal.ZERO);
        titulo.setValorCobrado(BigDecimal.ZERO);

        Boleto boleto = new Boleto(titulo);
        
        boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em " +
                                "qualquer Banco até o Vencimento.");
                boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
                                "cobrado não é o esperado, aproveite o DESCONTÃO!");
                boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
                boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
                boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
                boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
                boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
                boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
                boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
                boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");

        BoletoViewer boletoViewer = new BoletoViewer(boleto);

        File arquivoPDF = boletoViewer.getPdfAsFile("Banco do Brasil Boleto");

        mostreBoletoNaTela(arquivoPDF);
    }

    private static void mostreBoletoNaTela(File arquivoBoleto) {

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
            desktop.open(arquivoBoleto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
