
Generate javadoc:
  mvn javadoc:aggregate

Release on sourcesup:
  mvn release:prepare -DautoVersionSubmodules -Dpassword=xxxxxx
  mvn release:clean

Run as servlet when developing:
  mvn install
  cd esup-sifacmiss2-web-jsf-mixed
  mvn jetty:run
