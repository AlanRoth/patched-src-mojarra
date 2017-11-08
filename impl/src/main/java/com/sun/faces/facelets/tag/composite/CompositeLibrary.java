/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

/**
 * @author Jacob Hookom
 * @version $Id$
 */
public final class CompositeLibrary extends AbstractTagLibrary {

    public final static String Namespace = "http://java.sun.com/jsf/composite";
    public final static String XMLNSNamespace = "http://xmlns.jcp.org/jsf/composite";

    public final static CompositeLibrary Instance = new CompositeLibrary();

    public CompositeLibrary() {
        this(Namespace);
    }
    
    public CompositeLibrary(String namespace) {
        super(namespace);

        // The interface section
        this.addTagHandler("interface", InterfaceHandler.class);
        
        // Things that go insead of the interface section
        this.addTagHandler("attribute", AttributeHandler.class);
        this.addTagHandler("extension", ExtensionHandler.class);
        this.addTagHandler("editableValueHolder", EditableValueHolderAttachedObjectTargetHandler.class);
        this.addTagHandler("actionSource", ActionSource2AttachedObjectTargetHandler.class);
        this.addTagHandler("valueHolder", ValueHolderAttachedObjectTargetHandler.class);
        this.addTagHandler("clientBehavior", BehaviorHolderAttachedObjectTargetHandler.class);
        this.addTagHandler("facet", DeclareFacetHandler.class);
        
        // The implementation section
        this.addTagHandler("implementation", ImplementationHandler.class);
        
        // Things that go inside of the implementation section
        this.addTagHandler("insertChildren", InsertChildrenHandler.class);
        this.addTagHandler("insertFacet", InsertFacetHandler.class);
        this.addComponent("renderFacet", "javax.faces.Output",
                "javax.faces.CompositeFacet", RenderFacetHandler.class);
    }
}
