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

/**
 *
 * @author Braully Rocha
 */
public class LayoutTexgitFacade {

    public LayoutTexgitFacade() {
    }

    public static void main(String... args) {
        LayoutTexgitFacade layout = new LayoutTexgitFacade();

        flatfile(
                /*<layout>
                <name>Arquivo-Febraban_CNAB400</name>
                <version>Version 00</version>
                <description>
                    Layout padrão do Febraban
                </description>
            </layout>*/
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
                                field("codigo-registro").length(1).position(1).value(0),
                                field("codigo-retorno").length(1),
                                field("agencia").length(4).type("integer").padding("zero_left"),
                                field("data-arquivo").length(6).type("date").format("zero_left")
                        ),
                        titulo(
                                field("codigo-registro").length(1).position(1).value(7)
                        ),
                        rodape()
                )
        );
    }
}
