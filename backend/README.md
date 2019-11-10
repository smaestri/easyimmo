# kafka-ms
Ce projet exemple montre la communication entre microservices via Kakfka, et donc basé sur une architecture event-driven.

## Microservice Ad : gestion des annonces
Point d'entrée (API), sauvegarde de l'annonce au statut PENDING. Envoi message.
Réception de l'événement final EMAIL_SENT pour mettre l'annonce au statut final "CREATED"


## Microservice User : gestion des users
Vérification que l'utilisateur est éligibile et envoi les événements en conséquence


## Microservice Mail : gestion de l'envoi des mails
Si l'utilisateurest validé, envoi du mail


