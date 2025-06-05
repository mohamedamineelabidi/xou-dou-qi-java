@echo off
echo Starting git operations...
cd /d c:\Users\elabi\OneDrive\Desktop\xoudougame

echo Setting git editor to notepad...
git config core.editor "notepad.exe"

echo Aborting any rebase...
git rebase --abort

echo Current git status:
git status

echo Adding all files...
git add .

echo Committing changes...
git commit -m "Complete Xou Dou Qi implementation with interactive UI and game logic"

echo Pushing to GitHub...
git push origin main

echo Done!
pause
