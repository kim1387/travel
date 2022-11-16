
[Database ERD]
![img.png](img.png)

[RESTDocs 로 구현된 API 명세](src/docs/asciidoc/index.html)
[RESTDocs 로 구현된 API 명세 pdf](src/docs/asciidoc/api문서화.pdf)

[TODO List](src/docs/TodoList.md)

로컬 실행 후 사용 가능
[Swagger-UI](http://localhost:8080/swagger-ui/index.html)
![img_2.png](img_2.png)

Jacoco Code Coverage
![img_1.png](img_1.png)

## 로컬에서의 실행 방법
빠른 실행
먼저 docker compose up으로 MySQL과 backend 컨테이너를 실행합니다. flyway를 통해 자동으로 데이터를 채워주는 script가 작동됩니다.
```shell
$ docker compose up
```
실행을 했다면 swagger를 통해 api test가 가능합니다.
```text
http://localhost:8080/swagger-ui/index.html#/travel-controller/deleteTravel
```
