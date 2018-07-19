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
	public static int IN_CLOSE_WRITE = 0x00000008;

	public interface InotifyWrapper extends Library {

		public interface InotifyEventListener extends Callback {
			void invoke(String basedir, String filename, long evtname);
		}

		// define an implementation of the callback interface
		public static class InotifyEventListenerImpl implements InotifyEventListener{
			@Override
			public void invoke(String basedir, String filename, long evtname) {
				System.out.println(String.format("%s%s, evt is :%x", basedir, filename, evtname));
			}
		}

		public int startInotify(String root, InotifyEventListener listener);
	}

	public interface INotifyLibrary extends Library {
		//boolean isWindows = Platform.isWindows();
		INotifyLibrary INSTANCE = Native.loadLibrary("inotifytools",INotifyLibrary.class);


		public class Uint extends IntegerType {

			public Uint() {
				super(4, true);
			}
		}

		public static class InotifyEvent1 extends Structure{

			public static class ByReference extends InotifyEvent1 implements Structure.ByReference {}
			public int wd;
			public long mask;
			public long cookie;
			public long len;
			public Pointer name;


			protected List<String> getFieldOrder() {
				return Arrays.asList(new String[]{"wd","mask","cookie","len","name"});
			}
		};

		public static class InotifyEvent extends PointerType {
			public InotifyEvent(Pointer address){
				super(address);
			}

			public InotifyEvent() {
				super();
			}
		};

		int inotifytools_initialize();
		int inotifytools_watch_recursively(String root, int events );
		void inotifytools_set_printf_timefmt(String fmt);
		void inotifytools_printf(InotifyEvent1 event, String fmt);
		void inotifytools_sprintf(Pointer out, InotifyEvent1 event, String fmt);
		InotifyEvent1.ByReference inotifytools_next_event(long timeout);

	}
	public static void main( String[] args )
	{
        final InotifyWrapper wrapper = Native.loadLibrary("inotifywrapper", InotifyWrapper.class);
        final InotifyWrapper.InotifyEventListenerImpl listener = new InotifyWrapper.InotifyEventListenerImpl();
        wrapper.startInotify(args[0], listener);
	}
}
