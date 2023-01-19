Android Test Implementation
============================
This project contains the codebase for an Android Project for the Zemoga Android technical test. 

# Features 

This tests consists of remotely fetching posts objects from the <a href="https://jsonplaceholder.typicode.com/">JSON Placeholder API</a>, store this objects locally and then accessing each Posts details by fetching users and the comments related to each one.

# Architecture 

The architecture of choice for this project was a Clean Architectural Pattern with an MVVM Pattern on the Presentation layer. 

<img src="https://i.postimg.cc/wBGqNW9s/Architecture.png" height="400">

# Screenshots 

![](https://i.postimg.cc/NfcHL0Jv/image.png)
![](https://i.postimg.cc/76wFf02T/image.png)


# Modules

The app is distributed into 2 main modules, features and core. 

The feature module provides modules related to the main features of the application. They may contain Activities, Fragments, ViewModels specifically related to the modules purpose. 

The core modules pave the way for the architectural pattern used through out the entire app. The most relevants modules are :core:domain and :core:data. 

The :core:domain contains the Business Logic, while the :core:data contains code that abstracts the comunication with both the remote API and the local database, which are contained in the :core:network, :core:database, and :core:datastore modules. 

![](https://i.postimg.cc/fRtyKtRP/modules.png)


# Testing 

This app uses Hilt to facilitate testing. Main classes like repositories & data sources are injected as interfaces while the concrete implementation is provided by Hilt. Therefore, during testing concrete implementations for the testcases are created and provided manually quite easily.

- Unit Tests: The more relevant modules for testing were :core:domain and :core:data. Test cases for the modules :core:network, :core:database were not implemented due to time constraints.
- Instrumentation Tests: none implemented


# Build 

The project contains the "app" module as usual for running the app.



