package com.example.miniquiz;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button przyciskRozpocznij;
    private Button przyciskResetuj;
    private Button przyciskA;
    private Button przyciskB;
    private Button przyciskC;
    private TextView polePytanie;
    private TextView napisWynik;
    private LinearLayout obszarPytania;

    // Logika quizu
    private List<Question> wszystkiePytania;
    private List<Question> pytaniaDoQuizu;
    private int aktualnyIndeks;
    private int wynik;
    private Random rng = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        przyciskRozpocznij = findViewById(R.id.przyciskRozpocznij);
        przyciskResetuj = findViewById(R.id.przyciskResetuj);
        przyciskA = findViewById(R.id.przyciskA);
        przyciskB = findViewById(R.id.przyciskB);
        przyciskC = findViewById(R.id.przyciskC);
        polePytanie = findViewById(R.id.polePytanie);
        napisWynik = findViewById(R.id.napisWynik);
        obszarPytania = findViewById(R.id.obszarPytania);

        inicjalizujPytania();
        resetujStan();

        // kliknięcia
        przyciskRozpocznij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rozpocznijQuiz();
            }
        });

        przyciskResetuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetujStan();
            }
        });

        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                String tekst = btn.getText().toString();
                sprawdzOdpowiedz(tekst);
            }
        };

        przyciskA.setOnClickListener(answerListener);
        przyciskB.setOnClickListener(answerListener);
        przyciskC.setOnClickListener(answerListener);
    }

    private void inicjalizujPytania() {
        wszystkiePytania = new ArrayList<>();
        wszystkiePytania.add(new Question("Stolica Wloch to:", "Rzym", "Paryz", "Madryt", "Rzym"));
        wszystkiePytania.add(new Question("2 + 2 = ?", "3", "4", "5", "4"));
        wszystkiePytania.add(new Question("Kolor banana to:", "Niebieski", "Zolty", "Zielony", "Zolty"));
        wszystkiePytania.add(new Question("Najwiekszy ocean to:", "Atlantyk", "Spokojny", "Indyjski", "Spokojny"));
        wszystkiePytania.add(new Question("Jaki gaz oddychamy glownie?", "Tlen", "Azot", "Dwutlenek wegla", "Tlen"));
        wszystkiePytania.add(new Question("Stolica Polski to:", "Krakow", "Warszawa", "Gdansk", "Warszawa"));
        wszystkiePytania.add(new Question("Ile minut ma godzina?", "50", "60", "70", "60"));
        wszystkiePytania.add(new Question("Ktory zwierzak miauczy?", "Pies", "Kot", "Krowa", "Kot"));
        wszystkiePytania.add(new Question("Ktore z tych jest owocem?", "Marchew", "Jablko", "Salata", "Jablko"));
        wszystkiePytania.add(new Question("Co pije krowa?", "Mleko", "Wode", "Sok", "Wode"));
        wszystkiePytania.add(new Question("Kto wynalazl zarowke?", "Edison", "Newton", "Tesla", "Edison"));
        wszystkiePytania.add(new Question("Ktory pierwiastek chemiczny ma symbol O?", "Wodor", "Tlen", "Zelazo", "Tlen"));
        wszystkiePytania.add(new Question("Ile stopni ma kat prosty?", "45", "90", "180", "90"));
        wszystkiePytania.add(new Question("Kto napisal 'Pan Tadeusz'?", "Sienkiewicz", "Mickiewicz", "Slowacki", "Mickiewicz"));
        wszystkiePytania.add(new Question("Jakiego koloru jest chlorofil?", "Niebieski", "Zielony", "Czerwony", "Zielony"));
        wszystkiePytania.add(new Question("Ktory kraj slynie z sushi?", "Chiny", "Japonia", "Korea", "Japonia"));
        wszystkiePytania.add(new Question("W ktorym miesiacu jest najkrotszy dzien w Polsce?", "Czerwiec", "Grudzien", "Marzec", "Grudzien"));
        wszystkiePytania.add(new Question("Jak nazywa sie stolica Niemiec?", "Berlin", "Monachium", "Hamburg", "Berlin"));
    }

    private void resetujStan() {
        wynik = 0;
        aktualnyIndeks = 0;
        napisWynik.setText("Wynik: " + wynik);
        obszarPytania.setVisibility(View.GONE);
        przyciskResetuj.setVisibility(View.GONE);
        przyciskRozpocznij.setVisibility(View.VISIBLE);
    }

    private void rozpocznijQuiz() {
        // losujemy 5 unikalnych pytan (jesli mniej pytan - bierzemy ile jest)
        List<Question> kopia = new ArrayList<>(wszystkiePytania);
        Collections.shuffle(kopia, rng);
        int ile = Math.min(5, kopia.size());
        pytaniaDoQuizu = kopia.subList(0, ile);

        wynik = 0;
        aktualnyIndeks = 0;
        napisWynik.setText("Wynik: " + wynik);

        przyciskRozpocznij.setVisibility(View.GONE);
        obszarPytania.setVisibility(View.VISIBLE);
        przyciskResetuj.setVisibility(View.VISIBLE);

        pokazAktualnePytanie();
    }

    private void pokazAktualnePytanie() {
        if (aktualnyIndeks >= pytaniaDoQuizu.size()) {
            koniecQuizu();
            return;
        }

        Question q = pytaniaDoQuizu.get(aktualnyIndeks);
        polePytanie.setText(q.tekst);
        przyciskA.setText(q.odpowiedzA);
        przyciskB.setText(q.odpowiedzB);
        przyciskC.setText(q.odpowiedzC);
    }

    private void sprawdzOdpowiedz(String wybrana) {

        String poprawna = pytaniaDoQuizu.get(aktualnyIndeks).poprawna;
        if (wybrana.equals(poprawna)) {
            wynik++;
            napisWynik.setText("Wynik: " + wynik);
        }
        aktualnyIndeks++;
        if (aktualnyIndeks < pytaniaDoQuizu.size()) {
            pokazAktualnePytanie();
        } else {
            koniecQuizu();
        }
    }

    private void koniecQuizu() {
        obszarPytania.setVisibility(View.GONE);
        przyciskRozpocznij.setVisibility(View.VISIBLE);


        int ile = pytaniaDoQuizu.size();
        String tytul = "Koniec quizu!";
        String tekst = "Twoj wynik: " + wynik + " / " + ile;

        new AlertDialog.Builder(this)
                .setTitle(tytul)
                .setMessage(tekst)
                .setPositiveButton("OK", null)
                .show();
    }
}
