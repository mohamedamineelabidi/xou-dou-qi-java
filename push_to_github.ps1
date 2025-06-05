#!/usr/bin/env pwsh

# Navigate to the project directory
Set-Location "c:\Users\elabi\OneDrive\Desktop\xoudougame"

# Configure git to use notepad as editor to avoid vim issues
git config --global core.editor "notepad.exe"

# Abort any ongoing rebase
try {
    git rebase --abort
    Write-Host "Rebase aborted successfully" -ForegroundColor Green
} catch {
    Write-Host "No rebase to abort or already clean" -ForegroundColor Yellow
}

# Check git status
Write-Host "Current git status:" -ForegroundColor Cyan
git status

# Add all files
Write-Host "Adding all files..." -ForegroundColor Cyan
git add .

# Commit with a descriptive message
Write-Host "Committing changes..." -ForegroundColor Cyan
git commit -m "Complete Xou Dou Qi implementation with interactive UI and game logic

- Added comprehensive game implementation with full board logic
- Interactive console UI with enhanced visual display
- Player management system with statistics tracking
- Complete piece movement validation and game rules
- Testing framework and documentation
- Build scripts and project structure"

# Push to GitHub
Write-Host "Pushing to GitHub..." -ForegroundColor Cyan
git push origin main

Write-Host "Push completed successfully!" -ForegroundColor Green

# Display final status
Write-Host "Final git status:" -ForegroundColor Cyan
git status

Read-Host "Press Enter to continue..."
