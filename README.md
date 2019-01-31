# massive-guacamole-remote-guac1

------------------------------------------------------------
 About this README
------------------------------------------------------------

This README is intended to provide quick and to-the-point documentation for
technical users intending to compile parts of Guacamole themselves.

Distribution-specific packages are available from the files section of the main
project page:

    http://sourceforge.net/projects/guacamole/files/

Distribution-specific documentation is provided on the Guacamole wiki:

    http://guac-dev.org/


------------------------------------------------------------
 What is massive-guacamole-remote?
------------------------------------------------------------

massive-guacamole-remote is a Java library for use with the Guacamole web
application which supports shibboleth authentication. It provides a static set of
connections to anyone using the web application.

The connections are authorized through a backend MySQL database
------------------------------------------------------------
 Compiling and installing massive-guacamole-remote
------------------------------------------------------------

massive-guacamole-remote is built using Maven. Building massive-guacamole-remote
compiles all classes and packages them into a redistributable .jar file. This
.jar file can be installed in the library directory configured in
guacamole.properties such that the authentication provider is available.

1) Run mvn package

    $ mvn package

    Maven will download any needed dependencies for building the .jar file.
    Once all dependencies have been downloaded, the .jar file will be
    created in the target/ subdirectory of the current directory.

    If this process fails, check the build errors, and verify that the
    contents of your settings.xml file is correct.

2) Extract the .tar.gz file now present in the target/ directory, and
   place the massive-guacamole-remote-0.9.5.jar files in the extracted lib/ subdirectory in the library
   directory specified in guacamole.properties.

    You will likely need to do this as root.

    If you do not have a library directory configured in your
    guacamole.properties, you will need to specify one. The directory
    is specified using the "lib-directory" property.

3. Create a mysql database

    a). Create guacamole database
    b). Create vnc_user  and vnc_connection tables in the database
    c). Please reference the database table schema in the /doc/massive-guacamole-remote.schema

4) Configure guacamole.properties

    An additional property is required by massive-guacamole-remote which defines
    where the configuration file is found. It must be added to your
    guacamole.properties:

    #Required Env Attribute, please specify the required env attribute name here
    required-env-attribute: remote_user

    # Configuration for MySQL connection
    mysql-hostname: localhost
    mysql-port:     3306
    mysql-database: guacamole
    mysql-username: root
    mysql-password:

    # Authentication provider class
    auth-provider: au.org.massive.guacamole.auth.CookiesAuthenticationProvider

    #lib directory, where the massive-guacamole-remote-0.9.5.jar placed in
    lib-directory:/srv/guacamole/

    See the sample doc/guacamole.properties for more details.

 5) Copy the commons-lang-2.6 and mysql-connector-java-5.1.34 jars

    Extract the .tar.gz file now present in the target/ directory, and
    place the commons-lang-2.6 and mysql-connector-java-5.1.34.jar files into
    the WEB-INF/lib directory in the deployed guacamole web app.

6) Restart Tomcat

    The guacamole.properties file as well as any authentication provider .jar
    files are only read when Tomcat starts. This goes for any other servlet
    container as well.

    You must restart Tomcat before any of the above changes can take effect.


