package com.example.kenya.ui.Consulta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.kenya.databinding.FragmentConsultaBinding;

public class ConsultaFragment extends Fragment {

    private FragmentConsultaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConsultaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnWhatsApp = binding.btnWhatsApp;
        btnWhatsApp.setOnClickListener(v -> {
            String numero = "981183416";
            String mensaje = "Hola, necesito ayuda con el servicio técnico.";
            String url = "https://wa.me/51" + numero + "?text=" + Uri.encode(mensaje);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Debes tener WhatsApp instalado", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
