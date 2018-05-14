package survey.android.futuretek.ch.ft_survey.events;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import survey.android.futuretek.ch.ft_survey.Callback;
import survey.android.futuretek.ch.ft_survey.Database;
import survey.android.futuretek.ch.ft_survey.R;
import survey.android.futuretek.ch.ft_survey.SkillsActivity;

/**
 * Used to retrieve a skill from a dialog; the skill is then returned from the callback function
 */
public class SkillDialogListener implements View.OnClickListener {

    private final Dialog dialog;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Callback callback;

    private final String EMPTY_STRING = "";

    public SkillDialogListener(final Context context, final LayoutInflater layoutInflater, final Callback<String> callback) {
        this.context = context;
        this.dialog = new Dialog(this.context);
        this.layoutInflater = layoutInflater;
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        final View addSkillDialogView = layoutInflater.inflate(R.layout.dialog, null);
        dialog.setContentView(addSkillDialogView);
        final TextView textInput = (TextView) addSkillDialogView.findViewById(R.id.userInput);
        final Button okBtn = (Button) addSkillDialogView.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(view1 -> {
            // After having pressed on the dialog's button, get the skill name, clear the textInput and return to the callback the wanted name
            final String skillName = textInput.getText().toString();
            textInput.setText(EMPTY_STRING);
            dialog.hide();
            callback.callback(skillName);
        });
        dialog.show();
    }
}
