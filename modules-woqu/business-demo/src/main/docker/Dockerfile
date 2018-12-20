# Version 0.0.1
FROM java:8

MAINTAINER orrin "zhangorrin@126.cn"

# 环境变量
ENV WORK_PATH /home/project/nmcp
ENV APP_NAME @project.build.finalName@.@project.packaging@
ENV APP_VERSION @project.version@

EXPOSE 20000

#USER
#USER user:group

#VOLUME
VOLUME ["/home/project", "/tmp/data"]

#ADD

#COPY
COPY $APP_NAME $WORK_PATH/

#LABEL
#STOPSIGNAL
#ARG
#ONBUILD

# WORKDIR
WORKDIR $WORK_PATH

RUN echo "Asia/Shanghai" > /etc/timezone

# ENTRYPOINT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom"]

# CMD
CMD ["-jar", "@project.build.finalName@.@project.packaging@"]