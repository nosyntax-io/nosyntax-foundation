@echo off
setlocal enabledelayedexpansion

REM This is the manual initialization script; it is not utilized by Jenkins.

REM Set Playground parameter values
set "PARAM_BUILD_ENVIRONMENT=development"
set "PARAM_APP_ID=com.example.android"
set "PARAM_APP_NAME=Playground"
set "PARAM_APP_VERSION_NUMBER=1"
set "PARAM_APP_VERSION_NAME=1.0"
set "PARAM_APP_REMOTE_CONFIG=enabled"

REM Read the template file and replace placeholders
set "TEMPLATE_FILE=local.properties.template"
set "OUTPUT_FILE=local.properties"

if exist "%OUTPUT_FILE%" del "%OUTPUT_FILE%"

for /f "tokens=*" %%A in (%TEMPLATE_FILE%) do (
    set "line=%%A"
    set "line=!line:PARAM_BUILD_ENVIRONMENT=%PARAM_BUILD_ENVIRONMENT%!"
    set "line=!line:PARAM_APP_ID=%PARAM_APP_ID%!"
    set "line=!line:PARAM_APP_NAME=%PARAM_APP_NAME%!"
    set "line=!line:PARAM_APP_VERSION_NUMBER=%PARAM_APP_VERSION_NUMBER%!"
    set "line=!line:PARAM_APP_VERSION_NAME=%PARAM_APP_VERSION_NAME%!"
    set "line=!line:PARAM_APP_REMOTE_CONFIG=%PARAM_APP_REMOTE_CONFIG%!"
    echo !line! >> "%OUTPUT_FILE%"
)

echo Initialization completed. Please update your app details in the local.properties file.
pause