package com.tagtraum.perf.gcviewer.view;


import com.tagtraum.perf.gcviewer.view.model.RecentGCResourcesModel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * AutoCompletionComboBox.
 *
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 */
public class AutoCompletionComboBox extends JComboBox {
    private AutoCompletionTextField autoCompletionTextField;

    public AutoCompletionComboBox() {
        super();
        autoCompletionTextField = new AutoCompletionTextField();
        autoCompletionTextField.setColumns(45);
        setModel(autoCompletionTextField.getComboBoxModel());
        setEditor(autoCompletionTextField);
        setEditable(true);
        addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) autoCompletionTextField.setText((String)e.getItem());
            }
        });
    }

    public void setRecentResourceNamesModel(RecentGCResourcesModel recentResourceNamesModel) {
        autoCompletionTextField.setRecentResourceNamesModel(recentResourceNamesModel);
    }
}
