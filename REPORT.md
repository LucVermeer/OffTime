# Report #

<img src="https://github.com/LucVermeer/Siesta/blob/master/WhatsApp%20Image%202019-06-27%20at%2016.02.36.jpeg" alt="A screenshot of the app." width="200"/>

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
would either be AlarmManager or WorkManager libraries. Both are capable of doing work periodically, but the difference between both is that 
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
Whenever a timer is started a ForegroundService is started as well. With this ForegroundService a notification is made where the timer is shown. The timer is update on every tick so you can see how much time you have left every second. It is also visible on the lock screen so you don't have to unlock your phone and get tempted to waste time on other apps. The ForegroundService starts a timer of its own as I couldn't find an efficient way to pass the Timer from one fragment to the Service. This seemed to be the easiest way to implement this. On every tick of the Timer in the Service a new notification is pushed to the notification channel. Giving the illusion that the notification is updated every time.
When the timer hits zero, confetti drops from the top of the screen to the bottom. This is done with a simple library. 

### Fragments ###
Because the app had a very small amount of Activities and only few foreground functionality, I decided to replace the different
activities with one main activity that could contain different fragments. This makes the navigation between tabs run smoother, as the
animation that starts a new intent is eliminated. Also transition between tabs is faster.
When the app is started all different fragments are made once and a BottomNavigationMenu is used to navigated between the three different fragments. It took some time to figure out how fragments worked, as I had never used this method for showing screens before, but once I knew how it worked it was fairly easy to use, and pretty similar to using normal activities.

### Database ###
I use an SQLite database for saving two types of data: data from the siestas and settings. The length, and a timestamp. The timestamp can be used to improve suggestions for planning Siestas. Also, a user can review his history in the app by clicking on the Statistics tab. Here users can see how many Siestas they have taken, the sum of the lengths of all siestas and the longest siesta taken.
The only setting that can be changed is your productivity. You can choose 5 different values from 0-4. Whenever this is changed (the seekbar value is different), the old entry is deleted and updated with the new value. There is no button necessary for this, as I figured it would feel smoother this way.

## Reflection ##

Overall I am pretty satisfied with the product. I used a lot of techniques I had never used before and most turned out pretty nice. However I feel like there is still a lot to do. There is lots of future work I can work on. Mainly collecting data and creating a model that learns about the user. I also want to be able to detect which apps are used, as I think this is a very important feature for the succes of this app. And the last thing I'm going to improve this summer is make it so you can't use your phone (or atleast get very motivating messages) when you are taking a Siesta. If all this is completed I think I can put my app out in the beautiful slightly oversaturated world called the App Market!
