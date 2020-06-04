![Docker-Image (Rest-Service)](https://github.com/matthiaswronka/cloud-native-cookbook/workflows/Docker-Image%20(Rest-Service)/badge.svg) 
![Java Checkstyle](https://github.com/matthiaswronka/cloud-native-cookbook/workflows/Java%20Checkstyle/badge.svg)

# cloud-native-cookbook
Eine Sammlung kleiner Beispiel-Apps.

## Was ist es?
Ein Bild sagt mehr als Tausend Worte und ein Code-Snippet mehr als 1kg Dokumentation. Getreu diesem Motto werden hier
exemplarisch Probleme gelöst, denen man bei der Entwicklung von cloud-native Apps begegnet. Die Beispiel-Sammlung ist nicht
vollständig und kann es auch nicht sein. Aber sie soll Hilfestellung geben, wenn man zwar weiß, dass man in die cloud
will, aber nicht so genau weiß, wie man dahinkommt.

## Was ist es nicht?
Keines dieser Beispiele ist eine Handlungsanweisung oder eine Programmierrichtlinie. Jedes Problem kann nach einer individuellen
Lösung verlangen und die kann hier nicht vorgedacht werden. Es würde auch das gesteckte Ziel untergraben, Freiheit in der Wahl der
Mittel für den Entwickler einzuräumen.

## Rahmenbedingungen
Wenn hier von cloud-native Apps die Rede ist, dann ist damit gemeint, dass wir unsere Anwendungen in [Docker](https://docker.com)-Containern 
modularisieren, die dann in einem [Kubernetes-Cluster](https://kubernetes.io/de/) (K8s-Cluster) betrieben werden sollen. Diesen K8s-Cluster kann man in
den bekannten stages (dev -> prod) als gegeben voraussetzen - wir nutzen ihn als [managed-Service von Azure](https://azure.microsoft.com/de-de/services/kubernetes-service/).

Für das Management der Cluster und der Deployments werden einige zentrale Vorgaben gemacht, etwa, dass die Cloudressourcen in Azure
per [Terraform](https://www.terraform.io/) gescriptet werden, dass K8s-Apps per [Helm](https://helm.sh/) paketiert werden und dass
der Code und Continoues Integration in [GitHub](https://github.com/). Diese sind allerdings im Detail nicht Gegenstand dieser
Dokumentation, auch wenn sie sich in den Beispielen teilweise niederschlagen.

Wenn man eine Anwendung für die cloud entwickeln möchte, dann muss man dieser Entscheidung bereits im Entwicklungsprozess und in
grundlegenden Architekturentscheidungen Rechnung tragen. Wesentliche Erfolgsfaktoren sind 

- ein hoher Automatisierungsgrad sowohl im Entwicklungsprozess (Continouos Integration, Testautomatisierung, Statische Code- und 
Securityanalysen) als auch bei der Bereitstellung benötigter Infrastruktur (Compute, Storage, Netzwerk, ...) und Deployments. 
- Zustandslosigkeit (keine Sessions)
- Dependency-Management

Einen guten, technologieneutralen Einstieg in dieses Thema bietet die [12 Factor App](https://12factor.net/).

## Wo fange ich an?
Wenn K8s als Zielplattform - wenn man es denn so nennen mag - schon definiert ist, dann ist es vermutlich eine gute Idee, sich 
mit ein paar [grundlegenden Konzepten](https://kubernetes.io/de/docs/concepts/overview/what-is-kubernetes/) vertraut zu machen. Wer
bei _Docker_ zunächst an Schuhe denkt, sollte vielleicht auch [hier noch ein bisschen lesen](https://docs.docker.com/get-started/).

Worum geht es also? Wir - als Entwickler - wollen eine Anwendung programmieren. Die Programmiersprache, die eingesetzten Frameworks
und auch die Laufzeitumgebung können wir dem Problem und unseren Kenntnissen entsprechend selbst wählen (und ja: regulatorische Anforderungen
gelten trotzdem und müssen von dem, der es einführt, erfüllt werden). Warum können wir das auf einmal tun? Weil es keine zentrale
Stelle gibt, die einen ApplicationServer (wie z.B. einen Websphere) verbindlich vorschreibt, sondern wir unsere Laufzeitabhängigkeiten
einfach mit in den Docker-Container packen. Java, Kotlin, Ruby, Python: vom Grundsatz alles ok. Ihr seid selbst dafür verantwortlich,
welche Technologie ihr wählt. Ein paar Auswahlkriterien lassen sich aber anführen:

- ist die Technologie verbreitet, so dass ich leicht Mitstreiter finde?
- ist sie gut geeignet für die Problemstellung?
- ist sie ressourcenschonend (Speicher und CPU kosten Geld)?
- erhalte ich den benötigten Support?
- ermöglicht sie kurze Startups und Roundtrips in der Entwicklung?

Wenn unsere Anwendung im Container verpackt ist (das wollen wir regelmäßig, zum Beispiel bei jedem Merge
in den master-branch automatisch tun), ist das vermutlich noch nicht alles. Wahrscheinlich benötigen wir weitere
Anwendungen, vielleicht ein Angular- oder React-Frontend und dann noch einen Virenscanner als Microservice.
All das packen wir in einzelne Docker-Container.

Und spätestens jetzt kommt gedanklich K8s ins Spiel: alle einzelnen Container müssen zu einem Gesamtsystem
kombiniert werden. Mit K8s lässt sich steuern, welche Docker-Images als Pods (gern auch nach Last automatisch
skaliert) gestartet werden, über welche Ports innerhalb des K8s-Clusters kommuniziert wird und welche Ports
für ein- und ausgehenden Netzwerkstrecken geöffnet werden. Dazu kommen noch diverse Aspekte wie Health-Checks,
Restarts, Secrets, Ressourcenbedarf - kurz: alles, was wir klassisch dem Provider ins Betriebsführungshandbuch
geschrieben haben.

## Und weiter?
Im Zielbild müssen wir uns nicht um alles selber kümmern. Persistenz zum Beispiel. Davon will Kubernetes eigentlich
nichts wissen. K8s will Services schnell skalieren und verfügbar halten. Ein Pod muss jederzeit sterben
können, um auf einem anderen Node direkt neu gestartet zu werden. Das Gesamtsystem soll so etwas nicht stören.

Datenbanken stellen da andere Anforderungen und werden von den Public-Cloud-Providern hoch verfügbar mit 
Backups und allem Pipapo als managed Service auf Knopfdruck (Entschuldigung: natürlich per IaC-Skript) bereit
gestellt. Das gilt analog für andere _Middleware_.

> In diesen Beispielen wird für die lokale Entwicklung tatsächlich auch K8s genutzt, um eine lokale
> Datenbank per Helm aufzusetzen. In Azure, AWS, Google Cloud sollte man sich den Aufwand sparen.

## Garden
Einer der [12 Faktoren](https://12factor.net) ist die _Dev/Prod-Parity_, also der Anspruch, Entwicklung und
Produktion technologisch so einheitlich wie möglich zu halten. Warum also nicht schon auf dem Entwickler-Client
mit Docker und K8s arbeiten? Ein Grund könnte sein, dass man schlicht nicht genügende Power zur Verfügung
hat, aber wenn das gegeben ist, findet sich mit [garden.io](https://garden.io) ein Werkzeug, das eine
lokale Entwicklung mit Docker-Containern und Kubernetes auf dem Entwickler-Rechner gut unterstützt und
dabei die Abstraktionen der Zielumgebung (Service, Pod, Ingress, Job, ...) nutzt. Deshalb nutzen diese Beispiele
garden.io um frühestmöglich "in Containern zu denken".
