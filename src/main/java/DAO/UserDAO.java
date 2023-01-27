package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class UserDAO {
    

    /**
     * 1: Our API should be able to process new User registrations.
     */

     public Account createUser(Account account){

        Connection c = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?,?);";
            PreparedStatement ps = c.prepareStatement(sql);


            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }

     /**
      * 2: Our API should be able to process User logins.
      */
     public Account loginUser(Account account){
        Connection c = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE username=? AND password=?;";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            return new Account(account.getUsername(), account.getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }


}
