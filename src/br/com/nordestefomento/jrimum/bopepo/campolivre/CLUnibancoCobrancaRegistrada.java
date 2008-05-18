package br.com.nordestefomento.jrimum.bopepo.campolivre;

import java.util.Date;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.Util4Date;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras (<em>cobrança</em>)
 * com registro.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Direta (com registro)</font>
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" >
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><strong><font face="Arial">Posição</font></strong></td>
 * <td bgcolor="#C0C0C0"><strong><font face="Arial">Campo Livre No Código De
 * Barras (20 a 44)</font></strong></td>
 * <tr>
 * <td align="center"><font face="Arial">20 a 21</font></td>
 * <td><font face="Arial">Código da transação = 04</font></td>
 * 
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">22 a 27</font></td>
 * <td><font face="Arial">Data do Vencimento do Título (AAMMDD)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">28 a 31</font></td>
 * 
 * <td><font face="Arial">Agência do Cedente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">32</font></td>
 * 
 * <td><font face="Arial">Dígito Verificador da Agência do Cedente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">33 a 43</font></td>
 * <td><font face="Arial">Nosso Número</font></td>
 * </tr>
 * <tr>
 * 
 * <td align="center"><font face="Arial">44</font></td>
 * <td><font face="Arial">Super Digito do Nosso Número (*)</font></td>
 * </tr>
 * </table> </div>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class CLUnibancoCobrancaRegistrada extends ACLUnibanco {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2740172688796212239L;

	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;

	private static final String CODIGO_TRANSACAO = "04";

	/**
	 * @param fieldsLength
	 * @param stringLength
	 */
	protected CLUnibancoCobrancaRegistrada(Integer fieldsLength,
			Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	/**
	 * @param titulo
	 * @return
	 */
	static ICampoLivre getInstance(Titulo titulo) {

		ACampoLivre aCLUnibanco = new CLUnibancoCobrancaRegistrada(
				FIELDS_LENGTH, STRING_LENGTH);

		ContaBancaria conta = titulo.getContaBancaria();

		aCLUnibanco.add(new Field<String>(CODIGO_TRANSACAO, 2));

		aCLUnibanco.add(new Field<Date>(titulo.getDataDoVencimento(), 6,
				Util4Date.fmt_yyMMdd));

		aCLUnibanco.add(new Field<Integer>(conta.getAgencia()
				.getCodigoDaAgencia(), 4, Filler.ZERO_LEFT));

		aCLUnibanco.add(new Field<Integer>(Integer.valueOf(conta.getAgencia()
				.getDigitoDaAgencia()), 1));

		aCLUnibanco.add(new Field<String>(titulo.getNossoNumero(), 11,
				Filler.ZERO_LEFT));

		aCLUnibanco.add(new Field<String>(calculeSuperDigito(titulo
				.getNossoNumero()), 1));

		return aCLUnibanco;
	}

	/**
	 * <p>
	 * Calcula o Super Dígito do Nosso Número.
	 * </p>
	 * 
	 * <p>
	 * Super dígito do “Nosso Número” [calculado com o MÓDULO 11 (de 2 a 9)]
	 * obtido utilizando-se os algarismos do “Nosso Número” acrescido do número
	 * 1 à esquerda = [1/NNNNNNNNNNN] e multiplicando-se a sequência obetem-se a
	 * soma dos produtos. Em seguida multiplicando-se novamente a soma por 10 e
	 * depois realizando-se o módulo 11.
	 * </p>
	 * 
	 * 
	 * @param nossoNumero
	 * 
	 * @return Dígito verficador calculado
	 * 
	 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
	 * 
	 * @see #calculeDigitoEmModulo11(String)
	 * @see br.com.nordestefomento.jrimum.utilix.AModulo
	 * 
	 * @since 0.2
	 */
	private static String calculeSuperDigito(String nossoNumero) {

		return calculeDigitoEmModulo11("1" + nossoNumero);
	}
}
