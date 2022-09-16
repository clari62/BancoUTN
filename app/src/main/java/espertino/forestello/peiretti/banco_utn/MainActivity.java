package espertino.forestello.peiretti.banco_utn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        //setup spinner monedas
        spinner = (Spinner) binding.spinnerMoneda;
        monedas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.monedas));
        spinner.setAdapter(monedas);

        //setup boton simular
        Button botonSimular = (Button) binding.buttonSimular;
        MainActivity ctx = this;

        botonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent simular = new Intent(ctx,SimularPlazoFijo.class);
                simular.putExtra("moneda",(String) spinner.getSelectedItem());
                startActivity(simular);
            }
        });
        
    }
}