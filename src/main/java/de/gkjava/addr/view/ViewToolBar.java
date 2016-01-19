package de.gkjava.addr.view;

import de.gkjava.addr.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ViewToolBar {
	private Controller controller;
	private JToolBar toolBar;
	private JButton newButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton exportButton;

	public ViewToolBar(Controller controller) {
		this.controller = controller;
		build();
	}

	private void build() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		URL urlNew = ClassLoader.getSystemResource("de/gkjava/addr/images/new.png");
		ImageIcon iconNew = new ImageIcon(urlNew);
		newButton = new JButton(iconNew);
		newButton.setToolTipText(controller.getText("button.new"));
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.doNew();
			}
		});

		URL urlEdit = ClassLoader.getSystemResource("de/gkjava/addr/images/edit.png");
		ImageIcon iconEdit = new ImageIcon(urlEdit);
		editButton = new JButton(iconEdit);
		editButton.setToolTipText(controller.getText("button.edit"));
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.doEdit();
			}
		});

		URL urlDelete = ClassLoader.getSystemResource("de/gkjava/addr/images/delete.png");
		ImageIcon iconDelete = new ImageIcon(urlDelete);
		deleteButton = new JButton(iconDelete);
		deleteButton.setToolTipText(controller.getText("button.delete"));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.doDelete();
			}
		});

		URL urlExport = ClassLoader.getSystemResource("de/gkjava/addr/images/export.png");
		ImageIcon iconExport = new ImageIcon(urlExport);
		exportButton = new JButton(iconExport);
		exportButton.setToolTipText(controller.getText("button.export"));
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.doExport();
			}
		});

		toolBar.add(newButton);
		toolBar.add(editButton);
		toolBar.add(deleteButton);
		toolBar.add(exportButton);
	}

	public JToolBar getToolBar() {
		return toolBar;
	}
}
