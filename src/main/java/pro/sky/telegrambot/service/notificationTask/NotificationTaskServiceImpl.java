package pro.sky.telegrambot.service.notificationTask;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationTaskServiceImpl implements NotificationTaskService {

    public final String pattern = "(0[1-9]|[12]\\d|3[01]).(0?[1-9]|1[012]).((?:19|20)\\d\\d) (0\\d|1\\d|2[0-3]):([0-5]\\d) ([\\s\\S]*)";

    private final NotificationTaskRepository notificationTaskRepository;
    Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    public NotificationTaskServiceImpl(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public String getCreatePattern() {
        return pattern;
    }

    @Override
    public SendMessage create(Update update) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->create");

        try {
            Matcher matcher = Pattern.compile(pattern).matcher(update.message().text());

            if (matcher.find()) {
                LocalDateTime nowDateTime = LocalDateTime.now();
                LocalDateTime notificationDateTime = LocalDateTime.parse(matcher.group(3) + "-" + matcher.group(2) + "-" + matcher.group(1) + "T" + matcher.group(4) + ":" + matcher.group(5) + ":00");

                if (notificationDateTime.isBefore(nowDateTime) || notificationDateTime.isEqual(nowDateTime)) {
                    return new SendMessage(update.message().chat().id(), "Я работаю с будущим");
                }

                NotificationTask notificationTask = new NotificationTask();
                notificationTask.setChatId(update.message().chat().id());
                notificationTask.setMessage(matcher.group(6));
                notificationTask.setDatetime(notificationDateTime);

                if (notificationTaskRepository.existsByChatIdEqualsAndMessageEqualsIgnoreCaseAndDatetimeEqualsAndIsSentFalse(
                        notificationTask.getChatId(), notificationTask.getMessage(), notificationTask.getDatetime())
                ) {
                    return new SendMessage(update.message().chat().id(), "Я помню о таком оповещении");
                }

                notificationTaskRepository.save(notificationTask);

                return new SendMessage(update.message().chat().id(), "Запомнил, записал");
            }

            return new SendMessage(update.message().chat().id(), "Я вас какие-то неправильные данные");
        } catch (Exception exception) {
            return new SendMessage(update.message().chat().id(), "Сам не пойму, но я не могу записать вашу оповещение");
        }
    }

}
