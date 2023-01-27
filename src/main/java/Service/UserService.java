package Service;

import DAO.UserDAO;
import Model.Account;

public class UserService {
    private UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO();
    }
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    /**
     * 1: Our API should be able to process new User registrations.
     * @param account
     * @return
     */
    public Account addUser(Account account){
        if(account.getUsername().length() > 0 && account.getPassword().length() > 4){
            return userDAO.createUser(account);
        }else{
            return null;
        }
        
    }

    /**
     * 2: Our API should be able to process User logins.
     * @param username
     * @param password
     * @return
     */
    public Account loginUser(Account account){
       return userDAO.loginUser(account);
    }

}
