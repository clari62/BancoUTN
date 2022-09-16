package espertino.forestello.peiretti.banco_utn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

        //setup boton constituir
        Button botonConstituir = (Button) binding.buttonConstituir;
        botonConstituir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String moneda = spinner.getSelectedItem().toString().toLowerCase();
                String capital="CAPITAL";
                String dias="DIAS dias";


                builder.setTitle("Felicitaciones "+nombre+" "+apellido+"!!!!");
                builder.setMessage("Tu plazo fijo de "+capital+" "+moneda+" por "+dias+" ha sido constituido!");
                builder.setPositiveButton("Piola",null);
                builder.create().show();
            }
        });

        
    }
}