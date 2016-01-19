package de.gkjava.addr.view;

import de.gkjava.addr.controller.Controller;
import de.gkjava.addr.persistence.model.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewEditPanel {
	private Controller controller;
	private int id; // Schluessel der Adresse

	private JPanel panel;
	private JTextField lastnameField;
	private JTextField firstnameField;
	private JTextField emailField;
	private JTextField emailAdditionalField;
	private JTextField homepageField;
	private JButton saveButton;
	private JButton cancelButton;

	public ViewEditPanel(Controller controller) {
		this.controller = controller;
		build();
	}

	private void build() {
		JPanel labelPanel = new JPanel(new GridLayout(5, 1, 0, 10));
		labelPanel.add(new JLabel(controller.getText("address.lastname"),
				JLabel.RIGHT));
		labelPanel.add(new JLabel(controller.getText("address.firstname"),
				JLabel.RIGHT));
		labelPanel.add(new JLabel(controller.getText("address.email"),
				JLabel.RIGHT));
		labelPanel.add(new JLabel(controller
				.getText("address.email_additional"), JLabel.RIGHT));
		labelPanel.add(new JLabel(controller.getText("address.homepage"),
				JLabel.RIGHT));

		JPanel fieldPanel = new JPanel(new GridLayout(5, 1, 0, 10));
		lastnameField = new JTextField();
		firstnameField = new JTextField();
		emailField = new JTextField();
		emailAdditionalField = new JTextField();
		homepageField = new JTextField();
		fieldPanel.add(lastnameField);
		fieldPanel.add(firstnameField);
		fieldPanel.add(emailField);
		fieldPanel.add(emailAdditionalField);
		fieldPanel.add(homepageField);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		saveButton = new JButton(controller.getText("button.save"));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.doOk();
			}
		});
		cancelButton = new JButton(controller.getText("button.cancel"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.doCancel();
			}
		});
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		JPanel centerPanel = new JPanel(new BorderLayout(10, 0));
		centerPanel.add(labelPanel, BorderLayout.WEST);
		centerPanel.add(fieldPanel, BorderLayout.CENTER);

		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
		panel1.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
		panel1.add(centerPanel, BorderLayout.CENTER);
		panel1.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.add(buttonPanel, BorderLayout.CENTER);
		panel2.add(Box.createHorizontalStrut(20), BorderLayout.EAST);
		panel2.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);

		panel = new JPanel(new BorderLayout());
		panel.add(panel1, BorderLayout.NORTH);
		panel.add(panel2, BorderLayout.SOUTH);
	}

	public JPanel getPanel() {
		return panel;
	}

	// Uebertragung Adress-Objekt --> Formularfelder
	public void setAddress(Address address) {
		id = Math.toIntExact(address.getId());
		lastnameField.setText(address.getLastname());
		firstnameField.setText(address.getFirstname());
		emailField.setText(address.getEmail());
		emailAdditionalField.setText(address.getEmailAdditional());
		homepageField.setText(address.getHomepage());
	}

	// Uebertragung Formularfelder --> Adress-Objekt
	public Address getAddress() {
		Address address = new Address();
		address.setId(new Long(id));
		address.setLastname(lastnameField.getText().trim());
		address.setFirstname(firstnameField.getText().trim());
		address.setEmail(emailField.getText().trim());
		address.setEmailAdditional(emailAdditionalField.getText().trim());
		address.setHomepage(homepageField.getText().trim());
		return address;
	}

	public void clear() {
		lastnameField.setText("");
		firstnameField.setText("");
		emailField.setText("");
		emailAdditionalField.setText("");
		homepageField.setText("");
	}

	public void enable(boolean b) {
		lastnameField.setEnabled(b);
		firstnameField.setEnabled(b);
		emailField.setEnabled(b);
		emailAdditionalField.setEnabled(b);
		homepageField.setEnabled(b);
		saveButton.setEnabled(b);
		cancelButton.setEnabled(b);
	}
}
