package net.comorevi.cpapp.wallet;

import cn.nukkit.utils.TextFormat;
import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Label;
import net.comorevi.cphone.cphone.widget.element.Toggle;
import net.comorevi.cphone.presenter.SharingData;
import net.comorevi.moneyapi.MoneySAPI;

public class SettingsActivity extends CustomActivity {

    private Bundle bundle;
    private CPhone cPhone;

    private MoneySAPI api;

    public SettingsActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();

        this.setTitle(bundle.getString("title_settings"));

        api = (MoneySAPI) SharingData.server.getPluginManager().getPlugin("MoneySAPI");
        boolean status = api.getPublishStatus(cPhone.getPlayer().getName());
        this.addFormElement(new Label(bundle.getString("label_settings")));

        this.addFormElement(new Toggle(bundle.getString("label_settings_toggle"), status));
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse cResponse = (CustomResponse) response;
        api.setPublishStatus(cResponse.getPlayer().getName(), Boolean.valueOf(String.valueOf(cResponse.getResult().get(1))));
        cPhone.setHomeMessage(TextFormat.AQUA + "設定を保存しました");
        return ReturnType.TYPE_END;
    }
}
