import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    public JLabel MessaggioErrore;
    public JTable TabellaEventi;
    public JLabel Titolo;
    public JLabel TitoloTabella;
    public JLabel MessaggioQuantita;
    public JScrollPane jScrollPane1;
    MyListenerReload aggiuntaEventoHandler2;

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
        MessaggioErrore = new JLabel();
        MessaggioQuantita = new JLabel();

        Titolo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Titolo.setText("Event reservation app");
        MessaggioQuantita.setText("Number of seats: ");
        TabellaEventi.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Event name", "Seats Avaible"
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

        TitoloTabella.setText("Event List");



        AggiuntaEvento.setText("Book");
        AggiornaLista.setText("Reload Table");


        MyListenerBook aggiuntaEventoHandler1  = new MyListenerBook(this);
        AggiuntaEvento.addActionListener(aggiuntaEventoHandler1);

        aggiuntaEventoHandler2  = new MyListenerReload(this);
        AggiornaLista.addActionListener(aggiuntaEventoHandler2);





        JPanel Panel = new JPanel();

        Panel.setLayout(null);
        Panel.setPreferredSize(new Dimension(600,600));

        Titolo.setBounds(150,10,400,38);
        TitoloTabella.setBounds(100,60,70,15);
        jScrollPane1.setBounds(10,100,300,300);
        MessaggioQuantita.setBounds(10,420,100,20);
        QuantitaEvento.setBounds(115,420,150,20);
        AggiuntaEvento.setBounds(400,300,150,30);
        AggiornaLista.setBounds(400,250,150,30);
        MessaggioErrore.setBounds(400,200,200,15);
        Panel.add(Titolo);
        Panel.add(QuantitaEvento);
        Panel.add(AggiuntaEvento);
        Panel.add(AggiornaLista);
        Panel.add(jScrollPane1);
        Panel.add(TitoloTabella);
        Panel.add(MessaggioQuantita);
        Panel.add(MessaggioErrore);
        getContentPane().add(Panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        aggiornaTabella();

    }


    public void aggiornaTabella(){
        aggiuntaEventoHandler2.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null){});
    }

    public String getQuantitaEvento(){
        return QuantitaEvento.getText();
    }



    public JTable getEventsTable(){
        return this.TabellaEventi;
    }

}


