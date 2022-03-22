package pzbackups;

/**
 *
 * @author retbenwin
 */
public class Response {

    /**
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }

    /**
     * @param extra the extra to set
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }

    /**
     * @return the Message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param Message the Message to set
     */
    public void setMessage(String Message) {
        this.message = Message;
    }

    /**
     * @return the Success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param Success the Success to set
     */
    public void setSuccess(boolean Success) {
        this.success = Success;
    }
    private String message;
    private boolean success;
    private String extra;
    
    public Response(){
        extra = "";
        message = "";
        success = false;
    }
}
