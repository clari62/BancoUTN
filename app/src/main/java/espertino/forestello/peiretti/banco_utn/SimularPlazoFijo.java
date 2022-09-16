package espertino.forestello.peiretti.banco_utn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import espertino.forestello.peiretti.banco_utn.databinding.ActivitySimularPlazoFijoBinding;

public class SimularPlazoFijo extends AppCompatActivity {

    private ActivitySimularPlazoFijoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // setup subtitulo
        Intent simular = getIntent();
        String moneda = simular.getStringExtra("moneda");
        ((TextView) binding.tvSubtitulo).setText("Simulador Plazo Fijo en "+moneda);

        //setup textview cant de dias
        SeekBar sb = binding.seekBar;

        //listener de seekbar
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView cantDias = binding.tvCantDias;
                Integer valor = sb.getProgress();

                // seteo de cantidad de dias del seekbar.
                cantDias.setText(valor.toString() + (valor>1?" días":" día"));

                validarCalcularYActualizarTv();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //listener de los editText
        ((EditText) binding.etTasaNominal).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void afterTextChanged(Editable editable) {
                validarCalcularYActualizarTv();
            }
        });
        ((EditText) binding.etTasaEfectiva).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void afterTextChanged(Editable editable) {
                validarCalcularYActualizarTv();
            }
        });
        ((EditText) binding.etCapital).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void afterTextChanged(Editable editable) {
                validarCalcularYActualizarTv();
            }
        });
    }

    private void validarCalcularYActualizarTv(){
        //validaciones
        String tasaNominal = ((EditText) binding.etTasaNominal).getText().toString();
        String tasaEfectiva = ((EditText) binding.etTasaEfectiva).getText().toString();
        String capital = ((EditText) binding.etCapital).getText().toString();

        if(tasaNominal.isEmpty() || tasaEfectiva.isEmpty() || capital.isEmpty()) {
            ((Button) binding.buttonConfirmar).setEnabled(false);
            ((TextView)binding.tvPlazo).setText("Plazo:");
            ((TextView)binding.tvCapital).setText("Capital:");
            ((TextView)binding.tvIntereses).setText("Intereses ganados:");
            ((TextView)binding.tvMonto).setText("Monto total:");
            ((TextView)binding.tvMontoAnual).setText("Monto total anual:");

            return;
        }

        //calculo
        int dias = ((SeekBar) binding.seekBar).getProgress();
        Double ganancia = Double.valueOf(tasaEfectiva)*Double.valueOf(capital)*dias/(365*100);
        Double gananciaAnual = ganancia*365/dias;
        //seteo
        ((TextView)binding.tvPlazo).setText("Plazo: "+ String.valueOf(dias)+ ((dias>1)?"días":"día"));
        ((TextView)binding.tvCapital).setText("Capital: $"+capital);
        ((TextView)binding.tvIntereses).setText("Intereses ganados: $"+ganancia.toString());
        ((TextView)binding.tvMonto).setText("Monto total: $"+String.valueOf(Double.valueOf(capital)+ganancia));
        ((TextView)binding.tvMontoAnual).setText("Monto total anual: $"+String.valueOf(gananciaAnual+Double.valueOf(capital)));

        //habilitar boton
        ((Button) binding.buttonConfirmar).setEnabled(true);
    }
}