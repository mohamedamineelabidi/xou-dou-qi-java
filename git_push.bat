@echo off
cd /d "c:\Users\elabi\OneDrive\Desktop\xoudougame"
git config core.editor "notepad.exe"
git rebase --abort
git add .
git commit -m "Complete Xou Dou Qi implementation with interactive UI and game logic"
git push origin main
pause
