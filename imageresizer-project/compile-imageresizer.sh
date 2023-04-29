#!/usr/bin/env bash

rm -r outDir

javac -d outDir --module-source-path imageresizer/*/src/ -m main.app