import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GUI extends JFrame{

    //Variabili Interfaccia grafica
    public JButton AggiuntaEvento;
    public JButton AggiornaLista;
    public JTextField QuantitaEvento;
    public JTable TabellaEventi;
    public JLabel Titolo;
    public JLabel TitoloTabella;
    public JScrollPane jScrollPane1;


    public GUI(){
        super("Client");
        initComponents();
    }


    public void initComponents() {
        QuantitaEvento = new javax.swing.JTextField();
        AggiuntaEvento = new javax.swing.JButton();
        AggiornaLista = new javax.swing.JButton();
        TabellaEventi = new javax.swing.JTable();
        TitoloTabella = new javax.swing.JLabel();
        Titolo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();

        Titolo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Titolo.setText("Prenotazione evento");

        TabellaEventi.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Nome Evento", "Posti Disponibili"
                }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        TabellaEventi.getTableHeader().setReorderingAllowed(false);
        TabellaEventi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(TabellaEventi);

        TitoloTabella.setText("Lista Eventi");

        QuantitaEvento.setText("Inserire Quantita Persone");

        AggiuntaEvento.setText("Iscriviti all'evento");
        AggiornaLista.setText("Aggiorna tabella");


        MyListenerBook aggiuntaEventoHandler1  = new MyListenerBook(this);
        AggiuntaEvento.addActionListener(aggiuntaEventoHandler1);

        MyListenerReload aggiuntaEventoHandler2  = new MyListenerReload(this);
        AggiornaLista.addActionListener(aggiuntaEventoHandler2);




        JPanel Panel = new JPanel();
        Panel.add(Titolo);
        Panel.add(QuantitaEvento);
        Panel.add(AggiuntaEvento);
        Panel.add(AggiornaLista);
        Panel.add(jScrollPane1);
        Panel.add(TitoloTabella);
        getContentPane().add(Panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        aggiuntaEventoHandler2.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null){});

    }


    public String getQuantitaEvento(){
        return QuantitaEvento.getText();
    }



    public JTable getEventsTable(){
        return this.TabellaEventi;
    }

}


