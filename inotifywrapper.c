#include <stdio.h>
#include<stdlib.h>
#include <string.h>
#include <inotifytools/inotifytools.h>
#include <inotifytools/inotify.h>

#define MAX_STRLEN  4096

typedef void(*notifyEvent)(const char *path, const char *filename, long int evt);


int startInotify(const char* root, const notifyEvent notifyEvent) {
	if ( !inotifytools_initialize() || !inotifytools_watch_recursively( root, IN_ALL_EVENTS) ) {
		fprintf(stderr, "%s\n", strerror( inotifytools_error() ) );
		return -1;
	}

	// set time format to 24 hour time, HH:MM:SS
    inotifytools_set_printf_timefmt( "%T" );

	// Output all events as "<timestamp> <path> <events>"
	struct inotify_event * event = inotifytools_next_event( -1 );
    char pathbuff[MAX_STRLEN + 1];
	while ( event ) {
		event = inotifytools_next_event( -1 );
        char * evtname = inotifytools_event_to_str(event->mask);
        char * basedir= inotifytools_filename_from_wd(event->wd);
        if(event->mask == (IN_ISDIR | IN_CREATE)) {
                inotifytools_watch_recursively(basedir, IN_ALL_EVENTS);
        }
       (*notifyEvent)(basedir, event->name, event->mask);
	}
}
