/*******************************************************************************
 * Copyright 2015 Esri
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ******************************************************************************/
package com.esri.defensese.crowmap.java.view;

import com.esri.defensese.crowmap.common.model.UserMapPrompt;
import com.esri.client.local.ArcGISLocalTiledLayer;
import com.esri.core.geodatabase.Geodatabase;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.defensese.crowmap.common.controller.MapLoader;
import com.esri.defensese.crowmap.common.model.MapContents;
import com.esri.map.FeatureLayer;
import com.esri.map.Layer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class CrowMapJFrame extends javax.swing.JFrame {
    
    private static final int VERSION_CODE = 1;
    private static final String VERSION_NAME = "1.0.0";

    /**
     * Creates new form CrowMapJFrame
     */
    public CrowMapJFrame() {
        initComponents();
        
        setIconImage(new ImageIcon(getClass().getResource("/com/esri/defensese/crowmap/java/resources/crow-map.png")).getImage());
        
        MapLoader mapLoader = new MapLoader(new UserMapPrompt() {

            @Override
            public MapContents getMapContents() {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open ArcGIS Runtime Content Directory");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int fileChooserReturnState = fileChooser.showOpenDialog(CrowMapJFrame.this);
                if (JFileChooser.APPROVE_OPTION == fileChooserReturnState) {
                    File offlineMapDir = fileChooser.getSelectedFile();
                    return readDirectory(offlineMapDir);
                } else {
                    return null;
                }
            }

            @Override
            protected Object makeLocalTiledLayer(String path) {
                return new ArcGISLocalTiledLayer(path);
            }
            
            @Override
            protected List<Object> readLayers(String gdbPath) throws FileNotFoundException {
                ArrayList<Object> layers = new ArrayList<>();
                Geodatabase gdb = new Geodatabase(gdbPath);
                for (GeodatabaseFeatureTable table : gdb.getGeodatabaseTables()) {
                    FeatureLayer layer = new FeatureLayer(table);
                    layers.add(layer);
                }
                return layers;
            }
        
        });
        mapLoader.loadMap((Object layer) -> {
            if (layer instanceof Layer) {
                return map.getLayers().add((Layer) layer);
            } else {
                return false;
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

        map = new com.esri.map.JMap();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crow Map");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(map, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(map, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        map.dispose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CrowMapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrowMapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrowMapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrowMapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrowMapJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.esri.map.JMap map;
    // End of variables declaration//GEN-END:variables
}
