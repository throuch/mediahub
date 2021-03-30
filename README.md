# techtest

MediaHub demonstrator

pour télécharger les fichiers de resources sur IMDB (à lancer avant tout run)
src/main/resources/install.sh

pour lancer le programme:
export ADVERTISED_PORT=9000
export ADVERTISED_HOST=localhost
sbt run

ouvrir le navigateur pour accéder à l'API REST:

http://localhost:9000/swagger


### Détails au sujet du développement

J'ai utilisé Swagger, Akka http et Akka Stream pour implementer un web service RESTful.
L'utilisation de Akka Stream est limitée et les jointures sont faites en mémoire via des Maps.
Pour l'implémentation et le design j'ai utilisé les principes DDD (domain driven development) et SOLID et le "cake pattern" pour l'injection de dépendances [voir ici](https://www.infoq.com/fr/articles/cake-pattern-scala-explique-developpeurs-spring).

L'application est presque prête à être "dockerisée".

Limitations:
* les données ne sont pas streamées directement depuis le web

