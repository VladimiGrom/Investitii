package otenkInvestitii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/*
 создание графического интерфейса Акции
 */

public class SimpleGUIAktii extends JFrame {
    Aktii aktii = new Aktii();
    JTextField id_field, name_field, kLikvidnist_field, kAvtonomii_field,
            kOborchivaemost_field, kRentabelnost_field, kEBITDA_field,
            kChistieActivi_field, kPribilActii_field;


    public SimpleGUIAktii() {
        super("Оценки инвстиций в акциях");
        super.setBounds(200, 100, 750, 450);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(new GridLayout(11, 4, 10, 10));

        JLabel id = new JLabel("Введите порядковый номер (id): ");
        id_field = new JTextField("", 1);
        JLabel name = new JLabel("Введите наименование организации: ");
        name_field = new JTextField("", 1);
        JLabel kLikvidnist = new JLabel("Введите коэффициент текущей ликвидности: ");
        kLikvidnist_field = new JTextField("", 1);
        JLabel kAvtonomii = new JLabel("Введите коэффициент автономии: ");
        kAvtonomii_field = new JTextField("", 1);
        JLabel kOborchivaemost = new JLabel("Введите коэффициент оборачиваемости активов: ");
        kOborchivaemost_field = new JTextField("", 1);
        JLabel kRentabelnost = new JLabel("Введите коэффициент рентабельности активов: ");
        kRentabelnost_field = new JTextField("", 1);
        JLabel kEBITDA = new JLabel("Введите коэффициент результативности работы фирмы: ");
        kEBITDA_field = new JTextField("", 1);
        JLabel kChistieActivi = new JLabel("Введите стоимость чистых активов: ");
        kChistieActivi_field = new JTextField("", 1);
        JLabel kPribilActii = new JLabel("Введите прибыль на акцию: ");
        kPribilActii_field = new JTextField("", 1);

        container.add(id);
        container.add(id_field);
        container.add(name);
        container.add(name_field);
        container.add(kLikvidnist);
        container.add(kLikvidnist_field);
        container.add(kAvtonomii);
        container.add(kAvtonomii_field);
        container.add(kOborchivaemost);
        container.add(kOborchivaemost_field);
        container.add(kRentabelnost);
        container.add(kRentabelnost_field);
        container.add(kEBITDA);
        container.add(kEBITDA_field);
        container.add(kChistieActivi);
        container.add(kChistieActivi_field);
        container.add(kPribilActii);
        container.add(kPribilActii_field);


        JButton add_button = new JButton("Добавить");
        JButton delete_button = new JButton("Удалить по id");
        JButton calculate_button = new JButton("Оценка привлекательности");
        JButton cancel_button = new JButton("Назад");


        container.add(add_button);
        container.add(delete_button);
        container.add(cancel_button);
        container.add(calculate_button);

        add_button.addActionListener(new ButtonEventAdd());
        delete_button.addActionListener(new ButtonEventDelete());
        cancel_button.addActionListener(new ButtonEventCancel());
        calculate_button.addActionListener(new ButtonEventCalculate());
    }

    class ButtonEventDelete implements ActionListener {
        //удалние данных из таблицы aktii
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(id_field.getText());
            try {
                aktii.deleteAktii(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class ButtonEventCancel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleGui from = new SimpleGui();
            from.setVisible(true);
            dispose();
        }
    }

    class ButtonEventAdd implements ActionListener {
        //Выводим окно с добавленными данными в таблицу aktii
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(id_field.getText());
            String name = name_field.getText();
            double kLikvidnist = Double.parseDouble(kLikvidnist_field.getText());
            double kAvtonomii = Double.parseDouble(kAvtonomii_field.getText());
            double kOborchivaemost = Double.parseDouble(kOborchivaemost_field.getText());
            double kRentabelnost = Double.parseDouble(kRentabelnost_field.getText());
            double kEBITDA = Double.parseDouble(kEBITDA_field.getText());
            double kChistieActivi = Double.parseDouble(kChistieActivi_field.getText());
            double kPribilActii = Double.parseDouble(kPribilActii_field.getText());
            JOptionPane.showMessageDialog(null,
                    "Вы ввели порядковый номер (id): " + id +
                            "\nВы ввели наименование организации: " + name +
                            "\nВы ввели коэффициент текущей ликвидности: " + kLikvidnist +
                            "\nВы ввели коэффициент автономии: " + kAvtonomii +
                            "\nВы ввели коэффициент оборачиваемости активов: " + kOborchivaemost +
                            "\nВы ввели коэффициент рентабельности активов: " + kRentabelnost +
                            "\nВы ввели коэффициент результативности работы фирмы: " + kEBITDA +
                            "\nВы ввели стоимость чистых активов: " + kChistieActivi +
                            "\nВы ввели прибыль на акцию: " + kPribilActii,
                    "Данные добавлены в таблицу Акции",
                    JOptionPane.PLAIN_MESSAGE);

            try {
                aktii.addAktii(id, name, kLikvidnist, kAvtonomii, kOborchivaemost,
                        kRentabelnost, kEBITDA, kChistieActivi, kPribilActii);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Вы ввели данные не корректно!",
                        "Ошибка",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class ButtonEventCalculate implements ActionListener {
        // расчет интегрального коэффициента инвестиционной привлекательности в таблице aktii
        @Override
        public void actionPerformed(ActionEvent e) {
            OtenkaPrivlekatelnostyAktii otenka = new OtenkaPrivlekatelnostyAktii();
            otenka.setVisible(true);
            dispose();
        }
    }
}
