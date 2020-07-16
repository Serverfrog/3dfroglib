FROM openjdk:14-alpine
COPY build/libs/3dfroglib-*-all.jar 3dfroglib.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "3dfroglib.jar"]