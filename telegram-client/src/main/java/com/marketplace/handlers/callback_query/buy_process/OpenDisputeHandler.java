package com.marketplace.handlers.callback_query.buy_process;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.OrderStatus;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "OPEN_DISPUTE .*", type = MessageType.CALLBACK_QUERY)
public class OpenDisputeHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        long purchaseId = Long.parseLong(getKeys(update).get("i"));
        httpCoreInterface.statusUpdate(purchaseId, OrderStatus.DISPUTE);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setParseMode(ParseMode.HTML);
        payload.setText("""
                <b>Dispute was opened</b>
                Send command to bot: <code>/dispute %s your message</code>
                For example: <code>/dispute %s I've got some problems with the activation code. It's expired.</code>
                Bot administrators will check your claim and refund your purchase if seller won't solve your problem.
                You can see dispute chat in 'Disputes' section.
                """.formatted(purchaseId, purchaseId));
        bot.process(payload);
    }
}
