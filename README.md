# easyimmo
Ce projet exemple montre la communication entre microservices via Kafka, et est basé sur une architecture event-driven.
Uses-cases :
- Lister des annonces immobilières
- Ajouter une annonce immobilière

Une annonce a un cycle de vie :
PENDING -> USER_VALIDATED (l'utilisateur est éligible pour créer une annonce) -> MAIL_SENT (un mail a été envoyé pour informer l'utilisateur de la création deson annonce) ->  CREATED (status final)

Cette gestion est asynchrone afin d'avoir une architecture distribuée, et tolérante aux pannes.

L'application se décompose en plusieurs microservices, en plus des services GATEWAY (via Spring Cloud Gateway) et DISCOVERY (via Spring-netflix-eureka).
Chaque microservice a sa propre base locale (H2).

## Microservice Ad : gestion des annonces
Point d'entrée (API), sauvegarde de l'annonce au statut PENDING.
Réception des événements des différents microservices pour mise à jour de l'annonce.

## Microservice User : gestion des users
Vérification que l'utilisateur est éligibile quand réception événement PENDING puis envoi événements  USER_VALIDATED / USER_NOT_VALIDATED

## Microservice Mail : gestion de l'envoi des mails
Envoi d'email lors de réception événement USER_VALIDATED via événements MAIL_SENT




