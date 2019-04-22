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

import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jrimum.domkee.banco.IBanco;
import org.jrimum.texgit.IFiller;

/**
 *
 * @author strike
 */
public class TagLayout {

    public static class TagCreator {

        public static TagLayout field(String texto) {
            return tag(texto);
        }

        public static TagLayout cabecalho(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout titulo(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout rodape(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout group(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout descricao(String texto) {
            return tagin().withValue(texto);
        }

        public static TagLayout versao(String texto) {
            return tagin().withValue(texto);
        }

        public static TagLayout nome(String texto) {
            return tagin().withValue(texto);
        }

        public static TagLayout layout(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        public static TagLayout banco(IBanco banco) {
            return tagin().withValue(banco);
        }

        public static TagLayout servico(CNABServico servico) {
            return tagin().withValue(servico);
        }

        public static TagLayout cnab(CNAB cnab) {
            return tagin().withValue(cnab);
        }

        public static TagLayout flatfile(TagLayout... filhos) {
            return tagin().with(filhos);
        }

        private static TagLayout tagin() {
            String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
            return tag(nomeMetodoAnterior);
        }

        public static TagLayout tag(String nome) {
            return new TagLayout(nome);
        }
    }

    String nome;
    Object value;
    final List<TagLayout> filhos;
    final Map<String, Object> atributos;

    TagLayout get(String strfilho) {
        TagLayout fi = null;
        for (TagLayout filho : filhos) {
            if (filho.nome.equalsIgnoreCase(strfilho)) {
                fi = filho;
                break;
            }
        }
        return fi;
    }

    String getAtr(String stratt) {
        String ret = null;
        Object att = atributos.get(stratt);
        if (att != null) {
            ret = att.toString();
        }
        return ret;
    }

    Integer getInt(String stratt) {
        Integer ret = null;
        Object obj = atributos.get(stratt);
        if (obj instanceof Number) {
            if (obj instanceof Integer) {
                ret = (Integer) obj;
            } else {
                ret = ((Number) obj).intValue();
            }
        } else {
            ret = Integer.parseInt(obj.toString());
        }
        return ret;
    }

    Object getObj(String stratt) {
        return atributos.get(stratt);
    }

    Object getValue(String nome) {
        Object ret = null;
        ret = this.atributos.get(nome);
        if (ret == null) {
            TagLayout fi = this.get(nome);
            if (fi != null) {
                ret = fi.getValue();
            }
        }
        return ret;
    }

    public TagLayout(String nome) {
        this.nome = nome;
        this.filhos = new ArrayList<>();
        this.atributos = new TreeMap<>();
    }

    public TagLayout atr(String nome, Object valor) {
        atributos.put(nome, valor);
        return this;
    }

    public TagLayout type(Class tipo) {
        return setAttr(tipo);
    }

    public TagLayout filler(IFiller padding) {
        return setAttr(padding);
    }

    public TagLayout padding(IFiller padding) {
        return setAttr(padding);
    }

    public TagLayout format(Format padding) {
        return setAttr(padding);
    }

    public TagLayout length(int len) {
        return setAttr(len);
    }

    public TagLayout position(int len) {
        return setAttr(len);
    }

    public TagLayout value(Object len) {
        return setAttr(len);
    }

    public TagLayout withValue(Object texto) {
        this.value = texto;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public TagLayout with(TagLayout... filhos) {
        if (filhos != null) {
            for (TagLayout filho : filhos) {
                this.filhos.add(filho);
            }
        }
        return this;
    }

    protected TagLayout setAttr(Object valor) {
        //TODO: Melhorar isso;
        String nomeMetodoAnterior = Thread.currentThread().getStackTrace()[2].getMethodName();
        /* Propriedade a ser setada é o nome do metodo que chamou */
        this.atr(nomeMetodoAnterior, valor);
        return this;
    }
}
