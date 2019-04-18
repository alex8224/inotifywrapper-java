/**
 * inotifytools api封装
 *
 * @author zjp 20190418
 */
package com.comm.inotifywrapper;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class WrapperImpl {

    public static void main(String[] args) {
        final long await_event = EvtMask.IN_CLOSE_WRITE | EvtMask.IN_OPEN | EvtMask.IN_ISDIR | EvtMask.IN_CLOSE_NOWRITE | EvtMask.IN_DELETE | EvtMask.IN_MODIFY;
        System.out.println("aware event is " + await_event);
        final InotifyWrapper wrapper = Native.loadLibrary("inotifywrapper", InotifyWrapper.class);
        final InotifyWrapper.InotifyEventListenerImpl listener = new InotifyWrapper.InotifyEventListenerImpl(null);
        wrapper.startInotify(args[0], await_event, listener);
    }

    public void watch(String rootdir, final long evtMask, FileEventListener listener) {
        System.out.println("aware event is " + evtMask);
        final InotifyWrapper wrapper = Native.loadLibrary("inotifywrapper", InotifyWrapper.class);
        final InotifyWrapper.InotifyEventListenerImpl fileListener = new InotifyWrapper.InotifyEventListenerImpl(listener);
        wrapper.startInotify(rootdir, evtMask, fileListener);
    }

    public interface InotifyWrapper extends Library {

        int startInotify(String root, long eventmask, InotifyEventListener listener);

        interface InotifyEventListener extends Callback {
            void invoke(String basedir, String filename, long evtname);
        }

        class InotifyEventListenerImpl implements InotifyEventListener {
            private FileEventListener fileListener;

            InotifyEventListenerImpl(FileEventListener listenerFor) {
                this.fileListener = listenerFor == null ? new WatchFileService() : listenerFor;
            }

            @Override
            public void invoke(String basedir, String filename, long evt) {
                dispatchEvt(basedir, filename, evt);
            }

            private void dispatchEvt(String basedir, String filename, long evt) {

                boolean closeWriteEvt = (evt & EvtMask.IN_CLOSE_WRITE) > 0;
                boolean createDirEvt = ((evt & EvtMask.IN_ISDIR) > 0) && ((evt & EvtMask.IN_CREATE) > 0);

                boolean modifyEvt = (evt & EvtMask.IN_MODIFY) > 0;

                if (closeWriteEvt) {
                    fileListener.fireCloseWrite(basedir, filename);
                }
                if (createDirEvt) {
                    fileListener.fireDirCreate(basedir, filename);
                }
                if (modifyEvt) {
                    fileListener.fireFileModify(basedir, filename);
                }
            }
        }
    }
}
