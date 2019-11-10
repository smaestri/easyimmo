# easyimmo
Ce projet exemple montre la communication entre microservices via Kafka, et est basé sur une architecture event-driven.
Code source de l'article https://www.effectivecoding.fr/index.php/2019/11/10/communication-entre-microservices-avec-spring-cloud-et-kafka/

Uses-cases :
- Lister des annonces immobilières
- Ajouter une annonce immobilière

Une annonce a un cycle de vie :
PENDING -> USER_VALIDATED (l'utilisateur est éligible pour créer une annonce) -> MAIL_SENT (un mail a été envoyé pour informer l'utilisateur de la création deson annonce) ->  CREATED (status final)

Cette gestion est asynchrone afin d'avoir une architecture distribuée, et tolérante aux pannes.

## Microservies

L'application se décompose en plusieurs microservices, en plus des services GATEWAY (via Spring Cloud Gateway) et DISCOVERY (via Spring-netflix-eureka).
Chaque microservice a sa propre base locale (H2).

### Microservice Ad : gestion des annonces
Point d'entrée (API), sauvegarde de l'annonce au statut PENDING.
Réception des événements des différents microservices pour mise à jour de l'annonce.

### Microservice User : gestion des users
Vérification que l'utilisateur est éligibile quand réception événement PENDING puis envoi événements  USER_VALIDATED / USER_NOT_VALIDATED

### Microservice Mail : gestion de l'envoi des mails
Envoi d'email lors de réception événement USER_VALIDATED via événements MAIL_SENT

## Installation

Vous avez besoin pour ce projet, d'installer [kafka](https://kafka.apache.org/quickstart), [nodejs](https://nodejs.org/en/download/) et [angular-cli](https://cli.angular.io/).

### Lancer Zookeeper
`bin/zookeeper-server-start.sh config/zookeeper.properties`

### Lancer Kafka
`bin/kafka-server-start.sh config/server.properties`

### Lancer ms eureka (à lancer avant les autres!)
Lancer la classe main

### Lancer ms gateway
Lancer la classe main

### Lancer chaque microservice (ad, user, mailing)
Lancer la classe main associée

### Lancer le frontend Angular
Dans le répertoire `frontent`, lancer `npm start`

### Vérifier que ça fonctionne
Connectez-vous sur localhost:4200, et publier une annonce. Selon le temps de traitement et du fait que les traitements soient asynchrones, l'annonce peut ne pas etre au statut final "CREATED" immédiatement. Un refresh (F5) devrait l'afficher au status CREATED.



