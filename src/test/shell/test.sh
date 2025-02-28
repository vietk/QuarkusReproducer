#!/bin/sh

# Function to kill the application when the script exits
cleanup() {
  echo "Stopping the application..."
  kill "$APP_PID"
}

# Launch the application
echo "Starting the application..."
java -jar target/quarkus-app/quarkus-run.jar &
#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/quarkus-app/quarkus-run.jar &

# Record the PID of the Java process
APP_PID=$(pgrep -f 'java -jar target/quarkus-app/quarkus-run.jar')
echo "Application PID: $APP_PID"

# Set up trap to call cleanup on script exit
trap cleanup EXIT

# Wait for the application to start
echo "Waiting for the application to start..."
sleep 5

# Test the ResourceFinder endpoint
echo "Testing the ResourceFinder endpoint..."
response=$(curl -s -G --data-urlencode "resourcePath=classpath*:org/acme/**/*.class" http://localhost:8080/find/)

# Print the response
echo "Response from server:"
echo "$response"

# Check if the response contains the expected class
if echo "$response" | grep -q "org/acme/ResourceFinder.class"; then
  echo "Test passed: org/acme/ResourceFinder.class found"
  exit 0
else
  echo "Test failed: org/acme/ResourceFinder.class not found"
  exit 1
fi