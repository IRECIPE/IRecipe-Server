# IRecipe-Server
## 프로젝트 세팅
### 1. mapping table 생성
- Allrgy
- Ingredient
- IngredientCategory
- Member
- MemberAgree
- MemberAllergy
- Post
- PostImage
- Refri
- Review
- ReviewImage
- ReviewComment
- Query

### 2. Enum 생성
- Category
    - 음식 카테고리 ENUM
    - WESTERN, JAPANESE, KOREAN
- Gender
    - 회원가입 시 입력받는 성별 ENUM
    - MALE, FEMALE
- Image
    - POST, REVIEW에서 IMAGE 포함 여부 ENUM
    - YES, NO
- Level
    - 조리 난이도 나타내는 ENUM
    - EASY, MID, DIFFICULT
- Status
    - POST가 임시저장 OR 게시 완료
    - TEMP, POST
- Type
    - 재료 냉장, 냉동, 상온 보관 ENUM
    - REFRIGERATED, FROZEN, AMBIENT

### 3. Spring Boot 세팅
- Groovy-Gradle
- Packaging : jar
- Java 17
- Dependency
    - Spring Web
    - Lombok
    - MySQL Driver
    - Spring Data JPA