# AMT projet - Microservice d'authentification

Jean-Luc Blanc, Gwendoline Dössegger, Rui Filipe Lopes Gouveia, Noémie Plancherel, Cassandre Wojciechowski

## Description
Cette application web est réalisé en java à l'aide du framework Springboot.

## Necessaire
Java 11
Mysql ou MariaDB
Maven

## Installation
On commence par cloner le projet : git clone https://github.com/Lindwing/AMT_Microservice.
On crée une base de donnée avec le nom : AMT_Authentification.
On modifie le fichier src/main/resources/application.properties ou on va mettre l'url de base de donnée, le nom du compte et le mot de passe

## Utilisation dans Intellij
On ouvre le projet via Intellij, dans le run/debug configuration, on sélectionne Springboot et on met com.login.micrologin.MicrologinApplication dans main class.

## Utilisation sans Intellij
On execute le fichier ./mvnw spring-boot:run

## Créer compte administrateur
Les comptes utilisateur sont tous crée avec le rôle user.
On crée un compte via l'application, puis dans la base de donnée on chege le role de l'utilisateur.