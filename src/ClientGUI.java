import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientGUI extends JFrame{

    //Variabili Interfaccia grafica
    public JButton AggiuntaEvento;
    public JButton AggiornaLista;
    private JTextField NomeEvento;
    private JTextField QuantitaEvento;
    private JTable TabellaEventi;
    private JLabel Titolo;
    private JLabel TitoloTabella;
    private JScrollPane jScrollPane1;


    //variabili output input
    DataInputStream input;
    DataOutputStream output;

    public ClientGUI(DataInputStream input, DataOutputStream output){
        super("Client");
        this.input=input;
        this.output=output;
    }


    public void initComponents() {
        NomeEvento = new javax.swing.JTextField();
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
        jScrollPane1.setViewportView(TabellaEventi);

        TitoloTabella.setText("Lista Eventi");

        NomeEvento.setText("Inserire Nome Evento ");

        QuantitaEvento.setText("Inserire Quantita Persone");

        AggiuntaEvento.setText("Iscriviti all'evento");
        AggiornaLista.setText("Aggiorna tabella");

        MyWorkerBook aggiuntaEventoHandler1  = new MyWorkerBook(this);
        AggiuntaEvento.addActionListener(aggiuntaEventoHandler1);

        MyWorkerReload aggiuntaEventoHandler2  = new MyWorkerReload(this);
        AggiornaLista.addActionListener(aggiuntaEventoHandler2);

        JPanel Panel = new JPanel();
        Panel.add(Titolo);
        Panel.add(NomeEvento);
        Panel.add(QuantitaEvento);
        Panel.add(AggiuntaEvento);
        Panel.add(AggiornaLista);
        Panel.add(jScrollPane1);
        Panel.add(TitoloTabella);
        getContentPane().add(Panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();


    }


    public void aggiornaTabella(String eventList) {


        eventList = eventList.substring(0, eventList.indexOf('\0'));
        String[] righe = eventList.split("\n");
        for (int i = 0; i < righe.length; i++) {
            righe[i] = righe[i].substring(6);
        }
        String[] informazioni = new String[2];
        DefaultTableModel model = (DefaultTableModel) TabellaEventi.getModel();

        for (int j = 0; j < model.getRowCount(); j++) {
            model.removeRow(j);
        }
        for (int i = 0; i < righe.length; i++) {
            String[] tmp = righe[i].split(",");

            informazioni[0] = tmp[0].substring(0, tmp[0].indexOf(" "));
            informazioni[1] = tmp[1].substring(tmp[1].indexOf("=") + 1);

            model.addRow(informazioni);
            TabellaEventi.repaint();
        }
    }


    public DataInputStream getInput(){
        return this.input;
    }

    public DataOutputStream getOutput(){
        return this.output;
    }

}
