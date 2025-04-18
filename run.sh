#!/bin/bash

# Set path to your JavaFX SDK (change this if you saved it elsewhere)
JAVAFX_SDK=~/Downloads/javafx-sdk-24.0.1

# Run the fat jar with JavaFX modules
java --module-path "$JAVAFX_SDK/lib" \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/SoftwareConstructionProject-1.0-SNAPSHOT-jar-with-dependencies.jar
