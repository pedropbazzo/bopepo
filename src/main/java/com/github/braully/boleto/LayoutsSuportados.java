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

import static com.github.braully.boleto.CNAB.*;
import static com.github.braully.boleto.CNABServico.*;
import static com.github.braully.boleto.TagLayout.TagCreator.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.jrimum.bopepo.BancosSuportados.*;
import org.jrimum.texgit.Fillers;

/**
 *
 * @author Braully Rocha da Silva
 */
public class LayoutsSuportados {

    /*
     Baseado no arquivo: LayoutItauCNAB400Envio.txg
     
     */
    public static TagLayout LAYOUT_ITAU_REMESSA_COBRANCA_CNAB400 = flatfile(
            layout(nome("Arquivo-Remessa_ItauCNAB400"),
                    versao("01"),
                    banco(BANCO_ITAU.create()),
                    cnab(CNAB_400),
                    servico(COBRANCA_REMESSA)
            ),
            cabecalho(
                    /*
                    <IdType name="IDReg" value="0" length="1" position="1" />
                    <Field name="IdentificacaoRemessa" value="1" length="1" />
                    <Field name="LiteralRemessa" value="REMESSA" length="7" />
                    <Field name="CodigoDeServico" value="01" length="2" />
                    <Field name="LiteralServico" value="COBRANCA" length="15" />
                    <Field name="Agencia" length="4" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="Zeros" value="00" length="2"  />
                    <Field name="Conta" length="5" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="DacConta" length="1" type="INTEGER" />
                     */
                    field("id-ident-literal").length(26).value("01REMESSA01COBRANCA"),
                    field("agencia").length(4).type(Integer.class).padding(Fillers.ZERO_LEFT),
                    field("").value("00"),
                    field("conta").length(6).padding(Fillers.ZERO_LEFT), //Conta com DV
                    field("dataGeracao").length(6).type(Date.class).format(new SimpleDateFormat("ddMMYY")),
                    /*
                    <Field name="Brancos1" length="8" blankAccepted="true"  /><!--Enviar 8 Brancos -->
                    <Field name="NomeEmpresa" length="30" type="CHARACTER" />
                    <Field name="CodigoCompensacao" value="341" length="3" />
                    <Field name="NomeBanco" value="BANCO ITAU SA" length="15" />
                    <Field name="DataGeracao" length="6" type="DATE" format="DATE_DDMMYY" />
                    <Field name="Brancos2" length="294" blankAccepted="true" /><!--Enviar 294 Brancos -->
                    <Field name="NumeroSequencialRegistro" type="INTEGER" length="6" padding="ZERO_LEFT" /> 
                     */
                    field("").length(8).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedente").length(30).padding(Fillers.WHITE_SPACE_RIGHT),
                    field("comp-banco").value("341BANCO ITAU SA"),
                    field("dataGeracao").length(6).type(Date.class).format(new SimpleDateFormat("ddMMYY")),
                    field("").length(294).filler(Fillers.WHITE_SPACE_LEFT),
                    field("sequencialRegistro").type(Number.class).length(6).padding(Fillers.ZERO_LEFT)
            ),
            titulo(
                    /* 
                    <IdType name="IDReg" value="1" length="1" position="1" />
                    <Field name="CodigoInscricao" length="2" value="02" type="INTEGER" /> */
                    field("codigoRegistro").length(3).value("102"),
                    /*
                    <Field name="NumeroInscricao" length="14" type="INTEGER"  /><!-- CNPJ EMPRESA -->
                    <Field name="Agencia" length="4" padding="ZERO_LEFT" type="INTEGER" />
                    <Field name="Zeros" value="00" length="2"  />
                    <Field name="Conta" length="5" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="DacConta" length="1" type="INTEGER" />*/
                    field("cedenteCnpj").length(14),
                    field("agencia").length(4).type(Integer.class).padding(Fillers.ZERO_LEFT),
                    field("").value("00"),
                    field("conta").length(6).padding(Fillers.ZERO_LEFT) //Conta com DV                    
            /*
                    <Field name="Brancos1" length="4" type="CHARACTER"  /><!--Enviar 4 Brancos -->										
                    <Field name="InstrucaoAlegacao" length="4" type="INTEGER" /> 
                    <Field name="UsoDaEmpresa" length="25" type="CHARACTER" /> 
                    <Field name="NossoNumeroComDigito" length="8" type="INTEGER" />
                    <Field name="QtdMoeda" length="13" type="BIGDECIMAL" value="0" format="DECIMAL_DD" padding="ZERO_LEFT" />					
                    <Field name="NrCarteira" length="3" type="INTEGER" />
                    <Field name="UsoDoBanco" length="21" type="CHARACTER" />
                    <Field name="CodigoCarteira" length="1" type="CHARACTER" value="I" />					
                    <Field name="CodigoDeOcorrencia" length="2" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="NumeroDoDocumento" length="10" />
                    <Field name="Vencimento" length="6" type="DATE" format="DATE_DDMMYY" padding="ZERO_LEFT" />
                    <Field name="Valor" length="13" value="0" format="DECIMAL_DD" padding="ZERO_LEFT" /> 
                    <Field name="CodigoCompensacaoBancoRecebedor" value="341" length="3" padding="ZERO_LEFT" />
                    <Field name="AgenciaCobradora" length="5" value="00000" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="EspecieDeTitulo" length="2" type="CHARACTER" padding="ZERO_LEFT" />
                    <Field name="Aceite" length="1" type="CHARACTER" /><!-- A = Aceite N = Não Aceite -->
                    <Field name="Emissao" length="6" type="DATE" format="DATE_DDMMYY" padding="ZERO_LEFT" />
                    <Field name="Instrucao1" length="2" type="CHARACTER" />
                    <Field name="Instrucao2" length="2" type="CHARACTER" />
                    <Field name="JurosDeMora" type="BIGDECIMAL" length="13" value="0" format="DECIMAL_DD" padding="ZERO_LEFT" />
                    <Field name="DataDesconto" length="6" type="DATE" format="DATE_DDMMYY" padding="ZERO_LEFT" />
                    <Field name="DescontoConcedido" type="BIGDECIMAL" length="13" value="0" format="DECIMAL_DD" padding="ZERO_LEFT" />
                    <Field name="IOF_Devido" type="BIGDECIMAL" length="13" value="0" format="DECIMAL_DD" padding="ZERO_LEFT" />
                    <Field name="AbatimentoConcedido" type="BIGDECIMAL" length="13" value="0" format="DECIMAL_DD" padding="ZERO_LEFT" />
                    <Field name="TipoInscricaoSacado" length="2" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="NumeroInscricaoSacado" length="14" type="INTEGER" padding="ZERO_LEFT" />
                    <Field name="NomeSacado" length="30" type="CHARACTER"  />										
                    <Field name="Brancos2" length="10" type="CHARACTER"  /><!--Enviar 10 Brancos -->
                    <Field name="LogradouroSacado" length="40" type="CHARACTER"  />										
                    <Field name="BairroSacado" length="12" type="CHARACTER"  />
                    <Field name="CepSacado" length="8" type="INTEGER"  />
                    <Field name="Cidade" length="15" type="CHARACTER"  />
                    <Field name="Estado" length="2" type="CHARACTER"  />
                    <Field name="SacadorAvalista" length="30" type="CHARACTER"  />
                    <Field name="Brancos3" length="4" type="CHARACTER"  /><!--Enviar 4 Brancos -->										
                    <Field name="DataDeMora" length="6" type="DATE" format="DATE_DDMMYY" padding="ZERO_LEFT" />
                    <Field name="Prazo" length="2" type="INTEGER"  />
                    <Field name="Brancos4" length="1" type="CHARACTER"  /><!--Enviar 1 Branco -->													
                    <Field name="NumeroSequencialRegistro" type="INTEGER" length="6" padding="ZERO_LEFT" />
             */
            ),
            rodape(
                    /*
                    <IdType name="IDReg" value="9" length="1" position="1" />
                    <Field name="Filler" length="393" />
                    <SequencialNumber name="sequencia" type="INTEGER" length="6" position="3" padding="ZERO_LEFT" />
                     */
                    field("id").length(1).value(9),
                    field("").length(393).filler(Fillers.WHITE_SPACE_LEFT),
                    field("sequencialRegistro").type(Number.class).padding(Fillers.ZERO_LEFT).length(6)
            )
    );

    /*
    
     */
    private static List<TagLayout> layoutsSuportados = new ArrayList<>();

    TagLayout getLayoutArquivoBancario(String banco, CNABServico servico,
            String convenio, String carteira, CNAB cnab) {
        TagLayout ret = null;
        for (TagLayout layout : layoutsSuportados) {
            TagLayout descritor = layout.get("layout");
            if (descritor != null
                    && eq(descritor.getValue("banco"), banco)
                    && eq(descritor.getValue("convenio"), convenio)
                    && eq(descritor.getValue("carteira"), carteira)
                    && eq(descritor.getValue("cnab"), cnab)
                    && eq(descritor.getValue("servico"), servico)) {
                ret = layout;
                break;
            }
        }
        return ret;
    }

    private boolean eq(Object value1, Object value2) {
        return value1.equals(value2);
    }
}
