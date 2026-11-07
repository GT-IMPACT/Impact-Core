// SPDX-License-Identifier: LGPL-3.0-only
// Adapted from GTNewHorizons/GT5-Unofficial, gregtech.api.util.LongData.
// License: META-INF/licenses/GT5-Unofficial-LGPL-3.0 in the distributed JAR.
package com.impact.util.statistics;

import java.math.BigInteger;

public interface ILongData {

    default double avg() {
        int size = size();
        if (size == 0) {
            return 0d;
        }
        return sum().doubleValue() / size;
    }

    default long avgLong() {
        int size = size();
        if (size == 0) {
            return 0;
        }
        return sum().divide(BigInteger.valueOf(size)).longValueExact();
    }

    int size();

    BigInteger sum();
}
