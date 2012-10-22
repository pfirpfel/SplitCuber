package splitcuber.web;

public enum ContentType {
    JPEG("image/jpeg"), PNG("image/png"), TXT("text/plain"), HTML("text/html");
    
    ContentType(String pattern){
        this.pattern = pattern;
    }
    
    private String pattern;
    
    public boolean doesEqual(String s){
        if(pattern.equals(s)) return true;
        return false;
    }
}
