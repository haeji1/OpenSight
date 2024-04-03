# FROM node:lts-alpine AS build

# WORKDIR /app

# # 라이브러리 설치에 필요한 파일만 복사
# COPY package.json .
# COPY package-lock.json .

# # 라이브러리 설치
# RUN npm ci

# # 소스코드 복사
# COPY . /app

# # 소스코드 빌드
# RUN npm run build

# # 프로덕션 스테이지
# FROM nginx:1.21.4-alpine

# # ENV TZ=Asia/Seoul
# # RUN apk add --no-cache tzdata && \
# #     cp /usr/share/zoneinfo/$TZ /etc/localtime && \
# #     echo $TZ > /etc/timezone

# COPY nginx.conf /etc/nginx/conf.d/default.conf
# ENV BACKEND_HOST backend
# ENV BACKEND_PORT 8080

# COPY docker-entrypoint.sh /usr/local/bin/
# RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# # 빌드 이미지에서 생성된 dist 폴더를 nginx 이미지로 복사
# COPY --from=build /app/dist /usr/share/nginx/html

# EXPOSE 80
# # ENTRYPOINT ["docker-entrypoint.sh"]
# CMD ["nginx", "-g", "daemon off;"]
# Use the official Node.js LTS image as a base
FROM node:lts-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to leverage Docker cache
COPY package.json .
COPY package-lock.json .

# Install dependencies
RUN npm install
RUN npm ci

# Copy the rest of your Vue.js application source code
COPY . /app

# RUN npm install
# Build your Vue.js application
RUN npm run build

# Set up a new stage from node:lts-alpine
FROM node:lts-alpine

# Install 'serve' to serve the application
RUN npm install -g serve

# Copy the built application from the previous stage
COPY --from=build /app/dist /app

# Your application will run on port 80
EXPOSE 80

# Serve your app using 'serve'
CMD ["serve", "-s", "/app", "-l", "80"]
