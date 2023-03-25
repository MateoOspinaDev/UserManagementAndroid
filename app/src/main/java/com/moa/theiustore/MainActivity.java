package com.moa.theiustore;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText txtid, txtnom;
    private Button btnbus, btnmod, btnreg, btneli;
    private ListView lvDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        txtid = (EditText) findViewById(R.id.txtid);
        txtnom = (EditText) findViewById(R.id.txtnom);
        btnbus = (Button) findViewById(R.id.btnbus);
        btnmod = (Button) findViewById(R.id.btnmod);
        btnreg = (Button) findViewById(R.id.btnreg);
        btneli = (Button) findViewById(R.id.btneli);
        lvDatos = (ListView) findViewById(R.id.lvDatos);

        botonBuscar();
        botonModificar();
        botonRegistrar();
        botonEliminar();

        private void botonBuscar () {
        }

        private void botonModificar () {
        }

        private void botonRegistrar () {
        }

        private void botonEliminar () {
        }

        private void ocultarTeclado () {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
