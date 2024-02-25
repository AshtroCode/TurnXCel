package java.SetupExecutor;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Log4JProperty {
	static Logger log = Logger.getLogger(Log4JProperty.class);
    public static void configureLog4j() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String logFileName = "Logs_" + dateFormat.format(new Date()) + ".log";
            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c{1}:%L - %m%n"));
            consoleAppender.activateOptions();
            Logger.getRootLogger().addAppender(consoleAppender);
            RollingFileAppender fileAppender = new RollingFileAppender();
            fileAppender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c{1}:%L - %m%n"));
            fileAppender.setFile(Variables.LogDirectory+logFileName);
            fileAppender.setAppend(true);
            fileAppender.setMaxFileSize("50MB");
            fileAppender.activateOptions();
            Logger.getRootLogger().addAppender(fileAppender);
            log.info("Setting log4j properties success");
            log.info("Deleting old log files");
            checkLogFilesCount();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void checkLogFilesCount() {
    	List<String> fileNames = new ArrayList<String>();
    	try {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(Variables.LogDirectory));
		    for (Path path : directoryStream) {
		          fileNames.add(path.toString());
		          log.info("File: "+path.toString());
		        }
		} catch (IOException e) {
			log.error("Unable to check the files in Logs directory");
		}
    	Collections.sort(fileNames);
    	log.info("File Count:"+fileNames.size());
    	for(int i=0;i<(fileNames.size()-3); i++) {
    		File obj = new File(fileNames.get(i));
    		if(obj.delete()) {
    			fileNames.remove(i);
    			log.info("Deleted log file: "+obj.toString());
    		}
    		else {
    			log.info("Unable to delete log file: "+obj.toString());
    		}
    	}
    	log.info("Remaining files: ");
    	for(String file:fileNames) {
    		log.info(file);
    	}
    }
}


