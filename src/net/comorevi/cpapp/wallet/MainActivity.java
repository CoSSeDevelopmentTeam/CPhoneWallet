package net.comorevi.cpapp.wallet;

import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.moneyapi.MoneySAPI;

public class MainActivity extends ListActivity {

    private Bundle bundle;
    private CPhone cPhone;

    public MainActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();
        this.setTitle(bundle.getString("title_home"));
        this.setContent(bundle.getString("content_home") + MoneySAPI.getInstance().getMoney(cPhone.getPlayer()) + MoneySAPI.UNIT + ", " + MoneySAPI.getInstance().getCoin(cPhone.getPlayer()) + "coin");

        if (cPhone.getPlayer().isOp()) {
            this.addButton(new Button().setText(bundle.getString("button_home1")));
            this.addButton(new Button().setText(bundle.getString("button_home2")));
            this.addButton(new Button().setText(bundle.getString("button_home3")));
            this.addButton(new Button().setText(bundle.getString("button_home4")));
            this.addButton(new Button().setText(bundle.getString("button_home5")));
            this.addButton(new Button().setText(bundle.getString("button_home6")));
            this.addButton(new Button().setText(bundle.getString("button_home7")));
        } else {
            this.addButton(new Button().setText(bundle.getString("button_home1")));
            this.addButton(new Button().setText(bundle.getString("button_home2")));
            this.addButton(new Button().setText(bundle.getString("button_home3")));
            this.addButton(new Button().setText(bundle.getString("button_home7")));
        }
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case 0:
                new PayMoneyActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 1:
                new SeeMoneyActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 2:
                new ExchangeActivity(getManifest()).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            case 3:
                if (listResponse.getPlayer().isOp()) {
                    new GiveMoneyActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                } else {
                    new SettingsActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                }
                return ReturnType.TYPE_CONTINUE;
            case 4:
                new TakeMoneyActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 5:
                new SetMoneyActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 6:
                new SettingsActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
        }

        return ReturnType.TYPE_END;
    }
}
