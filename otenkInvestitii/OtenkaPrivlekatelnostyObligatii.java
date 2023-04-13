package otenkInvestitii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Создаем окно интегрального коэффициента инвестиционной привлекательности таблицы obligatii

public class OtenkaPrivlekatelnostyObligatii extends JFrame {
    Obligatii obligatii = new Obligatii();
    DBConnect dbConnect = new DBConnect();
    Statement statement;
    PreparedStatement ps;


    public OtenkaPrivlekatelnostyObligatii() {
        // создаем окно с данными
        super("Оценка привлекательности в облигациях");
        super.setBounds(200, 100, 750, 350);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(new GridLayout(9, 5, 10, 10));

        try {
            statement =  dbConnect.getConnection().createStatement();

            ResultSet res = statement.executeQuery("SELECT * FROM myinvestytii.otenkaprivlekatelnostyobligatii ORDER BY KoefitientPrivlekatelnosty ");
            while (res.next()) {
                int i = res.getInt("id");
                String nameOrg = res.getString("NaimenovanieOrganizatii");
                double calculate = res.getDouble("KoefitientPrivlekatelnosty");
                JLabel id_otenka = new JLabel(i + "." + nameOrg + " - " + calculate + ".");
                container.add(id_otenka);
            }

            JLabel opisanie = new JLabel("Примечание: организация находящайся в первой строчки списка " +
                    "самая привлекательная для инвестиций.");

            JButton cancel_button = new JButton("Назад");
            JButton add_button = new JButton("Добавить данные в таблицу");

            container.add(cancel_button);
            container.add(add_button);
            container.add(opisanie);

            cancel_button.addActionListener(new ButtonEventCancel());
            add_button.addActionListener(new ButtonEventAdd());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class ButtonEventCancel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleGUIObligatii simpleGUIObligatii = new SimpleGUIObligatii();
            simpleGUIObligatii.setVisible(true);
            dispose();
        }
    }

    class ButtonEventAdd implements ActionListener {
//дабавляем данные в таблицу otenkaprivlekatelnostyobligatii
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                statement =  dbConnect.getConnection().createStatement();

                String sql = String.format("SELECT * FROM myinvestytii.obligatii");

                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int i = resultSet.getInt("id");
                    String nameOrg = resultSet.getString("NameOrganisatii");
                    double calculate = obligatii.Calculate(i);

                    String sql2 = String.format("INSERT INTO myinvestytii.otenkaprivlekatelnostyobligatii (id, NaimenovanieOrganizatii, KoefitientPrivlekatelnosty)" +
                            " VALUES (?,?,?)", i, nameOrg, calculate);
                    ps = dbConnect.getConnection().prepareStatement(sql2);
                    ps.setInt(1, i);
                    ps.setString(2, nameOrg);
                    ps.setDouble(3, calculate);
                    int row = ps.executeUpdate();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
