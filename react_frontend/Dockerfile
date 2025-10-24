FROM node:18-alpine  AS builder

WORKDIR /app

COPY package.json ./
COPY package-lock.json ./
RUN npm ci


COPY . .

RUN npm run build


FROM nginx:alpine

# Remove nginx pages
RUN rm -rf /usr/share/nginx/html/*

# Copy dist to html serve
COPY --from=builder /app/dist /usr/share/nginx/html
RUN ls /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


