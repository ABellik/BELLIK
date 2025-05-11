Projet Java : Vigie des Médias @ Et3 IIM
<br>
Polytech Paris-Saclay | 2024-25
<br>
Réalisé par Adam BELLIK

___

# Configuration d'Intellij pour l'exécution du projet

Le projet Vigie des Médias peut être exécuté de 2 manières différentes :
- En mode console (l'utilisateur interagit avec une console).
- Avec une interface graphique (l'utilisateur interagit depuis une interface JavaFX) : Recommandé

-------

## Configuration

### A. Téléchargement du projet.

Le téléchargement peut se faire:
- via le fichier ZIP envoyé par mail
- en clonant le git de mon projet : https://github.com/ABellik/BELLIK.git
  - Le projet se trouve sur la branche master
  - Si c'est la branche main qui a été cloné, alors :
    - Cliquer en haut à gauche sur "main"
    - Chercher "Remote" puis "origin" puis "master" puis choisir "New branch from 'origin/master'..."

### B. Configuration / Installation de la version de Java à utiliser.
1. Allez dans *File* -> *Project Structure*.

2. Renseignez :
- *SDK* avec une version de Java 23 (de préférence Oracle OpenJDK 23)

### C. Configuration pour l'interface console.

1. Sur le menu d'Intellij, cliquez sur *Run* en haut de l'écran, et faites *Edit configurations*.
2. Cliquez sur *Add new run configuration...* -> *Application*.
3. Il y a un encadré rouge où est écrit *Main class*. Cliquez sur l'icône à droite et choisissez *Main of main*, puis faites *OK*, ensuite *OK*.
4. L'exécution console est normalement prête.

### C. Configuration pour l'interface graphique.

1. Identifiez un fichier appelé "pom.xml" et faites clic droit dessus, et faites dans *Maven* -> *Sync project*.
2. Sur le menu d'Intellij, cliquez sur *Run* en haut de l'écran, et faites *Edit configurations*.
3. Cliquez sur *Add new run configuration...* -> *Maven*.
4. Dans l'encadré où est écrit *Command Line*, écrivez *javafx:run*, puis faites *OK*, ensuite *OK*
5. L'exécution graphique est normalement prête. 


### D. Exécution du projet.

Essayez de cliquer sur l'icône Play en haut de l'écran pour vérifier que l'exécution se réalise correctement.
Si des warnings apparaissent à l'exécution, c'est normal.

Si cela ne fonctionne pas, voici un autre moyen d'exécuter utilisant un exécutable .jar :
- Sur le terminal d'Intellij, entrez : 
  - *java --module-path javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml -jar target/vigie-application-1.0-SNAPSHOT-shaded.jar*