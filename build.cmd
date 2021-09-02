@REM call mvnw -Djavacpp.platform.custom -Djavacpp.platform.host -Djavacpp.platform.linux-x86_64 kotlin:compile -Dmaven.test.skip=true package
call mvnw -Djavacpp.platform=linux-x86_64 kotlin:compile -Dmaven.test.skip=true package
call echo fc-core
call scp fc-core\target\fc-core-0.0.1-SNAPSHOT.jar root@123.57.160.67:~\flea\
call echo fc-socket
call scp fc-socket\target\fc-socket-0.0.1-SNAPSHOT.jar root@123.57.160.67:~\flea\