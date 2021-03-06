/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.properties;

import junit.framework.Test;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.netbeans.spi.queries.FileEncodingQueryImplementation;

/**
 * 
 * @author  Jaroslav Tulach
 */
public class PropertiesEncodingInMimeLookupTest extends NbTestCase {
    
    public PropertiesEncodingInMimeLookupTest() {
        super("Encoding test");
    }
    public static Test suite() {
        return NbModuleSuite.create(
            NbModuleSuite.emptyConfiguration().addTest(PropertiesEncodingInMimeLookupTest.class).
            gui(false)
        );
    }

    public void testEncodingRegisteredInMimeLookup() {
        FileEncodingQueryImplementation ec = MimeLookup.getLookup("text/x-properties").lookup(FileEncodingQueryImplementation.class);
        assertNotNull("FileEncodingQuery found", ec);
        assertEquals("Right class", PropertiesEncoding.class, ec.getClass());
    }
    
    
}
