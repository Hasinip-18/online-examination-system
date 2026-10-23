FROM nginx:alpine

COPY index.html /usr/share/nginx/html/index.html
COPY login.html /usr/share/nginx/html/login.html
COPY exam.html /usr/share/nginx/html/exam.html
COPY style.css /usr/share/nginx/html/style.css

EXPOSE 80
