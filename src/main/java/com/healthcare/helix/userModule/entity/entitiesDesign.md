```



┌────────────────────────────────┐
│         user_profile           │
├────────────────────────────────┤
│ id PK                          │
│ user_id (from auth-service)    │ ← UNIQUE
│ first_name                     │
│ last_name                      │
│ date_of_birth                  │
│ gender                         │
│ mobile                         │ ← UNIQUE
│ email                          │ ← UNIQUE
│ address                        │
│ city                           │
│ state                          │
│ country                        │
│                                │
│ emergency_contact_name         │
│ emergency_contact_relation     │
│ emergency_contact_mobile       │
│                                │
│ created_at                     │
│ updated_at                     │
└────────────┬───────────────────┘
             │
             │ 1 : 1
             ↓
┌────────────────────────────────┐
│        medical_profile         │
├────────────────────────────────┤
│ id PK                          │
│ user_profile_id FK             │ ← UNIQUE
│ blood_group                    │
│ height                         │
│ weight                         │
│ allergies                      │
│ current_medications            │
│ medical_history                │
│ created_at                     │
│ updated_at                     │
└──────┬──────────────┬──────────┘
       │              │
       │ 1:N           │ 1:N
       ↓              ↓
┌──────────────────┐  ┌──────────────────────────--─┐
│ medical_condition│  │  family_medical_history     │
├──────────────────┤  ├──────────────────────────── ┤
│ id PK            │  │ id PK                       │
│ medical_profile_id│  │ medical_profile_id FK      │
│    FK             │  │ relation                   │
│ condition_name    │  │ condition_name             │
│ description       │  │ created_at                 │
│ diagnosed_date     │  └───────────────────────────┘
│ active             │
│ created_at         │
│ updated_at         │
└───────────────────┘


┌────────────────────────────────┐
│        consent_record          │
├────────────────────────────────┤
│ id PK                          │
│ user_profile_id (ref, no FK)   │
│ consent_type                   │  "DATA_PROCESSING",
│                                │  "AI_ANALYSIS",
│                                │  "DATA_SHARING"
│ granted                        │
│ granted_at                     │
│ revoked_at                     │
│ consent_version                │
└────────────────────────────────┘
      ↑
      │ logically linked to user_profile
      │ (no hard FK — independent lifecycle,
      │  legal record even if profile deleted)
      
      
Note - medical_profile is owner.

##Simple Relation - 
User Profile has one Medical Profile and one Medical Profile can have many multiple Medical condition.

Means - 

@OneToOne
    ↓
UserProfile ↔ MedicalProfile

@OneToMany
    ↓
MedicalProfile ↔ MedicalCondition

```


### Complete Flow - 
```
                AUTH SERVICE
              ┌──────────────┐
              │    User      │
              ├──────────────┤
              │ id = B456    │
              │ email        │
              └──────┬───────┘
                     │
                     │ userId
                     ↓
                USER SERVICE
              ┌──────────────────┐
              │   UserProfile    │
              ├──────────────────┤
              │ id = A123        │
              │ user_id = B456   │
              │ firstName        │
              └────────┬─────────┘
                       │
                       │ user_profile_id
                       ↓
              ┌──────────────────┐
              │ MedicalProfile   │
              ├──────────────────┤
              │ id = M789        │
              │ user_profile_id  │
              │ bloodGroup       │
              └────────┬─────────┘
                       │
                       │ medical_profile_id
                       ↓
              ┌──────────────────┐
              │ MedicalCondition │
              ├──────────────────┤
              │ id               │
              │ medical_profile_id│
              │ conditionName     │
              └──────────────────┘
```

