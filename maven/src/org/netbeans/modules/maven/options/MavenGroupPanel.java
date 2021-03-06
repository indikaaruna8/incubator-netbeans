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

package org.netbeans.modules.maven.options;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.ui.ProjectGroup;
import org.netbeans.modules.maven.embedder.EmbedderFactory;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.filesystems.FileUtil;
import static org.netbeans.modules.maven.options.Bundle.*;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author mkleint
 */
public class MavenGroupPanel extends javax.swing.JPanel {
    private final DefaultComboBoxModel mavenHomeDataModel = new DefaultComboBoxModel();
    private String mavenRuntimeHome = null;
    private static final String SEPARATOR = "SEPARATOR";
    private final ProjectCustomizer.Category category;
    private final List<String>       userDefinedMavenRuntimes = new ArrayList<String>();
    private final List<String>       predefinedRuntimes = new ArrayList<String>();
    private final ActionListener listItemChangedListener;
    private int                lastSelected = -1;
    private String globalMavenValue;

    MavenGroupPanel(ProjectCustomizer.Category category, final ProjectGroup grp) {
        initComponents();
        this.category = category;
        comMavenHome.setModel(mavenHomeDataModel);
        comMavenHome.setRenderer(new ComboBoxRenderer());
        File f = EmbedderFactory.getMavenHome();
        if (f.equals(EmbedderFactory.getDefaultMavenHome())) {
            globalMavenValue = MAVEN_RUNTIME_Bundled();
        } else {
            globalMavenValue = MAVEN_RUNTIME_External(f.getAbsolutePath());
        }
        listItemChangedListener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SEPARATOR.equals(comMavenHome.getSelectedItem())) {
                    comMavenHome.setSelectedIndex(lastSelected);
                    return;
                }
                
                int selected = comMavenHome.getSelectedIndex();
                if (selected == mavenHomeDataModel.getSize() - 1) {
                    // browse
                    comMavenHome.setSelectedIndex(lastSelected);
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            browseAddNewRuntime();
                        }
                        
                    });
                    return;
                }
                
                listDataChanged();
                lastSelected = selected;
            }
        };
        loadValues(grp);
        category.setStoreListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               applyValues(grp);
            }
        });
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblExternalVersion = new javax.swing.JLabel();
        lblCommandLine = new javax.swing.JLabel();
        comMavenHome = new javax.swing.JComboBox();

        org.openide.awt.Mnemonics.setLocalizedText(lblCommandLine, org.openide.util.NbBundle.getMessage(MavenGroupPanel.class, "MavenGroupPanel.lblCommandLine.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblCommandLine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExternalVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comMavenHome, 0, 325, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCommandLine)
                    .addComponent(comMavenHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExternalVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 147, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comMavenHome;
    private javax.swing.JLabel lblCommandLine;
    private javax.swing.JLabel lblExternalVersion;
    // End of variables declaration//GEN-END:variables


    private class ComboBoxRenderer extends DefaultListCellRenderer {

        private final JSeparator separator;

        public ComboBoxRenderer() {
            super();
            separator = new JSeparator(JSeparator.HORIZONTAL);
        }

        @Override
        @NbBundle.Messages({
            "# {0} - global maven selection",
            "LBL_Global_selection={0} [Global selection]"})
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (SEPARATOR.equals(value)) {
                return separator;
            }
            if (globalMavenValue.equals(value)) {
                value = LBL_Global_selection(value);
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    };

    private String getSelectedRuntime(int selected) {
        if (selected < 0) {
            return null;
        }
        
        if (selected < predefinedRuntimes.size()) {
            return predefinedRuntimes.get(selected);

        } else if (!userDefinedMavenRuntimes.isEmpty() &&
                selected - predefinedRuntimes.size() <= userDefinedMavenRuntimes.size()) {
            return userDefinedMavenRuntimes.get(selected - 1 - predefinedRuntimes.size());
        }
        
        return null;
    }
    
     private void browseAddNewRuntime() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "TIT_Select2"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setFileHidingEnabled(false);
        int selected = comMavenHome.getSelectedIndex();
        String path = getSelectedRuntime(selected);
        if (path == null || path.trim().length() == 0) {
            path = new File(System.getProperty("user.home")).getAbsolutePath(); //NOI18N
        }
        if (path.length() > 0) {
            File f = new File(path);
            if (f.exists()) {
                chooser.setSelectedFile(f);
            }
        }
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            File projectDir = chooser.getSelectedFile();
            String newRuntimePath = FileUtil.normalizeFile(projectDir).getAbsolutePath();
            boolean existed = false;
            List<String> runtimes = new ArrayList<String>();
            runtimes.addAll(predefinedRuntimes);
            runtimes.addAll(userDefinedMavenRuntimes);
            for (String runtime : runtimes) {
                if (runtime.equals(newRuntimePath)) {
                    existed = true;
                }
            }
            if (!existed) {
                // do not add duplicated directory
                if (userDefinedMavenRuntimes.isEmpty()) {
                    mavenHomeDataModel.insertElementAt(SEPARATOR, predefinedRuntimes.size());
                }
                userDefinedMavenRuntimes.add(newRuntimePath);
                mavenHomeDataModel.insertElementAt(newRuntimePath, runtimes.size() + 1);
            }
            comMavenHome.setSelectedItem(newRuntimePath);
        }
    }
     

    private void loadValues(final ProjectGroup grp) {
        RequestProcessor.getDefault().post(new Runnable() {
            @Override
            public void run() {
                predefinedRuntimes.clear();
                predefinedRuntimes.add("");
                String defaultExternalMavenRuntime = MavenSettings.getDefaultExternalMavenRuntime();
                if (defaultExternalMavenRuntime != null) {
                    predefinedRuntimes.add(defaultExternalMavenRuntime);
                }
                userDefinedMavenRuntimes.clear();
                userDefinedMavenRuntimes.addAll(MavenSettings.getDefault().getUserDefinedMavenRuntimes());
                final List<String> toAdd = new ArrayList<String>();
                final File command = EmbedderFactory.getEffectiveMavenHome(grp);
                for (String runtime : predefinedRuntimes) {
                    boolean bundledRuntime = runtime.isEmpty();
                    String desc = bundledRuntime ? MAVEN_RUNTIME_Bundled()
                            : MAVEN_RUNTIME_External(runtime);
                    toAdd.add(desc);
                }

                if (!userDefinedMavenRuntimes.isEmpty()) {
                    toAdd.add(SEPARATOR);
                    for (String runtime : userDefinedMavenRuntimes) {
                        String desc = MAVEN_RUNTIME_External(runtime); // NOI18N
                        toAdd.add(desc);
                    }
                }

                toAdd.add(SEPARATOR);
                toAdd.add(MAVEN_RUNTIME_Browse());
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        comMavenHome.removeActionListener(listItemChangedListener);
                        mavenHomeDataModel.removeAllElements();
                        for (String s : toAdd) {
                            mavenHomeDataModel.addElement(s);
                        }
                        comMavenHome.setSelectedItem(command.getAbsolutePath()); //NOI18N
                        listDataChanged();
                        lastSelected = comMavenHome.getSelectedIndex();
                        comMavenHome.addActionListener(listItemChangedListener);
                    }
                });
            }
        });
        
        
    }
    
    public void applyValues(ProjectGroup grp) {
        // remember only user-defined runtimes of RUNTIME_COUNT_LIMIT count at the most
        List<String> runtimes = new ArrayList<String>();
        for (int i = 0; i < userDefinedMavenRuntimes.size() && i < SettingsPanel.RUNTIME_COUNT_LIMIT; ++i) {
            runtimes.add(0, userDefinedMavenRuntimes.get(userDefinedMavenRuntimes.size() - 1 - i));
        }
        int selected = comMavenHome.getSelectedIndex() - predefinedRuntimes.size() - 1;
        if (selected >= 0 && runtimes.size() == SettingsPanel.RUNTIME_COUNT_LIMIT &&
                userDefinedMavenRuntimes.size() - SettingsPanel.RUNTIME_COUNT_LIMIT > selected) {
            runtimes.set(0, userDefinedMavenRuntimes.get(selected));
        }
        if (predefinedRuntimes.size() > 1) {
            runtimes.add(0, predefinedRuntimes.get(1));
        }
        MavenSettings.getDefault().setMavenRuntimes(runtimes);
        String cl = mavenRuntimeHome;
        //MEVENIDE-553
        File command = (cl == null || cl.isEmpty()) ? null : new File(cl);
        if (command != null && command.isDirectory()) {
            EmbedderFactory.setGroupedMavenHome(grp, command);
        } else {
            EmbedderFactory.setGroupedMavenHome(grp, null);
        }
    }
    
     private void listDataChanged() {

        int selected = comMavenHome.getSelectedIndex();
        String path = getSelectedRuntime(selected);
        if (path != null) {
            path = path.trim();
            if ("".equals(path)) {
                path = null;
                category.setValid(true);
                lblExternalVersion.setText(NbBundle.getMessage(SettingsPanel.class, "LBL_ExMavenVersion2", SettingsPanel.BUNDLED_RUNTIME_VERSION));
            }
        }

        if (path != null) {
            path = path.trim();
            File fil = new File(path);
            String ver = null;
            if (fil.exists() && new File(fil, "bin" + File.separator + "mvn").exists()) { //NOI18N
                ver = MavenSettings.getCommandLineMavenVersion(new File(path));
            }

            if (ver != null) {
                lblExternalVersion.setText(NbBundle.getMessage(SettingsPanel.class, "LBL_ExMavenVersion2", ver));
                category.setValid(true);

            } else {
                lblExternalVersion.setText(NbBundle.getMessage(SettingsPanel.class, "ERR_NoValidInstallation"));
                category.setValid(false);
            }
        }

        mavenRuntimeHome = path;
    }
}
