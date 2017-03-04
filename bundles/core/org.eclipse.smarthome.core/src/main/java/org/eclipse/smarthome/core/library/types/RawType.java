/**
 * Copyright (c) 2014-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.core.library.types;

import java.util.Arrays;
import java.util.Base64;

import org.eclipse.smarthome.core.types.PrimitiveType;
import org.eclipse.smarthome.core.types.State;

/**
 * This type can be used for all binary data such as images, documents, sounds etc.
 * Note that it is NOT adequate for any kind of streams, but only for fixed-size data.
 *
 * @author Kai Kreuzer
 *
 */
public class RawType implements PrimitiveType, State {

    protected byte[] bytes;
    protected String mimeType;

    public RawType() {
        this(new byte[0]);
    }

    public RawType(byte[] bytes) {
        this(bytes, null);
    }

    public RawType(byte[] bytes, String mimeType) {
        this.bytes = bytes;
        this.mimeType = mimeType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static RawType valueOf(String value) {
        int idx;
        if (value == null || !value.startsWith("data:") || ((idx = value.indexOf(",")) < 0)) {
            return new RawType();
        }
        String mimeType = null;
        int idx2 = value.indexOf(";");
        if (idx2 > 5) {
            mimeType = value.substring(5, idx2);
        }
        return new RawType(Base64.getDecoder().decode(value.substring(idx + 1)), mimeType);
    }

    @Override
    public String toString() {
        return String.format("raw type (%s): %d bytes", (mimeType != null) ? mimeType : "unknown", bytes.length);
    }

    @Override
    public String toFullString() {
        return String.format("data:%s;base64,%s", (mimeType != null) ? mimeType : "",
                Base64.getEncoder().encodeToString(bytes));
    }

    @Override
    public String format(String pattern) {
        return toFullString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RawType other = (RawType) obj;
        if (mimeType == null && other.mimeType != null) {
            return false;
        } else if (mimeType != null && !mimeType.equals(other.mimeType)) {
            return false;
        }
        if (!Arrays.equals(bytes, other.bytes)) {
            return false;
        }
        return true;
    }

}
