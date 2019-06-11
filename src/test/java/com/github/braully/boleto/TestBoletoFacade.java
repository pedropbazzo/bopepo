package com.github.braully.boleto;

import org.jrimum.bopepo.view.BoletoViewer;
import org.junit.Test;

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
/**
 *
 * @author braully
 */
public class TestBoletoFacade {

    @Test
    public void testBoletoSimples() {
        BoletoFacade boletoFacade = new BoletoFacade();
        boletoFacade.sacado("Sacado da Silva Sauro").sacadoCpf("1");
        boletoFacade.banco("1").agencia("1").conta("1");
        boletoFacade.cedente("Cedente da Silva Sauro").cedenteCnpj("1");
        //boletoFacade.covenio("1");
        boletoFacade.carteira("1");
        boletoFacade.numeroDocumento("1")
                .nossoNumero("1234567890")
                .valor(100.23).dataVencimento("01/01/2019");

        boletoFacade.gerarLinhaDigitavel();
        BoletoViewer create = BoletoViewer.create(boletoFacade);
        create.getPdfAsFile("./target/teste.pdf");
    }
}
