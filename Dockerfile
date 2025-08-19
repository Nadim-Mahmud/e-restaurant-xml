FROM tomcat:9.0-jdk8

# Clean out the default ROOT and examples (optional, for a clean server)
RUN rm -rf /Users/nadim/MyFiles/tools/apache-tomcat-9.0.108/webapps/*

# Copy your WAR into Tomcat's webapps directory
COPY ./build/libs/E-Staurant-xml-1.0-SNAPSHOT.war /Users/nadim/MyFiles/tools/apache-tomcat-9.0.108/webapps/restaurant.war

# Expose the port the app runs on
EXPOSE 8080


