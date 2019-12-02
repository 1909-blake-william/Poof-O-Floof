# Poof-O-Floof
## 2019/11/25 Agreed on the following directions:
1. Use Java to fetch ip-geolocation data and petfinder.com
2. Use "photo" as a basis of user interaction. The app will generate multiple photo POJOs from a single animal object in the petfinder.com JSON response. The phto POJO has: photoId, animalId and url. Only the large-size url is stored.
3. animalId on petfinder.com seems to be an 8-digit number. The phto id will be a 10-digit number. The last two digit marks the photos sequence. e.g animalId 12345678 => photoIds: 1234567801, 1234567802, 1234567803 etc.
## 2019/11/26
* Welcome Gabby on board!
* Current responsibilities of the project: Gabby and Hao on the client-side/Angular. Boris and Tim on the backend.
* First-Stage goals (by 2019-11-30):
  * Get the user ip, and then translate into geolocation from a third-party API
    * Hao: I think we should use (latitude, longitude) because:
      1. It is pure geo-location data, independant of the arbitration of USPS/gerrymandering
      2. It is the same amount of work compared with using zip code/city state
      3. If the user ip is outsite of US, we don't need to handle those exceptions. Just make the search radius larger and larger and decide a limit.
      4. If we also record the coordinates of the animal/photo location, we can serve cached photo urls from our database by doing simple triangulation (instead of dragging extra Java libraries to parse the zip code. This is an extended goal of course.)
  * Fetch the JSON from petfinder
    * Hao: the API token expires every 3600 seconds. We need some logic to automatically renew it. Say by recording the timestamp of last renewal and check it periodically and/or upon serving new photos to users. 
  * Parse JSON into photo POJOs and store them in the DB
  * Display photo url properly in the client side
  * User can click 'next' and fetch the next photo from our server.
