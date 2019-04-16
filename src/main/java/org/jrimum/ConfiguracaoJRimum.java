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
package org.jrimum;

/**
 *
 * @author braully
 */
public class ConfiguracaoJRimum {

    /*
     * Ignorar os zero a esquerda na conversão string para inteiro.
     * Observação: Cuidado com a conversão da string "0ddddd", pois o número será interpetado como octal.
     */
    public static boolean ignorarZeroEsquerdaConversao = false;

    /* 
     * Falhar caso algum CPF ou CNPJ informado esteja com digito verificado inválido.
     */
    public static boolean falharEmCPRFInvalido = false;

    /*
     Falhar em caso de registro vazio, na geração de arquivo texto
     */
    public static boolean falharEmRegistroVazio = false;

}
