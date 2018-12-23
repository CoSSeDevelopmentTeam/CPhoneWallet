package net.comorevi.cpapp.wallet;

import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Input;
import net.comorevi.cphone.cphone.widget.element.Label;
import net.comorevi.cphone.presenter.SharingData;
import net.comorevi.moneyapi.MoneySAPI;

public class SeeMoneyActivity extends CustomActivity {

    private MoneySAPI api;

    private Bundle bundle;
    private CPhone cPhone;

    public SeeMoneyActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();
        this.api = (MoneySAPI) SharingData.server.getPluginManager().getPlugin("MoneySAPI");
        this.setTitle(bundle.getString("title_see"));
        String replacedString = bundle.getString("label_see1").replace("%1", String.valueOf(api.getMoney(bundle.getCPhone().getPlayer().getName()))).replace("%2", api.getMoneyUnit());
        this.addFormElement(new Label(replacedString + "\n" + bundle.getString("label_see2")));
        this.addFormElement(new Input(bundle.getString("label_see_input"), bundle.getString("see_input_placeholder")));
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse cResponse = (CustomResponse) response;
        if (!api.isExists(cResponse.getResult().get(1).toString())) {
            new ErrorActivity(getManifest(), "入力されたユーザーのデータがありません", this).start(cPhone.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }

        if (api.getPublishStatus(cResponse.getResult().get(1).toString()) == false) {
            new ErrorActivity(getManifest(), "入力されたプレイヤーはデータを非公開にしています", this).start(cPhone.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }

        new SeeMoneyResultActivity(getManifest(), cResponse.getResult().get(1).toString(), api).start(cResponse.getPlayer(), bundle.getStrings());
        return ReturnType.TYPE_END;
    }
}
