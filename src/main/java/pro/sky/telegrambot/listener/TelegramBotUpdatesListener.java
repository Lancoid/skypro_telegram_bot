package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.commands.CommandsService;
import pro.sky.telegrambot.service.notificationTask.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final CommandsService commandsService;
    private final NotificationTaskService notificationTaskService;

    public TelegramBotUpdatesListener(
            TelegramBot telegramBot,
            CommandsService commandsService,
            NotificationTaskService notificationTaskService
    ) {
        this.telegramBot = telegramBot;
        this.commandsService = commandsService;
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message().text() != null) {
                if (update.message().text().equals("/start")) {
                    telegramBot.execute(commandsService.start(update));
                } else if (Pattern.matches(notificationTaskService.getCreatePattern(), update.message().text())) {
                    telegramBot.execute(notificationTaskService.create(update));
                }
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
