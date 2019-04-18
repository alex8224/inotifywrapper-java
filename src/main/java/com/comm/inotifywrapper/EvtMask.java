package com.comm.inotifywrapper;

/**
 * @author zjp
 * @date: 2019/4/17
 */
public class EvtMask {
    /**
     * the following are legal, implemented events that user-space can watch for
     */

    /* File was accessed */
    public static long IN_ACCESS = 0x00000001;
    /* File was modified */
    public static long IN_MODIFY = 0x00000002;
    /* Metadata changed */
    public static long IN_ATTRIB = 0x00000004;
    public static long IN_CLOSE_WRITE = 0x00000008;    /* Writtable file was closed */
    /* Unwrittable file closed */
    public static long IN_CLOSE_NOWRITE = 0x00000010;
    /* File was opened */
    public static long IN_OPEN = 0x00000020;
    /* File was moved from X */
    public static long IN_MOVED_FROM = 0x00000040;
    /* File was moved to Y */
    public static long IN_MOVED_TO = 0x00000080;
    /* Subfile was created */
    public static long IN_CREATE = 0x00000100;
    /* Subfile was deleted */
    public static long IN_DELETE = 0x00000200;
    /* Self was deleted */
    public static long IN_DELETE_SELF = 0x00000400;
    /* Self was moved */
    public static long IN_MOVE_SELF = 0x00000800;

    /**
     * the following are legal events.  they are sent as needed to any watch
     */

    /* Backing fs was unmounted */
    public static long IN_UNMOUNT = 0x00002000;
    /* Event queued overflowed */
    public static long IN_Q_OVERFLOW = 0x00004000;
    /* File was ignored */
    public static long IN_IGNORED = 0x00008000;

    /* helper events */
    /* close */
    public static long IN_CLOSE = (IN_CLOSE_WRITE | IN_CLOSE_NOWRITE);
    /* moves */
    public static long IN_MOVE = (IN_MOVED_FROM | IN_MOVED_TO);


    /* special flags */
    /* only watch the path if it is a directory */
    public static long IN_ONLYDIR = 0x01000000;
    /* don't follow a sym link */
    public static long IN_DONT_FOLLOW = 0x02000000;
    /* add to the mask of an already existing watch */
    public static long IN_MASK_ADD = 0x20000000;
    /* event occurred against dir */
    public static long IN_ISDIR = 0x40000000;
    /* only send event once */
    public static long IN_ONESHOT = 0x80000000;
}
