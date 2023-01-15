package open.tools;

import open.tools.action.Filename;
import open.tools.model.FileVisitor;
import open.tools.strategy.SnakeToCamelOperation;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;


public class VisitorTest {
    Logger log = Logger.getLogger(VisitorTest.class.getName());
    FileVisitor visitor = new FileVisitor("C:\\Users\\moyuq\\Desktop\\file-test");

    @Test
    public void run() {
        rename();
        visitor.start();
    }

    public void rename() {
        visitor.addListener(file-> {
            log.info("filename: " + file.getName());
            Filename filename = new Filename();
            filename.setOperation(new SnakeToCamelOperation());
            boolean result = filename.handle(file);
            log.info("result: " + result);
        });
    }
}
