# So-sure Interview Test

- Implement the logic to perform a network request to `https://swapi.dev/api/people/4`.
- Feel free to use any library or tool you might need to achieve that. Some have been already added for your convience, but if you don't find the one you like feel free to add it!
- Show in the main screen a button to start the network request. Display a loader while the network request is being performed. If it's successful, show the name of the character. Otherwise, show an error message.
- While this is quite a simple feature, develop it like it was some production code you want to ship :) 

-
#Solution details
- This Kotlin Android app implements the **MVVM** pattern design.
- Project has been configured to deploy the **debug** and **release** version.


##Library Used
- Dagger2: *injects PeopleApi into PeopleService, and injects PeopleService into NameViewModel*
- Retrofit2: *interacts with REST API*
- Timber: *logging (enabled only for the debug build variant)*
- Mockito: *for unit testing (NameViewModel has been tested)*


