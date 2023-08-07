/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for languages.
 */
public enum Language {
    /**
     * value 0: English
     */
    ENGLISH,
    /**
     * value 1: Deutsch
     */
    DEUTSCH,
    /**
     * value 2: Portuguese
     */
    PORTUGUESE,
    /**
     * value 3: French
     */
    FRENCH,
    /**
     * value 4: Suomi
     */
    SUOMI,
    /**
     * value 5: norsk
     */
    NORSK,
    /**
     * value 6: Nederlands
     */
    NEDERLANDS,
    /**
     * value 7: Catalan
     */
    CATALAN,
    /**
     * value 8: Turkish
     */
    TURKISH,
    /**
     * value 9: Castellano
     */
    CASTELLANO,
    /**
     * value 10: Italiano
     */
    ITALIANO,
    /**
     * value 11: Dansk
     */
    DANSK,
    /**
     * value 12: Czech
     */
    CZECH,
    /**
     * value 13: Russian
     */
    RUSSIAN,
    /**
     * value 14: Estonian
     */
    ESTONIAN,
    /**
     * value 15: Serbian
     */
    SERBIAN,
    /**
     * value 16: Greek
     */
    GREEK,
    /**
     * value 17: Polski
     */
    POLSKI,
    /**
     * value 18: Croatian
     */
    CROATIAN,
    /**
     * value 19: Hungarian
     */
    HUNGARIAN,
    /**
     * value 20: Brazilian
     */
    BRAZILIAN,
    /**
     * value 21: Swedish
     */
    SWEDISH,
    /**
     * value 22: Slovak
     */
    SLOVAK,
    /**
     * value 23: Galego
     */
    GALEGO,
    /**
     * value 24: Slovenski
     */
    SLOVENSKI,
    /**
     * value 25: Belarussian
     */
    BELARUSSIAN,
    /**
     * value 26: Latvian
     */
    LATVIAN,
    /**
     * value 27: Lithuanian
     */
    LITHUANIAN,
    /**
     * value 28: Traditional Chinese
     */
    TRADITIONAL_CHINESE,
    /**
     * value 29: Simplified Chinese
     */
    SIMPLIFIED_CHINESE,
    /**
     * value 30: Japanese
     */
    JAPANESE,
    /**
     * value 31: Korean
     */
    KOREAN,
    /**
     * value 32: Bulgarian
     */
    BULGARIAN,
    /**
     * value 33: Latino
     */
    LATINO,
    /**
     * value 34: Ukrainian
     */
    UKRAINIAN,
    /**
     * value 35: Indonesian
     */
    INDONESIAN,
    /**
     * value 36: Romanian
     */
    ROMANIAN;

    /**
     * Converts ordinal number to enum value
     * @param ordinal ordinal number
     * @return enum value
     */
    public static Language fromOrdinal(int ordinal) {
        return EnumHelpers.get(Language.class).fromOrdinal(ordinal);
    }
}
