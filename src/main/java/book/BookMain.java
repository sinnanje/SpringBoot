package book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/*
 * Start of the string boot application. This is the startup. 
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BookMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(BookMain.class, args);
	}
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
	    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
	    threadPoolTaskScheduler.setPoolSize(4);
	    return threadPoolTaskScheduler;
	}
	
	

}
