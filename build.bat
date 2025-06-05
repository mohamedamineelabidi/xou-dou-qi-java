@echo off
echo Building Xou Dou Qi Java Application...

rem Clean build directory
if exist build rmdir /s /q build
mkdir build\classes

rem Compile all Java files
javac -d build\classes -cp src\main\java src\main\java\game\pieces\*.java
javac -d build\classes -cp build\classes src\main\java\game\board\*.java
javac -d build\classes -cp build\classes src\main\java\game\core\*.java
javac -d build\classes -cp build\classes src\main\java\player\accounts\*.java
javac -d build\classes -cp build\classes src\main\java\data\storage\*.java
javac -d build\classes -cp build\classes src\main\java\console\ui\*.java
javac -d build\classes -cp build\classes src\main\java\game\Main.java

if %errorlevel% equ 0 (
    echo Build successful! Running application...
    java -cp build\classes game.Main
) else (
    echo Build failed!
    pause
)
