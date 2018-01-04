package org.ohespaco.presentacion;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JTree;

public class JPanelTareas extends JPanel {

	/**
	 * Create the panel.
	 */
	public JPanelTareas() {
		setLayout(new BorderLayout(0, 0));
		
		JTree tree = new JTree();
		add(tree, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

	}

}
