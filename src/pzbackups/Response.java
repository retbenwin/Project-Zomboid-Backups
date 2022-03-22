package pzbackups;

/**
 *
 * @author retbenwin
 */
public class Response {

    /**
     * @return the Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message the Message to set
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return the Success
     */
    public boolean isSuccess() {
        return Success;
    }

    /**
     * @param Success the Success to set
     */
    public void setSuccess(boolean Success) {
        this.Success = Success;
    }
    private String Message;
    private boolean Success;
    
    public Response(){
        Message = "";
        Success = false;
    }
}
