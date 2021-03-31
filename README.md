# techtest

MediaHub demonstrator

pour télécharger les fichiers de resources sur IMDB (nécessite wget, à lancer avant tout run):

```src/main/resources/install.sh```

_installation manuelle des ressources (optionel):_
copier dans src/main/resources
* https://datasets.imdbws.com/name.basics.tsv.gz
* https://datasets.imdbws.com/title.principals.tsv.gz
* https://datasets.imdbws.com/title.basics.tsv.gz
* https://datasets.imdbws.com/title.episode.tsv.g

pour lancer le programme:

```export ADVERTISED_PORT=9000```

```export ADVERTISED_HOST=localhost```

```sbt run```

ouvrir le navigateur pour accéder à l'API REST:

http://localhost:9000/swagger


### Détails au sujet du développement

J'ai utilisé Swagger, Akka http et Akka Stream pour implementer un web service REST.
L'utilisation de Akka Stream est limitée et les jointures sont faites en mémoire via des Maps.
Pour l'implémentation et le design j'ai utilisé les principes DDD (domain driven development) et SOLID et le "cake pattern" pour l'injection de dépendances [voir ici](https://www.infoq.com/fr/articles/cake-pattern-scala-explique-developpeurs-spring).

L'application est presque prête à être "dockerisée".

Limitations:
* les données ne sont pas streamées directement depuis le web
* recherche sur le titre international uniquement, match exact -> pas de match partiel du titre ou avec fautes d'orthographe, de ponctuation, d'accents, d'espaces, etc...
* performances moyennes
* impossible de passer les variables d'environnement à _sbt_ autrement que comme dans la procédure de lancement indiquée
