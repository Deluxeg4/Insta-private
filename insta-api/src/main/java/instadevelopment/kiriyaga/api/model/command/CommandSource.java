package instadevelopment.kiriyaga.api.model.command;

import static instadevelopment.kiriyaga.api.InstaApi.CAT_FORMAT;
import static instadevelopment.kiriyaga.api.InstaApi.CHAT_SERVICE;

public class CommandSource {

    public void info(String msg) {
        CHAT_SERVICE.sendPersistent("Command", CAT_FORMAT.format("{gray}"+msg));
    }

    public void error(String msg) {
        CHAT_SERVICE.sendPersistent("Command", CAT_FORMAT.format("{red}" + msg));
    }
}