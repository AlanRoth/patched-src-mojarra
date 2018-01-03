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

package com.sun.faces.facelets.compiler;

import com.sun.faces.RIConstants;
import com.sun.faces.facelets.tag.TagLibrary;

import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagConfig;

/**
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
class TagUnit extends CompilationUnit implements TagConfig {

    private final TagLibrary library;

    private final String id;

    private final Tag tag;
    
    private final String namespace;
    
    private final String name;

    public TagUnit(TagLibrary library, String namespace, String name, Tag tag, String id) {
        this.library = library;
        this.tag = tag;
        this.namespace = namespace;
        this.name = name;
        this.id = id;
    }

    /*
     * special case if you have a ui:composition tag.  If and only if the
     * composition is on the same facelet page as the
     * composite:implementation, throw a FacesException with a helpful error
     * message.
     * */

    @Override
    protected void startNotify(CompilationManager manager) {
        if (this.name.equals("composition") && (this.namespace.equals(RIConstants.FACELET_NAMESPACE) || this.namespace.equals(RIConstants.FACELET_NAMESPACE))) {
            CompilerPackageCompilationMessageHolder messageHolder =
                    (CompilerPackageCompilationMessageHolder) manager.getCompilationMessageHolder();
            CompilationManager compositeComponentCompilationManager = messageHolder.
                getCurrentCompositeComponentCompilationManager();
            if (manager.equals(compositeComponentCompilationManager)) {
                // PENDING I18N
                String messageStr = 
                        "Because the definition of ui:composition causes any " +
                        "parent content to be ignored, it is invalid to use " +
                        "ui:composition directly inside of a composite component. " +
                        "Consider ui:decorate instead.";
                throw new FaceletException(messageStr);
            }
        }
    }

    @Override
    public FaceletHandler createFaceletHandler() {
        return this.library.createTagHandler(this.namespace, this.name, this);
    }

    @Override
    public FaceletHandler getNextHandler() {
        return this.getNextFaceletHandler();
    }

    @Override
    public Tag getTag() {
        return this.tag;
    }

    @Override
    public String getTagId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.tag.toString();
    }

}