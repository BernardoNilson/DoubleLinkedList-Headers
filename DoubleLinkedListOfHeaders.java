/**
 * Esta é a classe DoubleLinkedListOfHeaders do T2 de ALESTI. Leia o README.md para mais informações.
 */
public class DoubleLinkedListOfHeaders {
    // Referencia para o sentinela de inicio da lista encadeada.
    private Node header;
    // Referencia para o sentinela de fim da lista encadeada.
    private Node trailer;
    // Contador do numero de elementos da lista.
    private int count;
    
    private class Node {
        public String element; // primeira palavra que inicia com uma certa letra
        NodePalavra PalavrasComMesmaInicial; // Lista com as demais palavras da letra
        public Node next;
        public Node prev;

        public Node(String e) {
            element = e;
            next = null;
            prev = null;
            PalavrasComMesmaInicial = null;
        }
    }
    // Nodo da lista de palavras
    // Veja o atributo 'PalavrasComMesmaInicial' da classe Node
    private class NodePalavra {
        public String element;
        public NodePalavra next;

        public NodePalavra(String e) {
            element = e;
            next = null;
        }
    }

    public DoubleLinkedListOfHeaders() {
        header = new Node(null);
        trailer = new Node(null);
        header.next =  trailer;
        trailer.prev = header;
        count = 0;
    }

    // Este método insere manualmente algumas palavras na lista PalavrasComMesmaInicial apenas para exemplificar o funcionamento. 
    // ALTERAR
    void addInSublist(Node head, String e)
    {
        NodePalavra n = new NodePalavra(e);

        if (head.PalavrasComMesmaInicial == null){
            head.PalavrasComMesmaInicial = n;
        } else {
            NodePalavra aux = head.PalavrasComMesmaInicial;
            NodePalavra prev = null;
          
            while (aux != null && isSmaller(aux.element, e)){
                prev = aux;
                aux = aux.next;
            }
            
            n.next = aux;
            if (prev != null) prev.next = n;
            else head.PalavrasComMesmaInicial = n;
        }

        if (isSmaller(n.element, head.element)) swapNodes(n, head);
    }
    
    // ALTERAR
    public void addIncreasingOrder(String palavra)
    {
        int i;
        // insere a palavra na lista
        Node aux = header.next; // obtem uma referencia para o 1o. elemento

        for(i=0; i<count; i++) {
            if (aux.element.compareTo(palavra) > 0)
                break;
            aux = aux.next;
        }

        // Primeiro cria o nodo
        Node n = new Node(palavra);
        // Neste ponto 'n' aponta o para o novo nodo 'aux' aponta
        // para a posicao onde deve ser inserida a PALAVRA' 

        // A partir daqui, o CODIGO DEVE SER MODIFICADO para avaliar
        // se eh necessario algum ajuste na lista de palavras que tem
        // a mesma letra inicial que 'palavra'

        Node prev = aux.prev; 
        if (isInitialEqual(palavra, prev.element)) addInSublist(prev, palavra);
        else if (isInitialEqual(palavra, aux.element)) addInSublist(aux, palavra);
        else {
            // Primeiro "gruda" o novo nodo na lista
            n.next = aux;
            n.prev = aux.prev;

            // Atualiza as referencias para apontarem para o novo nodo
            Node ant = aux.prev; 
            ant.next = n;
            aux.prev = n;
        
            // Atualiza o contador
            count++;
        }

        
    }

    public void swapNodes(NodePalavra n, Node head){
        if (n != null && head != null){
            String temp = head.element;
            head.element = n.element;
            n.element = temp;
        }
    }

    // ALTERAR
    public void Remove(String palavra)
    {
        Node aux = findNode(palavra);
        System.out.println("preciso remover " + palavra + " e encontrei "+aux.element);
        if (aux != null){
            if (aux.element.equals(palavra)) removeNode(aux);
            else if (aux.PalavrasComMesmaInicial != null){
                NodePalavra auxiliar = aux.PalavrasComMesmaInicial;
                NodePalavra ant = null;

                if (auxiliar.element.equals(palavra)) aux.PalavrasComMesmaInicial = auxiliar.next;
                else {
                    while(auxiliar != null){
                        if (auxiliar.element.equals(palavra)){
                            ant.next = auxiliar.next;
                            break;
                        }
                        ant = auxiliar;
                        auxiliar = auxiliar.next;
                    }
                }
            }
            
        }
    }

    public void removeNode(Node n){
        Node ant = n.prev;
        Node prox = n.next;
        NodePalavra head = n.PalavrasComMesmaInicial;

        if (head != null){
            Node temp = new Node(head.element);
            temp.PalavrasComMesmaInicial = head.next;

            temp.next = prox;
            temp.prev = ant;
            ant.next = temp;
            prox.prev = temp;
            System.out.println("passei o nodo palavra pra frente " + temp.element);
        } else {
            ant.next = prox;
            prox.prev = ant;
            count--;
        }
    }

    public Node findNode(String word){
        Node aux = header.next;
        while(aux != null){
            if (isInitialEqual(word, aux.element)) return aux;
            aux = aux.next;
        }
        return null;
    }

    boolean isInitialEqual(String a, String b){
        if ((a == null) || b == null) return false;

        return a.charAt(0) == b.charAt(0);
    }

    boolean isSmaller(String a, String b){
        if ((a == null) || b == null) return false;

        return a.compareTo(b) < 0;
    }
    
    // NÃO ALTERAR A PARTIR DAQUI

    /**
     * Retorna true se a lista não contem elementos
     * @return true se a lista não contem elementos
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    // NÃO ALTERAR
    String geraHash()
    {
        HashCreator hash = new HashCreator();
        
        String s = hash.generateMD5(toString());    
        return s;
    }

    // NÃO ALTERAR
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        Node aux = header.next;
        for (int i = 0; i < count; i++) {
            s.append("**  " + aux.element.toString());
      
            NodePalavra aux2 = aux.PalavrasComMesmaInicial;
            if (aux2 != null)
                s.append(", ");
            else s.append("\n");
            while(aux2 != null)
            {
                s.append(aux2.element.toString());
                if (aux2.next != null)
                    s.append(", ");
                else s.append("\n");
                aux2 = aux2.next;
            }
            aux = aux.next;
        }
        return s.toString();
    }    
    
    // NÃO ALTERAR
    void Imprime(String s)
    {
        System.out.print(s);
    }
    
    // NÃO ALTERAR
    void ImprimeLista()
    {
        Imprime("\n**********************\n");
        Imprime("A Lista:\n");
        Imprime(toString());
        Imprime("HASH: " + geraHash()+ "\n");
    }

    // *********************************************
    // Deste ponto em diante, o código serve para gerar codigo DOT
    // NAO ALTERE
    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    public void GeraDOT() 
    {
        Imprime("digraph g { \nnode [shape = record,height=.1];\n" + "\n");

        Imprime("rankdir=LR;\n");
        GeraNodosDOT();
        GeraConexoesDOT();
        
        Imprime("}" + "\n");
    }
    public void GeraNodosDOT()
    {
        String S1, S2;
        S1 = "{rank = same;";
        S2 = "{rank = same;";

        Node aux = header.next;
        Imprime("node" + "HEADER" + "[label= \" " + "HEADER" + " | <next> \"]" + "\n");

        for(int i=0; i<count; i++) 
        {
            //Imprime("node" + aux.element + "[label= \" " + aux.element + " | <next> \"]" + "\n");
            Imprime("node" + aux.element + "[label= \" " + aux.element + " | <pal> | <next>\"]" + "\n");
            NodePalavra aux2 = aux.PalavrasComMesmaInicial;
            while(aux2 != null)
            {
                Imprime("node" + aux2.element + "[label= \" " + aux2.element + " | <next>\"]" + "\n");            
                aux2 = aux2.next;
            }
            S1 = S1 + "node" + aux.element + ";";
            aux = aux.next;
        } 
        Imprime (S1+"}\n");

    }
    public void GeraConexoesDOT()
    {
        Node aux = header.next;
        Imprime("\"node" + "HEADER" + "\":next -> \"node" + aux.element + "\" " + "\n");

            // "node150":esq -> "node23"
        for(int i=0; i<count; i++) 
        {
            if (aux.next != trailer)
                Imprime("\"node" + aux.element + "\":next -> \"node" + aux.next.element + "\" " + "\n");
            NodePalavra aux2 = aux.PalavrasComMesmaInicial;
            if (aux.PalavrasComMesmaInicial != null)
            {
                Imprime("subgraph cluster" + i + " {\n");
                Imprime("\t");
                Imprime("\"node" + aux.element + "\":pal -> \"node" + aux2.element + "\" " + "\n");
                //aux2 = aux2.next;
                while(aux2 != null)
                {
                    if (aux2.next != null)
                        Imprime("\t\"node" + aux2.element + "\":next -> \"node" + aux2.next.element + "\" " + "\n");
                    aux2 = aux2.next;
                }
                Imprime("\tstyle= invis }\n");
            }
            
            aux = aux.next;
        }
    }
}
