/*
 * (C) Copyright 2006-2009 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Leroy Merlin (http://www.leroymerlin.fr/) - initial implementation
 */

package org.nuxeo.opensocial.theme.fragment;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.webengine.WebEngine;
import org.nuxeo.ecm.webengine.model.WebContext;
import org.nuxeo.opensocial.theme.model.LayoutManagerModel;
import org.nuxeo.theme.fragments.AbstractFragment;
import org.nuxeo.theme.models.Model;
import org.nuxeo.theme.models.ModelException;


public class LayoutManagerFragment extends AbstractFragment{

  @Override
  public Model getModel() throws ModelException {
    if (WebEngine.getActiveContext() != null) {
      WebContext ctx = WebEngine.getActiveContext();
      CoreSession session = ctx.getCoreSession();

      return getModel(session);

    }
    return null;
  }


  public Model getModel(CoreSession session) throws ModelException {

    LayoutManagerModel model = new LayoutManagerModel();

    NuxeoPrincipal principal = (NuxeoPrincipal) session.getPrincipal();
    model.setAnonymous(principal.isAnonymous());

    return model;
  }

}