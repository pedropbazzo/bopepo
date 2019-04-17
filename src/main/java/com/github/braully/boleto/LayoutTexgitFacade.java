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
import static com.github.braully.boleto.RemessaFacade.*;

/**
 *
 * @author Braully Rocha
 */
public class LayoutTexgitFacade {

    TagLayout template;

    CabecalhoRemessa novoCabecalho() {
        CabecalhoRemessa cabecalho = new CabecalhoRemessa();
        return cabecalho;
    }

    TituloRemessa novoTitulo() {
        RemessaFacade.TituloRemessa titulo = new TituloRemessa();
        return titulo;
    }

    RodapeRemessa novoRodape() {
        RemessaFacade.RodapeRemessa rodape = new RodapeRemessa();
        return rodape;
    }

    public RegistroRemessa novoRegistro(String tipoRegistro) {
        TagLayout layoutRegistro = template.get("cabecalho");
        return new RegistroRemessa(layoutRegistro);
    }

    public LayoutTexgitFacade(TagLayout template) {
    }

    public static void main(String... args) {

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
                        description("Layout padrão do Febraban"),
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
                                field("agencia").length(4).type("integer").padding("zero_left"),
                                field("conta").length(7),
                                field("dataGeracao").length(6).type("date").format("zero_left")
                        ),
                        titulo(
                                field("codigo-registro").length(1).position(1).value(7)
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
                                field("codigo-registro").length(1).position(1).value(9),
                                field("codigo-retorno").length(1),
                                field("filler").length(2),
                                field("codigo-banco").length(3),
                                field("filler").length(10),
                                field("quantidade-titulos").length(8).type("bigdecimal").format("DECIMAL_DD"),
                                field("valor-total-titulos").length(8).type("bigdecimal").format("DECIMAL_DD"),
                                field("filler").length(8)
                        )
                )
        );

        LayoutTexgitFacade layout = new LayoutTexgitFacade(flatfileLayout);
    }

}
