Dieses Projekt ist eine Java-basierte Webanwendung, die Tierbilder klassifiziert. Nutzer:innen können ein Bild per Drag & Drop hochladen und erhalten die Top 3 erkannten Tierklassen mit Wahrscheinlichkeit. Die Bildklassifikation erfolgt über das vortrainierte Modell ResNet18 aus der Deep Java Library (DJL).

Live-Demo

Azure-Webapp:https://tierbilderkennung.azurewebsites.net

Technologien

Java 21

Spring Boot 3

Deep Java Library (DJL)

HTML5 + JavaScript + CSS (Drag & Drop UI)

Docker + GitHub Actions

Azure Web App for Containers

Beispiel


Ergebnisbeispiel (Top 3 Klassen):

[
  { "klasse": "tabby cat", "confidence": 43.86 },
  { "klasse": "tiger cat", "confidence": 34.19 },
  { "klasse": "Egyptian cat", "confidence": 18.22 }
]

Projektbeschreibung

Diese Anwendung läuft als Spring Boot Webservice und nutzt die Deep Java Library (DJL), um ein Bild per REST-Endpunkt zu analysieren. Im Frontend kann ein Bild per Drag & Drop hochgeladen werden.

Im Backend wird das ResNet18-Modell (ImageNet) aus dem Model Zoo geladen und lokal ausgeführt. Die Top 3 Ergebnisse werden als JSON zurückgegeben und im UI formatiert angezeigt.

Die App wurde in einem Docker Container verpackt und auf Azure Web App for Containers deployed. Das Docker-Image liegt öffentlich auf DockerHub und wird via GitHub Actions bei jedem Push automatisch aktualisiert.

Projektstruktur:

picture-classification/

├── resnetapp/            
│ 
  ├── Dockerfile
│
  ├── src/...
│
  └── pom.xml

├── .github/workflows/     
│   └── deploy.yml

└── README.md

Lokaler Start (ohne Docker)

cd resnetapp
./mvnw spring-boot:run

Dann im Browser: http://localhost:8080

Docker (lokal)

docker build -t resnetapp ./resnetapp
docker run -p 8080:8080 resnetapp

Deployment auf Azure

Dockerfile liegt im Projekt unter resnetapp/

DockerHub Image: bertaleo1/resnetapp

Deployment erfolgt über Azure Web App (Linux, Docker)

Port-Konfiguration via App Setting: WEBSITES_PORT=8080

GitHub Actions CI/CD

Der Workflow triggert:

- Checkout Code
- Set up Java 21
- chmod +x mvnw
- Maven Build
- Docker Build
- Docker Push nach DockerHub

Workflows liegen unter .github/workflows/deploy.yml.
Secrets: DOCKER_USERNAME, DOCKER_PASSWORD im Repository gesetzt.

