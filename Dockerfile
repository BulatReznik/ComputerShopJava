# Используем базовый образ с Java и Gradle для сборки
FROM gradle:7.3.3-jdk17 as builder

# Копируем файлы с исходным кодом в контейнер
COPY --chown=gradle:gradle . /home/gradle/project

# Задаем рабочий каталог
WORKDIR /home/gradle/project

# Собираем проект с помощью Gradle
RUN gradle clean build

# Используем базовый образ с Java для запуска
FROM openjdk:17-jdk-slim

# Задаем переменные среды для подключения к базе данных
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:./data
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=password

# Копируем JAR-файл из предыдущего этапа внутрь контейнера
COPY --from=builder /home/gradle/project/build/libs/sbapp-0.0.1-SNAPSHOT.jar /app/computerShop.jar

# Задаем переменные среды для Gradle
ENV PATH="/opt/gradle/bin:${PATH}"

# Задаем рабочий каталог
WORKDIR /app

# Пробрасываем порт 8090
EXPOSE 8090

# Запускаем JAR-файл
CMD ["java", "-jar", "computerShop.jar"]
