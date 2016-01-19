package de.gkjava.addr.view;

import de.gkjava.addr.controller.Controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class ViewList {
	private Controller controller;
	private JList list;

	public ViewList(Controller controller) {
		this.controller = controller;
		build();
	}

	private void build() {
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					controller.doEdit();
				}
			}
		};
		list.addMouseListener(mouseListener);
	}

	public JList getList() {
		return list;
	}

	public void setListData(Vector<String> data) {
		list.setListData(data);
	}

	public void clearSelection() {
		list.clearSelection();
	}

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	public void setSelectedIndex(int idx) {
		list.setSelectedIndex(idx);
	}
}
