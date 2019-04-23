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
                    /**
                     * Domínio: '00' = Crédito ou Débito Efetivado → Este código
                     * indica que o pagamento foi confirmado '01' =
                     * Insuficiência de Fundos - Débito Não Efetuado '02' =
                     * Crédito ou Débito Cancelado pelo Pagador/Credor '03' =
                     * Débito Autorizado pela Agência - Efetuado 'AA' = Controle
                     * Inválido 'AB' = Tipo de Operação Inválido 'AC' = Tipo de
                     * Serviço Inválido 'AD' = Forma de Lançamento Inválida 'AE'
                     * = Tipo/Número de Inscrição Inválido 'AF' = Código de
                     * Convênio Inválido 'AG' = Agência/Conta Corrente/DV
                     * Inválido 'AH' = Nº Seqüencial do Registro no Lote
                     * Inválido 'AI' = Código de Segmento de Detalhe Inválido
                     * 'AJ' = Tipo de Movimento Inválido 'AK' = Código da Câmara
                     * de Compensação do Banco Favorecido/Depositário Inválido
                     * 'AL' = Código do Banco Favorecido, Instituição de
                     * Pagamento ou Depositário Inválido 'AM' = Agência
                     * Mantenedora da Conta Corrente do Favorecido Inválida 'AN'
                     * = Conta Corrente/DV/Conta de Pagamento do Favorecido
                     * Inválido 'AO' = Nome do Favorecido Não Informado 'AP' =
                     * Data Lançamento Inválido 'AQ' = Tipo/Quantidade da Moeda
                     * Inválido 'AR' = Valor do Lançamento Inválido 'AS' = Aviso
                     * ao Favorecido - Identificação Inválida 'AT' = Tipo/Número
                     * de Inscrição do Favorecido Inválido 'AU' = Logradouro do
                     * Favorecido Não Informado 'AV' = Nº do Local do Favorecido
                     * Não Informado 'AW' = Cidade do Favorecido Não Informada
                     * 'AX' = CEP/Complemento do Favorecido Inválido 'AY' =
                     * Sigla do Estado do Favorecido Inválida 'AZ' = Código/Nome
                     * do Banco Depositário Inválido 'BA' = Código/Nome da
                     * Agência Depositária Não Informado 'BB' = Seu Número
                     * Inválido 'BC' = Nosso Número Inválido 'BD' = Inclusão
                     * Efetuada com Sucesso 'BE' = Alteração Efetuada com
                     * Sucesso 'BF' = Exclusão Efetuada com Sucesso 'BG' =
                     * Agência/Conta Impedida Legalmente ‘BH’= Empresa não pagou
                     * salário ‘BI’ = Falecimento do mutuário ‘BJ’ = Empresa não
                     * enviou remessa do mutuário ‘BK’= Empresa não enviou
                     * remessa no vencimento ‘BL’ = Valor da parcela inválida
                     * ‘BM’= Identificação do contrato inválida ‘BN’ = Operação
                     * de Consignação Incluída com Sucesso ‘BO’ = Operação de
                     * Consignação Alterada com Sucesso ‘BP’ = Operação de
                     * Consignação Excluída com Sucesso ‘BQ’ = Operação de
                     * Consignação Liquidada com Sucesso ‘BR’ = Reativação
                     * Efetuada com Sucesso ‘BS’ = Suspensão Efetuada com
                     * Sucesso 'CA' = Código de Barras - Código do Banco
                     * Inválido 'CB' = Código de Barras - Código da Moeda
                     * Inválido 'CC' = Código de Barras - Dígito Verificador
                     * Geral Inválido 'CD' = Código de Barras - Valor do Título
                     * Inválido 'CE' = Código de Barras - Campo Livre Inválido
                     * 'CF' = Valor do Documento Inválido 'CG' = Valor do
                     * Abatimento Inválido 'CH' = Valor do Desconto Inválido
                     * 'CI' = Valor de Mora Inválido 'CJ' = Valor da Multa
                     * Inválido 'CK' = Valor do IR Inválido 'CL' = Valor do ISS
                     * Inválido 'CM' = Valor do IOF Inválido 'CN' = Valor de
                     * Outras Deduções Inválido 'CO' = Valor de Outros
                     * Acréscimos Inválido 'CP' = Valor do INSS Inválido 'HA' =
                     * Lote Não Aceito 'HB' = Inscrição da Empresa Inválida para
                     * o Contrato 'HC' = Convênio com a Empresa
                     * Inexistente/Inválido para o Contrato 'HD' = Agência/Conta
                     * Corrente da Empresa Inexistente/Inválido para o Contrato
                     * 'HE' = Tipo de Serviço Inválido para o Contrato 'HF' =
                     * Conta Corrente da Empresa com Saldo Insuficiente 'HG' =
                     * Lote de Serviço Fora de Seqüência 'HH' = Lote de Serviço
                     * Inválido `HI` = Arquivo não aceito `HJ` = Tipo de
                     * Registro Inválido `HK` = Código Remessa / Retorno
                     * Inválido `HL` = Versão de layout inválida `HM` = Mutuário
                     * não identificado `HN` = Tipo do beneficio não permite
                     * empréstimo `HO` = Beneficio cessado/suspenso `HP` =
                     * Beneficio possui representante legal `HQ` = Beneficio é
                     * do tipo PA (Pensão alimentícia) `HR` = Quantidade de
                     * contratos permitida excedida `HS` = Beneficio não
                     * pertence ao Banco informado `HT` = Início do desconto
                     * informado já ultrapassado `HU`= Número da parcela
                     * inválida `HV`= Quantidade de parcela inválida `HW`=
                     * Margem consignável excedida para o mutuário dentro do
                     * prazo do contrato `HX` = Empréstimo já cadastrado `HY` =
                     * Empréstimo inexistente `HZ` = Empréstimo já encerrado
                     * `H1` = Arquivo sem trailer `H2` = Mutuário sem crédito na
                     * competência `H3` = Não descontado – outros motivos `H4` =
                     * Retorno de Crédito não pago `H5` = Cancelamento de
                     * empréstimo retroativo `H6` = Outros Motivos de Glosa ‘H7’
                     * = Margem consignável excedida para o mutuário acima do
                     * prazo do contrato ‘H8’ = Mutuário desligado do empregador
                     * ‘H9’ = Mutuário afastado por licença ‘IA’ = Primeiro nome
                     * do mutuário diferente do primeiro nome do movimento do
                     * censo ou diferente da base de Titular do Benefício ‘IB’ =
                     * Benefício suspenso/cessado pela APS ou Sisobi ‘IC’ =
                     * Benefício suspenso por dependência de cálculo ‘ID’ =
                     * Benefício suspenso/cessado pela inspetoria/auditoria ‘IE’
                     * = Benefício bloqueado para empréstimo pelo beneficiário
                     * ‘IF’ = Benefício bloqueado para empréstimo por TBM ‘IG’ =
                     * Benefício está em fase de concessão de PA ou
                     * desdobramento ‘IH’ = Benefício cessado por óbito ‘II’ =
                     * Benefício cessado por fraude ‘IJ’ = Benefício cessado por
                     * concessão de outro benefício ‘IK’ = Benefício cessado:
                     * estatutário transferido para órgão de origem ‘IL’ =
                     * Empréstimo suspenso pela APS ‘IM’ = Empréstimo cancelado
                     * pelo banco ‘IN’ = Crédito transformado em PAB ‘IO’ =
                     * Término da consignação foi alterado ‘IP’ = Fim do
                     * empréstimo ocorreu durante período de suspensão ou
                     * concessão ‘IQ’ = Empréstimo suspenso pelo banco ‘IR’ =
                     * Não averbação de contrato – quantidade de
                     * parcelas/competências informadas ultrapassou a data
                     * limite da extinção de cota do dependente titular de
                     * benefícios 'TA' = Lote Não Aceito - Totais do Lote com
                     * Diferença 'YA' = Título Não Encontrado 'YB' =
                     * Identificador Registro Opcional Inválido 'YC' = Código
                     * Padrão Inválido 'YD' = Código de Ocorrência Inválido 'YE'
                     * = Complemento de Ocorrência Inválido 'YF' = Alegação já
                     * Informada Observação: As ocorrências iniciadas com 'ZA'
                     * tem caráter informativo para o cliente 'ZA' = Agência /
                     * Conta do Favorecido Substituída ‘ZB’ = Divergência entre
                     * o primeiro e último nome do beneficiário versus primeiro
                     * e último nome na Receita Federal ‘ZC’ = Confirmação de
                     * Antecipação de Valor ‘ZD’ = Antecipação parcial de valor
                     * ‘ZE’ = Título bloqueado na base ‘ZF’ = Sistema em
                     * contingência – título valor maior que referência ‘ZG’ =
                     * Sistema em contingência – título vencido ‘ZH’ = Sistema
                     * em contingência – título indexado ‘ZI’ = Beneficiário
                     * divergente ‘ZJ’ = Limite de pagamentos parciais excedido
                     * ‘ZK’ = Boleto já liquidado
                     *
                     */
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

    public static final TagLayout LAYOUT_FEBRABAN_CNAB240
            = _LAYOUT_FEBRABAN_CNAB240.cloneReadonly();

    /*
     Layout Padrão Febraban CNAB 240 para Remessa de Cobrança
     */
    private static final TagLayout _LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_FEBRABAN_CNAB240.clone();

    static {
        _LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA.get(cabecalho())
                .get(fcodigoArquivo().value('1'));
    }

    public static final TagLayout LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA
            = _LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA.cloneReadonly();

    private static final TagLayout _LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_FEBRABAN_CNAB240.clone();

    static {
        _LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO.get(cabecalho())
                .get(fcodigoArquivo().value('2'));

    }

    public static final TagLayout LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO
            = _LAYOUT_FEBRABAN_CNAB240_COBRANCA_RETORNO.cloneReadonly();

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
