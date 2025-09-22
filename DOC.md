# Documentation for IFT3913 task, université de Montréal.

**authors** : Johanny Titouan, Mohammed Terbaoui.
**date** : Automn 2025.

## Classes containing the required tests

[NewGHUtilityTest](core/src/test/java/com/graphhopper/util/NewGHUtilityTest.java) contains all tests
for the class [GHUtility](core/src/main/java/com/graphhopper/util/GHUtility.java) that are part of
the assignment.

## PiTest

1) Effectuer une analyse de analyse de mutation

```bash
mvn -q -f core\pom.xml -Dpitest.targetClasses=com.graphhopper.util.GHUtility org.pitest:pitest-maven:1.16.1:mutationCoverage
```

Les résultats se trouvent dans le dossier [target/pit-reports](core/target/pit-reports).

2) Calculer le score de mutation des tests originaux seulement
   Dans core/pom.xml, ommenter la ligne :

```xml

<param>com.graphhopper.util.NewGHUtilityTest</param>
```

Puis lancer la même commande que dans la question 1.

Résultats :
Mutation coverage: 5/380 (~1%)

3) Calculer le score de mutation avec les nouveaux tests
   Décommenter la ligne précédemment citée dans pom.xml, puis lancer la même commande.

Résultats :
Mutation coverage: 30/380 (~8%)
