package de.gkjava.addr;

import de.gkjava.addr.controller.Controller;
import de.gkjava.addr.view.ViewFrame;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("de.gkjava.addr.persistence.repository")
@EnableTransactionManagement
@EnableAutoConfiguration
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new SpringApplicationBuilder(Main.class).headless(false).run(args);

		final Controller controller = (Controller)ctx.getBean("controller");

		ViewFrame frame = new ViewFrame(controller);

		controller.setFrame(frame);
		controller.load();

		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.getSplitPane().setDividerLocation(0.25);

		// Wird bei Beendigung des Programms ausgefuehrt
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {

			}
		});
	}
}
