#!/bin/bash

SCRIPT_PATH="$(dirname $(readlink -f "$0"))"

cd ${SCRIPT_PATH}

echo "Downloading data files..."
for i in $(cat resource_list.txt); do wget $i&; done

cd -
