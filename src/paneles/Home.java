/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import cache.Linea;
import cache.UtilCache;
import configuraciones.EspecificacionCache;
import configuraciones.EspecificacionRam;
import configuraciones.EstadoEspecificacion;
import cpu.Peticion;
import cpu.UtilDireccionamiento;
import java.util.ArrayList;
import java.util.List;
import javax.print.DocFlavor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.UnidadMedida;
import utilidades.Validador;

/**
 * Interacción con el proceso principal de peticiones del CPU.
 *
 * @author Marvin
 */
public class Home extends javax.swing.JPanel {

    private EspecificacionRam especiRam;
    private EspecificacionCache especificaCache;

    List<Peticion> listaPeticiones = new ArrayList<>();
    public static List<String> RAM = new ArrayList<>();
    public static List<Linea> CACHE = new ArrayList<>();

    //LISTA QUE DAN SOPORTE A REEMPLAZAMIENTO FIFO Y LRU DE LA TOTALMENTE ASOCIATIVA
    List<Integer> CAFIFO = new ArrayList<>();
    List<Integer> CALRU = new ArrayList<>();

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
    }

    public void aplicarEspecificacionRam() {
        String aux;
        //Capacidad de la RAM
        aux = String.valueOf(especiRam.getCapacidadMP());
        switch (especiRam.getUnidadMedidaMP()) {
            case UnidadMedida.KILO_BYTE:
                aux += " KiloBytes";
                break;
            case UnidadMedida.MEGA_BYTE:
                aux += " MegaBytes";
                break;
            case UnidadMedida.GIGA_BYTE:
                aux += " GigaBytes";
        }
        etiRam.setText("RAM: " + aux);

        //Tamaño Bloque
        aux = String.valueOf(especiRam.getTamañoBloque());
        switch (especiRam.getNivelDireccionable()) {
            case UnidadMedida.BYTE:
                aux += " Bytes";
                break;
            case UnidadMedida.PALABRA:
                aux += " Palabras";
        }
        etiTamañoBloque.setText("Tamaño Bloque: " + aux);

        //Total Bloques
        etiTotalBloques.setText("Total Bloques: " + especiRam.getTotalNumeroBloques() + " -->(2^" + Validador.esPotenciaDeDos(String.valueOf(especiRam.getTotalNumeroBloques())) + ")");

        //Maximo Direccionable
        etiMaxDireccionable.setText("Maximo Direccionable: " + especiRam.getMaxDireccionable() + " bits");

        //Bus de datos
        switch(especiRam.getNivelDireccionable()){
            case UnidadMedida.BYTE:
                etiBusDatos.setText("Bus de datos: 8 bits");
                break;
            case UnidadMedida.PALABRA:
                int numBits = 8 * especiRam.getTamañoPalabra();
                etiBusDatos.setText("Bus de datos: " + numBits + " bits");
        }
        
        //Creacion del componente que representara a la RAM
        switch (especiRam.getTipoLlenado()) {
            case UtilCache.MANUAL:
                Home.RAM = null;
                Home.RAM = new ArrayList<>();
                String cadByte = "00";
                String dato = "";
                if (especiRam.getNivelDireccionable() == UnidadMedida.PALABRA) {
                    for (int i = 0; i < especiRam.getTamañoPalabra(); i++) {
                        dato += cadByte;
                    }
                } else {
                    dato = cadByte;
                }
                for (int i = 0; i < especiRam.getTotalNumeroBloques() * especiRam.getTamañoBloque(); i++) {
                    RAM.add(dato);
                }
                break;
            case UtilCache.LLENADO_ALEAT:
                Home.RAM = null;
                Home.RAM = new ArrayList<>();
                int num;
                for (int i = 0; i < especiRam.getTotalNumeroBloques() * especiRam.getTamañoBloque(); i++) {
                    num = (int) (Math.random() * 256);
                    RAM.add(Integer.toHexString(num));
                }
        }

        EstadoEspecificacion.setEspeciRamEnHome(true); //especificacion aplicada
    }

    public void aplicarEspecificacionCache() {
        String aux;
        EspecificacionRam especificaRam = especificaCache.getRam();

        //Capacidad Cache
        aux = String.valueOf(especificaCache.getCapacidadCache());
        switch (especificaCache.getUnidadMedidaCache()) {
            case UnidadMedida.KILO_BYTE:
                aux += " KiloBytes";
                break;
            case UnidadMedida.MEGA_BYTE:
                aux += " MegaBytes";
                break;
            case UnidadMedida.GIGA_BYTE:
                aux += " GigaBytes";
        }
        etiCache.setText("CACHE: " + aux);

        //Correspondencia
        switch (especificaCache.getFuncionCorrespondencia()) {
            case UtilCache.DIRECTA:
                aux = "Directa";
                break;
            case UtilCache.ASOCIATIVA:
                aux = "Asociativa";
                break;
            case UtilCache.POR_CONJUNTO:
                aux = "Por Conjuntos";
        }
        etiCorrespondencia.setText("Correspondencia: " + aux);

        //Tamaño de linea
        aux = String.valueOf(especificaRam.getTamañoBloque());
        switch (especificaRam.getNivelDireccionable()) {
            case UnidadMedida.BYTE:
                aux += " Bytes";
                break;
            case UnidadMedida.PALABRA:
                aux += " Palabras";
        }
        etiTamLinea.setText("Tamaño de Linea: " + aux);

        //Numero de Lineas
        etiNumLineas.setText("Numero de Lineas: " + especificaCache.getNumTotalLineas());

        //Tamaño de direcciones
        aux = String.valueOf(especificaCache.getRam().getMaxDireccionable());
        etiTamDirecciones.setText("Tamaño direcciones: " + aux + " bits");

        //Reemplazo
        switch (especificaCache.getAlgoReemplazo()) {
            case UtilCache.LRU:
                aux = "LRU";
                break;
            case UtilCache.FIFO:
                aux = "FIFO";
                break;
            case UtilCache.ALEATORIO:
                aux = "Aleatorio";
        }
        etiReemplazo.setText("Reemplazo: " + aux);

        //Creacion de columnas del Formato direcciones en CACHE
        DefaultTableModel tablaFormato = (DefaultTableModel) tlbFormatoCache.getModel();
        DefaultTableModel tablaEjecu = (DefaultTableModel) tlbEjecucion.getModel();

        for (int i = tablaFormato.getRowCount() - 1; i >= 0; i--) {
            tablaFormato.removeRow(i);
        }
        for (int i = tablaEjecu.getRowCount() - 1; i >= 0; i--) {
            tablaEjecu.removeRow(i);
        }

        switch (especificaCache.getFuncionCorrespondencia()) {
            case UtilCache.DIRECTA:
                tablaFormato.setColumnCount(3);
                tablaFormato.setColumnIdentifiers(new Object[]{"Etiqueta", "Linea", "Palabra"});
                tablaFormato.addRow(new Object[]{especificaCache.getFormatEtiqueta() + " bits", especificaCache.getFormatLinea() + " bits", especificaCache.getFormatPalabra() + " bits"});

                tablaEjecu.setColumnCount(4);
                tablaEjecu.setColumnIdentifiers(new Object[]{"Direcciones", "Etiqueta", "Linea", "Palabra"});
                break;
            case UtilCache.ASOCIATIVA:
                tablaFormato.setColumnCount(2);
                tablaFormato.setColumnIdentifiers(new Object[]{"Etiqueta", "Palabra"});
                tablaFormato.addRow(new Object[]{especificaCache.getFormatEtiqueta() + " bits", especificaCache.getFormatPalabra() + " bits"});

                tablaEjecu.setColumnCount(3);
                tablaEjecu.setColumnIdentifiers(new Object[]{"Direcciones", "Etiqueta", "Palabra"});
                break;
            case UtilCache.POR_CONJUNTO:
                tablaFormato.setColumnCount(3);
                tablaFormato.setColumnIdentifiers(new Object[]{"Etiqueta", "Conjunto", "Palabra"});
                tablaFormato.addRow(new Object[]{especificaCache.getFormatEtiqueta() + " bits", especificaCache.getFormatConjunto() + " bits", especificaCache.getFormatPalabra() + " bits"});

                tablaEjecu.setColumnCount(4);
                tablaEjecu.setColumnIdentifiers(new Object[]{"Direcciones", "Etiqueta", "Conjunto", "Palabra"});
        }

        //Llenado de la cache
        switch (especificaRam.getTipoLlenado()) {
            case UtilCache.MANUAL:
                Home.CACHE = null;
                Home.CACHE = new ArrayList<>();
                String cadByte = "00";
                String dato = "";
                if (especificaRam.getNivelDireccionable() == UnidadMedida.PALABRA) {
                    for (int i = 0; i < especiRam.getTamañoPalabra(); i++) {
                        dato += cadByte;
                    }
                } else {
                    dato = cadByte;
                }
                for (int i = 0; i < especificaCache.getNumTotalLineas(); i++) {
                    Linea l = new Linea();
                    for (int j = 0; j < especificaRam.getTamañoBloque(); j++) {
                        l.elementos.add(dato);
                    }
                    CACHE.add(l);
                }
                break;
            case UtilCache.LLENADO_ALEAT:
                Home.CACHE = null;
                Home.CACHE = new ArrayList<>();
                switch (especificaCache.getFuncionCorrespondencia()) {
                    case UtilCache.DIRECTA:
                        for (int i = 0; i < especificaCache.getNumTotalLineas(); i++) {
                            Linea l = new Linea();
                            for (int j = 0; j < especificaRam.getTamañoBloque(); j++) {
                                l.elementos.add(Home.RAM.get(i * especificaRam.getTamañoBloque() + j));
                            }
                            String direccion = Integer.toHexString(i * especificaRam.getTamañoBloque());
                            String eti = UtilCache.generarEtiqueta(direccion, especificaRam.getMaxDireccionable(), especificaCache.getFormatEtiqueta());
                            l.etiqueta = eti;
                            Home.CACHE.add(l);
                        }
                        break;
                    case UtilCache.ASOCIATIVA:
                        CAFIFO = null;
                        CALRU = null;
                        CAFIFO = new ArrayList<>();
                        CALRU = new ArrayList<>();
                        for(int i = 0; i < especificaCache.getNumTotalLineas(); i++){
                            Linea l = new Linea();
                            for(int j = 0; j < especificaRam.getTamañoBloque(); j++){
                                l.elementos.add(Home.RAM.get(i * especificaRam.getTamañoBloque() + j));
                            }
                            String direccion = Integer.toHexString(i * especificaRam.getTamañoBloque());
                            String eti = UtilCache.generarEtiqueta(direccion, especificaRam.getMaxDireccionable(), especificaCache.getFormatEtiqueta());
                            l.etiqueta = eti;
                            Home.CACHE.add(l);
                            CALRU.add(i);
                            CAFIFO.add(i);
                        }
                        break;
                    case UtilCache.POR_CONJUNTO:
                        break;
                }
        }

        EstadoEspecificacion.setEspeciCacheEnHome(true); //Especificacion aplicada
    }

    public EspecificacionRam getEspeciRam() {
        return especiRam;
    }

    public void setEspeciRam(EspecificacionRam especiRam) {
        this.especiRam = especiRam;
    }

    public EspecificacionCache getEspecificaCache() {
        return especificaCache;
    }

    public void setEspecificaCache(EspecificacionCache especificaCache) {
        this.especificaCache = especificaCache;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiRam = new javax.swing.JLabel();
        etiTamañoBloque = new javax.swing.JLabel();
        etiTotalBloques = new javax.swing.JLabel();
        etiMaxDireccionable = new javax.swing.JLabel();
        etiCache = new javax.swing.JLabel();
        etiCorrespondencia = new javax.swing.JLabel();
        etiTamLinea = new javax.swing.JLabel();
        etiNumLineas = new javax.swing.JLabel();
        etiTamDirecciones = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tlbFormatoCache = new javax.swing.JTable();
        etiReemplazo = new javax.swing.JLabel();
        etiBusDatos = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        comboTipoOperacion = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        comboDireccionamiento = new javax.swing.JComboBox<>();
        etiCampoDireccion = new javax.swing.JLabel();
        txtCampoDireccion = new javax.swing.JTextField();
        etiCampoRegistro = new javax.swing.JLabel();
        txtCampoRegistro = new javax.swing.JTextField();
        etiDatoEscribir = new javax.swing.JLabel();
        txtDatoEscribir = new javax.swing.JTextField();
        btnAgregarPeticion = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tlbPeticiones = new javax.swing.JTable();
        btnProcesar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtFallos = new javax.swing.JTextField();
        txtAciertos = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tlbEjecucion = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tlbPasosSeguidos = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        etiRam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiRam.setText("RAM:");

        etiTamañoBloque.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiTamañoBloque.setText("Tamaño Bloque:");

        etiTotalBloques.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiTotalBloques.setText("Total Bloques:");

        etiMaxDireccionable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiMaxDireccionable.setText("Maximo Direccionable:");

        etiCache.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiCache.setText("CACHE:");

        etiCorrespondencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiCorrespondencia.setText("Correspondencia:");

        etiTamLinea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiTamLinea.setText("Tamaño de Linea:");

        etiNumLineas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiNumLineas.setText("Número de Lineas:");

        etiTamDirecciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiTamDirecciones.setText("Tamaño direcciones:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Formato de direcciones cache");

        tlbFormatoCache.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tlbFormatoCache.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tlbFormatoCache);

        etiReemplazo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiReemplazo.setText("Reemplazo:");

        etiBusDatos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiBusDatos.setText("Bus de datos:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiRam)
                    .addComponent(etiTamañoBloque)
                    .addComponent(etiTotalBloques)
                    .addComponent(etiMaxDireccionable)
                    .addComponent(etiBusDatos))
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiReemplazo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiCache)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(226, 226, 226))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiNumLineas)
                            .addComponent(etiTamLinea)
                            .addComponent(etiCorrespondencia)
                            .addComponent(etiTamDirecciones))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiRam)
                    .addComponent(etiCache)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etiTamañoBloque)
                            .addComponent(etiCorrespondencia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etiTotalBloques)
                            .addComponent(etiTamLinea))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etiMaxDireccionable)
                            .addComponent(etiNumLineas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etiTamDirecciones)
                            .addComponent(etiBusDatos)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiReemplazo))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Peticiones CPU"));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Tipo:");

        comboTipoOperacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboTipoOperacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lectura", "Escritura" }));
        comboTipoOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoOperacionActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Direccionamiento:");

        comboDireccionamiento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboDireccionamiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Directo", "Indirecto con Registro", "Desplazamieto Relativo", "Registro Base", "Indexado" }));
        comboDireccionamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDireccionamientoActionPerformed(evt);
            }
        });

        etiCampoDireccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiCampoDireccion.setText("Campo de Direccion:");

        txtCampoDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCampoDireccionKeyTyped(evt);
            }
        });

        etiCampoRegistro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiCampoRegistro.setText("Campo de Registro:");
        etiCampoRegistro.setEnabled(false);

        txtCampoRegistro.setEnabled(false);
        txtCampoRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCampoRegistroKeyTyped(evt);
            }
        });

        etiDatoEscribir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiDatoEscribir.setText("Dato a escribir:");
        etiDatoEscribir.setEnabled(false);

        txtDatoEscribir.setEnabled(false);
        txtDatoEscribir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDatoEscribirKeyTyped(evt);
            }
        });

        btnAgregarPeticion.setText("Agregar Peticion");
        btnAgregarPeticion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPeticionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAgregarPeticion)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(comboTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(comboDireccionamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(etiDatoEscribir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDatoEscribir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(etiCampoRegistro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCampoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(etiCampoDireccion)
                                .addGap(18, 18, 18)
                                .addComponent(txtCampoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(comboDireccionamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiCampoDireccion)
                    .addComponent(txtCampoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiCampoRegistro)
                    .addComponent(txtCampoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiDatoEscribir)
                    .addComponent(txtDatoEscribir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarPeticion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tlbPeticiones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tlbPeticiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dir.", "Reg.", "Direccionamiento", "Tipo", "Dato"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tlbPeticiones);

        btnProcesar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProcesar.setText("Procesar");
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ejecución"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Aciertos:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Fallos:");

        txtFallos.setEditable(false);

        txtAciertos.setEditable(false);

        tlbEjecucion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tlbEjecucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tlbEjecucion);

        tlbPasosSeguidos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tlbPasosSeguidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pasos Realizados"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tlbPasosSeguidos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAciertos)
                            .addComponent(txtFallos, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtAciertos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtFallos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(btnProcesar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(0, 51, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProcesar)
                            .addComponent(btnEliminar)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarPeticionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPeticionActionPerformed
        int tipoOperacion = comboTipoOperacion.getSelectedIndex();
        int direccionamiento = comboDireccionamiento.getSelectedIndex();
        String direc = txtCampoDireccion.getText();
        String reg = txtCampoRegistro.getText();
        String dato = txtDatoEscribir.getText();
        DefaultTableModel petiModelo = (DefaultTableModel) tlbPeticiones.getModel();
        String mensaje = "";
        Peticion peti = new Peticion();
        peti.setMetodoDireccion(direccionamiento);
        peti.setTipoPeticion(tipoOperacion);

        switch (tipoOperacion) {
            case UtilDireccionamiento.LECTURA:
                if (direccionamiento == UtilDireccionamiento.DIRECTO && !direc.isEmpty()) {
                    peti.setCampoDireccion(direc);
                    petiModelo.addRow(new Object[]{direc, "", comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem()});
                } else if (direccionamiento == UtilDireccionamiento.INDIRECTO_REGISTRO && !reg.isEmpty()) {
                    peti.setCampoRegistro(reg);
                    petiModelo.addRow(new Object[]{"", reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem()});
                } else if (direccionamiento == UtilDireccionamiento.DESPLAZAMIENTO_RELATIVO && !direc.isEmpty() && !reg.isEmpty()) {
                    peti.setCampoDireccion(direc);
                    peti.setCampoRegistro(reg);
                    petiModelo.addRow(new Object[]{direc, reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem()});
                } else if (direccionamiento == UtilDireccionamiento.REGISTRO_BASE && !direc.isEmpty() && !reg.isEmpty()) {
                    peti.setCampoDireccion(direc);
                    peti.setCampoRegistro(reg);
                    petiModelo.addRow(new Object[]{direc, reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem()});
                } else if (direccionamiento == UtilDireccionamiento.INDEXADO && !direc.isEmpty() && !reg.isEmpty()) {
                    peti.setCampoDireccion(direc);
                    peti.setCampoRegistro(reg);
                    petiModelo.addRow(new Object[]{direc, reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem()});
                } else {
                    mensaje = "Debe de completar todos los campos de texto disponibles para editar";
                }
                break;
            case UtilDireccionamiento.ESCRITURA:
                if (!dato.isEmpty()) {
                    if (direccionamiento == UtilDireccionamiento.DIRECTO && !direc.isEmpty()) {
                        peti.setCampoDireccion(direc);
                        peti.setCampoDatoEscribir(dato);
                        petiModelo.addRow(new Object[]{direc, "", comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem(), dato});
                    } else if (direccionamiento == UtilDireccionamiento.INDIRECTO_REGISTRO && !reg.isEmpty()) {
                        peti.setCampoRegistro(reg);
                        peti.setCampoDatoEscribir(dato);
                        petiModelo.addRow(new Object[]{"", reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem(), dato});
                    } else if (direccionamiento == UtilDireccionamiento.DESPLAZAMIENTO_RELATIVO && !direc.isEmpty() && !reg.isEmpty()) {
                        peti.setCampoDireccion(direc);
                        peti.setCampoRegistro(reg);
                        peti.setCampoDatoEscribir(dato);
                        petiModelo.addRow(new Object[]{direc, reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem(), dato});
                    } else if (direccionamiento == UtilDireccionamiento.REGISTRO_BASE && !direc.isEmpty() && !reg.isEmpty()) {
                        peti.setCampoDireccion(direc);
                        peti.setCampoRegistro(reg);
                        peti.setCampoDatoEscribir(dato);
                        petiModelo.addRow(new Object[]{direc, reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem(), dato});
                    } else if (direccionamiento == UtilDireccionamiento.INDEXADO && !direc.isEmpty() && !reg.isEmpty()) {
                        peti.setCampoDireccion(direc);
                        peti.setCampoRegistro(reg);
                        peti.setCampoDatoEscribir(dato);
                        petiModelo.addRow(new Object[]{direc, reg, comboDireccionamiento.getSelectedItem(), comboTipoOperacion.getSelectedItem(), dato});
                    } else {
                        mensaje = "Debe de completar todos los campos de texto disponibles para editar";
                    }
                } else {
                    mensaje = "Ingrese el valor a escribir en el campo correspondiente";
                }

                break;
        }
        if (!mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensaje, "Advertencia!", JOptionPane.WARNING_MESSAGE);
        } else {
            listaPeticiones.add(peti);
            txtCampoDireccion.setText("");
            txtCampoRegistro.setText("");
            txtDatoEscribir.setText("");
        }


    }//GEN-LAST:event_btnAgregarPeticionActionPerformed

    private void comboTipoOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoOperacionActionPerformed
        if (comboTipoOperacion.getSelectedIndex() == UtilDireccionamiento.LECTURA) {
            etiDatoEscribir.setEnabled(false);
            txtDatoEscribir.setEnabled(false);
        } else {
            etiDatoEscribir.setEnabled(true);
            txtDatoEscribir.setEnabled(true);
        }
    }//GEN-LAST:event_comboTipoOperacionActionPerformed

    private void comboDireccionamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDireccionamientoActionPerformed
        switch (comboDireccionamiento.getSelectedIndex()) {
            case UtilDireccionamiento.DIRECTO:
                etiCampoDireccion.setEnabled(true);
                txtCampoDireccion.setEnabled(true);
                etiCampoRegistro.setEnabled(false);
                txtCampoRegistro.setEnabled(false);
                break;
            case UtilDireccionamiento.INDIRECTO_REGISTRO:
                etiCampoRegistro.setEnabled(true);
                txtCampoRegistro.setEnabled(true);
                etiCampoDireccion.setEnabled(false);
                txtCampoDireccion.setEnabled(false);
                break;
            case UtilDireccionamiento.REGISTRO_BASE:
            case UtilDireccionamiento.DESPLAZAMIENTO_RELATIVO:
            case UtilDireccionamiento.INDEXADO:
                etiCampoDireccion.setEnabled(true);
                txtCampoDireccion.setEnabled(true);
                etiCampoRegistro.setEnabled(true);
                txtCampoRegistro.setEnabled(true);
        }
    }//GEN-LAST:event_comboDireccionamientoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        DefaultTableModel tabla = (DefaultTableModel) tlbPeticiones.getModel();
        int[] filas = tlbPeticiones.getSelectedRows();
        for (int fila : filas) {
            for (int i = 0; i < filas.length; i++) {
                filas[i]--;
            }
            tabla.removeRow(fila);
            listaPeticiones.remove(fila);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCampoDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoDireccionKeyTyped
        if (especiRam != null) {
            Validador.validarHexaYDirsAdmitidas(evt, txtCampoDireccion.getText().length(), especiRam.getMaxDireccionable());
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txtCampoDireccionKeyTyped

    private void txtCampoRegistroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoRegistroKeyTyped
        if (especiRam != null) {
            Validador.validarHexaYDirsAdmitidas(evt, txtCampoRegistro.getText().length(), especiRam.getMaxDireccionable());
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txtCampoRegistroKeyTyped

    private void txtDatoEscribirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDatoEscribirKeyTyped
        if (especiRam != null) {
            if (especiRam.getNivelDireccionable() == UnidadMedida.BYTE) {
                Validador.validarHexaYDatosAdmitidos(evt, txtDatoEscribir.getText().length(), 1);
            } else {
                Validador.validarHexaYDatosAdmitidos(evt, txtDatoEscribir.getText().length(), especiRam.getTamañoPalabra());
            }
        } else {
            evt.consume();
        }

    }//GEN-LAST:event_txtDatoEscribirKeyTyped

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarActionPerformed
        if (listaPeticiones.size() > 0) {

            txtAciertos.setText("");
            txtFallos.setText("");

            DefaultTableModel tablaDirecciones = (DefaultTableModel) tlbEjecucion.getModel();
            DefaultTableModel tablaPasos = (DefaultTableModel) tlbPasosSeguidos.getModel();
            for (int i = tlbEjecucion.getRowCount() - 1; i >= 0; i--) {
                tablaDirecciones.removeRow(i);
            }
            for (int i = tlbPasosSeguidos.getRowCount() - 1; i >= 0; i--) {
                tablaPasos.removeRow(i);
            }

            int aciertos = 0;
            int fallos = 0;

            for (Peticion p : listaPeticiones) {
                tablaPasos.addRow(new Object[]{"CPU lanza una Peticion"});
                tablaPasos.addRow(new Object[]{"Calculando Direccion Fisica..."});

                String dir = UtilDireccionamiento.generarDireccionFisica(p);
                detallesFormato(dir, tablaDirecciones); // Detalles de direccion en tabla

                tablaPasos.addRow(new Object[]{"Direccion Fisica Calculada: " + dir});
                tablaPasos.addRow(new Object[]{"Verificando la Cache"});

                String etiqueta = UtilCache.generarEtiqueta(dir, especiRam.getMaxDireccionable(), especificaCache.getFormatEtiqueta());
                long numBloque = UtilCache.generarBloqueMP(dir, especiRam.getMaxDireccionable(), especificaCache.getFormatPalabra());
                int palabra = UtilCache.generarPalabra(dir, especiRam.getMaxDireccionable(), especificaCache.getFormatPalabra());
                String dato = null;

                if (p.getTipoPeticion() == UtilDireccionamiento.ESCRITURA) {
                    RAM.set((int) numBloque * especiRam.getTamañoBloque() + palabra, p.getCampoDatoEscribir());
                }

                switch (especificaCache.getFuncionCorrespondencia()) {
                    case UtilCache.DIRECTA:
                        long numLinea = numBloque % especificaCache.getNumTotalLineas();
                        Linea l = CACHE.get((int) numLinea);
                        if (l.etiqueta != null && l.etiqueta.equals(etiqueta)) {
                            for (int i = 0; i < especiRam.getTamañoBloque(); i++) {
                                l.elementos.set(i, RAM.get((int) numBloque * especiRam.getTamañoBloque() + i));
                            }
                            aciertos++;
                            txtAciertos.setText(String.valueOf(aciertos));
                            tablaPasos.addRow(new Object[]{"Acierto en Cache. Linea: " + numLinea});
                            dato = l.elementos.get(palabra);
                            //tablaPasos.addRow(new Object[]{"Devolviendo dato a CPU: " + dato});
                        } else { //Fallo en la Cache
                            fallos++;
                            txtFallos.setText(String.valueOf(fallos));
                            tablaPasos.addRow(new Object[]{"Fallo en Cache"});
                            tablaPasos.addRow(new Object[]{"Actualizando Cache. Linea: " + numLinea});
                            dato = RAM.get((int) numBloque * especiRam.getTamañoBloque() + palabra);
                            tablaPasos.addRow(new Object[]{"Devolviendo dato a CPU: " + dato});
                            l.etiqueta = etiqueta;
                            for (int i = 0; i < especiRam.getTamañoBloque(); i++) {
                                l.elementos.set(i, RAM.get((int) numBloque * especiRam.getTamañoBloque() + i));
                            }
                        }
                        break;
                    case UtilCache.ASOCIATIVA:
                        int lineaExito = -1;
                        for (int i = 0; i < especificaCache.getNumTotalLineas(); i++) { //Verifiacndo si el dato ya esta en cache
                            Linea li = CACHE.get(i);
                            if (li.etiqueta != null && li.etiqueta.equals(etiqueta)) {
                                lineaExito = i;
                                break;
                            }
                        }
                        if (lineaExito != -1) { //Exito en la cache
                            aciertos++;
                            txtAciertos.setText(String.valueOf(aciertos));
                            tablaPasos.addRow(new Object[]{"Acierto en Cache. Linea: " + lineaExito});
                            tablaPasos.addRow(new Object[]{"Devolviendo dato a CPU. Palabra: " + palabra});
                            dato = CACHE.get(lineaExito).elementos.get(palabra);
                            CALRU.remove(new Integer(lineaExito)); //Eliminar de su posicion actual
                            CALRU.add(lineaExito); //Pasar al mas usado recientemente
                        } else { //NO HAY EXITO EN CACHE
                            fallos++;
                            txtFallos.setText(String.valueOf(fallos));
                            tablaPasos.addRow(new Object[]{"Fallo en Cache"});
                            //pasos.addRow(new Object[]{"Actualizando Cache. Linea: " + numLinea});
                            tablaPasos.addRow(new Object[]{"Devolviendo dato a CPU. Palabra: " + palabra});
                            int numElemEnCache = CAFIFO.size(); //verificar si la cache esta llena
                            if (numElemEnCache < especificaCache.getNumTotalLineas()) { //Hay espacio en cache: Meter dato en una linea vacia
                                tablaPasos.addRow(new Object[]{"Actualizando Cache. Linea: " + numElemEnCache});
                                Linea li = CACHE.get(numElemEnCache);
                                li.etiqueta = etiqueta;
                                for (int i = 0; i < especiRam.getTamañoBloque(); i++) {
                                    li.elementos.set(i, RAM.get((int)numBloque * especiRam.getTamañoBloque() + i));
                                }
                                dato = li.elementos.get(palabra);
                                CAFIFO.add(numElemEnCache);
                                CALRU.add(numElemEnCache); //Usado mas recientemente
                            } else if (especificaCache.getAlgoReemplazo() == UtilCache.LRU) { //Si la Cache esta llena, Usar un algoritmo de Sustitucion
                                int lineaReemplazar = CALRU.get(0);
                                tablaPasos.addRow(new Object[]{"Actualizando Cache. Linea: " + lineaReemplazar});
                                CALRU.remove(0);
                                CALRU.add(lineaReemplazar); //Usado mas recientemente
                                CAFIFO.remove(new Integer(lineaReemplazar)); //Nuevo dato, pasar al ultimo de la cola
                                CAFIFO.add(lineaReemplazar);
                                Linea li = CACHE.get(lineaReemplazar);
                                li.etiqueta = etiqueta;
                                for (int i = 0; i < especiRam.getTamañoBloque(); i++) {
                                    li.elementos.set(i, RAM.get((int)numBloque * especiRam.getTamañoBloque() + i));
                                }
                                dato = li.elementos.get(palabra);
                            } else if (especificaCache.getAlgoReemplazo() == UtilCache.FIFO) {
                                int lineaReemplazar = CAFIFO.get(0);
                                tablaPasos.addRow(new Object[]{"Actualizando Cache. Linea: " + lineaReemplazar});
                                CAFIFO.remove(0);
                                CAFIFO.add(lineaReemplazar); //pasar al final de la cola
                                CALRU.remove(new Integer(lineaReemplazar));
                                CALRU.add(lineaReemplazar); // Nuevo usado mas recientemente
                                Linea li = CACHE.get(lineaReemplazar);
                                li.etiqueta = etiqueta;
                                for (int i = 0; i < especiRam.getTamañoBloque(); i++) {
                                    li.elementos.set(i, RAM.get((int)numBloque * especiRam.getTamañoBloque() + i));
                                }
                                dato = li.elementos.get(palabra);
                            }
                    }
                        break;
                    case UtilCache.POR_CONJUNTO:
                        //Asociativa por conjunto
                        break;
                    
                }
                tablaPasos.addRow(new Object[]{"Dato devuelto al CPU:" + dato});
                tablaPasos.addRow(new Object[]{"-------------------------------------------"});
            }

        } else {
            JOptionPane.showMessageDialog(this, "No hay Peticiones que procesar", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnProcesarActionPerformed

    private void detallesFormato(String dirHexa, DefaultTableModel tabla) {
        String binario = UtilDireccionamiento.direccionEnBinario(dirHexa, especiRam.getMaxDireccionable());
        String etiqueta;
        String linea;
        String conjunto;
        String palabra;

        etiqueta = binario.substring(0, especificaCache.getFormatEtiqueta());
        palabra = binario.substring(especiRam.getMaxDireccionable() - especificaCache.getFormatPalabra());
        switch (especificaCache.getFuncionCorrespondencia()) {
            case UtilCache.DIRECTA:
                linea = binario.substring(especificaCache.getFormatEtiqueta(), especificaCache.getFormatEtiqueta() + especificaCache.getFormatLinea());
                tabla.addRow(new Object[]{binario, etiqueta, linea, palabra});
                tabla.addRow(new Object[]{dirHexa, Long.toHexString(Long.parseLong(etiqueta, 2)), Long.toHexString(Long.parseLong(linea, 2)), Long.toHexString(Long.parseLong(palabra, 2))});
                tabla.addRow(new Object[]{"", "", "", ""});
                break;
            case UtilCache.POR_CONJUNTO:
                conjunto = binario.substring(especificaCache.getFormatEtiqueta(), especificaCache.getFormatEtiqueta() + especificaCache.getFormatConjunto());
                tabla.addRow(new Object[]{binario, etiqueta, conjunto, palabra});
                tabla.addRow(new Object[]{dirHexa, Long.toHexString(Long.parseLong(etiqueta, 2)), Long.toHexString(Long.parseLong(conjunto, 2)), Long.toHexString(Long.parseLong(palabra, 2))});
                tabla.addRow(new Object[]{"", "", "", ""});
                break;
            case UtilCache.ASOCIATIVA:
                tabla.addRow(new Object[]{binario, etiqueta, palabra});
                tabla.addRow(new Object[]{dirHexa, Long.toHexString(Long.parseLong(etiqueta, 2)), Long.toHexString(Long.parseLong(palabra, 2))});
                tabla.addRow(new Object[]{"", "", ""});
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPeticion;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JComboBox<String> comboDireccionamiento;
    private javax.swing.JComboBox<String> comboTipoOperacion;
    private javax.swing.JLabel etiBusDatos;
    private javax.swing.JLabel etiCache;
    private javax.swing.JLabel etiCampoDireccion;
    private javax.swing.JLabel etiCampoRegistro;
    private javax.swing.JLabel etiCorrespondencia;
    private javax.swing.JLabel etiDatoEscribir;
    private javax.swing.JLabel etiMaxDireccionable;
    private javax.swing.JLabel etiNumLineas;
    private javax.swing.JLabel etiRam;
    private javax.swing.JLabel etiReemplazo;
    private javax.swing.JLabel etiTamDirecciones;
    private javax.swing.JLabel etiTamLinea;
    private javax.swing.JLabel etiTamañoBloque;
    private javax.swing.JLabel etiTotalBloques;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tlbEjecucion;
    private javax.swing.JTable tlbFormatoCache;
    private javax.swing.JTable tlbPasosSeguidos;
    private javax.swing.JTable tlbPeticiones;
    private javax.swing.JTextField txtAciertos;
    private javax.swing.JTextField txtCampoDireccion;
    private javax.swing.JTextField txtCampoRegistro;
    private javax.swing.JTextField txtDatoEscribir;
    private javax.swing.JTextField txtFallos;
    // End of variables declaration//GEN-END:variables
}
