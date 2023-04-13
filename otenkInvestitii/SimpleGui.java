package otenkInvestitii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Главный графический интрфейс
 */
public class SimpleGui extends JFrame {
    public SimpleGui() {
        super("Оценки инвстиций");
        super.setBounds(200, 100, 500, 150);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel info = new JLabel("Выбрать вид финансового инструмента: ");

        JButton obligatii_button = new JButton("Облигации");
        JButton aktii_button = new JButton("Акции");

        container.add(info);
        container.add(obligatii_button);
        container.add(aktii_button);

        obligatii_button.addActionListener(new ButtonEventObligatii());
        aktii_button.addActionListener(new ButtonEventAktii());


    }

    class ButtonEventObligatii implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleGUIObligatii from_obligatii = new SimpleGUIObligatii();
            from_obligatii.setVisible(true);
            dispose();
        }
    }

    class ButtonEventAktii implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleGUIAktii from_aktii = new SimpleGUIAktii();
            from_aktii.setVisible(true);
            dispose();
        }
    }
}
