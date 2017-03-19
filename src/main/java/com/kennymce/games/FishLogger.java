package com.kennymce.games;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FishLogger {
    final Logger fishLogger = LogManager.getLogger("Fish");

    public void LogMessage(String str){
        System.out.println(str);
        //Newline before string to make the messages neater
        str = System.lineSeparator().concat(str);
        fishLogger.info(str);
    }

    public void LogError(String message, Exception e){
        String trace =  Thread.currentThread().getStackTrace().toString();
        fishLogger.error(e);
        //TODO this isn't giving me the stack trace!
        fishLogger.error(trace);
    }
}
