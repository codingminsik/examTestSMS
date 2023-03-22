import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int lastId = 3;
		
		makeTestData();
		System.out.println("=== 프로그램 시작 ===");
		
		while(true) {
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine();
			
			if (cmd.equals("exit")) {
				break;
			}
			
			if (cmd.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
					continue;
				}
				
				System.out.println("번호    //    제목");
				
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d    //    %s\n", article.id, article.title);
				}
			}
			
			if (cmd.equals("article write")) {
				int id = lastId + 1;
				
				System.out.println("=== 게시글 작성 ===");
				System.out.print("제목 ) ");
				String title = sc.nextLine();
				System.out.print("내용 ) ");
				String body = sc.nextLine();
				String regDate = Util.getDateTime();
				
				Article article = new Article(id,title,body,regDate,regDate);
				articles.add(article);
				
				System.out.printf("%d번 글이 작성되었습니다.\n", id);
				lastId++;
				
			} else if (cmd.startsWith("article detail")) {
				String[] cmdDiv = cmd.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				Article foundArticle = getArticleId(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 글은 존재하지 않습니다.", id);
					continue;
				}
				
				System.out.println("번호 ) " + foundArticle.id);
				System.out.println("제목 ) " + foundArticle.title);
				System.out.println("내용 ) " + foundArticle.body);
				System.out.println("작성날짜 ) " + foundArticle.regDate);
				System.out.println("수정날짜 ) " + foundArticle.updateDate);
				
			} else if (cmd.startsWith("article modify")) {
				String[] cmdDiv = cmd.split(" ");

				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				Article foundArticle = getArticleId(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 글은 존재하지 않습니다.\n", id);
					continue;
				}
				
				System.out.print("새 제목 ) ");
				String title = sc.nextLine();
				System.out.print("새 내용 ) ");
				String body = sc.nextLine();
				String updateDate = Util.getDateTime();
				
				foundArticle.title = title;
				foundArticle.body = body;
				foundArticle.updateDate = updateDate;
				System.out.printf("%d번 글이 수정되었습니다.\n", id);
				
			} else if (cmd.startsWith("article delete")) {
				String[] cmdDiv = cmd.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				int foundIndex = getArticleIndex(id);
				
				if (foundIndex == -1) {
					System.out.printf("%d번 글은 존재하지 않습니다.\n", id);
					continue;
				}
				
				articles.remove(foundIndex);
				System.out.printf("%d번 글이 삭제되었습니다.\n", id);
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
		} 
		
		
		System.out.println("=== 프로그램 종료 ===");
		sc.close();
	}
	
	public static void makeTestData() {
		System.out.println("게시글 테스트 데이터를 생성합니다.");
		articles.add(new Article(1, "제목1", "내용1", Util.getDateTime(), Util.getDateTime()));
		articles.add(new Article(2, "제목2", "내용2", Util.getDateTime(), Util.getDateTime()));
		articles.add(new Article(3, "제목3", "내용3", Util.getDateTime(), Util.getDateTime()));
	}
	
	public static Article getArticleId(int id) {
		
		for (Article article : articles) {
			
			if (article.id == id) {
				return article;
			}
		}
		return null;
	}
	
	public static int getArticleIndex(int id) {
		int i = 0;
		
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	
}

class Article {
	int id;
	String title;
	String body;
	String regDate;
	String updateDate;
	
	Article (int id, String title, String body, String regDate, String updateDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
		
	}
}

class Util {

	public static String getDateTime() {
		  LocalDateTime now = LocalDateTime.now();

	        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

	        return formatedNow;
	}
}
