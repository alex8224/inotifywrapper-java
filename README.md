# inotifywrapper-java
A Inotifytools library java wrapper

Java file watch service no CLOSE_WRITE event, so, I can't get any notification when a process close write a file.
So I wrote this project.


The wrapper support all inotifytools features.I think is awesome.


## requirements

1. inotifytools and libinotifytools-dev
2. JNA


## compile

```
gcc -fPIC -shared -o libinotifywrapper.so inotifywrapper.c -linotifytools
mvn assembly:assembly
```
