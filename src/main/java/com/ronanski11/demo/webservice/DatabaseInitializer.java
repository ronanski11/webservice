
package com.ronanski11.demo.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(DatabaseInitializer.class);

	@Override
	public void run(String... args) throws Exception {
		LOG.info("\r\n   _____              _            _   _       _      __          __  _                         _          \r\n"
				+ "  / ____|            | |          | | (_)     | |     \\ \\        / / | |                       (_)         \r\n"
				+ " | |     _ __ ___  __| | ___ _ __ | |_ _  __ _| |___   \\ \\  /\\  / /__| |__  ___  ___ _ ____   ___  ___ ___ \r\n"
				+ " | |    | '__/ _ \\/ _` |/ _ \\ '_ \\| __| |/ _` | / __|   \\ \\/  \\/ / _ \\ '_ \\/ __|/ _ \\ '__\\ \\ / / |/ __/ _ \\\r\n"
				+ " | |____| | |  __/ (_| |  __/ | | | |_| | (_| | \\__ \\    \\  /\\  /  __/ |_) \\__ \\  __/ |   \\ V /| | (_|  __/\r\n"
				+ "  \\_____|_|  \\___|\\__,_|\\___|_| |_|\\__|_|\\__,_|_|___/     \\/  \\/ \\___|_.__/|___/\\___|_|    \\_/ |_|\\___\\___|\r\n"
				+ "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \r\n"
				+ " |______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|\r\n"
				+ "   / / / //  ____  \\                                 | |  (_)_ /_ \\ \\ \\ \\                                  \r\n"
				+ "  / / / //  / ___|  \\ _ __ ___  _ __   __ _ _ __  ___| | ___ | || |\\ \\ \\ \\                                 \r\n"
				+ " ( ( ( (|  | |       | '__/ _ \\| '_ \\ / _` | '_ \\/ __| |/ / || || | ) ) ) )                                \r\n"
				+ "  \\ \\ \\ \\  | |___    | | | (_) | | | | (_| | | | \\__ \\   <| || || |/ / / /                                 \r\n"
				+ "   \\_\\_\\_\\  \\____|  /|_|  \\___/|_| |_|\\__,_|_| |_|___/_|\\_\\_||_||_/_/_/_/                                  \r\n"
				+ "  ______ _\\________/__ ______ ______ ______ ______ ______ ______ ______ ______                             \r\n"
				+ " |______|______|______|______|______|______|______|______|______|______|______|                            \r\n"
				+ "                                                                                                          \r\n"
				+ "   :: Credentials Webservice ::		(v2.5)\r\n");
	}

}