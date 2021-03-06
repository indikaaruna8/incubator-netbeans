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

package org.netbeans.modules.profiler.snaptracer;

import org.netbeans.modules.profiler.snaptracer.impl.TracerSupportImpl;

/**
 * Entrypoint to the Tracer. Use this class to register and unregister
 * TracerPackageProvider implementations.
 *
 * @author Jiri Sedlacek
 */
public final class TracerSupport {

    private static TracerSupport INSTANCE;


    /**
     * Returns singleton instance of TracerSupport.
     *
     * @return singleton instance of TracerSupport
     */
    public static synchronized TracerSupport getInstance() {
        if (INSTANCE == null) INSTANCE = new TracerSupport();
        return INSTANCE;
    }


    /**
     * Registers TracerPackageProvider.
     *
     * @param provider TracerPackageProvider
     */
    public void registerPackageProvider(TracerPackageProvider provider) {
        TracerSupportImpl.getInstance().registerPackageProvider(provider);
    }

    /**
     * Unregisters TracerPackageProvider.
     *
     * @param povider TracerPackageProvider
     */
    public void unregisterPackageProvider(TracerPackageProvider povider) {
        TracerSupportImpl.getInstance().unregisterPackageProvider(povider);
    }


    private TracerSupport() {}
    
}
