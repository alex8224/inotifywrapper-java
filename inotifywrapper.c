/**
inotify-tools封装
回调Java通知
**/
#include <stdio.h>
#include<stdlib.h>
#include <string.h>
#include <inotifytools/inotifytools.h>
#include <inotifytools/inotify.h>

typedef void(*notifyEvent)(const char *path, const char *filename, long int evt);

int startInotify(const char* root, unsigned int eventmask, const notifyEvent notifyEvent);
int startInotify(const char* root, unsigned int eventmask, const notifyEvent notifyEvent) {
    if ( !inotifytools_initialize() || !inotifytools_watch_recursively( root, IN_ALL_EVENTS) ) {
        fprintf(stderr, "%s\n", strerror( inotifytools_error() ) );
        return -1;
    }

    struct inotify_event * event = inotifytools_next_event( -1 );
    while ( event ) {
        event = inotifytools_next_event( -1 );
        fprintf(stdout, "passed event mask is %d\n", event->mask);
        char * basedir = inotifytools_filename_from_wd(event->wd);
        if(event->mask == (IN_ISDIR | IN_CREATE)) {
            inotifytools_watch_recursively(basedir, IN_ALL_EVENTS);
        }

        if (event->mask == (IN_ISDIR | IN_DELETE)) {
            inotifytools_remove_watch_by_wd(event->wd);
        }

        unsigned int bitmask = event->mask & eventmask;
        if(event->mask == bitmask ) {
            (*notifyEvent)(basedir, event->name, event->mask);
        }
    }
}
