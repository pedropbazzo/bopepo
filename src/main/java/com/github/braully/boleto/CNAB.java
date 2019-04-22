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

/**
 *
 * @author Braully Rocha da Silva
 */
public enum CNAB {
    CNAB_400("400"), CNAB_240("240");
    final String len;

    private CNAB(String len) {
        this.len = len;
    }

    public String getLen() {
        return len;
    }

    @Override
    public String toString() {
        return len;
    }
}
