call mvn -B -s settings.xml -DskipTests=true clean package
call java -Dspring.profiles.active="jpa,heroku" -jar target/dependency/webapp-runner.jar target/*.war