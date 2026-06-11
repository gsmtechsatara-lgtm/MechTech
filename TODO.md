# TODO - Customer Service API (production-ready)

- [x] Edit `customer-service/pom.xml` to add `spring-boot-starter-validation`.

- [x] Create `ApiConstants` in `customer/.../customer/comman`.

- [x] Create DTOs: `CreateCustomerRequest`, `UpdateCustomerRequest`, `CustomerResponse`, and generic `ApiResponse<T>`.

- [x] Create custom exceptions: `DuplicateMobileNumberException`, `ResourceNotFoundException`.
- [x] Create `GlobalExceptionHandler` to map exceptions to `ApiResponse` + HTTP status codes.
- [x] Create `CustomerRepository` with required query methods.
- [x] Create `CustomerService` interface.
- [x] Create `CustomerServiceImpl` with business rules (unique mobile, timestamps, active=true) using constructor injection.
- [x] Create `CustomerController` with REST endpoints under `/api/v1/customers`.

- [x] Run `mvn test` to ensure compilation.


