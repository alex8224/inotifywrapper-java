#include <stdio.h>
#include<stdlib.h>
#include <string.h>
#include <inotifytools/inotifytools.h>
#include <inotifytools/inotify.h>

#define MAX_STRLEN  4096

typedef void(*notifyEvent)(const char *path, const char *filename, long int evt);


int startInotify(const char* root, unsigned int eventmask, const notifyEvent notifyEvent);
int startInotify(const char* root, unsigned int eventmask, const notifyEvent notifyEvent) {
    if ( !inotifytools_initialize() || !inotifytools_watch_recursively( root, IN_ALL_EVENTS) ) {
        fprintf(stderr, "%s\n", strerror( inotifytools_error() ) );
        return -1;
    }

    //inotifytools_set_printf_timefmt( "%T" );

    struct inotify_event * event = inotifytools_next_event( -1 );
    while ( event ) {
        event = inotifytools_next_event( -1 );
        char * basedir = inotifytools_filename_from_wd(event->wd);
        if(event->mask == (IN_ISDIR | IN_CREATE)) {
            inotifytools_watch_recursively(basedir, IN_ALL_EVENTS);
        }

        if (event->mask == (IN_ISDIR | IN_DELETE)) {
            inotifytools_remove_watch_by_wd(event->wd);
            fprintf(stderr, "delete dir watch %s\n", event->name);
        }
        if(event->mask == eventmask) {
            (*notifyEvent)(basedir, event->name, event->mask);
        }

    }
}
