README
======
<img width="200" border="1"   src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-010527.png"> - <img  width="200"  src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-010842.png"> - <img width="200"  src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-010905.png"> - <img width="200"  src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-010934.png"> - <img width="200"   src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-010956.png"> - <img width="200"   src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-011016.png"> - <img width="200"  src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-011039.png"> - <img width="200" src="https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screenshots/device-2017-05-03-011102.png">
 
 
Android-Open-Radio - Radio App for Android
----------------------------------

Android-Open-Radio is a bare bones app for listening to radio programs over the internet. The app stores stations as files on your device's external storage. It currently understands streams encoded in MP3 and OGG.

Important note: This is an app of type BYOS ("bring your own station"). It does not feature any kind of built-in search option. You will have to manually add radio stations.

Android-Open-Radio is free software. It is published under the [MIT open source license](https://opensource.org/licenses/MIT). Want to help? Please check out the notes in [CONTRIBUTE.md](https://github.com/malah-code/Android-Open-Radio/blob/master/CONTRIBUTE.md) first.

This open source application based on (y20k/transistor) "https://github.com/y20k/transistor".

New in 3.1.3 (Beta) (Build 39)
------------------------------
- Add search functionality to search title and subtitle.
- Add Default XML source for radio stations. if found it will load this XML first.
- Fix UI issues. 

New in 3.1.2 (Beta) (Build 36)
------------------------------
- Add Favorit functionality to listing and detail pages.
- Add basic categorization to the listing page.
- Prepare db new field for tags, to be ready for next feature of search by tags.
- And also, some code fixes and enhancements. 

New in 3.1.1 (Beta) (Build 35)
-----------------------------
- Use DB with SQLITE as main storage of the application. this will reduce the dependancy of files, and open application for features that we will have with SQLITE that we can add it to the application later easily, like Sorting,Filer,Grouping,relation with other tables like categories/rating/favourits, add easily add more station metadata anytime, and many more.
- Adding more metadata to stations (currently no way to change all metadata throw UI, we can only import xml with all metadata of channel, we may add editing later in next versions).
- new station rating modules (currently it's local rating only, not sync to central rating, may be later in next versions)
- Using Facebook fresco image viewer (SimpleDraweeView) with it's great loading features
- Apply Material design components and transitions
- Integrate with Firebase compoenents (Authentication , Analytics)
- Add login by Google account function (currently nothing happend after login, but in the next versions we can connect user by ID and save his data to cloud for example, and many more.)
	

Install Android-Open-Radio
------------------
Download the latest beta from HockeyApp :

[<img src="https://transistor-open.firebaseapp.com/assets/hockeyapp.jpg" width="192">](https://rink.hockeyapp.net/apps/2b8994e7a60b485dad0a5507ceb05c01)

[<img alt="Chart?cht=qr&amp;chl=https%3a%2f%2frink.hockeyapp" src="https://chart.googleapis.com/chart?cht=qr&amp;chl=https%3A%2F%2Frink.hockeyapp.net%2Fapps%2F2b8994e7a60b485dad0a5507ceb05c01&amp;chs=192x192">](https://rink.hockeyapp.net/apps/2b8994e7a60b485dad0a5507ceb05c01)

How to use Android-Open-Radio
---------------------
Vew a short video on how to use Android-Open-Radio on [Vimeo](https://vimeo.com/215778690).
	[![IMAGE ALT TEXT HERE](https://raw.githubusercontent.com/malah-code/Android-Open-Radio/Enhancements-v1/screen.png)](https://vimeo.com/215778690#)
### How to add a new radio station?
The easiest way to add a new station is to search for streaming links and then choose Android-Open-Radio as a your default handler for those file types. You can also tap the (+) symbol in the top bar and paste in streaming links directly. Please note: Android-Open-Radio does not feature any kind of built-in search option.
Also, it's new in that version , you are able to add bulk of stations with it's metadata from XML file, file format can be as below sample 

https://transistor-open.firebaseapp.com/radios.xml

Xml file should contains nelow metadata :
* unique_id :Station  UNIQUE ID: used to identify station in DB, and should be UNIQUE and it can be string value
* title : Station Title
* subtitle : Station Sub Title
* image : (IMAGE_PATH) This is image link (this should be external http link or in storage file link to image)
* small_image_URL : (SMALL_IMAGE_PATH) : This is small image link (icon) (this should be external http link or in storage file link to image)
* uri : (StreamURI): Station Stream URI (Mandatory)
* Station DESCRIPTION : (metadata) - string value and not have any formats
* content_type : Station CONTENT TYPE (value auto detected / or can be read from xml metadata - if it's imported using xml file)
* rating : Station RATING
* category : Station CATEGORY
* markdown_description : (markdown Description) : Station markdown Description , with markdown formal, it will be visible inside in-app in detail view card.
 
# Sample Xml file
            <channels>
                        <entry>
                                    <unique_id>BBCArabic_1</unique_id>
                                    <title>BBC Arabic</title>
                                    <subtitle>BBC Arabic Main Channel</subtitle>
                                    <description>This is description of BBC Arabic Main Channel</description>
                                    <markdown_description>...</markdown_description>
                                    <small_image_URL>
                                    http://www.liveonlineradio.net/wp-content/uploads/2011/06/BBC-Arabic1.jpg
                                    </small_image_URL>
                                    <image>
                                    http://ichef.bbci.co.uk/corporate2/images/width/live/p0/1w/4y/p01w4yxr.jpg/624
                                    </image>
                                    <uri>
                                    http://bbcwssc.ic.llnwd.net/stream/bbcwssc_mp1_ws-araba
                                    </uri>
                                    <content_type>audio/mpeg</content_type>
                                    <rating>5</rating>
                                    <category>news</category>
                                    </entry>
                                    <entry>
                                    <unique_id>ABCNewsRadio_1</unique_id>
                                    <title>ABC News Radio</title>
                                    <subtitle>ABC News Radio Main Channel</subtitle>
                                    <description>This is description of ABC news Main Channel</description>
                                    <image>
                                    https://cdn.pixabay.com/photo/2016/04/19/17/54/radio-1339200_960_720.jpg
                                    </image>
                                    <uri>
                                    http://www.abc.net.au/res/streaming/audio/mp3/news_radio.pls
                                    </uri>
                                    <content_type>audio/mpeg</content_type>
                                    <rating>5</rating>
                                    <small_image_URL>
                                    http://vignette2.wikia.nocookie.net/onceuponatime/images/3/3b/ABC_logo.png/revision/latest?cb=20150913120112&path-prefix=fr
                                    </small_image_URL>
                                    <category>news</category>
                        </entry>
            </channels>


### Default XML source

You will find file in the folder "Res\raw\starter_stations.xml", this is the default XML source file, onLoad of the application first time, it will load stations from this file, with it's images.
 This is not mandatory file, but it should be exist even if t's empty.

### How to play back a radio station?
Tap the big Play button ;)
 
### How to stop playback?
Tap the big Stop button or unplug your headphones or swipe off the notification from the lockscreen
 
### How to start the sleep timer?
Tapping the Clock symbol starts a 15 minute countdown after which Android-Open-Radio stops playback. An additional tap adds 15 minutes to the clock. Playback must be running to be able to activate the sleep timer
 
### How to place a station shortcut on the Home screen?
The option to place a shortcut for a station on the Home screen can be accessed from the station's three dots menu. A tap on a shortcut will open Android-Open-Radio - playback will start immediately.
 
### How to rename or delete a station?
The rename and delete options can be accessed both from the station's context menu.Just tap on the three dots symbol.
 
### Where does Android-Open-Radio store its stations?
Android-Open-Radio saves its list of stations in a database. and stores stations images files on your device's external storage. The files are stored in /Android/data/{{Application-Name}}/files/Collection.
 
### How do I backup and transfer my radio stations?
Android-Open-Radio has support for the Auto Backup feature in Android 6 [Auto Backup](http://developer.android.com/about/versions/marshmallow/android-6.0.html#backup) . Radio stations are always backed up to your Google account and will be restored at reinstall.On devices running on older versions of Android you must manually save and restore the &quot;/Android/data/{{Application-Name}}/files/Collection&quot; folder and &quot;//data/data/{{Application-Name}}/databases/&quot; folder.
 
### Why are there no settings in Android-Open-Radio?
There is nothing to set ;). Android-Open-Radio is a very simple app.Depending on your point of view &quot;simple&quot; is either great or lame.
  
  
Which Permissions does Android-Open-Radio need?
---------------------------------------
### Permission "INSTALL_SHORTCUT" and "UNINSTALL_SHORTCUT"
This permission is needed to install and uninstall radio station shortcuts on the Android Home screen.

### Permission "INTERNET"
Android-Open-Radio streams radio stations over the internet.

### Permission "READ_EXTERNAL_STORAGE"
Android-Open-Radio needs access to images, photos and documents to be able to customize radio station icons and to able to open locally saved playlist files.
            
### Permission "VIBRATE"
Tapping and holding a radio station will toggle a tiny vibration.

### Permission "WAKE_LOCK"
During Playback Android-Open-Radio acquires a so called partial wake lock. That prevents the Android system to stop playback for power saving reasons.
