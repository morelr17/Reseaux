# Rapport pour le TP3

---

# Protocole TCP

## Exercice 1

### Q1

- Créée une Socket Serveur 
- Accepté la socket client dans le serveur 
- Envoi du message par le serveur 
- Fermeture de la socket du client 

### Q2

- Si la socket du serveur a bien été créé sans erreur 
- Si la socket du client a bien été accepté 
- Si le message s'est bien envoyé 
- Si la socket du client a bien été fermé  

### Q3

On lance le serveur dans un terminal de la machine 
Ensuite, on lance telnet dans un autre terminal executé :

```
telnet [ip][2021]
```

### Q4



## Exercice 2

### Q1

Un thread est crée à chaque nouvelle connection au serveur. Il s'active lorsqu'il reçoit une nouvelle demande d'acceptation

### Q2

Il faut utiliser l'InputStream de la Socket avec un read() 
Mais il est plus facile d'utiliser un bufferedReader pour pouvoir utiliser la commander readLine() 

### Q3

Les utilisateurs sont dans une liste qui sont dans la classe Server, il suffit donc de faire une boucle qui parcours tout la liste de tout les utilisateurs 
et de Print le message à chaque utilisateur en récupérant leurs Output 

### Q4

Pour gérer la trace toutes les connections on va envoyer un message sur le terminal à chaque connection et déconnexion (sur le serveur) d'un client.
Si on voulait une trace, il faudrait donc faire en sorte que la sortie du terminal soit dans un fichier.

### Q5

Lorsque qu'un client telnet quitte le Serveur le Serveur continue normalement mais si on ferme la fenetre du serveur alors la le programme arrete de fonctionner 
