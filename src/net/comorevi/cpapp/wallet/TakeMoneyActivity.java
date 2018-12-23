package net.comorevi.cpapp.wallet;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Dropdown;
import net.comorevi.cphone.cphone.widget.element.Input;
import net.comorevi.cphone.cphone.widget.element.Label;
import net.comorevi.cphone.presenter.SharingData;
import net.comorevi.moneyapi.MoneySAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TakeMoneyActivity extends CustomActivity {

    private Bundle bundle;
    private CPhone cPhone;

    public TakeMoneyActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();

        this.setTitle(bundle.getString("title_take"));
        this.addFormElement(new Label(bundle.getString("label_take")));

        List<String> dropDownPlayers = new ArrayList<>();
        Map<UUID, Player> onlinePlayers = SharingData.server.getOnlinePlayers();
        for (UUID uuid : onlinePlayers.keySet()) {
            dropDownPlayers.add(String.valueOf(onlinePlayers.get(uuid).getName()));
        }
        this.addFormElement(new Dropdown(bundle.getString("label_take_dropdown"), dropDownPlayers));

        this.addFormElement(new Input(bundle.getString("label_take_input"), bundle.getString("take_input_placeholder")));
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse cResponse = (CustomResponse) response;
        String dropdown = cResponse.getResult().get(1).toString();
        String input = cResponse.getResult().get(2).toString();

        if (isPositiveNumber(input)) {
            MoneySAPI api = (MoneySAPI) SharingData.server.getPluginManager().getPlugin("MoneySAPI");
            api.addMoney(dropdown, Integer.parseInt(input));

            if (SharingData.server.getPlayer(dropdown) != null) {
                SharingData.server.getPlayer(dropdown).sendMessage("システム>>WalletApp>>\n - " + cResponse.getPlayer().getName() + " により" + input + api.getMoneyUnit() + "所持金を減らされました");
            }
            this.cPhone.setHomeMessage(TextFormat.AQUA + "お金を没収しました");
        } else {
            new ErrorActivity(getManifest(), "0より大きい数字を入力してください", this).start(cPhone.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }
        return ReturnType.TYPE_END;
    }

    private boolean isPositiveNumber(String value) {
        try {
            int i = Integer.parseInt(value);
            if (i > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
