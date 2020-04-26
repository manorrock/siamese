FROM debian
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y curl tar gzip && \
    cd /usr/local && \
    curl --insecure -L -O https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.7%2B10/OpenJDK11U-jdk_x64_linux_hotspot_11.0.7_10.tar.gz && \
    tar xfvz OpenJDK11U-jdk_x64_linux_hotspot_11.0.7_10.tar.gz && \
    mv jdk-11.0.7+10 jdk && \
    rm OpenJDK11U-jdk_x64_linux_hotspot_11.0.7_10.tar.gz && \
    curl --insecure -L -O http://apache.mirrors.hoobly.com/tomcat/tomcat-9/v9.0.34/bin/apache-tomcat-9.0.34.tar.gz && \
    tar xfvz apache-tomcat-9.0.34.tar.gz && \
    mv apache-tomcat-9.0.34 tomcat && \
    rm apache-tomcat-9.0.34.tar.gz && \
    rm -rf tomcat/webapps/*docs* && \
    rm -rf tomcat/webapps/*examples* && \
    rm -rf tomcat/webapps/*manager* && \
    rm -rf tomcat/webapps/ROOT* && \
    rm -rf /var/lib/apt/lists/*
ENV PATH=$PATH:/usr/local/jdk/bin
WORKDIR /usr/local/tomcat
CMD ["bin/catalina.sh", "run"]
COPY target/siamese.war /usr/local/tomcat/webapps/ROOT.war
