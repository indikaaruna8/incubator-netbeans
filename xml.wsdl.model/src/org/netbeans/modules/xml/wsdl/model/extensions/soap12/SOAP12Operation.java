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

package org.netbeans.modules.xml.wsdl.model.extensions.soap12;

/**
 *
 * @author Sujit Biswas
 * Represents the operation element under wsdl:operation for SOAP binding
 */
public interface SOAP12Operation extends SOAP12Component {
    public static final String SOAP_ACTION_PROPERTY = "soapAction";
    public static final String STYLE_PROPERTY = "style";
    public static final String SOAP_ACTION_REQUIRED_PROPERTY = "soapActionRequired";
    
    SOAP12Binding.Style getStyle();
    void setStyle(SOAP12Binding.Style v);
    
    void setSoapAction(String soapActionURI) ;
    String getSoapAction();
    
    void setSoapActionRequired(String soapActionRequired) ;
    String getSoapActionRequired();
}
