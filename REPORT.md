Start with a short description of your application (like in the README.md, but very short, including a single screen shot).

Clearly describe the technical design: how is the functionality implemented in your code? This should be like your DESIGN.md but updated to reflect the final application. First, give a high level overview, which helps us navigate and understand the total of your code (which components are there?). Second, go into detail, and describe the modules/classes (apps) files/functions (data) and how they relate.

Clearly describe challenges that your have met during development. Document all important changes that your have made with regard to your design document (from the PROCESS.md). Here, we can see how much you have learned in the past month.

Defend your decisions by writing an argument of a most a single paragraph. Why was it good to do it different than you thought before? Are there trade-offs for your current solution? In an ideal world, given much more time, would you choose another solution?

Make sure the document is complete and reflects the final state of the application. The document will be an important part of your grade.

# Report #
Siesta is an app that helps you with concentrating. Kind off like a pomodoro timer. You set a time and during this time you promise
to yourself to work on whatever you want to work on. Because it is a timer you can see how much time you have left and this helps you to
stay concentrated. The timer is visible in a notification so you can see it on your lock screen. 
In addition to this the app also sends suggestions for planning such a timer (Siesta).

The planning of this timer was supposed to work with a reinforcement learning algorithm. This however required lots of sample data, so now 
this data is being collected to make it possible to train such an algorithm in the future when there is enough data. Whenever a 'Siesta'
is finished it is saved along with a timestamp. This timestamp can be used to improve future suggestion notifications.

### The suggestion notifications ###
To send notifications the app had to be capable of running in the background. This was one of the biggest challenges as I had zero
experience with this, and I had to create a thread that was capable of running in the background efficiently, as it would be running
most of the time when the phone is turned on (for optimal usage). After checking out some ways to do this it became clear the choice 
would either be AlarmManager or Workmanager. Both are capable of doing work periodically, but the difference between both is that 
WorkManager will defer an action if this would make the phone run more efficiently. Also WorkManager is capable of executing a task
whenever certain conditions are met. Unfortunately, later it turned out that this conditions were pretty limited, and were more along
the lines of: 'does the phone have internet connection' than: 'is this app running'. This was kind of a set back because it meant
I couldn't sent notifications when certain charactistically time wasting apps (e.g. Instagram) were running.
Nonetheless WorkManager seemed to be the better choice because of it's efficiency. 

The notifications are send from a WorkManager that is started whenever the user opens the app and the 'Notify me' switch in the Settings
fragment is checked. Based on the productivity the user choose in the seekbar above this switch notifications are send every 15, 20, 25
or 30 minutes, or not at all. The user has to do this manually as there is no model because there is no data to train the model on
(aka the cold start problem). The notifications are then send periodically at approximately these time intervals. Approximately because
the WorkManager might delay certrain actions to optimize battery life or calculation power. As notifications are not very computationaly
heavy, they are hardly ever delayed on most modern phones.


### The timer ###
Whenever a timer is started a ForegroundService is started as well. With this ForegroundService a notification is made where the timer is
shown. The timer is update on every tick so you can see how much time you have left every second. It is also visible on the lock screen
so you don't have to unlock your phone and get tempted to waste time on other apps. 

### Fragments ###

