rem @echo off

rem SET JAVA_HOME="C:\Javajdk"
rem SET JDK_HOME=%JAVA_HOME%
rem SET JRE_HOME="C:\Javajdk\jre"

rem SET CLASSPATH=C:\Javajdk\lib;C:\Javajdk\jre\lib;
rem SET PATH=%PATH%;%JAVA_HOME%\bin;


rem Javac -encoding UTF-8 HelloWorldApp.java
rem Javac *.java

rem The directory tree where these files are
rem corresponds to their Jar package tree.

rem Compile all of them.
cd \Eric\Main\CodeEditor4
del *.class
cls
Javac -Xlint -Xstdout Build.log *.java

rem type Build.log
rem don't use pause
