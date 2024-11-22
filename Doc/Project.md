# 本项目内容

- jdk
- mysql
- maven
- redis
- nginx
- mq

https://start.spring.io/

```shell
# 分析依赖
mvn dependency:tree
# 打包命令
mvn clean package -D maven.test.skip=true
mvn clean package -P dev -D maven.test.skip=true
mvn clean package -P prod -D maven.test.skip=true
```

```
export MAVEN_HOME=/Users/xxx/apache-maven
export PATH="$MAVEN_HOME/bin:${PATH}"

mvn com.github.ekryd.sortpom:sortpom-maven-plugin:sort -Dsort.keepBlankLines -Dsort.predefinedSortOrder=custom_1

```

api doc swagger http://localhost:8080/api json http://localhost:8080/api-docs
user password(启动日志)

springboot + mybatis-plus + jwt + log

