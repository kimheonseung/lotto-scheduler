# Lotto Scheduler

### Library & Framework
- Spring Data JPA
- Spring Data Elasticsearch (Deprecated)
- Lombok
- Jsoup
- Spring Boot (non-web)
- Gradle

### Packages
- com.devh.common : Common Package
- com.devh.scheduler.lotto.configuration : Java Configurations
- com.devh.scheduler.lotto.constant : Constants
- com.devh.scheduler.lotto.vo : DTO
- com.devh.scheduler.lotto.entity : Entity
- com.devh.scheduler.lotto.repository : Spring Data Repository
- com.devh.scheduler.lotto.core : Data Scraping Scheduler
- com.devh.scheduler.lotto.service : Service for request
- com.devh.scheduler.lotto.util : Utils

### Configuration File
- server.ip : server ip (using mtu control)
- database.ip : db ip
- database.port : db port
- database.schema : db name
- database.username : db username
- database.password : db password
- elasticsearch.ip : es ip (Deprecated)
- elasticsearch.port : es port (Deprecated)
```
server.ip=192.168.0.3
database.ip=127.0.0.1
database.port=3306
database.schema=devh
database.username=devh
database.password=1234
elasticsearch.ip=127.0.0.1 (Deprecated)
elasticsearch.port=9200 (Deprecated)
```

### Build  
1. Gradle clean & build
2. Create some directory
   - mkdir lotto-scheduler
3. Move Jar to directory
   - mv build/libs/lotto-scheduler-v1.jar lotto-scheduler
4. Create conf file
   - lotto-scheduler/conf/lotto-scheduler.properties
5. Package Directory Tree
   - lotto-scheduler
     - lotto-scheduler-v1.jar
     - conf
       - lotto-scheduler.properties
6. Check Database & Elasticsearch
7. Run Jar (java)
---
## Database & Elasticsearch Settings
- [Check docker] (https://github.com/kimheonseung/docker-devh-dbes)
