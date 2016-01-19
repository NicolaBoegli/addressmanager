package de.gkjava.addr.controller;

import de.gkjava.addr.persistence.model.Address;
import de.gkjava.addr.view.ViewFrame;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

@Component
public class Controller {
    private List<Address> model;
    private ViewFrame frame;

    @Resource
    private AddressController aController;

    private boolean update;
    private int selectedIndex = -1;

    private static final String BUNDLE = "de.gkjava.addr.bundle";
    private ResourceBundle bundle;

    @PostConstruct
    private void init() {
        this.model = aController.findAll();
        bundle = ResourceBundle.getBundle(BUNDLE);
    }

    public void setFrame(ViewFrame frame) {
        this.frame = frame;
    }

    // Laedt alle Adressen aus der Datenbank
    public void load() {
        try {
            Vector names = getNames();

            frame.getList().setListData(names);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(),
                    getText("message.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    // Bereitet die Erfassung einer neuen Adresse vor
    public void doNew() {
        frame.getList().clearSelection();
        frame.getEditPanel().clear();
        frame.getEditPanel().enable(true);
        update = false;
    }

    // Bereitet die Aenderung einer Adresse vor
    public void doEdit() {
        selectedIndex = frame.getList().getSelectedIndex();
        if (selectedIndex >= 0) {
            Address address = model.get(selectedIndex);
            frame.getEditPanel().setAddress(address);
            frame.getEditPanel().enable(true);
            update = true;
        }
    }

    // Loescht eine Adresse
    public void doDelete() {
        selectedIndex = frame.getList().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                int answer = JOptionPane
                        .showConfirmDialog(frame,
                                getText("confirm.delete.message"),
                                getText("confirm.delete.title"),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.NO_OPTION)
                    return;

                Long id = model.get(selectedIndex).getId();

                aController.delete(id);
                model.remove(selectedIndex);

                Vector names = getNames();

                frame.getList().setListData(names);

                // Selektiert den Eintrag oberhalb des gelöschten Eintrags
                int idx = Math.max(0, selectedIndex - 1);
                frame.getList().setSelectedIndex(idx);

                frame.getEditPanel().clear();
                frame.getEditPanel().enable(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(),
                        getText("message.error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Speichert eine neue bzw. geaenderte Adresse
    public void doOk() {
        Address address = frame.getEditPanel().getAddress();
        if (hasErrors(address)) {
            return;
        }

        if (update) {
            if (selectedIndex >= 0) {
                try {
                    aController.persist(address);

                    Vector names = getNames();

                    frame.getList().setListData(names);
                    // Selektiert den Listeneintrag zur geänderten Adresse
                    int idx = getNewId(address.getId());
                    frame.getList().setSelectedIndex(idx);
                } catch (Exception e) {
                    JOptionPane
                            .showMessageDialog(frame, e.getMessage(),
                                    getText("message.error"),
                                    JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            try {
                aController.persist(address);

                model.add(address);
                Vector names = getNames();

                frame.getList().setListData(names);
                // Selektiert den Listeneintrag zur neuen Adresse
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(),
                        getText("message.error"), JOptionPane.ERROR_MESSAGE);
            }
        }

        frame.getEditPanel().clear();
        frame.getEditPanel().enable(false);
    }

    public void doCancel() {
        frame.getEditPanel().clear();
        frame.getEditPanel().enable(false);
    }

    // Exportiert alle Adressen im CSV-Format
    public void doExport() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(getText("text.filename")));

        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory())
                    return true;
                if (file.getName().endsWith(".csv"))
                    return true;
                else
                    return false;
            }

            public String getDescription() {
                return "CSV (*.csv)";
            }

        });

        int opt = chooser.showSaveDialog(frame);
        if (opt != JFileChooser.APPROVE_OPTION)
            return;

        try {
            File file = chooser.getSelectedFile();
            if (file.exists()) {
                JOptionPane.showMessageDialog(frame, file,
                        getText("message.fileexists"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            export(file);
            JOptionPane.showMessageDialog(frame, file,
                    getText("message.filesaved"),
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(),
                    getText("message.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void export(File file) throws Exception {
        List<Address> data = aController.findAll();
        PrintWriter out = new PrintWriter(new FileWriter(file));
        String quote = "\"";
        String sep = ";";

        StringBuilder sb = new StringBuilder();
        sb.append(quote + getText("address.id") + quote + sep);
        sb.append(quote + getText("address.lastname") + quote + sep);
        sb.append(quote + getText("address.firstname") + quote + sep);
        sb.append(quote + getText("address.email") + quote + sep);
        sb.append(quote + getText("address.email_additional") + quote + sep);
        sb.append(quote + getText("address.homepage") + quote);
        out.println(sb);

        for (Address a : data) {
            sb = new StringBuilder();
            sb.append(quote + a.getId() + quote + sep);
            sb.append(quote + a.getLastname() + quote + sep);
            sb.append(quote + a.getFirstname() + quote + sep);
            sb.append(quote + a.getEmail() + quote + sep);
            sb.append(quote + a.getEmailAdditional() + quote + sep);
            sb.append(quote + a.getHomepage() + quote);
            out.println(sb);
        }

        out.close();
    }

    // Prueft einen Adresseintrag
    public boolean hasErrors(Address address) {
        if (address.getLastname().length() == 0) {
            JOptionPane.showMessageDialog(frame,
                    getText("message.lastname.invalid"),
                    getText("message.error"), JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    // Liefert den sprachabhaengigen Text
    public String getText(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    // Holt die Nachnamen + Vornamen umd gibt diese im Vektor zurück
    public Vector getNames() {
        model = aController.findAll();
        List names = new Vector();
        for (int i = 0; i < model.size();i++) {
            names.add(model.get(i).getLastname() + " " + model.get(i).getFirstname());
        }
        return (Vector)names;
    }

    public int getNewId(Long id) {
        for(int i = 0; i < model.size(); i++) {
            if (model.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

}
