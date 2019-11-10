# easyimmo
Ce projet exemple montre la communication entre microservices via Kakfka, et donc basé sur une architecture event-driven.
Uses-cases :
- Listes des annonces
- Ajouter une annonce

Une annonce a un cycle de vie :
PENDING -> USER_VALIDATED (l'utilisateur est éligible pour créer une annonce) -> MAIL_SENT (un mail a été envoyé pour informer l'utilisateur de la création deson annonce) ->  CREATED (status final)

L'application se décompose en plusieurs microservices, en plus des services GATEWAY (via Spring Cloud Gateway) et DISCOVERY (via Spring-netflix-eureka) :

## Microservice Ad : gestion des annonces
Point d'entrée (API), sauvegarde de l'annonce au statut PENDING. Envoi message.
Réception des événements des différents ms pour mise à jour de l'annonce.

## Microservice User : gestion des users
Vérification que l'utilisateur est éligibile quand réception événement PENDING puis envoi événements  USER_VALIDATED / USER_NOT_VALIDATED

## Microservice Mail : gestion de l'envoi des mails
Envoi d'email lors de réception événement USER_VALIDATED via événements MAIL_SENT




