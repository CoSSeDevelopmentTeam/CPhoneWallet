package net.comorevi.cpapp.wallet;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Label;
import net.comorevi.moneyapi.MoneySAPI;

public class SeeMoneyResultActivity extends CustomActivity {

    private String name;
    private MoneySAPI api;

    public SeeMoneyResultActivity(ApplicationManifest manifest, String name, MoneySAPI api) {
        super(manifest);
        this.name = name;
        this.api = api;
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.setTitle(bundle.getString("title_see_result"));
        String replacedString = bundle.getString("label_see_result").replace("%1", name).replace("%2", String.valueOf(api.getMoney(name))).replace("%3", api.getMoneyUnit());
        this.addFormElement(new Label(replacedString));
    }

    @Override
    public ReturnType onStop(Response response) {
        return ReturnType.TYPE_END;
    }
}
