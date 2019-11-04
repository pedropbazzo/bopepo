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

import org.apache.log4j.Logger;

/**
 *
 * @author braully
 */
public class RemessaFacade extends ArquivoFacade {

    public static Logger logger = Logger.getLogger(ArquivoFacade.class);

    public RemessaFacade(TagLayout template) {
        this.template = template;
    }

    public CabecalhoArquivo addNovoCabecalho() {
        CabecalhoArquivo cabecalho = novoCabecalho("cabecalho");
        this.add(cabecalho);
        return cabecalho;
    }

    public CabecalhoArquivo addNovoCabecalhoLote() {
        CabecalhoArquivo cabecalho = novoCabecalho("cabecalhoLote");
        this.add(cabecalho);
        return cabecalho;
    }

    public CabecalhoArquivo novoCabecalho(String tipoCabecalho) {
        CabecalhoArquivo cabecalho = new CabecalhoArquivo(template.get(tipoCabecalho));
        return cabecalho;
    }

    public TituloArquivo addNovoTitulo() {
        TituloArquivo titulo = this.novoTitulo("titulo");
        this.add(titulo);
        return titulo;
    }

    public TituloArquivo addNovoTituloJ() {
        TituloArquivo titulo = this.novoTitulo("tituloJ");
        this.add(titulo);
        return titulo;
    }

    public TituloArquivo addNovoTituloJ52() {
        TituloArquivo titulo = this.novoTitulo("tituloJ52");
        this.add(titulo);
        return titulo;
    }

    public TituloArquivo novoTitulo(String tipoTitulo) {
        TituloArquivo titulo = new TituloArquivo(template.get(tipoTitulo));
        return titulo;
    }

    public RodapeArquivo addNovoRodape() {
        RodapeArquivo rodape = this.novoRodape("rodape");
        this.add(rodape);
        return rodape;
    }

    public RodapeArquivo addNovoRodapeLote() {
        RodapeArquivo rodape = this.novoRodape("rodapeLote");
        this.add(rodape);
        return rodape;
    }

    public RodapeArquivo novoRodape(String tipoRodape) {
        RodapeArquivo rodape = new RodapeArquivo(template.get(tipoRodape));
        return rodape;
    }

    public RegistroArquivo addNovoRegistro(String tipoRegistro) {
        RegistroArquivo novoRegistro = novoRegistro(tipoRegistro);
        this.add(novoRegistro);
        return novoRegistro;
    }

    public RegistroArquivo novoRegistro(String tipoRegistro) {
        TagLayout layoutRegistro = template.get(tipoRegistro);
        if (layoutRegistro == null) {
            throw new IllegalArgumentException("Não existe registro do tipo " + tipoRegistro + " no layout " + template);
        }
        return new RegistroArquivo(layoutRegistro);
    }
}
