#!/bin/bash
# setup.sh - Install dependencies required for building SmartTube
set -e

# Update package lists
sudo apt-get update

# Install OpenJDK 11 and other tools
sudo apt-get install -y openjdk-11-jdk git curl wget unzip

# Additional dependencies can be installed here

