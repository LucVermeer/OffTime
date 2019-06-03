# Siesta / Leisure / Lull /Getting Off #

Lull (noun)
1.
a temporary interval of quiet or lack of activity.

Siesta is een app die je helpt om tijd offline te nemen. De app detecteerd wanneer je behoefte hebt aan wat tijd zonder je telefoon, zij het omdat je wat anders te doen hebt, of omdat je even rust nodig hebt. Op het moment dat de app denkt dat je dit nodig hebt stuurt de app een notificatie. Je hebt dan de optie om een 'Siesta' in te plannen; tijd zonder je telefoon. Er wordt een timer gestart, de tijd hiervan kan je zelf inplannen. Gedurende deze tijd kan je niet op je telefoon. Wanneer je dit wel probeert wordt een melding verzonden met een motiverende tekst, en het evenement waar je mee bezig zou moeten zijn (eventueel te halen uit Google Calendar). 

Het is ook zonder notificatie mogelijk een 'Siesta' in te plannen door gewoon de app te openen.

Als extra wil ik achievements op basis van statistieken implementeren als extra motivatie.

Hoe weet de app wanneer je dit nodig hebt?
Dit wordt geleerd door een reinforcement learning algoritme. Of de notificatie succesvol is of niet wordt bepaald of een notificatie wordt genegeerd of niet (binair). 
Features kunnen zijn:
 * App die in gebruik is op dat moment (categorical; one-hot?)
 * Tijdstip (numeriek)
 * Weekend (binair)
 
 -- Meer interessante features kunnen zijn: -- 
 
 * Of je met iets anders bezig moet zijn (Google Calendar, binair)
 * Hoe lang je die app gebruikt (numeriek)
 * Hoe lang je op je telefoon zit (numeriek)
 * ...
 
 Eventueel valkuilen: wanneer geen notificaties worden gestuurd is de succesrate 100%. Weet niet zeker of dit probleem wordt maar iets om over na te denken.

Wellicht kan niet alles worden geïmplementeerd (waarschijnlijk niet zelfs), maar vanuit hier wil ik wat essentiële features uitkiezen.
