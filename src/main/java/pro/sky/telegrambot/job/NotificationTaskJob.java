package pro.sky.telegrambot.job;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.notificationTask.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationTaskJob {

    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;
    Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    public NotificationTaskJob(
            TelegramBot telegramBot,
            NotificationTaskRepository notificationTaskRepository
    ) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Scheduled(cron = "${cron.interval.notification.task}")
    @SchedulerLock(name = "notificationTaskJob")
    public void sendingNotifications() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->sendingNotifications");

        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        List<NotificationTask> list = notificationTaskRepository.findByDatetimeLessThanEqualAndIsSentFalse(date);

        for (NotificationTask notificationTask : list) {
            telegramBot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getMessage()));
            notificationTask.setSent(true);
            notificationTaskRepository.save(notificationTask);
        }
    }

}
