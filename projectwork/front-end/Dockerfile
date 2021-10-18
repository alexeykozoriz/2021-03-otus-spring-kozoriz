# Stage 1
FROM node:16.9-alpine as build-step
WORKDIR /app
COPY . /app
RUN npm install
RUN npm run build --prod

# Stage 2
FROM nginx:1.21.3-alpine
COPY --from=build-step /app/dist/front-end /usr/share/nginx/html
