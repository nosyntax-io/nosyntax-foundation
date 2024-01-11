#!/bin/bash

# This is the manual initialization script; it is not utilized by Jenkins.

# Read template from local.properties.template
template=$(<local.properties.template)

# Replace placeholders with playground details
template=${template/PARAM_BUILD_ENVIRONMENT/development}
template=${template/PARAM_APP_ID/com.example.android}
template=${template/PARAM_APP_NAME/Playground}
template=${template/PARAM_APP_VERSION_NUMBER/1}
template=${template/PARAM_APP_VERSION_NAME/1.0}
template=${template/PARAM_APP_REMOTE_CONFIG/enabled}

# Write the result to local.properties
echo "$template" > local.properties

echo "Initialization completed. Please update your app details in the local.properties file."
echo "Press any key to continue..."
read -r