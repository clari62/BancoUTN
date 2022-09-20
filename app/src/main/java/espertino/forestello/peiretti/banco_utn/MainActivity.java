package espertino.forestello.peiretti.banco_utn;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private String dias="";
    private String capital="";
    private boolean simulo=false;

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

        //setup boton constituir
        ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        dias = result.getData().getExtras().get("dias").toString();
                        capital = result.getData().getExtras().get("capital").toString();
                        simulo=true;
                        if(!(binding.etNombre.getText().toString().isEmpty() || binding.etApellido.getText().toString().isEmpty()))
                            binding.buttonConstituir.setEnabled(true);
                    }
                });

        botonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent simular = new Intent(ctx,SimularPlazoFijo.class);
                simular.putExtra("moneda",(String) spinner.getSelectedItem());

                startActivityIntent.launch(simular);

            }
        });

        Button botonConstituir = (Button) binding.buttonConstituir;
        botonConstituir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String moneda = spinner.getSelectedItem().toString().toLowerCase();

                builder.setTitle("Felicitaciones "+nombre+" "+apellido+"!!!!");
                builder.setMessage("Tu plazo fijo de "+capital+" "+moneda+" por "+dias+" ha sido constituido!");
                builder.setPositiveButton("Piola",null);
                builder.create().show();

                simulo=false;
                botonConstituir.setEnabled(false);
            }
        });

        //setup edit text de nombre y apellido
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(simulo && !binding.etNombre.getText().toString().isEmpty() && !binding.etApellido.getText().toString().isEmpty())
                    botonConstituir.setEnabled(true);
                else{
                    botonConstituir.setEnabled(false);
                }
            }
        };
        binding.etNombre.addTextChangedListener(tw);
        binding.etApellido.addTextChangedListener(tw);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean("buttonConstituir",binding.buttonConstituir.isEnabled());
        outState.putString("dias",dias);
        outState.putString("capital",capital);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        binding.buttonConstituir.setEnabled(savedInstanceState.getBoolean("buttonConstituir"));
        dias=savedInstanceState.getString("dias");
        capital=savedInstanceState.getString("capital");
    }
}