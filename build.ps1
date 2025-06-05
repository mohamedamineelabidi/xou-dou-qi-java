# PowerShell build script for Xou Dou Qi

Write-Host "🐘🐅🐀 Building Xou Dou Qi: Java Jungle Showdown 🐀🐅🐘" -ForegroundColor Cyan

# Clean and create build directory
Write-Host "Cleaning build directory..." -ForegroundColor Yellow
Remove-Item -Recurse -Force build -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path build\classes -Force | Out-Null

# Find all Java source files
Write-Host "Finding Java source files..." -ForegroundColor Yellow
$sourceFiles = Get-ChildItem -Path src\main\java -Recurse -Include "*.java"
Write-Host "Found $($sourceFiles.Count) Java files" -ForegroundColor Green

# Compile Java sources
Write-Host "Compiling Java sources..." -ForegroundColor Yellow
javac -d build\classes -cp src\main\java $sourceFiles.FullName

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Build successful!" -ForegroundColor Green
    Write-Host "Running application..." -ForegroundColor Cyan
    Write-Host ""
    java -cp build\classes game.Main
} else {
    Write-Host "❌ Build failed!" -ForegroundColor Red
    exit 1
}
