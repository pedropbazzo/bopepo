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

    public static TagLayout LAYOUT_FEBRABAN_REMESSA_COBRANCA_CNAB240 = flatfile(
            layout(nome("Arquivo-Remessa-Febraban-CNAB240"),
                    versao("05"),
                    cnab(CNAB_240),
                    servico(COBRANCA_REMESSA),
                    tag("url").value("https://portal.febraban.org.br/pagina/3053/33/pt-br/layout-240")
            ),
            cabecalho(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(),
                    flote().value("0000"),
                    fcodigoRegistro().value("0"),
                    //Uso Exclusivo FEBRABAN / CNAB9179-AlfaBrancosG004
                    fbranco().length(9),
                    //Tipo de Inscrição: '0'  =  Isento / Não Informado
                    //                   '1'  =  CPF
                    //                   '2'  =  CGC / CNPJ
                    //                   '3'  =  PIS / PASEP
                    //                   '9'  =   Outros
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
                    fdac(), fcedente().length(30), fbanco().length(30),
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
                    field("versaoLayoutArquivo").value("103"),
                    //Densidade de gravação (BPI), do arquivo encaminhado. Domínio:1600 BPI ou 6250 BPI
                    field("densidadeArquivo").length(5).padding(Fillers.ZERO_LEFT),
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
                    flote(),
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
                    field("operacao").length(1),
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
                    field("servico").length(2).padding(Fillers.WHITE_SPACE_LEFT),
                    //Código adotado pela FEBRABAN para identificar a operação que está contida no lote.
                    /**
                     * Domínio: '01' = Crédito em Conta Corrente/Salário '02' =
                     * Cheque Pagamento / Administrativo '03' = DOC/TED (1) (2)
                     * '04' = Cartão Salário (somente para Tipo de Serviço =
                     * '30') '05' = Crédito em Conta Poupança '10' = OP à
                     * Disposição ‘11’ = Pagamento de Contas e Tributos com
                     * Código de Barras ‘16’ = Tributo - DARF Normal ‘17’ =
                     * Tributo - GPS (Guia da Previdência Social) ‘18’ = Tributo
                     * - DARF Simples ‘19’ = Tributo - IPTU – Prefeituras '20' =
                     * Pagamento com Autenticação ‘21’ = Tributo – DARJ ‘22’ =
                     * Tributo - GARE-SP ICMS ‘23’ = Tributo - GARE-SP DR ‘24’ =
                     * Tributo - GARE-SP ITCMD ‘25’ = Tributo - IPVA ‘26’ =
                     * Tributo - Licenciamento ‘27’ = Tributo – DPVAT '30' =
                     * Liquidação de Títulos do Próprio Banco '31' = Pagamento
                     * de Títulos de Outros Bancos '40' = Extrato de Conta
                     * Corrente '41' = TED – Outra Titularidade (1) '43' = TED –
                     * Mesma Titularidade (1) ‘44’ = TED para Transferência de
                     * Conta Investimento '50' = Débito em Conta Corrente '70' =
                     * Extrato para Gestão de Caixa ‘71’ = Depósito Judicial em
                     * Conta Corrente ‘72’ = Depósito Judicial em Poupança ‘73’
                     * = Extrato de Conta Investimento ‘80’= Pagamento de
                     * tributos municipais ISS – LCP 157 – próprio Banco ‘81’=
                     * Pagamento de Tributos Municipais ISS – LCP 157 – outros
                     * Bancos
                     */
                    field("forma").length(2),
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
                    fcedente().length(30),
                    //Texto referente a mensagens que serão impressas nos documentos e/ou avisos a serem emitidos.
                    //Informação 1: Genérica. Quando informada constará em todos os avisos e/ou
                    //documentos originados dos detalhes desse lote. Informada no Header do Lote.
                    field("mensagem1").length(40).filler(Fillers.WHITE_SPACE_LEFT),
                    field("cedenteEndereco").length(80).filler(Fillers.WHITE_SPACE_LEFT),
                    //Uso Exclusivo da FEBRABAN/CNAB
                    fbranco().length(8),
                    //Código das Ocorrências p/ Retorno
                    focorrencias().filler(Fillers.WHITE_SPACE_LEFT)
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
                    // Domínio: '0' = Indica INCLUSÃO
                    //          ‘1’ = Indica CONSULTA
                    //          ‘2’ = Indica SUSPENSÃO
                    //          '3' = Indica ESTORNO (somente para retorno)
                    //          ‘4’ = Indica REATIVAÇÃO
                    //          '5' = Indica ALTERAÇÃO
                    //          ‘7` = Indica LIQUIDAÇAO
                    //          '9' = Indica EXCLUSÃO
                    field("movimentoTipo").value(0).length(1),
                    //Código da Instrução para Movimento
                    //Código adotado pela FEBRABAN, para identificar a ação a ser realizada com o
                    //lançamento enviado no arquivo. 
                    //Domínio:
                    //'00' = Inclusão de Registro Detalhe Liberado
                    //'09' = Inclusão do Registro Detalhe Bloqueado
                    //'10' = Alteração do Pagamento Liberado para Bloqueado (Bloqueio)
                    //'11' = Alteração do Pagamento Bloqueado para Liberado (Liberação)
                    //'17' = Alteração do Valor do Título
                    //'19' = Alteração da Data de Pagamento
                    //'23' = Pagamento Direto ao Fornecedor - Baixar
                    //'25' = Manutenção em Carteira - Não Pagar
                    //'27' = Retirada de Carteira - Não Pagar
                    //'33' = Estorno por Devolução da Câmara Centralizadora (somente para Tipo de
                    //Movimento = '3')
                    //'40' = Alegação do Pagador
                    //'99' = Exclusão do Registro Detalhe Incluído Anteriormente
                    field("movimentoCodigo").value("00").length(2),
                    field("codigoBarras").length(44),
                    fsacado().length(30),
                    fdataVencimento(),
                    fvalor().length(15),
                    //Valor do Desconto + Abatimento
                    //Valor de desconto (bonificação) sobre o valor nominal do documento, somado ao Valor
                    //do abatimento concedido pelo Beneficiário, expresso em moeda corrente.
                    fvalorDesconto().length(15),
                    //Valor da Mora + Multa
                    //Valor do juros de mora somado ao Valor da multa, expresso em moeda corrente
                    fvalorAcrescimo().length(15),
                    fdataPagamento(), fvalorPagamento().length(15),
                    //Número de unidades do tipo de moeda identificada para cálculo do valor do documento. G041
                    field("qtdeMoeda").length(15),
                    //Referência Pagador Nº do Docto Atribuído pela Empresa 183 202 20 - Alfa G064
                    fnumeroDocumento().length(20),
                    //Nosso Número Nº do Docto Atribuído pelo Banco 203 222 20 - Alfa *G043
                    //Número do Documento Atribuído pelo Banco (Nosso Número)
                    //Número atribuído pelo Banco para identificar o lançamento, que será utilizado nas manutenções do mesmo. 
                    fnossoNumero().length(20),
                    //G065 Código da Moeda
                    //Código adotado pela FEBRABAN para identificar a moeda referenciada no Título
                    //Domínio:
                    //'01' = Reservado para Uso Futuro
                    //'02' = Dólar Americano Comercial (Venda)
                    //'03' = Dólar Americano Turismo (Venda)
                    //'04' = ITRD
                    //'05' = IDTR
                    //'06' = UFIR Diária
                    //'07' = UFIR Mensal
                    //'08' = FAJ-TR
                    //'09' = Real
                    //'10' = TR
                    //'11' = IGPM
                    //'12' = CDI
                    //'13' = Percentual do CDI
                    //‘14’ = Euro
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
                    /**
                     * Domínio: '01' = Entrada de Títulos '02' = Pedido de Baixa
                     * ‘03’ = Protesto para Fins Falimentares '04' = Concessão
                     * de Abatimento '05' = Cancelamento de Abatimento '06' =
                     * Alteração de Vencimento '07' = Concessão de Desconto '08'
                     * = Cancelamento de Desconto '09' = Protestar '10' = Sustar
                     * Protesto e Baixar Título '11' = Sustar Protesto e Manter
                     * em Carteira ‘12’ = Alteração de Juros de Mora ‘13’ =
                     * Dispensar Cobrança de Juros de Mora ‘14’ = Alteração de
                     * Valor/Percentual de Multa ‘15’ = Dispensar Cobrança de
                     * Multa ‘16’ = Alteração de Valor/Data de Desconto ‘17’ =
                     * Não conceder Desconto ‘18’ = Alteração do Valor de
                     * Abatimento ‘19’ = Prazo Limite de Recebimento - Alterar
                     * ‘20’ = Prazo Limite de Recebimento - Dispensar ‘21’ =
                     * Alterar número do título dado pelo Beneficiário ‘22’ =
                     * Alterar número controle do Participante ‘23’ = Alterar
                     * dados do Pagador ‘24’ = Alterar dados do Sacador/Avalista
                     * '30' = Recusa da Alegação do Pagador '31' = Alteração de
                     * Outros Dados '33' = Alteração dos Dados do Rateio de
                     * Crédito '34' = Pedido de Cancelamento dos Dados do Rateio
                     * de Crédito '35' = Pedido de Desagendamento do Débito
                     * Automático '40' = Alteração de Carteira ‘41’ = Cancelar
                     * protesto ‘42’ = Alteração de Espécie de Título ‘43’ =
                     * Transferência de carteira/modalidade de cobrança ‘44’ =
                     * Alteração de contrato de cobrança ‘45’ = Negativação Sem
                     * Protesto ‘46’ = Solicitação de Baixa de Título Negativado
                     * Sem Protesto ‘47’ = Alteração do Valor Nominal do Título
                     * ‘48’ = Alteração do Valor Mínimo/ Percentual ‘49’ =
                     * Alteração do Valor Máximo/Percentual
                     */
                    field("movimentoCodigo").value("00").length(2),
                    //08.4.J52 Código Reg. Opcional Identificação Registro Opcional 18 19 2 - Num “52” G067
                    field("idRegOpcional").length(2).value("52"),
                    //DADOS DO PAGADOR
                    //Tipo de Inscrição: '0'  =  Isento / Não Informado
                    //                   '1'  =  CPF
                    //                   '2'  =  CGC / CNPJ
                    //                   '3'  =  PIS / PASEP
                    //                   '9'  =   Outros
                    field("tipoInscricaoSacado").value("1"),
                    fsacadoCpf().length(15),
                    fsacado().length(40),
                    //DADOS DO BENEFICIARIO
                    field("tipoInscricaoCedente").value("2"),
                    fcedenteCnpj().length(15),
                    fcedente().length(40),
                    //DADOS DO PAGADORR
                    //Pagadorr - Dados sobre o Beneficiário responsável pela emissão do título original
                    field("tipoInscricaoPagadorr").value("2"),
                    field("pagadorrInscricao").length(15).padding(Fillers.ZERO_LEFT),
                    field("pagadorr").length(40)
            ),
            rodapeLote(
                    //Controle: Banco, lote e registro
                    //Banco: Código do Banco na Compensação133-NumG001
                    fbancoCodigo(), flote(), fcodigoRegistro().value("5"),
                    //04.5 CNAB Uso Exclusivo FEBRABAN/CNAB 9 17 9 - Alfa Brancos G004
                    fbranco().length(9),
                    //Quantidade de Registros do Lote 18 23 6 - Num *G057
                    fquantidadeRegistros().length(6), fvalorTotalRegistros().length(18),
                    //G058 Somatória de Quantidade de Moedas
                    //Valor obtido pela somatória das quantidades de moeda dos registros de detalhe
                    //(Registro = '3' / Código de Segmento = {'A' / 'J'}).
                    field("qtedMoedas").padding(Fillers.ZERO_LEFT).length(5),
                    //08.5 Número Aviso Débito Número Aviso Débito 60 65 6 - Num G066
                    //Número do Aviso de Débito
                    //Número atribuído pelo Banco para identificar um Débito efetuado na Conta Corrente a
                    //partir do(s) pagamento(s) efetivado(s), visando facilitar a Conciliação Bancária.
                    field("numAvisoDebito"), fbranco().length(165),
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
                    field("qtedContas").padding(Fillers.ZERO_LEFT).length(6),
                    //Uso Exclusivo FEBRABAN/CNAB9179-AlfaBrancosG004
                    fbranco().length(205)
            )
    );

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
                    fcedente().length(30).padding(Fillers.WHITE_SPACE_RIGHT),
                    field("comp-banco").value("341BANCO ITAU SA"),
                    field("dataGeracao").length(6).type(Date.class).format(new SimpleDateFormat("ddMMYY")),
                    field("").length(294).filler(Fillers.WHITE_SPACE_LEFT),
                    fsequencialRegistro().type(Number.class).length(6).padding(Fillers.ZERO_LEFT)
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
                    fcedenteCnpj().length(14),
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
                    fsequencialRegistro().type(Number.class).padding(Fillers.ZERO_LEFT).length(6)
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
