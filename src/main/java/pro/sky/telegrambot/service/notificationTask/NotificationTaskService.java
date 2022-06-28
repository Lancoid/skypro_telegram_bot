package pro.sky.telegrambot.service.notificationTask;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface NotificationTaskService {

    String getCreatePattern();

    SendMessage create(Update update);
}
