package open.tools.model;


import open.tools.listener.VisitorListener;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileVisitor {
    Logger log = Logger.getLogger(this.getClass().getName());
    private final String path;

    private final List<String> ignoreFiles = new ArrayList<>();

    private final List<VisitorListener> listeners = new ArrayList<>();

    public FileVisitor(String path) {
        this.path = path;
    }


    public void start() {
        visit(new File(this.path));
    }

    public void visit(File file) {
        if (inBanList(file.getName())) {
            log.info(String.format("[%s] in ban list", file.getName()));
            return;
        }
        call(file);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                log.info(String.format("[%s] children file be empty", file.getName()));
                return;
            }
            for (File item : files) {
                visit(item);
            }
        }
    }

    public void call(File file) {
        for (VisitorListener listener : listeners) {
            listener.onListen(file);
        }
    }

    public boolean inBanList(String filename) {
        String extendName = StringUtils.substringAfterLast(filename, ".");
        return ignoreFiles.contains(filename) || ignoreFiles.contains(extendName);
    }

    public FileVisitor addListener(VisitorListener listener) {
        listeners.add(listener);
        return this;
    }
}
