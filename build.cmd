@REM call mvnw -Djavacpp.platform.custom -Djavacpp.platform.host -Djavacpp.platform.linux-x86_64 kotlin:compile -Dmaven.test.skip=true package
call mvnw -Djavacpp.platform=linux-x86_64 kotlin:compile -Dmaven.test.skip=true package
call scp target\fleaCampus-0.0.1-SNAPSHOT.jar root@47.107.178.147:~