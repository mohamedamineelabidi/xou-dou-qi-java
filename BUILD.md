# Xou Dou Qi - Build Instructions

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- Windows PowerShell or Command Prompt

## Project Structure
```
xoudougame/
├── src/main/java/
│   ├── game/
│   │   ├── Main.java
│   │   ├── board/
│   │   ├── pieces/
│   │   └── core/
│   ├── player/accounts/
│   ├── data/storage/
│   └── console/ui/
└── build/ (generated)
```

## Building the Application

### Using PowerShell/Command Prompt:

1. Navigate to the project root directory:
```powershell
cd "c:\Users\elabi\OneDrive\Desktop\xoudougame"
```

2. Create build directory:
```powershell
mkdir -Force build\classes
```

3. Compile the Java sources:
```powershell
javac -d build\classes -cp src\main\java src\main\java\game\Main.java src\main\java\game\board\*.java src\main\java\game\pieces\*.java src\main\java\game\core\*.java src\main\java\player\accounts\*.java src\main\java\data\storage\*.java src\main\java\console\ui\*.java
```

4. Run the application:
```powershell
java -cp build\classes game.Main
```

## Quick Build Script
Save this as `build.ps1`:
```powershell
# Clean and create build directory
Remove-Item -Recurse -Force build -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path build\classes -Force

# Compile Java sources
$sourceFiles = Get-ChildItem -Path src\main\java -Recurse -Include "*.java"
javac -d build\classes -cp src\main\java $sourceFiles.FullName

if ($LASTEXITCODE -eq 0) {
    Write-Host "Build successful! Running application..." -ForegroundColor Green
    java -cp build\classes game.Main
} else {
    Write-Host "Build failed!" -ForegroundColor Red
}
```

Run with: `.\build.ps1`
