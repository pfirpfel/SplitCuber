package splitcuber.error;

public class WebFetchError extends Exception {
    private static final long serialVersionUID = 2789605162110096724L;
    
    public WebFetchError(String message){
        super(message);
    }
}
