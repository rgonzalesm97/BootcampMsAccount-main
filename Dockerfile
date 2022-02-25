FROM openjdk:11

COPY ["./target/account-0.0.1-SNAPSHOT.jar", "/usr/app/"]

CMD ["java", "-jar", "/usr/app/account-0.0.1-SNAPSHOT.jar"]

EXPOSE 8081