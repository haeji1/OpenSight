FROM node:lts-alpine AS build

WORKDIR /app

# Copy only the files needed for installing dependencies
COPY package.json .
COPY package-lock.json .

# Install dependencies
RUN npm ci

# Copy the source code
COPY . /app

# Build the source code
RUN npm run build

# The following lines related to Nginx are removed. This Dockerfile will now only build your application.
# To use the build artifacts, you would manually have to copy them from the Docker container to wherever you need them, 
# or use a Docker volume to make the build accessible to your host or another container.
