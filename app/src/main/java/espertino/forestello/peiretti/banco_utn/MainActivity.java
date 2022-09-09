package espertino.forestello.peiretti.banco_utn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import espertino.forestello.peiretti.banco_utn.databinding.ActivityConstruirPlazoFijoBinding;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<CharSequence> monedas;
    private ActivityConstruirPlazoFijoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  ActivityConstruirPlazoFijoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spinner = (Spinner) binding.spinnerMoneda;
        monedas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.monedas));
        spinner.setAdapter(monedas);

    }
}