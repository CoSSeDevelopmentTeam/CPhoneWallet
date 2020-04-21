package net.comorevi.cpapp.wallet;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.activity.original.MessageActivity;
import net.comorevi.cphone.cphone.widget.element.Dropdown;
import net.comorevi.cphone.cphone.widget.element.Element;
import net.comorevi.cphone.cphone.widget.element.Input;
import net.comorevi.cphone.cphone.widget.element.Label;
import net.comorevi.moneyapi.MoneySAPI;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class ExchangeActivity extends CustomActivity {

    private Bundle bundle;

    public ExchangeActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        Element[] elements = {
                new Label(bundle.getString("label_exchange1")),
                new Dropdown(bundle.getString("text_exchange_dropdown"), new LinkedList<>(List.of(bundle.getString("content_exchange_dropdown0"), bundle.getString("content_exchange_dropdown1"))), 0),
                new Label(bundle.getString("label_exchange2") +
                        "\nMoney: " + MoneySAPI.getInstance().getMoney(bundle.getCPhone().getPlayer()) +
                        "\nCoin: " + MoneySAPI.getInstance().getCoin(bundle.getCPhone().getPlayer()) +
                        "\nExchange Rate: 1MS = " + MoneySAPI.getInstance().getExchangeRate() + "coin"),
                new Input(bundle.getString("text_exchange_input"), bundle.getString("exchange_input_placeholder"))
        };
        this.setTitle(bundle.getString("title_exchange"));
        this.addFormElements(elements);
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse cResponse = (CustomResponse) response;
        if (!isPositiveNumber(cResponse.getResult().get(3).toString())) {
            new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
            return ReturnType.TYPE_CONTINUE;
        } else if (cResponse.getResult().get(1).toString().equals(bundle.getString("content_exchange_dropdown0"))) {
            if (!MoneySAPI.getInstance().existsCoinData(cResponse.getPlayer())) {
                new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange1"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            } else if (MoneySAPI.getInstance().getCoin(cResponse.getPlayer()) < Integer.parseInt(cResponse.getResult().get(3).toString())) {
                new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange2"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            } else if (Integer.parseInt(cResponse.getResult().get(3).toString()) < MoneySAPI.getInstance().getExchangeRate()) {
                new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange3"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            } else {
                try {
                    MoneySAPI.getInstance().exchangeCoinToMoney(cResponse.getPlayer(), Integer.parseInt(cResponse.getResult().get(3).toString()));
                    bundle.getCPhone().setHomeMessage(bundle.getString("exchange_completed"));
                    return ReturnType.TYPE_END;
                } catch (Exception e) {
                    new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange1"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
                    return ReturnType.TYPE_CONTINUE;
                }
            }
        } else if (cResponse.getResult().get(1).toString().equals(bundle.getString("content_exchange_dropdown1"))) {
            if (MoneySAPI.getInstance().getMoney(cResponse.getPlayer()) < Integer.parseInt(cResponse.getResult().get(3).toString())) {
                new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange4"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            } else if (Calendar.getInstance(TimeZone.getTimeZone("Tokyo/Asia")).get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                new MessageActivity(getManifest(), bundle.getString("title_error"), bundle.getString("error_exchange5"), bundle.getString("button_error1"), bundle.getString("button_error2"), new ExchangeActivity(getManifest())).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            }
            MoneySAPI.getInstance().exchangeMoneyToCoin(cResponse.getPlayer(), Integer.parseInt(cResponse.getResult().get(3).toString()));
            bundle.getCPhone().setHomeMessage(bundle.getString("exchange_completed"));
            return ReturnType.TYPE_END;
        }
        new MainActivity(getManifest()).start(bundle);
        return ReturnType.TYPE_CONTINUE;
    }

    private boolean isPositiveNumber(String value) {
        try {
            int i = Integer.parseInt(value);
            return i > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
