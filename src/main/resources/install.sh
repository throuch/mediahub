#!/bin/bash

SCRIPT_PATH="$(dirname $(readlink -f "$0"))"

cd ${SCRIPT_PATH}

echo "Téléchargement des fichiers IMDB, cela peut prendre du temps..."
for i in $(cat resource_list.txt); do wget  $i; done

cd -
