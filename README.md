# IF2210 Pemrograman Berorientasi Objek - Tugas Besar 2

## 💻 Objectify App
```
📢 "Simple ChatGPT using String Matching Algorithms and Regular Expression"
```

## **📜 Table of Contents**
* [Program Description](#-program-description)
* [Required Program](#%EF%B8%8F-required-program)
* [How to Run The Program](#-how-to-run-the-program-locally)
* [Progress Report](#-progress-report)
* [Folders and Files Description](#-folders-and-files-description)
* [Author](#-authors)
* [Extra](#extra)

## **📄 Program Description**
The program to be created in this Assignment is a Point of Sales (POS) program, which aims to help a store manage transactions related to their business. The POS program to be created has basic inventory management and transaction management features. In addition, the program has a membership feature so that the store can reward loyal customers, and also a report creation feature to support the store in conducting evaluations. The program is also extensible by providing plugin support, so that users can easily add program functionality.

The POS application to be created will use concepts in Object Oriented Programming (OOP) to ensure the reliability and scalability of the program. This POS application will also implement Graphical User Interface (GUI) to make it easier for users to interact with the application. In addition, the application will also use several Java API features such as Collection, Reflection, and Threading to improve efficiency and application performance.

## **🛠️ Required Program or Dependencies**
| Required Program   | Reference Link                      |
|--------------------|-------------------------------------|
| Java 19.0.2        | [Java](https://www.java.com/download/ie_manual.jsp)  |
| Apache Maven 3.8.7 | [Apache Maven](https://maven.apache.org/) |



## **💻 How to Run The Program**
### **Backend**
1. Clone this repository and make sure that you are in 'IF2223_TugasBesar_2_Objectify' folder
```sh
cd 'IF2223_TugasBesar_2_Objectify'
```

2. Build the project or package
```sh
mvn clean package
```

3. Run the Main Program's Jar File
```sh
java -cp ObjectifyMainApp/target/ObjectifyMainApp-v1.0-shaded.jar com.objectify.Main
```
4. (Optional) You could also use the .sh file (Linux) or .bat file (Windows) to run the program
```sh
./run.sh or ./run.bat
```


## **📂 Folders and Files Description**
```bash
ObjectifyApp
├───.idea
│   ├───codeStyles
│   ├───dictionaries
│   └───inspectionProfiles
├───BasePlugin
│   ├───src
│   │   └───main
│   │       └───java
│   │           └───com
│   │               └───objectify
│   │                   └───BasePlugin
│   └───target
│       ├───classes
│       │   └───com
│       │       └───objectify
│       │           └───BasePlugin
│       ├───generated-sources
│       │   └───annotations
│       ├───maven-archiver
│       └───maven-status
│           └───maven-compiler-plugin
│               └───compile
│                   └───default-compile
├───CurrencyPlugin
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───objectify
│   │   │   │           └───CurrencyPlugin
│   │   │   └───resources
│   │   └───test
│   │       └───java
│   └───target
│       ├───classes
│       │   └───com
│       │       └───objectify
│       │           └───CurrencyPlugin
│       ├───generated-sources
│       │   └───annotations
│       ├───maven-archiver
│       └───maven-status
│           └───maven-compiler-plugin
│               ├───compile
│               │   └───default-compile
│               └───testCompile
│                   └───default-testCompile
├───ObjectifyMainApp
│   ├───src
│   │   ├───main
│   │   │   └───java
│   │   │       └───com
│   │   │           └───objectify
│   │   │               ├───controllers
│   │   │               │   ├───components
│   │   │               │   │   ├───alerts
│   │   │               │   │   ├───menubar
│   │   │               │   │   └───menus
│   │   │               │   ├───pages
│   │   │               │   └───scenes
│   │   │               ├───datastore
│   │   │               │   ├───adapter
│   │   │               │   ├───enums
│   │   │               │   └───interfaces
│   │   │               ├───exceptions
│   │   │               ├───models
│   │   │               │   ├───entities
│   │   │               │   ├───items
│   │   │               │   └───transactions
│   │   │               ├───plugin
│   │   │               └───utils
│   │   ├───resources
│   │   │   ├───css
│   │   │   ├───images
│   │   │   ├───JSON
│   │   │   ├───OBJ
│   │   │   ├───settings
│   │   │   └───XML
│   │   └───test
│   │       └───java
│   │           └───com
│   │               └───objectify
│   │                   └───datastore
│   │                       └───enums
│   └───target
│       ├───classes
│       │   └───com
│       │       └───objectify
│       │           ├───controllers
│       │           │   ├───components
│       │           │   │   ├───alerts
│       │           │   │   ├───menubar
│       │           │   │   └───menus
│       │           │   ├───pages
│       │           │   └───scenes
│       │           ├───datastore
│       │           │   ├───adapter
│       │           │   ├───enums
│       │           │   └───interfaces
│       │           ├───exceptions
│       │           ├───models
│       │           │   ├───entities
│       │           │   ├───items
│       │           │   └───transactions
│       │           ├───plugin
│       │           └───utils
│       ├───generated-sources
│       │   └───annotations
│       ├───generated-test-sources
│       │   └───test-annotations
│       ├───maven-archiver
│       ├───maven-status
│       │   └───maven-compiler-plugin
│       │       ├───compile
│       │       │   └───default-compile
│       │       └───testCompile
│       │           └───default-testCompile
│       ├───site
│       │   └───jacoco-ut
│       │       ├───com.objectify
│       │       ├───com.objectify.controllers
│       │       ├───com.objectify.controllers.components.alerts
│       │       ├───com.objectify.controllers.components.menubar
│       │       ├───com.objectify.controllers.components.menus
│       │       ├───com.objectify.controllers.pages
│       │       ├───com.objectify.controllers.scenes
│       │       ├───com.objectify.datastore
│       │       ├───com.objectify.datastore.adapter
│       │       ├───com.objectify.datastore.enums
│       │       ├───com.objectify.exceptions
│       │       ├───com.objectify.models.entities
│       │       ├───com.objectify.models.items
│       │       ├───com.objectify.models.transactions
│       │       ├───com.objectify.plugin
│       │       ├───com.objectify.utils
│       │       └───jacoco-resources
│       ├───surefire-reports
│       └───test-classes
│           └───com
│               └───objectify
│                   └───datastore
│                       └───enums
└───PaymentPlugin
    ├───src
    │   ├───main
    │   │   ├───java
    │   │   │   └───com
    │   │   │       └───objectify
    │   │   │           └───PaymentPlugin
    │   │   └───resources
    │   └───test
    │       └───java
    └───target
        ├───classes
        │   └───com
        │       └───objectify
        │           └───PaymentPlugin
        ├───generated-sources
        │   └───annotations
        ├───maven-archiver
        └───maven-status
            └───maven-compiler-plugin
                ├───compile
                │   └───default-compile
                └───testCompile
                    └───default-testCompile
```

## **👨‍💻 Authors**
| Name                      | Student ID | Role               | Job Specification                |
|---------------------------|------------|--------------------|----------------------------------|
| Hosea Nathanael Abednego  | 13521057   | Frontend Developer | Billing Management Page          |  
| Moch. Sofyan Firdaus      | 13521083   | Backend Developer  | Plugination System, DataStore    |
| Tabitha Permalla          | 13521111   | Frontend Developer | Product Management Page          | 
| Nicholas Liem             | 13521135   | Backend Developer  | Plugination System, DataStore    |
| Nathania Calista Djunaedi | 13521139   | Frontend Developer | Member Management Page           |