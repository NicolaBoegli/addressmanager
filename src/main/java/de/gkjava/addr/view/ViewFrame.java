package de.gkjava.addr.view;

import de.gkjava.addr.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
	private Controller controller;
	private ViewToolBar toolBar;
	private ViewList list;
	private ViewEditPanel editPanel;
	private JSplitPane splitPane;

	public ViewFrame(Controller controller) {
		this.controller = controller;
		build();
	}

	private void build() {
		setTitle(controller.getText("frame.title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();

		toolBar = new ViewToolBar(controller);
		container.add(toolBar.getToolBar(), BorderLayout.NORTH);

		list = new ViewList(controller);
		editPanel = new ViewEditPanel(controller);
		editPanel.enable(false);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				new JScrollPane(list.getList()), editPanel.getPanel());
		container.add(splitPane, BorderLayout.CENTER);
	}

	public ViewList getList() {
		return list;
	}

	public ViewEditPanel getEditPanel() {
		return editPanel;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}
}
