#!/bin/bash

# ======================================
# CONFIGURATION
# ======================================

PROJECT_NAME="forage-app"
TOMCAT_HOME="/opt/tomcat"   
WAR_NAME="forage-app.war"

echo "======================================"
echo " Build + Deploy Spring MVC Project"
echo "======================================"

# ======================================
# 1. CLEAN + BUILD MAVEN
# ======================================

echo "[1/3] Nettoyage du projet..."
mvn clean

echo "[2/3] Compilation + packaging WAR..."
mvn package

# ======================================
# 2. CHECK WAR
# ======================================

WAR_PATH="target/${WAR_NAME}"

if [ ! -f "$WAR_PATH" ]; then
    echo "❌ ERREUR : WAR introuvable ($WAR_PATH)"
    exit 1
fi

echo "✔ WAR généré : $WAR_PATH"

# ======================================
# 3. DEPLOY TOMCAT
# ======================================

echo "[3/3] Déploiement dans Tomcat..."

# Stop Tomcat
echo "Arrêt de Tomcat..."
$TOMCAT_HOME/bin/shutdown.sh

sleep 3

# Suppression ancienne version
rm -f $TOMCAT_HOME/webapps/$WAR_NAME
rm -rf $TOMCAT_HOME/webapps/${PROJECT_NAME}

# Copie WAR
cp $WAR_PATH $TOMCAT_HOME/webapps/

# Start Tomcat
echo "Démarrage de Tomcat..."
$TOMCAT_HOME/bin/startup.sh

echo "======================================"
echo "✔ Déploiement terminé avec succès"
echo "URL : http://localhost:8080/${PROJECT_NAME}"
echo "======================================"