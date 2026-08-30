
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

### 2 - Entities Relationship 

```

| Table Pair                                  | Relationship           | Type of Link      | Description                                                                                         |
| ------------------------------------------- | ---------------------- | ----------------- | --------------------------------------------------------------------------------------------------- |
| `users ↔ user_profiles`                     | **1:1**                | Logical           | `userId` column; no DB-level FK because it crosses the module/service boundary                      |
| `user_profiles ↔ medical_profiles`          | **1:1**                | Actual FK         | `user_profile_id` FK; same module                                                                   |
| `medical_profiles ↔ medical_condition`      | **1:N**                | Actual FK         | `medical_profile_id` FK                                                                             |
| `medical_profiles ↔ family_medical_history` | **1:N**                | Actual FK         | `medical_profile_id` FK                                                                             |
| `users ↔ refresh_tokens`                    | **1:N**                | Logical           | Linked using `userId`                                                                               |
| `users / user_profiles ↔ consent_record`    | **Logical link**       | Depends on design | Typically linked using `userId` or `userProfileId`                                                  |
| `user_profiles ↔ emergency_contact`         | **1:N or Flat Fields** | Depends on design | Depends on whether emergency contacts are stored in a separate table or directly in `user_profiles` |


```


