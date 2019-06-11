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

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author strike
 */
public class TestItext {

    public static final String SRC = "src/main/resources/pdf/BoletoTemplateSemSacadorAvalista.pdf";
    public static final String DEST = "target/BoletoTemplateSemSacadorAvalista_format.pdf";

    //https://itextpdf.com/en/resources/examples/itext-5-legacy/filling-out-forms
    @Test
    public void testFillForm() throws DocumentException, IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        manipulatePdf(SRC, DEST);

    }

    public void manipulatePdf(String src, String dest) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
//        form.setField("Name", "1.0", "100%");
//        form.setField("Company", "1217000.000000", "$1,217,000");

        form.setField("txtFcCodBanco", "001-9");
        form.setField("txtFcSacadorAvalistaL1", "");
        form.setField("txtFcSacadorAvalistaL2", "");
        form.setField("txtFcSacadoL1", "Sacado da Silva Sauro, CPF: 000.000.000-01", "Sacado da Silva");
        form.setField("txtFcSacadoL2", "");
        form.setField("txtRsOutraDeducao", "");
        form.setField("txtFcNumeroDocumento", "1");
        form.setField("txtRsValorDocumento", "R$ 100,23");
        form.setField("txtFcInstrucaoAoCaixa1", "");
        form.setField("txtFcCedente", "Cedente da Silva Sauro");
        form.setField("txtFcInstrucaoAoCaixa2", "");
        form.setField("txtFcValorDocumento", "R$ 100,23");
        form.setField("txtFcDescontoAbatimento", "");
        form.setField("txtRsDescontoAbatimento", "");
        form.setField("txtRsEspecie", "REAL");
        form.setField("txtFcAgenciaCodigoCedente", "1 / 1");
        form.setField("txtFcInstrucaoAoCaixa7", "");
        form.setField("txtFcInstrucaoAoCaixa8", "");
        form.setField("txtFcInstrucaoAoCaixa5", "");
        form.setField("txtFcInstrucaoAoCaixa6", "");
        form.setField("txtFcInstrucaoAoCaixa3", "");
        form.setField("txtFcInstrucaoAoCaixa4", "");
        form.setField("txtFcEspecieDocumento", "");
        form.setField("txtRsCpfCnpj", "00.000.000/0000-01");
        form.setField("txtRsSacado", "Sacado da Silva Sauro, CPF: 000.000.000-01");
        form.setField("txtRsOutroAcrescimo", "");
        form.setField("txtFcAceite", "");
        form.setField("txtFcValorCobrado", "");
        form.setField("txtRsInstrucaoAoSacado", "");
        form.setField("txtFcEspecie", "REAL");
        form.setField("txtFcCarteira", "1");
        form.setField("txtFcDataProcessamento", "11/06/2019");
        form.setField("txtRsNumeroDocumento", "1");
        form.setField("txtRsLinhaDigitavel", "00190.00009 00000.001123 34567.890016 5 77560000010023");
        form.setField("txtFcMoraMulta", "");
        form.setField("txtFcNossoNumero", "1234567890");
        form.setField("txtFcDataDocumento", "");
        form.setField("txtRsDataVencimento", "01/01/2019");
        form.setField("txtRsAgenciaCodigoCedente", "1 / 1");
        form.setField("txtFcOutraDeducao", "");
        form.setField("txtFcLocalPagamento", "");
        form.setField("txtFcOutroAcrescimo", "");
        form.setField("txtRsCodBanco", "001-9");
        form.setField("txtFcDataVencimento", "01/01/2019");
        form.setField("txtRsCedente", "Cedente da Silva Sauro");
        form.setField("txtRsMoraMulta", "");
        form.setField("txtRsValorCobrado", "");
        form.setField("txtRsNossoNumero", "1234567890");
        form.setField("txtFcLinhaDigitavel", "00190.00009 00000.001123 34567.890016 5 77560000010023");

        stamper.flush();
        stamper.close();
    }
}
