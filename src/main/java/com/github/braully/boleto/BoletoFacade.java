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

import org.jrimum.ConfiguracaoJRimum;
import java.math.BigDecimal;
import org.apache.commons.lang3.tuple.Pair;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.LinhaDigitavel;
import org.jrimum.domkee.banco.Agencia;
import org.jrimum.domkee.banco.Carteira;
import org.jrimum.domkee.banco.Cedente;
import org.jrimum.domkee.banco.ContaBancaria;
import org.jrimum.domkee.banco.NumeroDaConta;
import org.jrimum.domkee.banco.Sacado;
import org.jrimum.domkee.banco.TipoDeCobranca;
import org.jrimum.domkee.banco.Titulo;
import org.jrimum.domkee.pessoa.CNPJ;
import org.jrimum.domkee.pessoa.CPF;
import org.jrimum.texgit.Filler;
import org.jrimum.utilix.DateUtil;

/**
 *
 * @author braully
 */
public class BoletoFacade extends Boleto {

    public static final String SEPARADOR_DIGITO_VERIFICADOR = "-";

    public BoletoFacade() {
        super();
    }

    @Override
    public LinhaDigitavel getLinhaDigitavel() {
        if (this.linhaDigitavel == null) {
            log.debug("Boleto não gerado");
            gerarLinhaDigitavel();
        }
        return super.getLinhaDigitavel();
    }

    /* Metodos da API Publica */
    public BoletoFacade banco(String numeroBanco) {
        String numeroBancoCompleto = this.completaCodigoBanco(numeroBanco);
        this.getContaBancaria().setBanco(BancosSuportados.instancia(numeroBancoCompleto));
        return this;
    }

    /**
     * Número da agência, caso tenha digito verificador, favor informar no
     * formato "numero-dv".
     *
     * @param strNumero
     * @return o proprio boleto, para facilidades de utilização.
     */
    public BoletoFacade agencia(String strNumero) {
        Pair<Integer, String> numeroDV = quebraNumeroDV(strNumero);

        Integer numero = numeroDV.getLeft();
        String dv = numeroDV.getRight();

        if (dv != null) {
            this.getContaBancaria().setAgencia(new Agencia(numero, dv));
        } else {
            this.getContaBancaria().setAgencia(new Agencia(numero));
        }

        return this;
    }

    /**
     * Número da conta, caso tenha digito verificador, favor informar no formato
     * "numero-dv".
     *
     * @param strconta
     * @return o proprio boleto, para facilidades de utilização.
     */
    public BoletoFacade conta(String strconta) {
        Pair<Integer, String> numeroDV = quebraNumeroDV(strconta);
        Integer numero = numeroDV.getLeft();
        String dv = numeroDV.getRight();

        if (dv != null) {
            this.getContaBancaria().setNumeroDaConta(new NumeroDaConta(numero, dv));
        } else {
            this.getContaBancaria().setNumeroDaConta(new NumeroDaConta(numero));
        }
        return this;
    }

    public BoletoFacade sacado(String nomeSacado) {
        this.getSacado().setNome(nomeSacado);
        return this;
    }

    public BoletoFacade sacadoCpf(String cpf) {
        String completaCpf = this.completaCpf(cpf);
        this.getSacado().setCPRF(new CPF(completaCpf));
        return this;
    }

    public BoletoFacade sacadoCnpj(String cnpj) {
        String completaCnpj = this.completaCnpj(cnpj);
        this.getSacado().setCPRF(new CNPJ(completaCnpj));
        return this;
    }

    public BoletoFacade cedente(String nomeSacado) {
        this.getCedente().setNome(nomeSacado);
        return this;
    }

    public BoletoFacade cedenteCnpj(String cnpj) {
        cnpj = completaCnpj(cnpj);
        this.getCedente().setCPRF(new CNPJ(cnpj));
        return this;
    }

    public BoletoFacade nossoNumero(String nossoNumero) {
        this.getTitulo().setNossoNumero(nossoNumero);
        return this;
    }

    public BoletoFacade valor(Double valor) {
        this.getTitulo().setValor(BigDecimal.valueOf(valor));
        return this;
    }

    public BoletoFacade valor(long val) {
        this.getTitulo().setValor(BigDecimal.valueOf(val));
        return this;
    }

    public BoletoFacade dataVencimento(String strDate) {
        this.getTitulo().setDataDoVencimento(DateUtil.parse(strDate, "dd/MM/yyyy"));
        return this;
    }

    public BoletoFacade numeroDocumento(String string) {
        this.getTitulo().setNumeroDoDocumento(string);
        return this;
    }

    void carteira(String string) {
        this.getCarteira().setCodigo(parseInt(string));
    }

//    public BoletoFacade convenio(String string) {
//        return this;
//    }
    /* Metodos internos */
    private String completaCodigoBanco(String banco) {
        String fill = Filler.ZERO_LEFT.fill(banco, 3);
        return fill;
    }

    private String completaCnpj(String cnpj) {
        String fill = Filler.ZERO_LEFT.fill(cnpj, 14);
        return fill;
    }

    private String completaCpf(String cnpj) {
        String fill = Filler.ZERO_LEFT.fill(cnpj, 11);
        return fill;
    }

    private Cedente getCedente() {
        Cedente sacado = this.getTitulo().getCedente();
        if (sacado == null) {
            sacado = new Cedente("");
            this.getTitulo().setCedente(sacado);
        }
        return sacado;
    }

    private Sacado getSacado() {
        Sacado sacado = this.getTitulo().getSacado();
        if (sacado == null) {
            sacado = new Sacado("");
            this.getTitulo().setSacado(sacado);
        }
        return sacado;
    }

    private Carteira getCarteira() {
        Carteira carteira = this.getContaBancaria().getCarteira();
        if (carteira == null) {
            carteira = new Carteira();
            this.getContaBancaria().setCarteira(carteira);
        }
        return carteira;
    }

    public Titulo getTitulo() {
        Titulo titulo1 = super.getTitulo();
        if (titulo1 == null) {
            titulo1 = new Titulo();
            this.setTitulo(titulo1);
        }
        return titulo1;
    }

    private ContaBancaria getContaBancaria() {
        if (this.getTitulo().getContaBancaria() == null) {
            this.getTitulo().setContaBancaria(new ContaBancaria());
        }
        return this.getTitulo().getContaBancaria();
    }

    static Integer parseInt(String string) {
        if (string == null) {
            throw new IllegalArgumentException("null string");
        }
        if (!ConfiguracaoJRimum.ignorarZeroEsquerdaConversao && string.charAt(0) == '0') {
            throw new IllegalArgumentException("Octal converter: " + string);
        }
        return Integer.parseInt(string);
    }

    private Pair<Integer, String> quebraNumeroDV(String strNumero) {
        Integer numero = null;
        String dv = null;
        if (strNumero == null) {
            throw new IllegalArgumentException("Numero esta null");
        }
        //TODO: Melhorar isso, ir pelo formato de agencia de cada banco.
        if (strNumero.contains(SEPARADOR_DIGITO_VERIFICADOR)) {
            String[] split = strNumero.split(SEPARADOR_DIGITO_VERIFICADOR);
            numero = parseInt(split[0]);
            dv = split[1];
        } else {
            numero = parseInt(strNumero);
        }
        return Pair.of(numero, dv);
    }

    protected BoletoFacade gerarLinhaDigitavel() {
        this.processFromTitulo(titulo);
        return this;
    }
}
