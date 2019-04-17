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

import org.jrimum.utilix.FileUtil;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author braully
 */
public class TestRemessaFacade {

    @Test
    public void testRemessaSimples() {
        RemessaFacade remessa = new RemessaFacade();
        remessa.layout(FileUtil.streamFromClasspath("layouts/remessa/layout-referencia"));
        remessa.addCabecalho().agencia("").conta("").numeroConvenio("")
                .cedente("").cedenteCnpj("").dataGeracao("");

        remessa.addTitulo().valor("").vencimento("")
                .numeroDocumento("").nossoNumero("")
                .dataEmissao("").carteira("")
                .sacado("").sacadoCpf("")
                .sacadoEndereco("")
                .instrucao("");

        remessa.addRodape();

        String remessaStr = remessa.render();
    }

    @Ignore
    @Test
    public void testRemessaVazia() {
        RemessaFacade remessa = new RemessaFacade();
        remessa.addCabecalho().agencia("").conta("").numeroConvenio("")
                .cedente("").cedenteCnpj("").dataGeracao("");

        remessa.addTitulo().valor("").vencimento("")
                .numeroDocumento("").nossoNumero("")
                .dataEmissao("").carteira("")
                .sacado("").sacadoCpf("")
                .sacadoEndereco("")
                .instrucao("");

        remessa.addRodape();

        String remessaStr = remessa.render();
        assertEquals(remessaStr, "");
    }
}
