package net.comorevi.cpapp.wallet;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Label;
import net.comorevi.np.moneys.MoneySAPI;

public class SeeMoneyResultActivity extends CustomActivity {

    private String name;

    public SeeMoneyResultActivity(ApplicationManifest manifest, String name) {
        super(manifest);
        this.name = name;
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.setTitle(bundle.getString("title_see_result"));
        String replacedString = bundle.getString("label_see_result").replace("%1", name).replace("%2", String.valueOf(MoneySAPI.getInstance().getMoney(name))).replace("%3", MoneySAPI.UNIT);
        this.addFormElement(new Label(replacedString));
    }

    @Override
    public ReturnType onStop(Response response) {
        return ReturnType.TYPE_END;
    }
}
