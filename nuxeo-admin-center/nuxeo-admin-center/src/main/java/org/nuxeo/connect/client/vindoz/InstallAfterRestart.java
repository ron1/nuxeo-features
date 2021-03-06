/*
 * (C) Copyright 2010 Nuxeo SAS (http://nuxeo.com/) and contributors.
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
 *     Nuxeo - initial API and implementation
 */
package org.nuxeo.connect.client.vindoz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.connect.update.Package;
import org.nuxeo.connect.update.PackageType;
import org.nuxeo.runtime.api.Framework;

/**
 * Helper class used to manage packages installation issue under windows systems.
 * <p>
 * Because the Vindoz OS locks all the jar files loaded by the JVM, we can not
 * do proper installation. So installation is delayed until next restart where
 * installation is done before nuxeo starts (and loads the jars).
 *
 * @author Tiry (tdelprat@nuxeo.com)
 */
public class InstallAfterRestart {

    public static final String FILE_NAME = "installAfterRestart.log";

    public static final String FAKE_VIDOZ = "org.nuxeo.fake.vindoz";

    protected static final List<String> pkgIds = new ArrayList<String>();

    protected static final List<String> uninstallpkgIds = new ArrayList<String>();

    protected static final Log log = LogFactory.getLog(InstallAfterRestart.class);

    protected static boolean isNeededByOs() {
        if ("true".equals(Framework.getProperty(FAKE_VIDOZ, "false"))) {
            return true;
        }
        return isVindozBox();
    }

    public static boolean isNeededForPackage(Package pkg) {
        return (PackageType.STUDIO != pkg.getType() && isNeededByOs()) || (PackageType.HOT_FIX == pkg.getType()) || (PackageType.ADDON == pkg.getType() && !pkg.supportsHotReload());
    }

    protected static boolean isVindozBox() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }

    public static void addPackageForInstallation(String pkgId) {
        if (!pkgIds.contains(pkgId)) {
            pkgIds.add(pkgId);
            savePkgList();
        }
    }

    public static void addPackageForUnInstallation(String pkgId) {
        if (!pkgIds.contains(pkgId) && !(uninstallpkgIds.contains(pkgId))) {
            pkgIds.add(pkgId);
            uninstallpkgIds.add(pkgId);
            savePkgList();
        }
    }

    public static boolean isMarkedForInstallAfterRestart(String pkgId) {
        return pkgIds.contains(pkgId);
    }

    protected static void savePkgList() {
        String path = Framework.getProperty("nuxeo.data.dir");
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        File installFile = new File(path + FILE_NAME);
        List<String> cmds = new ArrayList<String>();
        for (String pkgId : pkgIds) {
            String cmd = pkgId;
            if (uninstallpkgIds.contains(pkgId)) {
                cmd = "uninstall " + pkgId;
            }
            cmds.add(cmd);
        }

        try {
            FileUtils.writeLines(installFile, cmds);
        } catch (IOException e) {
            log.error(
                    "Unable to same listing of packages to install on restart",
                    e);
        }
    }

}
