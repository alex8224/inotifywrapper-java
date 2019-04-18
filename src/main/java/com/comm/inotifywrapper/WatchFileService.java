package com.comm.inotifywrapper;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author zjp
 * @date: 2019/4/17
 */
public class WatchFileService implements FileEventListener {

    private static Logger logger = Logger.getLogger("watchFileEvent");

    public static void main(String[] args) {
        WrapperImpl app = new WrapperImpl();
        FileEventListener listener = new WatchFileService();
        System.out.println(args[0]);
        app.watch(args[0], EvtMask.IN_CLOSE_WRITE | EvtMask.IN_ISDIR | EvtMask.IN_CREATE | EvtMask.IN_DELETE | EvtMask.IN_MODIFY, listener);
    }

    @Override
    public void fireEvt(String basedir, String filename, long bitmask) {
        throw new NotImplementedException();
    }

    @Override
    public void fireCloseWrite(String basedir, String filename) {
        String fullpath = new File(basedir, filename).getAbsolutePath();
        logger.info(fullpath + " close write");
    }

    @Override
    public void fireDirCreate(String basedir, String filename) {
        String fullpath = new File(basedir, filename).getAbsolutePath();
        logger.info(fullpath + " dir created");
    }

    @Override
    public void fireFileModify(String basedir, String filename) {
        String fullpath = new File(basedir, filename).getAbsolutePath();
        logger.info(fullpath + " file modifyed...");
    }
}
