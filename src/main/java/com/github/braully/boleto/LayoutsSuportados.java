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
import static com.github.braully.boleto.TagLayout.TagCreator.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jrimum.texgit.Fillers;

/**
 *
 * @author Braully Rocha da Silva
 */
public class LayoutsSuportados {

    private static final TagLayout _LAYOUT_FEBRABAN_CNAB240 = flatfile(
            layout(nome("Layout Padrão Febraban CNAB240"),
                    cnab(CNAB_240),
                    tag("url").value("https://portal.febraban.org.br/pagina/3053/33/pt-br/layout-240"),
                    versao("05")
            ),
            cabecalho(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(),
                    flote().value("0000"),
                    fcodigoRegistro().value("0"),
                    //Uso Exclusivo FEBRABAN / CNAB9179-AlfaBrancosG004
                    fbranco().length(9),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj().length(14).padding(Fillers.ZERO_LEFT),
                    //ConvênioCódigo do Convênio no Banco335220-Alfa*G007
                    //Código adotado pelo Banco para identificar o Contrato entre este e a Empresa Cliente.
                    fconvenio().length(20),
                    //Agência Mantenedora da Conta 53 57 5-Num*G008
                    //Dígito Verificador da Agência 58 58 1-Alfa*G009
                    fagencia().length(6),
                    //Número da Conta Corrente5970 12-Num*G010
                    //Dígito Verificador da Conta7171 1-Alfa*G011
                    fconta().length(13), //Conta com DV
                    //Dígito Verificador da Ag/Conta72721-Alfa*G012
                    //Dígito Verificador da Agência / Conta CorrenteCódigo  
                    //adotado  pelo  Banco  responsável  pela  conta  corrente,
                    //para  verificação  da autenticidade do par Código da Agência / Número da Conta Corrente.
                    //Para os Bancos que se utilizam de duas posições para o Dígito Verificador 
                    //do Número da Conta Corrente, preencher este campo com a 2ª posição deste dígito. 
                    fdac(), fcedenteNome().length(30), fbancoNome().length(30),
                    //Uso Exclusivo FEBRABAN / CNAB
                    fbranco().length(10),
                    //Código Remessa / Retorno1431431-NumG015
                    //Código adotado pela FEBRABAN para qualificar o envio ou devolução de arquivo 
                    //entre a Empresa  Cliente e o Banco prestador dos Serviços.
                    //Domínio:'1'  =  Remessa (Cliente -> Banco) '2'  =  Retorno (Banco -> Cliente)
                    fcodigoArquivo().value(1),
                    //Data de Geração do Arquivo1441518-Num
                    fdataGeracao(),
                    //Hora de Geração do Arquivo1521576
                    field("horaGeracao").length(6).format(new SimpleDateFormat("hhmmss")),
                    //Número Seqüencial do Arquivo1581636-Num*G018
                    fsequencialArquivo().length(6),
                    field("versaoLayoutArquivo").valLen("103"),
                    //Densidade de gravação (BPI), do arquivo encaminhado. Domínio:1600 BPI ou 6250 BPI
                    field("densidadeArquivo").value(0).length(5).padding(Fillers.ZERO_LEFT),
                    //Para Uso Reservado do Banco17219120-AlfaG021
                    fbranco().length(20),
                    //Para Uso Reservado da Empresa19221120-AlfaG022
                    fbranco().length(20),
                    //Uso Exclusivo FEBRABAN / CNAB21224029-AlfaBrancosG004
                    fbranco().length(29)
            ),
            cabecalhoLote(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(),
                    //Valor default para o primeiro lote, demais devem ser alterados
                    flote().value(1),
                    fcodigoRegistro().value("1"),
                    //Código adotado pela FEBRABAN para identificar a transação que será realizada com os registros detalhe do lote.
                    /* 
                    Domínio:
                        'C' = Lançamento a Crédito
                        'D' = Lançamento a Débito
                        'E' = Extrato para Conciliação
                        'G' = Extrato para Gestão de Caixa
                        'I' = Informações de Títulos Capturados do Próprio Banco
                        'R' = Arquivo Remessa
                        'T' = Arquivo Retorno
                     */
                    foperacao(),
                    //Código adotado pela FEBRABAN para indicar o tipo de serviço / produto (processo) contido no arquivo / lote.
                    /*
                    TODO: Fazer um enum
                     Domínio:
                            '01' = Cobrança
                            '03' = Boleto de Pagamento Eletrônico
                            '04' = Conciliação Bancária
                            '05' = Débitos
                            '06' = Custódia de Cheques
                            '07' = Gestão de Caixa
                            '08' = Consulta/Informação Margem
                            '09' = Averbação da Consignação/Retenção
                            '10' = Pagamento Dividendos
                            ‘11’ = Manutenção da Consignação
                            ‘12’ = Consignação de Parcelas
                            ‘13’ = Glosa da Consignação (INSS)
                            ‘14’ = Consulta de Tributos a pagar
                            '20' = Pagamento Fornecedor
                            ‘22’ = Pagamento de Contas, Tributos e Impostos
                            ‘23’ = Interoperabilidade entre Contas de Instituições de Pagamentos
                            ‘25’ = Compror
                            ‘26’ = Compror Rotativo
                            '29' = Alegação do Pagador
                            '30' = Pagamento Salários
                            ‘32’ = Pagamento de honorários
                            ‘33’ = Pagamento de bolsa auxílio
                            ‘34’ = Pagamento de prebenda (remuneração a padres e sacerdotes)
                            '40' = Vendor
                            '41' = Vendor a Termo
                            '50' = Pagamento Sinistros Segurados
                            '60' = Pagamento Despesas Viajante em Trânsito
                            '70' = Pagamento Autorizado
                            '75' = Pagamento Credenciados
                            ‘77’ = Pagamento de Remuneração
                            '80' = Pagamento Representantes / Vendedores Autorizados
                            '90' = Pagamento Benefícios
                            '98' = Pagamentos Diversos                     
                     */
                    fservico(),
                    //Código adotado pela FEBRABAN para identificar a operação que está contida no lote.
                    fforma(),
                    field("versaoLayoutLote").value("040").length(3),
                    //Uso Exclusivo da FEBRABAN/CNAB 17 17 1 - Alfa Brancos G004
                    fbranco().length(1),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    //ConvênioCódigo do Convênio no Banco335220-Alfa*G007
                    //Código adotado pelo Banco para identificar o Contrato entre este e a Empresa Cliente.
                    fconvenio().length(20),
                    //Agência Mantenedora da Conta 53 57 5-Num*G008
                    //Dígito Verificador da Agência 58 58 1-Alfa*G009
                    fagencia().length(6),
                    //Número da Conta Corrente5970 12-Num*G010
                    //Dígito Verificador da Conta7171 1-Alfa*G011
                    fconta().length(13), //Conta com DV
                    //Dígito Verificador da Ag/Conta72721-Alfa*G012
                    //Dígito Verificador da Agência / Conta CorrenteCódigo  
                    //adotado  pelo  Banco  responsável  pela  conta  corrente,
                    //para  verificação  da autenticidade do par Código da Agência / Número da Conta Corrente.
                    //Para os Bancos que se utilizam de duas posições para o Dígito Verificador 
                    //do Número da Conta Corrente, preencher este campo com a 2ª posição deste dígito. 
                    fdac(), //Conta com DV
                    fcedenteNome().length(30),
                    //Texto referente a mensagens que serão impressas nos documentos e/ou avisos a serem emitidos.
                    //Informação 1: Genérica. Quando informada constará em todos os avisos e/ou
                    //documentos originados dos detalhes desse lote. Informada no Header do Lote.
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    //Uso Exclusivo da FEBRABAN/CNAB
                    fbranco().length(8),
                    //Código das Ocorrências p/ Retorno
                    focorrencias()
            ),
            //Registro Detalhe - Segmento J (Obrigatório - Remessa / Retorno)
            tituloJ(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(),
                    //Número seqüencial para identificar univocamente um lote de serviço. Criado e
                    //controlado pelo responsável pela geração magnética dos dados contidos no arquivo.
                    //Preencher com '0001' para o primeiro lote do arquivo. Para os demais: número do lote
                    //anterior acrescido de 1. O número não poderá ser repetido dentro do arquivo.
                    //Se registro for Header do Arquivo preencher com '0000'
                    //Se registro for Trailer do Arquivo preencher com '9999'
                    flote().value(1),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    //Código adotado pela FEBRABAN para identificar o segmento do registro.
                    field("segmento").value("J").length(1),
                    //Código adotado pela FEBRABAN, para identificar o tipo de movimentação enviada no arquivo.
                    fmovimentoTipo().value(0),
                    //Código da Instrução para Movimento
                    //Código adotado pela FEBRABAN, para identificar a ação a ser realizada com o
                    //lançamento enviado no arquivo. 
                    fmovimentoCodigo().value("00"),
                    //Código adotado pela FEBRABAN para identificar o Título.
                    //Especificações do Código de Barras do Boleto de Pagamentode Cobrança -Ficha de Compensação
                    fcodigoBarras().length(44),
                    fsacadoNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    //Valor do Desconto + Abatimento
                    //Valor de desconto (bonificação) sobre o valor nominal do documento, somado ao Valor
                    //do abatimento concedido pelo Beneficiário, expresso em moeda corrente.
                    fvalorDesconto().value(0).length(15),
                    //Valor da Mora + Multa
                    //Valor do juros de mora somado ao Valor da multa, expresso em moeda corrente
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    //Número de unidades do tipo de moeda identificada para cálculo do valor do documento. G041
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    //Referência Pagador Nº do Docto Atribuído pela Empresa 183 202 20 - Alfa G064
                    fnumeroDocumento().length(20),
                    //Nosso Número Nº do Docto Atribuído pelo Banco 203 222 20 - Alfa *G043
                    //Número do Documento Atribuído pelo Banco (Nosso Número)
                    //Número atribuído pelo Banco para identificar o lançamento, que será utilizado nas manutenções do mesmo. 
                    fnossoNumero().length(20),
                    //G065 Código da Moeda
                    //Código adotado pela FEBRABAN para identificar a moeda referenciada no Título
                    fcodigoMoeda(),
                    //20.3J CNAB Uso Exclusivo FEBRABAN/CNAB 225 230 6 - Alfa Brancos G004
                    fbranco().length(6),
                    //Código das Ocorrências para Retorno/Remessa
                    //Código adotado pela FEBRABAN para identificar as ocorrências detectadas no
                    //processamento.
                    //Pode-se informar até 5 ocorrências simultaneamente, cada uma delas codificada com
                    //dois dígitos, conforme relação abaixo.
                    focorrencias()
            ),
            //Registro Detalhe - Segmento J-52 (Obrigatório – Remessa / Retorno)
            tituloJ52(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    //Número seqüencial para identificar univocamente um lote de serviço. Criado e
                    //controlado pelo responsável pela geração magnética dos dados contidos no arquivo.
                    //Preencher com '0001' para o primeiro lote do arquivo. Para os demais: número do lote
                    //anterior acrescido de 1. O número não poderá ser repetido dentro do arquivo.
                    //Se registro for Header do Arquivo preencher com '0000'
                    //Se registro for Trailer do Arquivo preencher com '9999'
                    fbancoCodigo(), flote().value(1), fcodigoRegistro().value("3"), fsequencialRegistro().length(5),
                    //Código adotado pela FEBRABAN para identificar o segmento do registro.
                    field("segmento").value("J").length(1),
                    //06.4.J52 CNAB Uso Exclusivo FEBRABAN/CNAB 15 15 1 - Alfa Brancos G004
                    fbranco().length(1),
                    //C004: Código de Movimento Remessa 
                    //Código adotado pela FEBRABAN, para identificar o tipo de movimentação enviado nos
                    //registros do arquivo de remessa.
                    //Cada Banco definirá os campos a serem alterados para o código de movimento '31'
                    fmovimentoCodigo().value("00"),
                    //08.4.J52 Código Reg. Opcional Identificação Registro Opcional 18 19 2 - Num “52” G067
                    field("idRegOpcional").length(2).value("52"),
                    //DADOS DO PAGADOR
                    //Tipo de Inscrição: '0'  =  Isento / Não Informado
                    //                   '1'  =  CPF
                    //                   '2'  =  CGC / CNPJ
                    //                   '3'  =  PIS / PASEP
                    //                   '9'  =   Outros
                    field("tipoInscricaoSacado").valLen("1"),
                    fsacadoCpf().length(15),
                    fsacadoNome().length(40),
                    //DADOS DO BENEFICIARIO
                    field("tipoInscricaoCedente").valLen("2"),
                    fcedenteCnpj().length(15),
                    fcedenteNome().length(40),
                    //DADOS DO PAGADORR
                    //Pagadorr - Dados sobre o Beneficiário responsável pela emissão do título original
                    field("tipoInscricaoPagadorr").valLen("2"),
                    field("pagadorrInscricao").length(15).filler(Fillers.ZERO_LEFT),
                    field("pagadorr").length(40).filler(Fillers.WHITE_SPACE_LEFT)
            ),
            rodapeLote(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(),
                    flote().value(1), // o mesmo do cabeçalho do lote
                    fcodigoRegistro().value("5"),
                    //04.5 CNAB Uso Exclusivo FEBRABAN/CNAB 9 17 9 - Alfa Brancos G004
                    fbranco().length(9),
                    //Quantidade de Registros do Lote 18 23 6 - Num *G057
                    fquantidadeRegistros().length(6), fvalorTotalRegistros().length(18),
                    //Qtde de Moeda Somatória de Quantidade de Moedas 42 59 13 5 Num G058
                    //G058 Somatória de Quantidade de Moedas
                    //Valor obtido pela somatória das quantidades de moeda dos registros de detalhe
                    //(Registro = '3' / Código de Segmento = {'A' / 'J'}).
                    field("qtedMoedas").length(18).padding(Fillers.ZERO_LEFT).value(1),
                    //08.5 Número Aviso Débito Número Aviso Débito 60 65 6 - Num G066
                    //Número do Aviso de Débito
                    //Número atribuído pelo Banco para identificar um Débito efetuado na Conta Corrente a
                    //partir do(s) pagamento(s) efetivado(s), visando facilitar a Conciliação Bancária.
                    field("numAvisoDebito").length(6).filler(Fillers.ZERO_LEFT),
                    fbranco().length(165),
                    //Código das Ocorrências para Retorno/Remessa G0059
                    focorrencias()
            ),
            rodape(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(), flote().value("9999"), fcodigoRegistro().value("9"),
                    //Uso Exclusivo FEBRABAN/CNAB9179-AlfaBrancosG004
                    fbranco().length(9),
                    //Qtde. de LotesQuantidade de Lotes do Arquivo18236-NumG049
                    field("qtdeLotes").value(1).padding(Fillers.ZERO_LEFT).length(6),
                    //Qtde. de RegistrosQuantidade de Registros do Arquivo24296-NumG0
                    fquantidadeRegistros().length(6),
                    //Qtde. de Contas Concil.Qtde de Contas p/ Conc. (Lotes)30356-Num*G037
                    /**
                     * Número indicativo de lotes de Conciliação Bancária
                     * enviados no arquivo. Somatória dos registros de tipo 1 e
                     * Tipo de Operação = 'E'. Campo específico para o serviço
                     * de Conciliação Bancária
                     */
                    field("qtedContas").value(0).padding(Fillers.ZERO_LEFT).length(6),
                    //Uso Exclusivo FEBRABAN/CNAB9179-AlfaBrancosG004
                    fbranco().length(205)
            )
    );
    public static final TagLayout _LAYOUT_SICREDI_CNAB240 = flatfile(
            layout(nome("Layout padrão SICREDI CNAB240"),
                    cnab(CNAB_240),
                    tag("url").value("https://www.sicredi.com.br/html/para-sua-empresa/recebimentos/cobranca/"),
                    versao("05")
            ),
            cabecalho(
                    fbancoCodigo().value("748"),
                    flote().value("0001"),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj().length(14).padding(Fillers.ZERO_LEFT),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(), fcedenteNome().length(30), fbancoNome().length(30),
                    fdataGeracao(),
                    field("horaGeracao").length(6).format(new SimpleDateFormat("hhmmss")),
                    fsequencialArquivo().length(6),
                    field("versaoLayoutArquivo").valLen("103"),
                    field("densidadeArquivo").value(0).length(5).padding(Fillers.ZERO_LEFT)
            ),
            cabecalhoLote(
                    fbancoCodigo().value("748"),
                    flote().value(1),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ52(
                    fbancoCodigo().value("748"),
                    flote().value(1),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    fmovimentoCodigo().value("00"),
                    field("idRegOpcional").length(2).value("52"),
                    field("tipoInscricaoSacado").valLen("1"),
                    fsacadoCpf().length(15),
                    fsacadoNome().length(40),
                    //DADOS DO BENEFICIARIO
                    field("tipoInscricaoCedente").valLen("2"),
                    fcedenteCnpj().length(15),
                    fcedenteNome().length(40),
                    //DADOS DO PAGADORR
                    //Pagadorr - Dados sobre o Beneficiário responsável pela emissão do título original
                    field("tipoInscricaoPagadorr").valLen("2"),
                    field("pagadorrInscricao").length(15).filler(Fillers.ZERO_LEFT),
                    field("pagadorr").length(40).filler(Fillers.WHITE_SPACE_LEFT)
            ),
            rodapeLote(
                    fbancoCodigo().value("748"),
                    flote().value(1), // o mesmo do cabeçalho do lote
                    fcodigoRegistro().value("5"),
                    fquantidadeRegistros().length(9),
                    fvalorTotalRegistros().length(18),
                    field("qtedMoedas").length(18).padding(Fillers.ZERO_LEFT).value(1),
                    field("numAvisoDebito").length(6).filler(Fillers.ZERO_LEFT),
                    fbranco().length(165),
                    focorrencias()
            ),
            rodape(
                    fbancoCodigo(),
                    flote().value("9999"),
                    fcodigoRegistro().value("9"),
                    fquantidadeRegistros().length(6),
                    field("qtedContas").value(0).padding(Fillers.ZERO_LEFT).length(6)
            )
    );
    public static final TagLayout _LAYOUT_BB_CNAB240 = flatfile(
            layout(nome("Layout padrão Banco do Brasil CNAB240"),
                    cnab(CNAB_240),
                    tag("url").value("https://www.bb.com.br/docs/pub/emp/empl/dwn/CbrVer04BB.pdf"),
                    versao("05")
            ),
            cabecalho(
                    fbancoCodigo().value("001"),
                    flote().value("0000"),
                    fcodigoRegistro().value("0"),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj().length(14).padding(Fillers.ZERO_LEFT),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    fbancoNome().length(30),
                    fcodigoArquivo().value(1),
                    fdataGeracao(),
                    field("horaGeracao").length(6).format(new SimpleDateFormat("hhmmss")),
                    fsequencialArquivo().length(6),
                    field("versaoLayoutArquivo").valLen("103"),
                    field("densidadeArquivo").value(0).length(5).padding(Fillers.ZERO_LEFT)
            ),
            cabecalhoLote(
                    fbancoCodigo().value("001"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("R"),
                    fservico().value("01"),
                    field("versaoLayoutLote").value("042").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT)
            ),
            tituloJ(
                    fbancoCodigo().value("001"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    field("segmento").value("P").length(1),
                    fmovimentoCodigo().value("01"),
                    fcodigoBarras().length(44),
                    fsacadoNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    fnossoNumero().length(9999),
                    fcodigoMoeda()
            ),
            tituloJ(
                    fbancoCodigo().value("001"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    field("segmento").value("Q").length(1),
                    fmovimentoCodigo().value("01"),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj().length(14).padding(Fillers.ZERO_LEFT),
                    fcodigoBarras().length(44),
                    fsacadoNome().length(30)
            ),
            tituloJ(
                    fbancoCodigo().value("001"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    field("segmento").value("R").length(1),
                    fmovimentoCodigo().value("01"),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13)
            ),
            tituloJ(
                    fbancoCodigo().value("001"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    field("segmento").value("S").length(1),
                    fmovimentoCodigo().value("01")
            ),
            rodapeLote(
                    fbancoCodigo().value("001"),
                    flote().value(1), // o mesmo do cabeçalho do lote
                    fcodigoRegistro().value("5"),
                    fquantidadeRegistros().length(6)
            ),
            rodape(
                    fbancoCodigo().value("001"),
                    flote().value(9999), // o mesmo do cabeçalho do lote
                    fcodigoRegistro().value("9"),
                    fquantidadeRegistros().length(6)
            )
    );
    public static final TagLayout _LAYOUT_SANTANDER_CNAB240 = flatfile(
            layout(nome("Layout padrão Banco Santander CNAB240"),
                    cnab(CNAB_240),
                    tag("url").value("https://www.bb.com.br/docs/pub/emp/empl/dwn/CbrVer04BB.pdf"),
                    versao("05")
            ),
            //REGISTRO HEADER DE ARQUIVO - TIPO DE REGISTRO = 0

            cabecalho(
                    fbancoCodigo().value("033"),
                    flote().value("0000"),
                    fcodigoRegistro().value("0"),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj().length(14).padding(Fillers.ZERO_LEFT),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    fbancoNome().length(30),
                    fcodigoArquivo().value(1),
                    fdataGeracao(),
                    field("horaGeracao").length(6).format(new SimpleDateFormat("hhmmss")),
                    fsequencialArquivo().length(6),
                    field("versaoLayoutArquivo").valLen("103"),
                    field("densidadeArquivo").value(0).length(5).padding(Fillers.ZERO_LEFT),
                    focorrencias()
            ),
            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("C"),
                    fservico().value("01"),
                    field("versaoLayoutLote").value("031").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("A").length(1),
                    fmovimentoCodigo().value("01"),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcodigoBarras().length(44),
                    fsacadoNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("B").length(1),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj().length(14).padding(Fillers.ZERO_LEFT)
            ),
            //PAGAMENTO ATRAVÉS DE ORDEM DE CRÉDITO POR TELEPROCESSAMENTO
            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("C"),
                    fservico().value("01"),
                    fforma().value("35"),
                    field("versaoLayoutLote").value("035").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("I").length(1),
                    fmovimentoCodigo().value("01"),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    focorrencias()
            ),
            //PAGAMENTO ATRAVÉS DE TITULOS BANCÁRIOS

            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("C"),
                    fservico().value("01"),
                    fforma().value("35"),
                    field("versaoLayoutLote").value("030").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("I").length(1),
                    fmovimentoCodigo().value("01"),
                    fcodigoBarras().length(44),
                    fcedenteNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    focorrencias()
            ),
            //PAGAMENTO ATRAVÉS DE TITULOS BANCÁRIOS

            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("C"),
                    fservico().value("01"),
                    fforma().value("35"),
                    field("versaoLayoutLote").value("030").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("I").length(1),
                    fmovimentoCodigo().value("01"),
                    fcodigoBarras().length(44),
                    fcedenteNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    focorrencias()
            ),
            tituloJ52(
                    fbancoCodigo().value("033"),
                    flote(),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("J").length(1),
                    fmovimentoCodigo().value("00"),
                    field("idRegOpcional").length(2).value("52"),
                    field("tipoInscricaoSacado").valLen("1"),
                    fsacadoCpf().length(15),
                    fsacadoNome().length(40),
                    //DADOS DO BENEFICIARIO
                    field("tipoInscricaoCedente").valLen("2"),
                    fcedenteCnpj().length(15),
                    fcedenteNome().length(40),
                    //DADOS DO PAGADORR
                    //Pagadorr - Dados sobre o Beneficiário responsável pela emissão do título original
                    field("tipoInscricaoPagadorr").valLen("2"),
                    field("pagadorrInscricao").length(15).filler(Fillers.ZERO_LEFT),
                    field("pagadorr").length(40).filler(Fillers.WHITE_SPACE_LEFT)
            ),
            //PAGAMENTO DE TRIBUTOS E IMPOSTOS SEM CÓDIGO DE BARRAS

            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("C"),
                    fservico().value("01"),
                    fforma(),
                    field("versaoLayoutLote").value("010").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("N").length(1),
                    fmovimentoCodigo().value("01"),
                    fcodigoBarras().length(44),
                    fcedenteNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    focorrencias()
            ),
            //PAGAMENTO DE TRIBUTOS E CONCESSIONÁRIAS COM CÓDIGO DE BARRAS

            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("C"),
                    fservico().value("01"),
                    fforma(),
                    field("versaoLayoutLote").value("010").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30),
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    focorrencias()
            ),
            tituloJ(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro(),
                    field("segmento").value("O").length(1),
                    fmovimentoCodigo().value("01"),
                    fcodigoBarras().length(44),
                    fcedenteNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    fnumeroDocumento().length(20),
                    focorrencias()
            ),
            //CAPTURA DE TITULOS DE COBRANÇA - ARQUIVO RETORNO

            cabecalhoLote(
                    fbancoCodigo().value("033"),
                    flote().value("0001"),
                    fcodigoRegistro().value("1"),
                    foperacao().value("I"),
                    fservico().value("03"),
                    fforma(),
                    field("versaoLayoutLote").value("020").length(3),
                    ftipoInscricao().value("2"),
                    fcedenteCnpj(),
                    fconvenio().length(20),
                    fagencia().length(6),
                    fconta().length(13),
                    fdac(),
                    fcedenteNome().length(30)
            ),
            tituloJ(
                    fbancoCodigo(),
                    flote().value(1),
                    fcodigoRegistro().value("3"),
                    fsequencialRegistro().length(5),
                    field("segmento").value("J").length(1),
                    fmovimentoTipo().value(0),
                    fmovimentoCodigo().value("00"),
                    fcodigoBarras().length(44),
                    fsacadoNome().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    fvalorDesconto().value(0).length(15),
                    fvalorAcrescimo().value(0).length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    field("qtdeMoeda").filler(Fillers.ZERO_LEFT).value(1).length(15),
                    fnumeroDocumento().length(20),
                    fnossoNumero().length(20),
                    fcodigoMoeda()
            ),
            rodapeLote(
                    fbancoCodigo().value("033"),
                    flote(),
                    fcodigoRegistro().value("5"),
                    fquantidadeRegistros().length(5),
                    fvalorTotalRegistros().length(18),
                    field("qtedMoedas").length(18).padding(Fillers.ZERO_LEFT).value(1),
                    field("numAvisoDebito").length(6).filler(Fillers.ZERO_LEFT),
                    focorrencias()
            ),
            rodape(
                    fbancoCodigo().value("001"),
                    flote().value(9999), // o mesmo do cabeçalho do lote
                    fcodigoRegistro().value("9"),
                    fquantidadeRegistros().length(6),
                    field("qtedContas").value(0).padding(Fillers.ZERO_LEFT).length(6)
            )
    );
    public static final TagLayout LAYOUT_FEBRABAN_CNAB240
            = _LAYOUT_FEBRABAN_CNAB240.cloneReadonly();

    public static final TagLayout LAYOUT_SICREDI_CNAB240
            = _LAYOUT_SICREDI_CNAB240.cloneReadonly();

    public static final TagLayout LAYOUT_BB_CNAB240
            = _LAYOUT_BB_CNAB240.cloneReadonly();
    
    public static final TagLayout LAYOUT_SANTANDER_CNAB240
            = _LAYOUT_SANTANDER_CNAB240.cloneReadonly();

    private static final TagLayout _LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_FEBRABAN_CNAB240.clone();

    private static final TagLayout _LAYOUT_SICREDI_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_SICREDI_CNAB240.clone();

    private static final TagLayout _LAYOUT_BB_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_BB_CNAB240.clone();
    
    private static final TagLayout _LAYOUT_SANTANDER_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_SANTANDER_CNAB240.clone();

    static {
        _LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA.get(cabecalho())
                .get(fcodigoArquivo().value('1'));
    }

    static {
        _LAYOUT_SICREDI_CNAB240_COBRANCA_REMESSA.get(cabecalho())
                .get(fcodigoArquivo().value('1'));
    }

    static {
        _LAYOUT_BB_CNAB240_COBRANCA_REMESSA.get(cabecalho())
                .get(fcodigoArquivo().value('1'));

    }
    static {
        _LAYOUT_SANTANDER_CNAB240_COBRANCA_REMESSA.get(cabecalho())
                .get(fcodigoArquivo().value('1'));
    }

    public static final TagLayout LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA.cloneReadonly();

    public static final TagLayout LAYOUT_SICREDI_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_SICREDI_CNAB240_COBRANCA_REMESSA.cloneReadonly();

    public static final TagLayout LAYOUT_BB_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_BB_CNAB240_COBRANCA_REMESSA.cloneReadonly();
    
    public static final TagLayout LAYOUT_SANTANDER_CNAB240_COBRANCA_REMESSA
            =_LAYOUT_SANTANDER_CNAB240_COBRANCA_REMESSA.cloneReadonly();

    private static final TagLayout _LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_FEBRABAN_CNAB240.clone();

    private static final TagLayout _LAYOUT_SICREDI_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_SICREDI_CNAB240.clone();

    private static final TagLayout _LAYOUT_BB_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_BB_CNAB240.clone();

    private static final TagLayout _LAYOUT_SANTANDER_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_SANTANDER_CNAB240.clone();
    static {
        _LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO.get(cabecalho())
                .get(fcodigoArquivo().value('2'));

        _LAYOUT_SICREDI_CNAB240_COBRANCA_RETORNO.get(cabecalho())
                .get(fcodigoArquivo().value('2'));
        
        _LAYOUT_BB_CNAB240_COBRANCA_RETORNO.get(cabecalho())
                .get(fcodigoArquivo().value('2'));
        
        _LAYOUT_SANTANDER_CNAB240_COBRANCA_RETORNO.get(cabecalho())
                .get(fcodigoArquivo().value('2'));
    }

    public static final TagLayout LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO.cloneReadonly();

    public static final TagLayout LAYOUT_SICREDI_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_SICREDI_CNAB240_COBRANCA_RETORNO.cloneReadonly();

    public static final TagLayout LAYOUT_BB_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_BB_CNAB240_COBRANCA_RETORNO.cloneReadonly();
    
    public static final TagLayout LAYOUT_SANTANDER_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_SANTANDER_CNAB240_COBRANCA_RETORNO.cloneReadonly();

    private static final List<TagLayout> layoutsSuportados;

    static {
        List<TagLayout> layoutsSuportadosTmp = new ArrayList<>();
        /* */
        //TODO: Adicionar teste e layouts homologados
        layoutsSuportadosTmp.add(LAYOUT_FEBRABAN_CNAB240);
        layoutsSuportadosTmp.add(LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA);
        layoutsSuportadosTmp.add(LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO);
        layoutsSuportadosTmp.add(LAYOUT_SICREDI_CNAB240);
        layoutsSuportadosTmp.add(LAYOUT_SICREDI_CNAB240_COBRANCA_REMESSA);
        layoutsSuportadosTmp.add(LAYOUT_SICREDI_CNAB240_COBRANCA_RETORNO);
        layoutsSuportadosTmp.add(LAYOUT_BB_CNAB240);
        layoutsSuportadosTmp.add(LAYOUT_BB_CNAB240_COBRANCA_REMESSA);
        layoutsSuportadosTmp.add(LAYOUT_BB_CNAB240_COBRANCA_RETORNO);
        layoutsSuportadosTmp.add(LAYOUT_SANTANDER_CNAB240);
        layoutsSuportadosTmp.add(LAYOUT_SANTANDER_CNAB240_COBRANCA_REMESSA);
        layoutsSuportadosTmp.add(LAYOUT_SANTANDER_CNAB240_COBRANCA_RETORNO);

        layoutsSuportados = Collections.unmodifiableList(layoutsSuportadosTmp);
    }

    public static TagLayout getLayoutArquivoBancarioRemessaCobranca(String codBanco, String numConvenio, String agencia, String conta, String carteira, Boolean registrado) {
        return getLayoutArquivoBancario(CNABServico.COBRANCA_REMESSA, CNAB_240,
                codBanco, numConvenio, agencia, conta, carteira, registrado);
    }

    //TODO: Implementar um metodo de busca por layouts, a partir de atributos relevantes
    public static TagLayout getLayoutArquivoBancario(CNABServico servico, CNAB cnab, String banco,
            String convenio, String agencia, String conta, String carteira, Boolean registrado) {
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

    public static boolean eq(Object value1, Object value2) {
        return value1 == value2
                || value1 != null && value1.equals(value2)
                || value2 != null && value2.equals(value1);
    }
}
