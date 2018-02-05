package net.mrliuli.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import net.mrliuli.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by li.liu on 2018/2/5.
 */
public class LogbackDemoTest extends BaseTest{

    private static Logger logger = LoggerFactory.getLogger(LogbackDemoTest.class);

    @Test
    public void main() throws Exception {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        JoranConfigurator configurator = new JoranConfigurator();

        configurator.setContext(lc);

        lc.reset();

        try {
            configurator.doConfigure("src/main/resources/logback.xml");
        }catch (JoranException e){
            e.printStackTrace();
        }

        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);

        logger.trace("===============================trace");
        logger.debug("===============================debug");
        logger.info("================================info");
        logger.warn("================================warn");
        logger.error("================================error");

    }

}