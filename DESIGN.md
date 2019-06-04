# Design #

Als een Siesta gaande is wil ik dat het apparaat een notificatie stuurt wanneer het apparaat voor iets anders wordt gebruikt.
Hiervoor wil detecteren of de app op de foreground runt, of dat de device locked is. [Background/Foreground](https://android.jlelse.eu/how-to-detect-android-application-open-and-close-background-and-foreground-events-1b4713784b57)

De app moet detecteren welke apps gebruikt worden, zodat op basis hiervan voorspellingen worden gedaan wanneer de gebruiker tijd verspilt, om het algoritme te trainen en om een voorspelling te maken. Helaas kan je niet zomaar vragen welke app runt. Je moet een app opgeven, en dan krijg je terug of deze runt of niet. Om toch te weten te kunnen komen welke apps runnen zal ik dus bijvoorbeeld kunnen kijken naar een vast aantal apps, of kijken of ik een lijst van apps op kan vragen die op het apparaat geïnstalleerd staan. Voordeel is dat de feature welke app actief is voor het reinforcement learning algoritme dus binair zal zijn.
[Check if app is active](https://stackoverflow.com/questions/22500959/detect-when-other-application-opened-or-launched)

Voor het reinforcement learning algoritme wil ik [TensorFlow Lite](https://medium.com/mindorks/android-tensorflow-lite-machine-learning-example-b06ca29226b6) gebruiken.

![App design](/app_design.png)
