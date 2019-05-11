@echo off
rem user needs to edit this batch file to specify the correct file location to install, replace default values
rem ------- SET YOUR VALUES IN THIS SECTION ---------
rem note: do not leave any space before or after the equal (=) sign.

set groupId=com.google.code.kaptcha
set packaging=jar
set artifactId=kaptcha
set version=2.3
set fileName=d:/projects-2018/mvn-install/kaptcha-2.3.jar
rem ------- NO MORE EDITING REQUIRED AFTER THIS POINT ----------

echo installing maven file from: %fileName%

call mvn install:install-file -DgroupId=%groupId% -Dpackaging=%packaging% -DartifactId=%artifactId% -Dversion=%version% -Dfile=%fileName%

if %ERRORLEVEL% == 0 GOTO ok

if not %ERRORLEVEL% == 0 GOTO fail

:ok
cls
set status=ok
GOTO quit

:fail
set status=fail
GOTO quit

:quit
echo %status%
pause
exit




