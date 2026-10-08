@echo off
setlocal

set MAVEN_HOME=%~dp0.maven\apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%

call mvn %*

endlocal
