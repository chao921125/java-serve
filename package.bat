 @echo off

 ECHO.
 	ECHO.  [1] Build打包test
 	ECHO.  [2] Build打包prod
 	ECHO.  [9] 清除
 	ECHO.  [0] 退 出
 ECHO.

 ECHO.请输入选择项目的序号:
 set /p ID=
 	IF "%id%"=="1" GOTO build-test
 	IF "%id%"=="2" GOTO build-prod
 	IF "%id%"=="9" GOTO clean
 	IF "%id%"=="0" EXIT
 PAUSE

 :build-test
     call mvn clean package -P test
 goto:eof

 rem 函数stop通过jps命令查找pid并结束进程
 :build-prod
     call mvn clean package -P prod
 goto:eof

 :clean
 	call mvn clean
 goto:eof
