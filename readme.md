# 도서관리 프로그램
도서 등록 및 대여 기능을 GUI 프로그램으로 구현해본다.

### 필요 기능 정리
- 도서 등록, 수정, 삭제 기능 
	- 도서 등록시 항목 
		- 도서명
		- 출판사
		- 저자 
		- 도서 일련번호
		- 도서의 대출 가능일 수 
					
- 도서 조회 기능
	- 조회기능을 구현시 도서의 구성 항목별로 조합해서 조회가 될수 있도록 구현 한다.
	
- 도서 대출, 반납 기능 
	- 도서를 대출할 경우 일련번호에 해당하는 도서에 대출일자를 기록하고 반납 예정일을 기록한다.
	- 반납 예정일이 지난 경우는 반납 예정 목록에 노출될 수 있도록 한다.
	- 반납한 경우 대출일자 및 반납 예정일을 초기화 한다.
	- 대출, 반납시 해당 기록을 로그로 남긴다.
	
### 기능 구현시 유의점
- 도서는 대여를 목적으로 하기 때문에 재고로 관리하지 않는다.
- 동일한 도서가 2권이라면 일련번호를 다르게 해서 등록하여 그 일련번호로 대여 기록을 관리할 수 있도록 한다.
- 모든 화면은 사용자 편의성을 위해 GUI로 구성한다.

### 클래스 설계
- **com.codefty.library.book** 패키지<br>도서의 등록, 수정, 삭제 전반과 관련되어 있는 패키지 이다.
	- Book.java  : 도서 등록, 수정 항목 DTO(Data Transfer Object)
	- RegisterBook.java : 도서 등록 기능 설계
		```java
		public interface RegisterBook {
			boolean register();
		}
		```
	- UpdateBook.java : 도서 수정 기능 설계
		```java
		public interface UpdateBook {
			boolean update();
		}
		```
	- DeleteBook.java : 도서 삭제 기능 설계
		```java
		public interface DeleteBook {
			boolean delete();
		}
		```

		
		
	
	