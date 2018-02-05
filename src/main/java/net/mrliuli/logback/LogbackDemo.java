package net.mrliuli.logback;

import ch.qos.logback.access.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by li.liu on 2018/2/5.
 */
public class LogbackDemo {

    private static Logger logger = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String[] args){


//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//        JoranConfigurator configurator = new JoranConfigurator();
//
//        configurator.setContext(lc);
//
//        lc.reset();
//
//        try {
//            configurator.doConfigure("src/main/resources/logback.xml");
//        }catch (JoranException e){
//            e.printStackTrace();
//        }


        logger.trace("======trace");
        logger.debug("======debug");
        logger.info("======info");
        logger.warn("======warn");
        logger.error("======error");
    }

}
