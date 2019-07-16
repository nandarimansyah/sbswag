FROM openjdk:11-jdk-slim as build
ENV PROJECT sbswag

WORKDIR /workspace/app

# install git and slim down image
RUN apt-get -y update && apt-get -y install git && apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /usr/share/man/?? /usr/share/man/??_*

#RUN apk add --no-cache openssh
#ARG SSH_PRIVATE_KEY
#RUN mkdir ~/.ssh/ && \
#    echo -e "${SSH_PRIVATE_KEY}" > ~/.ssh/id_rsa && \
#    chmod 0600 ~/.ssh/id_rsa && \
#    touch ~/.ssh/known_hosts && \
#    ssh-keyscan github.com >> ~/.ssh/known_hosts

RUN git clone https://github.com/nandarimansyah/${PROJECT}.git

WORKDIR /workspace/app/${PROJECT}
RUN chmod +x mvnw && ./mvnw install -DskipTests

FROM openjdk:11-jdk-slim
ENV PROJECT sbswag
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/${PROJECT}/target
COPY --from=build ${DEPENDENCY}/${PROJECT}.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]