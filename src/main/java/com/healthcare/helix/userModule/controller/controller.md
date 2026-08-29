

### 1. User Profile Endpoints
```
POST   /api/v1/users/profile              → Create profile (after auth-service registration)
GET    /api/v1/users/profile              → Get logged-in user's own profile
PUT    /api/v1/users/profile              → Update own profile
DELETE /api/v1/users/profile              → Soft-delete own profile (deactivate account)
GET    /api/v1/users/profile/{userId}     → Get specific user profile (admin-only)
```


### 2. Medical Profile Endpoints
```
POST   /api/v1/users/medical-profile              → Create medical profile
GET    /api/v1/users/medical-profile               → Get own medical profile
PUT    /api/v1/users/medical-profile               → Update medical profile (blood group, height, weight)
(1:1 relation hai, isliye create/get/update kaafi hai — delete alag se nahi, profile delete hone pe cascade ho jayega)

```


### 3. Medical Condition Endpoints
```
POST   /api/v1/users/medical-conditions            → Add new condition
GET    /api/v1/users/medical-conditions            → List all conditions (own)
GET    /api/v1/users/medical-conditions/{id}       → Get specific condition
PUT    /api/v1/users/medical-conditions/{id}       → Update condition (e.g. mark inactive)
DELETE /api/v1/users/medical-conditions/{id}       → Remove condition
```




### 4. Family Medical History Endpoints
```
POST   /api/v1/users/family-history                → Add family history record
GET    /api/v1/users/family-history                → List all family history
PUT    /api/v1/users/family-history/{id}            → Update record
DELETE /api/v1/users/family-history/{id}            → Delete record
```




### 5. Consent Record Endpoints
```
POST   /api/v1/users/consent                        → Grant consent (specify type)
GET    /api/v1/users/consent                         → Get all consent records (own history)
PUT    /api/v1/users/consent/{id}/revoke              → Revoke a specific consent
(Consent ko kabhi hard-delete mat karo — sirf revoked=true + revokedAt set karo, legal audit trail ke liye)
```




### 6. Internal Endpoints (service-to-service only, gateway se expose nahi)

```

GET    /internal/users/{userId}/medical-summary      → report-service ke liye — 
                                                          age, gender, bloodGroup, 
                                                          conditions[], familyHistory[]

GET    /internal/users/{userId}/exists                → auth-service/other services ke liye 
                                                          quick existence check
                                                          
                                                        
```
                                                          
                                                          
                                                          
### 7. Admin/Utility Endpoints (optional, agar admin panel is planned for future)
```

GET    /api/v1/admin/users                           → List all users (paginated)
GET    /api/v1/admin/users/{userId}                  → Get any user's full profile
PUT    /api/v1/admin/users/{userId}/deactivate         → Admin deactivate a user

```
