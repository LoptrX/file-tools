package open.tools.action;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import open.tools.strategy.Operation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@Setter
public class TextFile implements Handler {
    Logger log = Logger.getLogger(this.getClass().getName());
    private Operation operation;

    @Override
    public boolean handle(File file) {
        if (file.isDirectory()) {
            return false;
        }

        StringBuilder content = read(file);

        if (content.isEmpty()) {
            return true;
        }

        String operated = operation.doOperate(content.toString());

        if (StringUtils.equals(content.toString(), operated)) {
            return true;
        }

        return write(operated, file);
    }

    private StringBuilder read(File file) {
        StringBuilder sb = new StringBuilder((int) (file.length() / 4));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                sb.append(operation.doOperate(readLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    private boolean write(String data, File file) {
        try (OutputStream out = Files.newOutputStream(Path.of(file.getPath()))) {
            IOUtils.write(data, out);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
