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

import com.github.braully.boleto.ArquivoFacade.CabecalhoArquivo;
import com.github.braully.boleto.ArquivoFacade.RodapeArquivo;
import com.github.braully.boleto.ArquivoFacade.TituloArquivo;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author braully
 */
public class TestRetornoFacade {

    @Ignore
    @Test
    public void testRetornoCobancaGenericaFebraban240V5() {
        RetornoFacade retorno = new RetornoFacade(LayoutsSuportados.LAYOUT_FEBRABAN_CNAB240);


        ArquivoFacade.RegistroArquivo cabecalho = retorno.cabecalho();
        assertNotNull(cabecalho);

        ArquivoFacade.RegistroArquivo cabecalhoLote = retorno.cabecalhoLote();
        assertNotNull(cabecalhoLote);

        List<ArquivoFacade.RegistroArquivo> titulos = retorno.detalhes();
        assertNotNull(titulos);

        ArquivoFacade.RegistroArquivo rodapeLote = retorno.rodapeLote();
        assertNotNull(rodapeLote);

        ArquivoFacade.RegistroArquivo rodape = retorno.rodape();
        assertNotNull(rodape);

        //System.err.println(remessaStr);
//        assertEquals(remessaStr, "");
    }
}
