package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO msgDAO;
    
    public MessageService(){
        msgDAO = new MessageDAO();
    }
    public MessageService(MessageDAO msgDAO){
        this.msgDAO = msgDAO;
    }

    /**
     * 3: Our API should be able to process the creation of new messages.
     * @param msg
     * @return
     */
    public Message addMsg(Message msg){
        if(msg.getMessage_text().length() > 0 && msg.getMessage_text().length() < 255 && msg.getPosted_by() >=0){
            return msgDAO.createMsg(msg);
        }else{
            return null;
        }
    }

    /**
     * 4: Our API should be able to retrieve all messages.
     * @return
     */
    public List<Message> getAllMsg(){
        return msgDAO.getAllMsg();
    }

    /**
     * 5: Our API should be able to retrieve a message by its ID.
     * @param id
     * @return
     */
    public Message getMsgById(int id){
        return msgDAO.getMsgById(id);
    }

    /**
     * 6: Our API should be able to delete a message identified by a message ID.
     * @param id
     * @return
     */
    public Message deleteMsgById(int id){
        if(msgDAO.getMsgById(id) != null){
            Message msg = msgDAO.getMsgById(id);
            msgDAO.deleteMsgById(id);
            return msg;
        }else{
            return null;
        }
    }

    /**
     * 7: Our API should be able to update a message text identified by a message ID.
     * @param id
     * @param newText
     * @return
     */
    public Message updateMsgById(int id, String newText){
        if(msgDAO.getMsgById(id) != null && newText.length() > 0 && newText.length() < 255){
            return msgDAO.updateMsg(id, newText);
        }else{
            return null;
        }
    }

    /**
     * 8: Our API should be able to retrieve all messages written by a particular user.
     * @param id
     * @return
     */
    public List<Message> getAllMsgByUser(int id){
        return msgDAO.getAllMsgByUser(id);
    }

}
