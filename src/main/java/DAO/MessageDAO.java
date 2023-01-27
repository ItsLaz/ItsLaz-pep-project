package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    /**
     * 3: Our API should be able to process the creation of new messages.
     */
    public Message createMsg(Message msg){
        Connection c = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());
            ps.executeUpdate();
            return msg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 4: Our API should be able to retrieve all messages.
     */
    public List<Message> getAllMsg(){
        Connection c = ConnectionUtil.getConnection();
        List<Message> msgs = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement ps = c.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                msgs.add(msg);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgs;
    }

    /**
     * 5: Our API should be able to retrieve a message by its ID.
     */
    public Message getMsgById(int id){
        Connection c = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return msg;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 6: Our API should be able to delete a message identified by a message ID.
     */
    public Message deleteMsgById(int id){
        Connection c  = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
    
            ps.setInt(1, id);
            
            int rowsDeleted = ps.executeUpdate();
            if(rowsDeleted == 1){
                String sql2 = "SELECT * FROM Message WHERE message_id = ?";
                PreparedStatement ps2 = c.prepareStatement(sql2);
                ps2.setInt(1, id);
                ResultSet rs = ps2.executeQuery();
                while(rs.next()){
                    Message msg = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                    return msg;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 7: Our API should be able to update a message text identified by a message ID.
     */
    public Message updateMsg(int id, String newText) {
        Connection c = ConnectionUtil.getConnection();
    
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, newText);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                String sql2 = "SELECT * FROM Message WHERE message_id = ?";
                PreparedStatement ps2 = c.prepareStatement(sql2);
                ps2.setInt(1, id);
                ResultSet rs = ps2.executeQuery();
                while (rs.next()) {
                    Message msg = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                    return msg;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 8: Our API should be able to retrieve all messages written by a particular user.
     */
    public List<Message> getAllMsgByUser(int id){
        Connection c = ConnectionUtil.getConnection();
        List<Message> msgs = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message WHERE posted_by=?';";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                msgs.add(msg);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgs;
    }
}
