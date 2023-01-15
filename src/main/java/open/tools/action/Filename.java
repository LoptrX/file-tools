package open.tools.action;

import lombok.Setter;
import open.tools.strategy.Operation;

import java.io.File;
import java.util.logging.Logger;

@Setter
public class Filename implements Handler {
    private Operation operation;

    @Override
    public boolean handle(File file) {
        String operated = operation.doOperate(file.getName());
        return file.renameTo(new File(file.getParent() + File.separator + operated));
    }
}
