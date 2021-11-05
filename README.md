# storm_test
# refer setup_storm_cluster/setup_storm_cluster_1.1.0 for storm cluster startup

# set up Storm topo project

mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=mytoll.test -DartifactId=StormKeberosTest



vim pom.xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>mytoll.test</groupId>
  <artifactId>StormKeberosTest</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>StormKeberosTest</name>
  <url>http://maven.apache.org</url>
  <dependencies>


<dependency>
      <groupId>org.apache.storm</groupId>
      <artifactId>storm-core</artifactId>
      <version>1.1.0</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
    <build>
      <plugins>
          <plugin>
          <artifactId>maven-assembly-plugin</artifactId>
          <configuration>
            <descriptorRefs>
              <descriptorRef>jar-with-dependencies</descriptorRef>
            </descriptorRefs>
            <archive>
              <manifest>
                <mainClass>mytoll.test.StormKeberosTest</mainClass>
              </manifest>
            </archive>
          </configuration>
        </plugin>
      </plugins>
  </build>
</project>



# install java 1.8 intead of java 11

sudo yum install java-1.8.0-openjdk-src.x86_64

#sudo yum install jre will install java 11 by default which will cause the worker error, for example:
2019-11-24 02:55:10.627 STDERR Thread-0 [INFO] Unrecognized VM option 'PrintGCDateStamps'
2019-11-24 02:55:10.636 STDERR Thread-0 [INFO] Error: Could not create the Java Virtual Machine.
2019-11-24 02:55:10.637 STDERR Thread-0 [INFO] Error: A fatal exception has occurred. Program will exit.


