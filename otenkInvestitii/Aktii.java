package otenkInvestitii;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Aktii {
    DBConnect dbConnect = new DBConnect();
    Statement statement;
    public Aktii() {
    }

    public void addAktii(int id, String name, double kLikvidnist,
                             double kAvtonomii, double kOborchivaemost,
                             double kRentabelnost, double kEBITDA,
                             double kChistieActivi, double kPribilActii) throws SQLException {
        //метод добавление данных
        String sql = String.format("INSERT INTO  myinvestytii.aktii " +
                "(id, NameOrganisatii, Likvidnist, Avtonomii, Oborchivaemost, Rentabelnost, EBITDA) VALUES (?,?,?,?,?,?,?,?,?)",
                id, name, kLikvidnist, kAvtonomii, kOborchivaemost, kRentabelnost, kEBITDA, kChistieActivi, kPribilActii);

        PreparedStatement ps = dbConnect.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setDouble(3, kLikvidnist);
        ps.setDouble(4, kAvtonomii);
        ps.setDouble(5, kOborchivaemost);
        ps.setDouble(6, kRentabelnost);
        ps.setDouble(7, kEBITDA);
        ps.setDouble(8, kChistieActivi);
        ps.setDouble(9, kPribilActii);

        int row = ps.executeUpdate();
        System.out.println("Данные добавлены");
    }

    public void deleteAktii(int id) throws SQLException {
//удалние данных из таблицы aktii
        Statement statement = dbConnect.getConnection().createStatement();
        String sql = String.format("DELETE FROM myinvestytii.aktii WHERE id=%d",id);
        statement.executeUpdate(sql);
        System.out.println("Данные удалены из таблицы Акции!");
    }

    public double Calculate(int id) {
        // расчет интегрального коэффициента инвестиционной привлекательности таблицы aktii
        double kCalculate = 0;
        double kLikvidnist = 0,
                kAvtonomii = 0,
                kOborchivaemost = 0,
                kRentabelnost = 0,
                kEBITDA = 0,
                kChistieActivi = 0,
                kPribilActii = 0;

        double etalonLikvidnist = etalonKoef("Likvidnist");
        double etalonAvtonomii = etalonKoef("Avtonomii");
        double etalonOborchivaemost = etalonKoef("Oborchivaemost");
        double etalonRentabelnost = etalonKoef("Rentabelnost");
        double etalonEBITDA = etalonKoef("EBITDA");
        double etalonChistieActivi = etalonKoef("ChistieActivi");
        double etalonPribilActii = etalonKoef("PribilActii");

        try {
            statement =  dbConnect.getConnection().createStatement();
            String sql = String.format("SELECT * FROM myinvestytii.aktii WHERE id=%d",id);

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                kLikvidnist = resultSet.getDouble("Likvidnist");
                kAvtonomii = resultSet.getDouble("Avtonomii");
                kOborchivaemost = resultSet.getDouble("Oborchivaemost");
                kRentabelnost = resultSet.getDouble("Rentabelnost");
                kEBITDA = resultSet.getDouble("EBITDA");
                kChistieActivi = resultSet.getDouble("ChistieActivi");
                kPribilActii = resultSet.getDouble("PribilActii");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        kCalculate = Math.sqrt(((1-kLikvidnist / etalonLikvidnist)*(1-kLikvidnist / etalonLikvidnist)) +
                ((1 - kAvtonomii / etalonAvtonomii) * (1 - kAvtonomii / etalonAvtonomii)) +
                ((1 - kOborchivaemost / etalonOborchivaemost) * (1 - kOborchivaemost / etalonOborchivaemost)) +
                ((1 - kRentabelnost / etalonRentabelnost) * (1 - kRentabelnost / etalonRentabelnost)) +
                ((1 - kEBITDA / etalonEBITDA) * (1 - kEBITDA / etalonEBITDA)) +
                ((1 - kChistieActivi / etalonChistieActivi) * (1 - kChistieActivi / etalonChistieActivi)) +
                ((1 - kPribilActii / etalonPribilActii) * (1 - kPribilActii / etalonPribilActii)));
        return kCalculate;
    }

    public double etalonKoef(String nameKolonki) {
        // поиск эталонного значение в таблицы aktii
        double maxResultSet = 0;
        try {
            statement =  dbConnect.getConnection().createStatement();
            String sql = String.format("SELECT * FROM myinvestytii.aktii ORDER BY %s DESC",nameKolonki);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            maxResultSet = resultSet.getDouble(nameKolonki);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxResultSet;
    }
}
