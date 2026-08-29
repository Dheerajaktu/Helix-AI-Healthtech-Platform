
### 1 - Project Architecture (Module Monolithic )

```

helix-health-platform/                    
├── pom.xml
├── src/main/java/com/healthcare/helix/
│   ├── HelixApplication.java            
│   │
│   ├── common/                           
│   │   ├── security/
│   │   │   ├── SecurityConfig.java      
│   │   │   ├── JwtService.java           
│   │   │   └── JwtAuthFilter.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java  
│   │   │   └── ...custom exceptions
│   │   └── config/
│   │       └── (CORS, Swagger, etc.)
│   │
│   ├── authModule/                           
│   │   ├── controller/
│   │   ├── service/
│   │   ├── entity/User.java
│   │   ├── repository/
│   │   └── dto/
│   │
│   ├── userModule
│   │   ├── controller/
│   │   ├── service/
│   │   ├── entity/ (UserProfile, MedicalProfile, MedicalCondition)
│   │   ├── repository/
│   │   └── dto/
│   │
│   └── reportModule/                            
│       ├── controller/
│       ├── service/
│       ├── entity/ (Document, Report, TestFactor)
│       ├── repository/
│       └── dto/
│
└── src/main/resources/
    └── application.yml                   


```