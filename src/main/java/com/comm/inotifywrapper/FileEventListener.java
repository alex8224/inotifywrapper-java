package com.comm.inotifywrapper;

/**
 * @author zjp
 * @date: 2019/4/17
 */
public interface FileEventListener {

    /**
     * 触发文件系统事件
     *
     * @param basedir
     * @param filename
     * @param bitmask
     */
    void fireEvt(String basedir, String filename, long bitmask);

    void fireCloseWrite(String basedir, String filename);

    void fireDirCreate(String basedir, String filename);

    void fireFileModify(String basedir, String filename);
}
