# ucbit-beconavi
 
ucBit BLE beacon application is revamped yet again to build a physical web based Indoor Navigation System for the cities of future. Even today with most precise GPS sensors, navigating indoors is a challenge, especially in big malls, university campuses, etc. Building on top of the previous ucBit UART sensor data logger application for assignment3 (which collected sensor metrics from various BLE sensor motes and weather data from an Open weather API and published it to a Firebase Realtime database), the complete code for BLE service discovery which relied on the heavy Bluetooth Low Energy library published by [Kai-Morich](http://www.kai-morich.de/android/index.html) is now removed instead each BBC micro:bit module is configured to act like a [iBeacon](https://developer.apple.com/ibeacon/) enabling use of lightweight beacon finder API from [altbeacon](https://github.com/kshoji/pxt-bluetooth-beacons) which listens for 16 Bytes long UUID is then used by the android application to localize its position within the building. 

### Dealing with location data: 

Initially the idea was to deploy at least 3 micro:bit beacons in a room and implement a Trilateration algorithm to locate position within the room and then fit it into a 3d model of the room. But due to constrains like the time for this assignment and unavailability of enough BBC micro:bit module, it was the thought of implementing a basic proximity based location sensing. Once the user’s location was available based on the nearest iBeacon node, it was marked on a mapbox[3] Map layout. Location changes are calculated by measuring the location using the Haversine formula[4] to get distance in meter form a latitude, longitude pair obtained by gps. 

a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)   
c = 2 ⋅ atan2( √a, √(1−a) )   
d = R ⋅ c  
To get a better indoor location data fusion of gps location and beacon location is used where the indoor location is computed based on the location changed listener to update the available UUID list with the nearest beacon. 

### Extra Tasks:  

To build a complete indoor navigation application, it was required to have a UI layer. After exploring about current state of the art, I came across an Open Source SDK for indoor navigation called [indoorlocation](https://www.indoorlocation.io/). I integrated their basic UI and rendered mapbox maps on It. This was then uplifted by using the [mapwize](https://www.mapwize.io/) framework to lay floorplans / Blueprints of buildings on to Map, add Point of interests (POI), add paths and connectors for navigation and beacon markers for location aware routing. 

### Features:
The current version of the app has the following functionality using which the user location marker is updated based on the nearest micro:bit beacon. Upon clicking the notification, the user is redirected to the Wikipedia page of the location if available.  
1. BLE service discovery to scan devices with CC254X and NRF BLE chips advertising iBeacon header.  
2. Render part of the map defined in Mapwize along with various POIs and option to switch floors.  
3. Update user location in Realtime based on proximity to a beacon. (Uses both GPS and beacon based positioning).   
4. When in proximity to a beacon pull out realtime weather info from OpenWeatherMap API   
5. Get in the notification information about that location from Wikipedia using the wiki opensearch API   
6. Store the data, received from the microbit beacon, wikipedia and the weather API is the send  to Google cloud platform and stored in a Firebase database.  
 
Microbit act as iBeacon and advertise their UUID, Android app using alt beacon library to discover and manage ibeacons nearby, Mapwize sdk used to design the floorplan, routing and UI, maps rendered using mapbox. HTTP client gets realtime information about weather and user location from open data end points. Due to unavailability of multiple uBit modules, the connection must be invoked manually by scanning for the available modules and testing if the location is updated. 

### Reference:

[1] (https://developer.apple.com/ibeacon/)

[2] (https://github.com/AltBeacon/android-beacon-library)

[3] (https://docs.mapbox.com/android/navigation/overview/)

[4] (https://www.movable-type.co.uk/scripts/latlong.html?from=48.8608,-122.1261&to=48.8599,122.1449)

[5] (https://docs.mapwize.io/developers/android/sdk/latest/#indoor-location)

[6] (https://openweathermap.org/price [7] (https://www.mediawiki.org/wiki/API:Opensearch)

[8] (https://github.com/kshoji/pxt-bluetooth-beacons)

[9] (https://codebeautify.org/json-to-java-converter)

[10] http://www.bittysoftware.com/)

[11] (https://wagnerguide.com/c/trinitycollegedublin/hamiltonlibrary?mapfile=1685)

[12] (https://medium.com/@ferrygunawan/turn-the-bbc-micro-bit-into-a-physical-web-beacon-andchange-the-url-link-dynamically-c75d728c1e3b)

[13] (https://scss.tcd.ie/Local/Floor_Plans_and_Technical_Diagrams/FloorPlans/)
