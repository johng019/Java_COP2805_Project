package sample;

import java.sql.*;
import java.util.ArrayList;

public class HighScoreDBManager {

    public void createDatabase() throws SQLException {

        String url = "jdbc:sqlite:src/HiScore.db";

        try(Connection conn = DriverManager.getConnection(url)){
            if(conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("The driver name is: " + meta.getDriverName());
                //System.out.println("A new Database has been created");

                String sql = "CREATE TABLE IF NOT EXISTS records ( id integer PRIMARY KEY , \n"
                        + "Username text NOT NULL, Score Integer NOT NULL)";

                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                //System.out.println("The records Table has been created");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Connection connect(){
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:src/HiScore.db";
            conn = DriverManager.getConnection(url);
            //System.out.println("Connection to sqlite has been established");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public void callDB() {

        try {
            createDatabase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertRecord(Record record){
        Connection conn = connect();
        PreparedStatement ps = null;

        //Insert into table
        try {
            String sqlInsert = "INSERT INTO records(username,score) VALUES(?,?)";
            ps =  conn.prepareStatement(sqlInsert);
            ps.setString(1, record.getUsername());
            ps.setDouble(2, record.getScore());
            ps.execute();

            System.out.println(" ");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Record> findAllRecords() throws SQLException {

        Connection conn = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Record> people = new ArrayList<>();

        //get info from table
        try {
            String sqlInfo = "SELECT * FROM records";
            ps = conn.prepareStatement(sqlInfo);
            rs = ps.executeQuery();

            //loop through result set
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");

                people.add(new Record(rs.getString("username"),rs.getDouble("score")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
            conn.close();
        }
        return people;
    }

    public Object selectRecord(int id) throws SQLException {

        Connection conn = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object selectedRecord = " ";

        try {
            String select = "SELECT * from records where id = ?";
            ps = conn.prepareStatement(select);
            ps.setInt(1,id);
            rs = ps.executeQuery();

            selectedRecord = new Record(rs.getString("username"), rs.getDouble("score"));

            return "Selected Record : \n" + selectedRecord;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return selectedRecord;
    }

    public void deleteAllRecords() throws SQLException {
        Connection conn = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "DELETE FROM records";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }  catch(Exception e) {
            System.out.println(e.getMessage());
        } finally
        {
            try{
                ps.close();
                conn.close();
            }catch (SQLException e ){
                e.printStackTrace();
            }
        }
    }

}
