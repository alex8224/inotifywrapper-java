package com.doordu.inotifywrapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.Pointer;
import com.sun.jna.Platform;
import com.sun.jna.IntegerType;
import com.sun.jna.Callback;
import com.sun.jna.Structure; 
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Arrays;


public class App 
{
	public static long IN_CLOSE_WRITE = 0x00000008;


	public interface InotifyWrapper extends Library {

		public interface InotifyEventListener extends Callback {
			void invoke(String basedir, String filename, long evtname);
		}

		public static class InotifyEventListenerImpl implements InotifyEventListener{
			@Override
			public void invoke(String basedir, String filename, long evtname) {
				System.out.println(String.format("%s%s, evt is :0x%06x", basedir, filename, evtname));
			}
		}

		public int startInotify(String root, long eventmask, InotifyEventListener listener);
	}
	
	public static void main( String[] args )
	{
        final InotifyWrapper wrapper = Native.loadLibrary("inotifywrapper", InotifyWrapper.class);
        final InotifyWrapper.InotifyEventListenerImpl listener = new InotifyWrapper.InotifyEventListenerImpl();
        wrapper.startInotify(args[0], IN_CLOSE_WRITE, listener);
	}
}
