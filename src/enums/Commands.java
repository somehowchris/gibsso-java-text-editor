package enums;

public enum Commands {
    SAVE("save"),
    OPEN("open"),
    CLOSE("close");

    String cmd;
    Commands(String cmd){
        this.cmd = cmd;
    }

    public String getCommand(){
        return this.cmd;
    }
}
