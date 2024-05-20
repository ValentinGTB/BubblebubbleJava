// Classe MyThread aggiornata con Singleton
package com.bubblebobble.models;

// ---- CREATA SECONDO IL SINGLETON PATTERN ----

import com.bubblebobble.Constants;


public class MyThread extends Thread {

    private static MyThread instance;
    private static int callCount = 0;
    PlayerModel pm;

    // ---- PER CREARE LE ISTANZE DEI THREAD QUINDI PER CREARE PIU THREAD ----

    private MyThread() {}

    public static synchronized MyThread getInstance() { // Torna una istanza di MyThread se esiste, creala altrimenti
        if (instance == null) {
            instance = new MyThread();
        }
        return instance;
    }

    public synchronized void startThread(PlayerModel pm) { // Se il thread non è attivo attivalo, incrementa le chiamate altrimenti
        if (!isAlive()) {
            start();
        }
        this.pm = pm; //Inizializzo il playermodel
        incrementCallCount();
    }

    private synchronized void incrementCallCount() { // Incrementa le chiamate
        callCount++;
        System.out.println("chiamato " + callCount + " volte");
    }


    // ---- METODO PRINCIPALE CON LE ISTRUZIONI DA ESEGUIRE ----
    // ---- CHIAMATO UNA VOLTA OGNI DUE SECONDI ---- 

    @Override
    public void run() {
        int count = 0;
        while (true) {
            if(Constants.colpito)
            {
                count++;
                if (pm.getVita() > 0) {
                    pm.setVita();
                }
            }

            try {
                Thread.sleep(3000); // Vanish per 3 secondi
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, exiting...");
                break;
            }
        }
    }
}