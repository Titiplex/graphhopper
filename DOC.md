# Documentation for IFT3913 task, université de Montréal.

**authors** : Johanny Titouan, Mohammed Terbaoui.
**date** : Automn 2025.

## Classes containing the required tests

[NewGHUtilityTest](core/src/test/java/com/graphhopper/util/NewGHUtilityTest.java) contiens les tests de la classe
[GHUtility](core/src/main/java/com/graphhopper/util/GHUtility.java)

[ArrayUtilTest](core/src/test/java/com/graphhopper/util/ArrayUtilTest.java) contiens les tests de la classe
[ArrayUtil](core/src/main/java/com/graphhopper/util/ArrayUtil.java)

[DistanceCalcEuclideanTest](core/src/test/java/com/graphhopper/util/DistanceCalcEuclideanTest.java) contiens les tests de la classe
[DistanceCalcEuclidean](core/src/main/java/com/graphhopper/util/DistanceCalcEuclidean.java)

[IntersectionValuesTest](core/src/test/java/com/graphhopper/util/IntersectionValuesTest.java) contiens les tests de la classe
[IntersectionValues](core/src/main/java/com/graphhopper/util/details/IntersectionValues.java)

## Couverture de code avant/après nos tests

Avant:

![1](screenshots/coverage1.png)
![2](screenshots/coverage2.png)
![3](screenshots/coverage3.png)
![4](screenshots/coverage4.png)
![5](screenshots/coverage5.png)

Après:

![6](screenshots/coverage6.png)
![7](screenshots/coverage7.png)
![8](screenshots/coverage8.png)
![9](screenshots/coverage9.png)
![10](screenshots/coverage10.png)

## Tests de mutations avec PiTest

1. Effectuer une analyse de analyse de mutation

```bash
mvn -q -f core/pom.xml -Dpitest.targetClasses=com.graphhopper.util.GHUtility,com.graphhopper.util.ArrayUtil,com.graphhopper.util.DistanceCalcEuclidean,com.graphhopper.util.details.IntersectionValues org.pitest:pitest-maven:1.16.1:mutationCoverage
```

Les résultats se trouvent dans le dossier [target/pit-reports](core/target/pit-reports).

2. Calculer le score de mutation des tests originaux seulement
   Dans core/pom.xml, commenter les lignes :

```xml

<param>com.graphhopper.util.NewGHUtilityTest</param>
<param>com.graphhopper.util.DistanceCalcEuclideanTest</param>
<param>com.graphhopper.util.IntersectionValuesTest</param>
<param>com.graphhopper.util.ArrayUtilTest</param>
```

Puis lancer la même commande que dans la question 1.

##

Résultats :
Mutation coverage pour [GHUtility](core/src/main/java/com/graphhopper/util/GHUtility.java) : 5/380 (~1%)

3. Calculer le score de mutation avec les nouveaux tests
   Décommenter les lignes précédemment citées dans pom.xml, puis lancer la même commande.

Résultats :
Mutation coverage: 199/588 (~34%)
[1](screenshots/pitest-1.png)
[2](screenshots/pitest-2.png)
[3](screenshots/pitest-3.png)

## Test avec Java-faker

Dans [NewGHUtilityTest](core/src/test/java/com/graphhopper/util/NewGHUtilityTest.java) les trois derniers tests sont réalisés avec JavaFaker.
-> JavaFaker a été utilisé pour input des valeurs randoms qui servent à tester le bon fonctionnement du graph.
