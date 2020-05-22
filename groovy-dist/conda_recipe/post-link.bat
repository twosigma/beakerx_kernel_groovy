@echo off
(
  REM Uninstall BeakerX notebook extension
  "%PREFIX%\Scripts\beakerx-kernel-groovy.exe" "install"
) >>"%PREFIX%\.messages.txt" 2>&1