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

package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.el.LegacyMethodBinding;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.view.facelets.*;

/**
 * 
 * @author Jacob Hookom
 */
public final class EditableValueHolderRule extends MetaRule {

    final static class LiteralValidatorMetadata extends Metadata {

        private final String validatorId;

        public LiteralValidatorMetadata(String validatorId) {
            this.validatorId = validatorId;
        }

        @Override
        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((EditableValueHolder) instance).addValidator(ctx.getFacesContext()
                    .getApplication().createValidator(this.validatorId));
        }
    }

    final static class ValueChangedExpressionMetadata extends Metadata {
        private final TagAttribute attr;

        public ValueChangedExpressionMetadata(TagAttribute attr) {
            this.attr = attr;
        }

        @Override
        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((EditableValueHolder) instance)
                    .addValueChangeListener(new MethodExpressionValueChangeListener(
                            this.attr.getMethodExpression(ctx, null,
                                    VALUECHANGE_SIG)));
        }
    }

    final static class ValueChangedBindingMetadata extends Metadata {
        private final TagAttribute attr;

        public ValueChangedBindingMetadata(TagAttribute attr) {
            this.attr = attr;
        }

        @Override
        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((EditableValueHolder) instance)
                    .setValueChangeListener(new LegacyMethodBinding(this.attr
                            .getMethodExpression(ctx, null, VALUECHANGE_SIG)));
        }
    }

    final static class ValidatorExpressionMetadata extends Metadata {
        private final TagAttribute attr;

        public ValidatorExpressionMetadata(TagAttribute attr) {
            this.attr = attr;
        }

        @Override
        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((EditableValueHolder) instance)
                    .addValidator(new MethodExpressionValidator(this.attr
                            .getMethodExpression(ctx, null, VALIDATOR_SIG)));
        }
    }

    final static class ValidatorBindingMetadata extends Metadata {
        private final TagAttribute attr;

        public ValidatorBindingMetadata(TagAttribute attr) {
            this.attr = attr;
        }

        @Override
        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((EditableValueHolder) instance)
                    .setValidator(new LegacyMethodBinding(this.attr
                            .getMethodExpression(ctx, null, VALIDATOR_SIG)));
        }
    }

    private final static Class[] VALIDATOR_SIG = new Class[] {
            FacesContext.class, UIComponent.class, Object.class };

    private final static Class[] VALUECHANGE_SIG = new Class[] { ValueChangeEvent.class };

    public final static EditableValueHolderRule Instance = new EditableValueHolderRule();

    @Override
    public Metadata applyRule(String name, TagAttribute attribute,
            MetadataTarget meta) {

        if (meta.isTargetInstanceOf(EditableValueHolder.class)) {

            if ("validator".equals(name)) {
                if (attribute.isLiteral()) {
                    return new LiteralValidatorMetadata(attribute.getValue());
                } else {
                    return new ValidatorExpressionMetadata(attribute);
                }
            }

            if ("valueChangeListener".equals(name)) {
                return new ValueChangedExpressionMetadata(attribute);
            }

        }
        return null;
    }

}
