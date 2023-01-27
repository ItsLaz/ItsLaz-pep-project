package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    MessageService msgService;
    UserService userService;

    public SocialMediaController(){
        this.msgService = new MessageService();
        this.userService = new UserService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        //test commit
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages",this::addMsgHandler);
        app.get("/messages", this::getAllMsgHandler);
        app.get("/messages/{message_id}", this::getMsgByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMsgByIdHandler);
        app.patch("/messages/{message_id}", this::updateMsgByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMsgsByUserHandler);

        return app;
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * 1: Our API should be able to process new User registrations.
     * @param ctx
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void registerUserHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredUser = userService.addUser(account);
        if(registeredUser!=null){
            ctx.json(mapper.writeValueAsString(registeredUser));
        }else{
            ctx.status(400);
        }
    }

    /**
     * 2: Our API should be able to process User logins.
     * @param ctx
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void loginUserHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInUser = userService.loginUser(account);
        if(loggedInUser!= null){
            ctx.json(mapper.writeValueAsString(loggedInUser));
        }else{
            ctx.status(401);
        }
    }

    /**
     * 3: Our API should be able to process the creation of new messages.
     * @param ctx
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void addMsgHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message newMsg = msgService.addMsg(msg);
        if(newMsg != null){
            ctx.json(mapper.writeValueAsString(newMsg));
        }else{
            ctx.status(400);
        }
    }

    /**
     * 4: Our API should be able to retrieve all messages.
     * @param ctx
     */
    private void getAllMsgHandler(Context ctx){
        List<Message> msgs = msgService.getAllMsg();
        ctx.json(msgs);

    }

    /**
     * 5: Our API should be able to retrieve a message by its ID.
     * @param ctx
     */
    private void getMsgByIdHandler(Context ctx){
        String id = ctx.pathParam("message_id");
        Message msg = msgService.getMsgById(Integer.parseInt(id));
        if(msg != null){
            ctx.json(msg);
        }else{
            ctx.status(404);
        }
    }

    /**
     * 6: Our API should be able to delete a message identified by a message ID.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void deleteMsgByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String id = ctx.pathParam("message_id");
        Message msg = msgService.deleteMsgById(Integer.parseInt(id));
        if(msg != null){
            ctx.json(mapper.writeValueAsString(msg));
        }else{
            ctx.status(200);
        }
    }

    /**
     * 7: Our API should be able to update a message text identified by a message ID.
     * @param ctx
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void updateMsgByIdHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        JsonNode json = mapper.readTree(ctx.body());
        String newMessageText = json.get("message_text").asText();
        Message updatedMessage = msgService.updateMsgById(messageId, newMessageText);
        if (updatedMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(404);
        }
    }


    /**
     * 8: Our API should be able to retrieve all messages written by a particular user.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void getMsgsByUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> msgs = msgService.getAllMsgByUser(accountId);
        if(msgs != null) {
            ctx.json(mapper.writeValueAsString(msgs));
        } else {
            ctx.json(msgs);
        }
    }



}