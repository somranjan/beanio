/*
 * Copyright 2010-2011 Kevin Seim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beanio;

import java.util.*;

/**
 * Exception thrown when a record or one of its fields does not pass
 * validation. 
 * 
 * @author Kevin Seim
 * @since 1.0
 */
public class InvalidRecordException extends BeanReaderException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new <tt>InvalidRecordException</tt>.
     * @param context the current context of the reader
     * @param message the error message
     * @param cause the root cause
     */
    public InvalidRecordException(BeanReaderContext context, String message, Throwable cause) {
        super(context, message, cause);
    }

    /**
     * Constructs a new <tt>InvalidRecordException</tt>.
     * @param context the current context of the reader
     * @param message the error message
     */
    public InvalidRecordException(BeanReaderContext context, String message) {
        super(context, message);
    }
    
    @Override
    public String toString() {
        BeanReaderContext context = super.getContext();
        
        String message = super.toString();
        if (context == null || !(context.hasFieldErrors() || context.hasRecordErrors())) {
            return message;
        }

        StringBuilder s = new StringBuilder(message);

        if (context.hasRecordErrors()) {
            for (String error : context.getRecordErrors()) {
                s.append("\n ==> ");
                s.append(error);
            }
        }
        if (context.hasFieldErrors()) {
            for (Map.Entry<String, Collection<String>> entry : context.getFieldErrors().entrySet()) {
                String fieldName = entry.getKey();
                for (String error : entry.getValue()) {
                    s.append("\n ==> Invalid '");
                    s.append(fieldName);
                    s.append("':  ");
                    s.append(error);
                }
            }
        }
        return s.toString();
    }
}