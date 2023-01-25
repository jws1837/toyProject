아직 슬러그 한글은 적용되지 않았습니다. 

[//]: # ([![Actions]&#40;https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg&#41;]&#40;https://github.com/gothinkster/spring-boot-realworld-example-app/actions&#41;)

> ### Spring boot + MyBatis codebase (CRUD, auth, advanced patterns, etc)
 
이 프로젝트는 Spring boot + Mybatis로 Rest Api(Crud,authentication, routing, pagination)을 만드는 데 목적이 있다.

# How it works

Spring boot (Web, Mybatis).

* DDD개념(비즈니스와 인프라스트럭처 용어 분리)과 Layered 아키텍처를 사용해 프로젝트를 구성하였습니다. 
* 지속성을 위해 "Data Mapper" 패턴을 구현하기 위해서 MyBatis를 사용하였습니다. 
* 읽기 모델과 쓰기 모델을 분리하기 위해 "CRQS" 패턴을 사용하였습니다. 

크게 4부분으로 나누어져있습니다. 

1. `api` 는 Spring MVC에 의해 구현된 WEB 레이어입니다.  
2. `core` 는 the 엔티티와 서비스 같은 비즈니스 로직과 관련된 핵심부분입니다. 
3. `application` 는 DTO와 함께 쿼리하기 위한 높은 레벨의 서비스들입니다.
4. `infrastructure`는 기술적인 요소와 관련된 모든 구현 클래스들을 포함하는 구역입니다. 

# Security

Spring Security와 연동하고 jwt 토큰 처리를 위한 다른 필터를 추가합니다

비밀키는 application.properties에 저장되어 있습니다. 운영 중에는 노출되지 않아야 합니다. 

# Database

Mysql 8.0을 이용하였습니다. 
create_tables.sql에 스키마 생성 쿼리를 작성해놓았습니다.  

# Getting started

Java 11기반으로 작성되었습니다. 

    ./gradlew bootRun

이것이 동작하는지 확인하기 위해서, 브라우저 탭에 http://localhost:8080/tags를 입력해주세요

브라우저단에서 확인하기 어려운 환경이라면 다음의 명령어를 입력해주세요. 

    curl http://localhost:8080/tags

# Help

개선사항 있으시면 언제든 포크해서 PR날려주세요. 
