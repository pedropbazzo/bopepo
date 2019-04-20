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

import static com.github.braully.boleto.TagLayout.TagCreator.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jrimum.texgit.Fillers;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author braully
 */
public class TestRemessaFacade {

    @Test
    public void testRemessaVazia() {
        RemessaFacade remessa = new RemessaFacade(layoutFebrabanTest());
        remessa.addNovoCabecalho().agencia("1")
                .conta("1").numeroConvenio("1")
                .cedente("ACME S.A LTDA.").cedenteCnpj("1")
                .dataGeracao(new Date()).setValue("codigoRetorno", "1");

        remessa.addNovoTitulo().valor("1").vencimento("1")
                .numeroDocumento("1").nossoNumero("1")
                .dataEmissao("1").carteira("1")
                .sacado("Fulano de Tal").sacadoCpf("1")
                .sacadoEndereco("Rua 1, Numero 1, Cidade Z")
                .instrucao("Senhor caixa não receber nunca");

        remessa.addNovoRodape().setValue("codigoRetorno", "1");

        String remessaStr = remessa.render();
        assertEquals(remessaStr, "");
    }

    TagLayout layoutFebrabanTest() {
        TagLayout flatfileLayout = flatfile(
                /*
                <layout>
                <name>Arquivo-Febraban_CNAB400</name>
                <version>Version 00</version>
                <description>
                Layout padrão do Febraban
                </description>
                </layout>
                 */
                layout(
                        name("Arquivo-Febraban_CNAB400"),
                        version("00"),
                        description("Layout padrão do Febraban")),
                /*
                        <GroupOfRecords>
                        <Record name="cabecalho" description="Protocolo de comunicação">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1" value="0" />
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="AGENCIA" length="4" type="INTEGER" padding="ZERO_LEFT" />
                        <Field name="DATA_ARQUIVO" length="6" type="DATE" format="DATE_DDMMYY" />
                 */
                cabecalho(
                        field("codigoRegistro").length(1).position(1).value(0),
                        field("codigoRetorno").length(1),
                        field("agencia").length(4).type(Integer.class).padding(Fillers.ZERO_LEFT),
                        field("conta").length(7).padding(Fillers.ZERO_LEFT),
                        field("dataGeracao").length(6).type(Date.class).format(new SimpleDateFormat("ddMMYY"))
                ),
                titulo(
                        field("codigoRegistro").length(1).position(1).value(7)
                ),
                /*
                        <Record name="TRAILLER">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1"  value="9"/>
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="Filler" length="2" />
                        <Field name="CODIGO_BANCO" length="3" />
                        <Field name="Filler" length="10" />
                        <Field name="QUANTIDADE_TITULOS" length="8" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="VALOR_TOTAL_TITULOS" length="15" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="Filler" length="8" />
                        </GroupOfFields>
                        </Record>
                 */
                rodape(
                        field("codigoRegistro").length(1).position(1).value(9),
                        field("codigoRetorno").length(1),
                        field("filler").length(2),
                        field("codigoBanco").length(3),
                        field("filler").length(10),
                        field("quantidadeTitulos").length(8).type(BigDecimal.class).format(new DecimalFormat("DD")),
                        field("valorTotalTitulos").length(8).type(BigDecimal.class).format(new DecimalFormat("DD")),
                        field("filler").length(8)
                )
        );
        return flatfileLayout;
    }
}
