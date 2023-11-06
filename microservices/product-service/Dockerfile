# Incluir a layer do jre 17 e extrair fat jar em diretorios
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
WORKDIR extracted
# Adicionar o jar ja compilado a mais uma layer
ADD ./build/libs/*.jar app.jar
# Definir o modo de configuracao jar para extracao em camadas
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR application
# Incluir a extracao do fat jar para o escopo do projeto
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
# Expor a porta 8080 para o profile docker
EXPOSE 8080
# Iniclialicacao do microservice
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

# Compilar product-service para gerar o .jar
# ./gradlew :microservices:product-service:build
# docker build -t product-service microservices/product-service/.
# docker image ls
# docker run --rm -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker" product-service
