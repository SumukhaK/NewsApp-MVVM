**Overview**

News app is built with most stable Android tools and frameworks and promises you to keep updated with any modern tech stacks that is available in the market to enhance overall user experience and app’s stability.Built with open source back-end weather API, MVVM architecture, XML UI. Eventually migrates to Jetpack-Compose, Hilt, Work Manager based periodic background silent updates and many more exciting updates on the cards. 

**Major Highlights**<br />
* Retrofit for smooth networking.<br />
* Dagger2 to eventually Dagger Hilt migration for dependency Injection.<br />
* Kotlin and Kotlin DSL.<br />
* Coroutine and Flow for fast and asynchronous programming.<br />
* XML based UI to Jetpack-Compose migration.<br />
* Glide/Coil for efficeint image loading.<br />
* Room DB ORM implementation for offline reading.<br />
* Quick search for your favorite news.<br />

**Architecture**<br />
* MVVM Architecture (View - ViewModel - Model): Facilitates separation of concerns and promotes maintainability.<br />
* Repository Pattern: Acts as a mediator between different data sources and the application's business logic.<br />


	<img width="554" height="312" alt="image" src="https://github.com/user-attachments/assets/8cb7f0b7-5c32-49c7-b8fb-7f5f7bf9bb81" />



 **Features implemented**:

* TopHeadlines of the news <br />
* Multiple sources wise news <br />
*  Instant search using Flow operators  <br />
* Debounce<br />
	- Filter<br />
	- DistinctUntilChanged<br />
	- FlatMapLatest<br />
*   Offline news <br />
*   Unit Test <br />
	- Mockito<br />
	- Turbine https://github.com/cashapp/turbine<br />

![Screenshot_20250805_230844_Screenshot_20250805_231133_Screenshot_20250805_231117_Screenshot_20250805_231021](https://github.com/user-attachments/assets/376695e0-7ef2-40d5-a60c-02f3949b8d0a)

**Folder Structure**:
```
└───com
    └───ksa
        └───newsapp_mvvm_architecture
            │   NewsApplication.kt
            │
            ├───data
            │   ├───api
            │   │       NetworkService.kt
            │   │
            │   ├───local
            │   │   │   AppDatabase.kt
            │   │   │   AppDatabaseService.kt
            │   │   │   DatabaseService.kt
            │   │   │
            │   │   ├───dao
            │   │   │       ArticleDao.kt
            │   │   │
            │   │   └───entity
            │   │           ArticleEntity.kt
            │   │           SourceEntity.kt
            │   │
            │   ├───model
            │   │       Article.kt
            │   │       Country.kt
            │   │       CountryListResponse.kt
            │   │       NewsSourcesResponse.kt
            │   │       SearchNewsResponse.kt
            │   │       Source.kt
            │   │       TopHeadlinesResponse.kt
            │   │
            │   └───repository
            │           CountryListRepository.kt
            │           NewsSearchByKeywordRepository.kt
            │           NewsSourcesRepository.kt
            │           OfflineArticlesRepository.kt
            │           TopHeadlinesRepository.kt
            │
            ├───di
            │   │   Qualifier.kt
            │   │   Scopes.kt
            │   │
            │   ├───component
            │   │       ActivityComponent.kt
            │   │       ApplicationComponent.kt
            │   │
            │   └───module
            │           ActivityModule.kt
            │           ApplicationModule.kt
            │
            ├───ui
            │   ├───base
            │   │       UiState.kt
            │   │       ViewModelProviderFactory.kt
            │   │
            │   ├───countrylist
            │   │       CountryListActivity.kt
            │   │       CountryListAdapter.kt
            │   │       CountryListViewModel.kt
            │   │
            │   ├───mainscreen
            │   │       MainActivity.kt
            │   │
            │   ├───newssources
            │   │       NewsSourcesActivity.kt
            │   │       NewsSourcesAdapter.kt
            │   │       NewsSourcesViewModel.kt
            │   │
            │   ├───offlinefirst
            │   │       OfflineFirstViewModel.kt
            │   │       ReadOfflineActivity.kt
            │   │
            │   ├───search
            │   │       NewsSearchActivity.kt
            │   │       NewsSearchViewModel.kt
            │   │
            │   └───topheadline
            │           TopHeadlineAdapter.kt
            │           TopHeadlinesActivity.kt
            │           TopHeadlinesViewModel.kt
            │
            └───utils
                    AppConstants.kt
                    DefaultDispatcherProvider.kt
                    NetworkHelper.kt
                    ReadJSONFromAssests.kt
```
