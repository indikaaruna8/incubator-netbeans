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

package org.netbeans.modules.j2ee.persistenceapi;

import org.netbeans.modules.j2ee.persistence.api.PersistenceScope;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopeImplementation;

/**
 *
 * @author Andrei Badea
 */
public abstract class PersistenceScopeAccessor {

    public static PersistenceScopeAccessor DEFAULT;

    static {
        Class c = PersistenceScope.class;
        try {
            Class.forName(c.getName(), true, c.getClassLoader());
        } catch (Exception ex) {
            // XXX should probably use ErrorManager, but this could
            // be called very early during the startup, when EM is not initialized yet?
            ex.printStackTrace();
        }
    }

    public abstract PersistenceScope createPersistenceScope(PersistenceScopeImplementation impl);
}
