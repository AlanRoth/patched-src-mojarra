/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.test.servlet30.annotationscanning;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author bhavanishankar@java.net
 */

public class JSFTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        FacesContextFactory facesContextFactory =
                (FacesContextFactory) FactoryFinder.getFactory(
                        FactoryFinder.FACES_CONTEXT_FACTORY);

        LifecycleFactory lifecycleFactory =
                (LifecycleFactory) FactoryFinder.getFactory(
                        FactoryFinder.LIFECYCLE_FACTORY);

        ServletContext context = getServletContext();
        FacesContext facesContext =
                facesContextFactory.getFacesContext(context,
                        request,
                        response,
                        lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE));

        Application application = facesContext.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(facesContext, null);
        facesContext.setViewRoot(viewRoot);

        PrintWriter pw = response.getWriter();
        pw.println("Created viewRoot " + viewRoot);
        pw.flush();
        pw.close();
    }
}
