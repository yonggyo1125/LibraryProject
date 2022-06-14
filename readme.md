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
	- Book : 도서 등록, 수정 항목 DTO(Data Transfer Object)
	- RegisterBook : 도서 등록 기능 설계
		```java
		public interface RegisterBook {
			boolean register(Book book);
		}
		```
	- UpdateBook : 도서 수정 기능 설계
		```java
		public interface UpdateBook {
			boolean update(Book book);
		}
		```
	- DeleteBook : 도서 삭제 기능 설계
		```java
		public interface DeleteBook {
			boolean delete(Book book);
		}
		```

	- BookService : 도서 등록, 수정, 삭제 기능 구현 클래스
		```java
		public class BookService implements DeleteBook, RegisterBook,  UpdateBook {

			@Override
			public boolean update(Book book) {

				return false;
			}

			@Override
			public boolean register(Book book) {

				return false;
			}

			@Override
			public boolean delete(Book book) {

				return false;
			}
		}
		```
	- BookDao : 도서의 등록, 수정, 조회, 삭제등 데이터 접근 처리 
	
- **com.codefty.library.rental** 패키지	: 도서 대여, 반납 관리와 관련 패키지 입니다.
	- RentalBook : 도서 대여 설계
		```java
		public interface RentBook {
			boolean rentBook(Book book);
		}
		```
	- ReturnBook : 도서 반납 설계
		```java
		public interface ReturnBook {
			boolean returnBook(Book book);
		}
		```
	- RentalService : 도서 대여, 반납 기능 구현 클래스 
		```java
		public class RentalService implements RentBook, ReturnBook {

			@Override
			public boolean returnBook(Book book) {
				
				return false;
			}

			@Override
			public boolean rentBook(Book book) {
				
				return false;
			}
		}
		```
- **com.codefty.library.search** 패키지 : 도서 조회 기능과 관련 패키지 입니다.
	- SearchService : 도서 조회 기능 구현 클래스 

- **com.codefty.library.gui** 패키지 : GUI 구성과 관련된 패키지 입니다.
	- GraphicUserInterface : 메인 인터페이스 설계 
		```java
		public class GraphicUserInterface extends JFrame {
			public GraphicUserInterface() {
				super("도서관리 시스템");
				
				JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);
				jtp.addTab("대여/반납관리", new BookManagerUI());
				jtp.addTab("도서관리", new BookManagerUI());
				add(jtp, BorderLayout.CENTER);
				
				setSize(900, 800);
				setVisible(true);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		}
		```
		- BookManagerUI : 대여/반납 관리
			```java
			public class BookManagerUI extends JPanel {
	
			}
			```
		- RentalManagerUI : 도서 관리 
			```java
			public class RentalManagerUI extends JPanel {

			}
			```
		
- **com.codefty.library.common** 패키지: 공통적으로 사용할 상수 및 기능을 정의하는 패키지
	- RentalStatus : 도서 대여 등의 상태를 나타내는 enum 클래스
		```java
		public enum RentalStatus {
			READY, // 대기중  
			RENTAL, // 대여중 
			RETURN, // 반납 
			CHECKING // 검수 중
		}
		```
	
	- CommonException : GUI 상에서 발생하는 예외는 팝업 알림으로 처리하기 위한 기본 클래스 각 기능의 예외는 이 클래스를 상속받아 재정의 해야 한다.
		```java
		public class CommonException extends RuntimeException {
			public CommonException(Component parentComponent, String message) {
				super(message);
				
				JOptionPane.showMessageDialog(parentComponent, message, "알림", JOptionPane.ERROR_MESSAGE);
			}
		}
		```
		
		- 사용 예 : 예외를 발생시키면 팝업이 뜬다.
		```java
		try {
			throw new com.codefty.library.common.CommonException(this, "테스트 메세지");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		```