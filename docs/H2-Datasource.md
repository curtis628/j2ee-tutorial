# H2 Datasource for WildFly (j2ee-tutorial)

This document explains how to configure a simple **H2 in-memory datasource** in WildFly so the `j2ee-tutorial` app can connect to a database.

---

## 1. Prerequisites
- WildFly 10.x running locally
- Java 1.8
- The `j2ee-tutorial` project built (`mvn clean package`)

---

## 2. Add Datasource to WildFly
Edit `$JBOSS_HOME/standalone/configuration/standalone.xml` and locate the `<datasources>` section. Add this snippet inside it:

```xml
<datasource jndi-name="java:jboss/datasources/TutorialDS"
            pool-name="TutorialDS"
            enabled="true"
            use-java-context="true">
    <!--
    DB_CLOSE_ON_EXIT=FALSE → prevents unexpected shutdown.
    AUTO_SERVER=TRUE lets multiple processes (WildFly + H2 console) connect safely. 
    -->
    <connection-url>jdbc:h2:file:/Users/tyler/tmp/h2/tutorialdb;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE</connection-url>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
        <password>sa</password>
    </security>
</datasource>
```

Also ensure the <drivers> section includes H2:
```xml
<drivers>
    <driver name="h2" module="com.h2database.h2">
        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
    </driver>
</drivers>
```

## 3. Initialize Schema
WildFly will automatically execute an `import.sql` file at startup if it exists in your WAR classpath.

In `j2ee-tutorial/src/main/resources/import.sql` you can define:
```sql
CREATE TABLE jobs (...);
CREATE TABLE grades (...);
CREATE TABLE employees (...);

-- sample data
INSERT INTO jobs (job_code, job_title) VALUES ('IT','IT Specialist');
```

## 4. Test Connection
- Start WildFly
```bash
$JBOSS_HOME/bin/standalone.sh
```
- Open Admin Console: http://localhost:9990
- Navigate: _Configuration → Subsystems → Datasources_
- You should see `TutorialDS` listed and enabled.

## 5. Use in code
Inject the datasource in your app:
```java
@Resource(lookup = "java:jboss/datasources/TutorialDS")
private DataSource ds;
```

Then you can query employees, jobs, etc. from your REST/SOAP endpoints.