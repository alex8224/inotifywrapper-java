# inotifywrapper-java
A Inotifytools library java wrapper

Java file watch service no CLOSE_WRITE event, so, I can't get any notification when a process close write a file.
So I wrote this project.


The wrapper support all inotifytools features.I think is awesome.


## requirements

1. inotifytools and libinotifytools-dev
2. JNA

```
yum install epel-release
yum install inotify-tools-devel.x86_64

```
## compile

```
gcc -fPIC -shared -o libinotifywrapper.so inotifywrapper.c -linotifytools
mvn assembly:assembly
```

## usage 

```
env LD_LIBRARY=$(pwd) java -jar inotify-java-wrapper-0.1-with-dependencies.jar $(pwd)
```
